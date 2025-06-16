package studyapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert;
import studyapp.model.QuizProgress;
import studyapp.model.User;
import studyapp.util.ProgressDAO;
import studyapp.util.Session;

import java.util.List;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserOwnProgressController {
    @FXML private Label userNameLabel;
    @FXML private FlowPane progressCardContainer;
    @FXML private Button btnViewDetails;

    private final DateTimeFormatter dbFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy h:mm a");

    // Initializes the user's own progress view and sets up event handlers
    @FXML
    public void initialize() {
        User currentUser = Session.getLoggedInUser();
        if (currentUser != null) {
            userNameLabel.setText("Progress for " + currentUser.getUsername());
            loadProgressForUser(currentUser.getId());
        }

        btnViewDetails.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initOwner(btnViewDetails.getScene().getWindow());
            alert.setTitle("StudyApp - OOP Learning");
            alert.setHeaderText(null);
            alert.setContentText("This feature is under development, after it is implemented, users will be able to view detailed progress for each quiz they've taken.\n\nStay tuned, we wish to deliver it to you soon!");
            alert.showAndWait();
            // Manual centering
            Stage stage = (Stage) btnViewDetails.getScene().getWindow();
            alert.setX(stage.getX() + (stage.getWidth() - alert.getWidth()) / 2);
            alert.setY(stage.getY() + (stage.getHeight() - alert.getHeight()) / 2);
        });
    }

    // Loads quiz progress for the current user
    private void loadProgressForUser(int userId) {
        List<QuizProgress> progressList = ProgressDAO.getProgressByUser(userId);
        displayProgressCards(progressList);
    }

    // Displays progress cards for each quiz attempt
    private void displayProgressCards(List<QuizProgress> progressList) {
        progressCardContainer.getChildren().clear();

        for (QuizProgress progress : progressList) {
            VBox card = new VBox(8);
            card.getStyleClass().add("progress-card");

            Label quizLabel = new Label("📘 Quiz: " + progress.getQuizTitle());
            int percentage = (int) ((progress.getScore() / (double) progress.getTotalQuestions()) * 100);
            Label scoreLabel = new Label("✅ Score: " + percentage + "%");
            Label statusLabel = new Label("📝 Status: " + progress.getStatus());

            // Format the date
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
