package studyapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import studyapp.model.QuizAttemptDetail;
import studyapp.util.DatabaseManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class QuizAttemptDetailController {
    @FXML private Label quizTitleLabel;
    @FXML private Label userInfoLabel;
    @FXML private VBox questionContainer;

    private int userId;
    private int quizId;
    private String quizTitle;
    private String dateTaken;
    private int score;

    private final DateTimeFormatter dbFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final DateTimeFormatter displayFormat = DateTimeFormatter.ofPattern("MMM dd, yyyy h:mm a");

    public void setAttemptInfo(int userId, int quizId, String quizTitle, String dateTaken, int score) {
        this.userId = userId;
        this.quizId = quizId;
        this.quizTitle = quizTitle;
        this.dateTaken = dateTaken;
        this.score = score;

        quizTitleLabel.setText("Quiz: " + quizTitle);

        String formattedDate;
        try {
            LocalDateTime parsedDate = LocalDateTime.parse(dateTaken, dbFormat);
            formattedDate = parsedDate.format(displayFormat);
        } catch (Exception e) {
            formattedDate = dateTaken;
        }

        userInfoLabel.setText("Taken on: " + formattedDate + " • Score: " + score + "%");
        loadAttemptDetails();
    }

    private void loadAttemptDetails() {
        List<QuizAttemptDetail> attempts = new ArrayList<>();

        String sql = """
            SELECT 
                q.question_text,
                q.correct_option AS correct_label,
                co.option_text AS correct_text,
                so.option_label AS selected_label,
                so.option_text AS selected_text
            FROM questions q
            LEFT JOIN options co ON co.question_id = q.id AND co.option_label = q.correct_option
            LEFT JOIN user_question_answers a ON a.question_id = q.id
            LEFT JOIN user_quiz_progress p ON a.progress_id = p.id
            LEFT JOIN options so ON so.id = a.selected_option
            WHERE p.user_id = ? AND p.quiz_id = ?
        """;

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, quizId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                attempts.add(new QuizAttemptDetail(
                    rs.getString("question_text"),
                    rs.getString("correct_label"),
                    rs.getString("selected_label"),
                    rs.getString("correct_text"),
                    rs.getString("selected_text")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        displayAttemptDetails(attempts);
    }

    private void displayAttemptDetails(List<QuizAttemptDetail> details) {
        questionContainer.getChildren().clear();
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double webViewWidth = Math.min(screenWidth * 0.8, 1000);

        int qNum = 1;
        for (QuizAttemptDetail detail : details) {
            VBox card = new VBox(8);
            card.getStyleClass().add("question-card");
            card.setStyle("-fx-padding: 15; -fx-background-color: #f9f9f9; -fx-background-radius: 8; -fx-border-color: #ccc; -fx-border-radius: 8;");

            Label questionLabel = new Label("Q" + qNum + ":");
            questionLabel.setStyle("-fx-font-weight: bold;");

            WebView questionText = new WebView();
            questionText.getEngine().loadContent(detail.getQuestionText(), "text/html");
            questionText.setPrefHeight(300);
            questionText.setPrefWidth(webViewWidth);
            questionText.setContextMenuEnabled(false);

            String selectedDisplay = detail.getSelectedOptionLabel() != null
                    ? detail.getSelectedOptionLabel() + ". " + detail.getSelectedOptionText()
                    : "—";
            String correctDisplay = detail.getCorrectOptionLabel() != null
                    ? detail.getCorrectOptionLabel() + ". " + detail.getCorrectOptionText()
                    : "—";

            Label selected = new Label("Your answer: " + selectedDisplay);
            Label correct = new Label("Correct answer: " + correctDisplay);

            boolean isCorrect = detail.getCorrectOptionLabel() != null &&
                                detail.getCorrectOptionLabel().equalsIgnoreCase(detail.getSelectedOptionLabel());
            selected.setTextFill(isCorrect ? Color.FORESTGREEN : Color.CRIMSON);

            card.getChildren().addAll(questionLabel, questionText, selected, correct);
            questionContainer.getChildren().add(card);

            qNum++;
        }
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) questionContainer.getScene().getWindow();
        stage.close();
    }
}
