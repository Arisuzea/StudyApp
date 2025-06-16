package studyapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import studyapp.model.QuizProgress;
import studyapp.model.User;
import studyapp.util.ProgressDAO;
import studyapp.util.Session;

import java.util.List;

public class UserOwnProgressController {

    @FXML private Label userNameLabel;
    @FXML private FlowPane progressCardContainer;
    @FXML private Button btnViewDetails;
    

    @FXML
    public void initialize() {
        User currentUser = Session.getLoggedInUser();
        if (currentUser != null) {
            userNameLabel.setText("Progress for " + currentUser.getUsername());
            loadProgressForUser(currentUser.getId());
        }

        btnViewDetails.setOnAction(e -> {
            // Placeholder: Replace with your own detail logic or navigation
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("StudyApp - OOP Learning");
            alert.setHeaderText(null);
            alert.setContentText("This feature is under development, after it is implemented, users will be able to view detailed progress for each quiz they've taken.\n\nStay tuned, we wish to deliver it to you soon!");
            alert.showAndWait();
        });
    }

    private void loadProgressForUser(int userId) {
        List<QuizProgress> progressList = ProgressDAO.getProgressByUser(userId);
        displayProgressCards(progressList);
    }

    private void displayProgressCards(List<QuizProgress> progressList) {
        progressCardContainer.getChildren().clear();

        for (QuizProgress progress : progressList) {
            VBox card = new VBox(8);
            card.getStyleClass().add("progress-card");

            Label quizLabel = new Label("📘 Quiz: " + progress.getQuizTitle());
            int percentage = (int) ((progress.getScore() / (double) progress.getTotalQuestions()) * 100);
            Label scoreLabel = new Label("✅ Score: " + percentage + "%");
            Label statusLabel = new Label("📝 Status: " + progress.getStatus());
            Label dateLabel = new Label("📅 Date: " + progress.getDateTaken());

            // Set emoji-compatible font
            quizLabel.setStyle("-fx-font-family: 'Segoe UI Emoji';");
            scoreLabel.setStyle("-fx-font-family: 'Segoe UI Emoji';");
            statusLabel.setStyle("-fx-font-family: 'Segoe UI Emoji';");
            dateLabel.setStyle("-fx-font-family: 'Segoe UI Emoji';");

            card.getChildren().addAll(quizLabel, scoreLabel, statusLabel, dateLabel);
            progressCardContainer.getChildren().add(card);
        }
    }
}
