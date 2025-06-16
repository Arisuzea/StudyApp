package studyapp.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import studyapp.model.Question;
import studyapp.model.Quiz;
import studyapp.util.UserAnswerDAO;
import studyapp.util.DatabaseManager;
import studyapp.util.Session;
import studyapp.util.UIUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class QuizTakingController {
    @FXML private Label questionLabel;
    @FXML private VBox optionContainer;
    @FXML private Button nextButton;
    @FXML private Label timerLabel;

    private Quiz quiz;
    private List<Question> questions;
    private int currentIndex = 0;
    private Map<Integer, Character> userAnswers = new HashMap<>();

    private Timeline countdown;
    private int timeRemaining; // in seconds

    // Begins a quiz session with the provided quiz and questions
    public void beginQuizSession(Quiz quiz, List<Question> questions) {
        this.quiz = quiz;
        this.questions = questions;

        int totalMinutes = Math.min(questions.size() * 5, 120); // 5 min per item, max 2h
        this.timeRemaining = totalMinutes * 60;

        startTimer();
        loadCurrentQuestion();
    }

    // Starts the countdown timer for the quiz
    private void startTimer() {
        countdown = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeRemaining--;
            int mins = timeRemaining / 60;
            int secs = timeRemaining % 60;
            timerLabel.setText(String.format("Time Left: %02d:%02d", mins, secs));

            if (timeRemaining <= 0) {
                countdown.stop();
                submitQuiz();
            }
        }));
        countdown.setCycleCount(Timeline.INDEFINITE);
        countdown.play();
    }

    // Loads the current question and its options into the UI
    private void loadCurrentQuestion() {
        if (currentIndex >= questions.size()) {
            submitQuiz();
            return;
        }

        Question q = questions.get(currentIndex);
        questionLabel.setText("Q" + (currentIndex + 1) + ": " + q.getQuestionText());

        optionContainer.getChildren().clear();
        ToggleGroup group = new ToggleGroup();

        char option = 'A';
        for (String opt : q.getOptions()) {
            RadioButton rb = new RadioButton(option + ". " + opt);
            rb.setToggleGroup(group);
            char finalOption = option;
            rb.setOnAction(e -> userAnswers.put(q.getId(), finalOption));
            optionContainer.getChildren().add(rb);
            option++;
        }

        nextButton.setText((currentIndex == questions.size() - 1) ? "Submit" : "Next");
    }

    // Advances to the next question or submits the quiz if at the end
    @FXML
    private void nextQuestion() {
        currentIndex++;
        loadCurrentQuestion();
    }

    // Submits the quiz, saves answers, and shows the result popup
    private void submitQuiz() {
        countdown.stop();
        int correctCount = 0;

        try {
            int userId = Session.getLoggedInUser().getId();
            int progressId = UserAnswerDAO.resetAndCreateProgress(userId, quiz.getId());


            for (Question q : questions) {
                char selectedChar = userAnswers.getOrDefault(q.getId(), ' ');
                int selectedOptionId = getOptionId(q.getId(), selectedChar);
                boolean isCorrect = selectedChar == q.getCorrectOption();

                if (isCorrect) correctCount++;

                UserAnswerDAO.saveAnswer(progressId, q.getId(),
                    selectedOptionId > 0 ? selectedOptionId : null,
                    isCorrect);
            }

            UserAnswerDAO.completeProgress(progressId, correctCount);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/User - QuizPopup.fxml"));
            Parent popupRoot = loader.load();

            QuizResultPopupController controller = loader.getController();
            controller.setScore(correctCount, questions.size());

            Stage popupStage = new Stage();
            UIUtil.applyAppIcon(popupStage);
            popupStage.setScene(new Scene(popupRoot));
            popupStage.setResizable(false);

            Stage quizStage = (Stage) nextButton.getScene().getWindow();
            controller.setQuizStage(quizStage); // Pass quiz window to popup

            popupStage.initStyle(javafx.stage.StageStyle.UNDECORATED);
            popupStage.initOwner(quizStage);
            popupStage.initModality(javafx.stage.Modality.WINDOW_MODAL);
            popupStage.setAlwaysOnTop(true);
            popupStage.show();
            popupStage.setX(quizStage.getX() + (quizStage.getWidth() - popupStage.getWidth()) / 2);
            popupStage.setY(quizStage.getY() + (quizStage.getHeight() - popupStage.getHeight()) / 2);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Retrieves the option ID for a given question and label
    private int getOptionId(int questionId, char label) throws SQLException {
        String sql = "SELECT id FROM options WHERE question_id = ? AND option_label = ?";
        try (Connection conn = DatabaseManager.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, questionId);
            stmt.setString(2, String.valueOf(label));
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt("id") : -1;
        }
    }
}
