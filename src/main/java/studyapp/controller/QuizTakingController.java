package studyapp.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import studyapp.model.Question;
import studyapp.model.Quiz;
import studyapp.util.UserAnswerDAO;
import studyapp.util.DatabaseManager;
import studyapp.util.ProgressDAO;
import studyapp.util.Session;

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

    public void beginQuizSession(Quiz quiz, List<Question> questions) {
        this.quiz = quiz;
        this.questions = questions;

        int totalMinutes = Math.min(questions.size() * 5, 120); // 5 min per item, max 2h
        this.timeRemaining = totalMinutes * 60;

        startTimer();
        loadCurrentQuestion();
    }

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

    @FXML
    private void nextQuestion() {
        currentIndex++;
        loadCurrentQuestion();
    }

    private void submitQuiz() {
        countdown.stop();
        int correctCount = 0;

        try {
            int userId = Session.getLoggedInUser().getId();
            int progressId = UserAnswerDAO.getOrCreateProgressId(userId, quiz.getId());

            for (Question q : questions) {
                char selectedChar = userAnswers.getOrDefault(q.getId(), ' ');
                int selectedOptionId = getOptionId(q.getId(), selectedChar); // You need this
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

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Quiz Complete");
        alert.setHeaderText("You scored: " + correctCount + "/" + questions.size());
        alert.setContentText("Your answers have been recorded.");
        alert.showAndWait();

        nextButton.getScene().getWindow().hide();
    }

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
