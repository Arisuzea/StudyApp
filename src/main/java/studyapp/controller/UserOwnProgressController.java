package studyapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import studyapp.model.QuizProgress;
import studyapp.model.User;
import studyapp.util.ProgressDAO;
import studyapp.util.Session;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UserOwnProgressController {
    @FXML private Label userNameLabel;
    @FXML private FlowPane progressCardContainer;

    private final DateTimeFormatter dbFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy h:mm a");

    @FXML
    public void initialize() {
        User currentUser = Session.getLoggedInUser();
        if (currentUser != null) {
            userNameLabel.setText("Progress for " + currentUser.getUsername());
            loadProgressForUser(currentUser.getId());
        }
    }

    private void loadProgressForUser(int userId) {
        List<QuizProgress> progressList = ProgressDAO.getProgressByUser(userId);
        displayProgressCards(progressList);
    }

    private void displayProgressCards(List<QuizProgress> progressList) {
        progressCardContainer.getChildren().clear();
        User currentUser = Session.getLoggedInUser();

        for (QuizProgress progress : progressList) {
            VBox card = new VBox(8);
            card.getStyleClass().add("progress-card");
            card.setStyle("-fx-padding: 12; -fx-background-color: #f5f5f5; -fx-background-radius: 10;");

            Label quizLabel = new Label("📘 Quiz: " + progress.getQuizTitle());
            int percentage = (int) ((progress.getScore() / (double) progress.getTotalQuestions()) * 100);
            Label scoreLabel = new Label("✅ Score: " + percentage + "%");
            Label statusLabel = new Label("📝 Status: " + progress.getStatus());

            String formattedDate = "—";
            String rawDate = progress.getDateTaken();
            if (rawDate != null && !rawDate.isEmpty()) {
                try {
                    LocalDateTime parsed = LocalDateTime.parse(rawDate, dbFormatter);
                    formattedDate = parsed.format(dateFormatter);
                } catch (Exception e) {
                    formattedDate = "Invalid Date";
                }
            }
            Label dateLabel = new Label("📅 Date: " + formattedDate);

            // View Details button
            Button detailsBtn = new Button("View Details");
            detailsBtn.setOnAction(e -> openAttemptDetail(progress, currentUser.getId()));

            // Set emoji-compatible font
            String fontStyle = "-fx-font-family: 'Segoe UI Emoji';";
            quizLabel.setStyle(fontStyle);
            scoreLabel.setStyle(fontStyle);
            statusLabel.setStyle(fontStyle);
            dateLabel.setStyle(fontStyle);
            detailsBtn.setStyle("-fx-background-color: #34495e; -fx-text-fill: white;");

            card.getChildren().addAll(quizLabel, scoreLabel, statusLabel, dateLabel, detailsBtn);
            progressCardContainer.getChildren().add(card);
        }
    }

    private void openAttemptDetail(QuizProgress progress, int userId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/User - QuizAttemptDetail.fxml"));
            Parent root = loader.load();

            QuizAttemptDetailController controller = loader.getController();
            controller.setAttemptInfo(
                userId,
                progress.getQuizId(),
                progress.getQuizTitle(),
                progress.getDateTaken(),
                (int) ((progress.getScore() / (double) progress.getTotalQuestions()) * 100)
            );

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(javafx.stage.StageStyle.UNDECORATED); // ❗ This removes title bar and buttons
            stage.setTitle("Quiz Attempt Detail");

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setMaximized(true); // or setWidth/setHeight if needed
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
