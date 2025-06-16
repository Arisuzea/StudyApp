package studyapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class QuizResultPopupController {

    @FXML private Label titleLabel;
    @FXML private Label scoreLabel;
    @FXML private Label messageLabel;
    @FXML private Button closeButton;

    private Stage quizStage; // Reference to the main quiz window

    public void setScore(int score, int total) {
        titleLabel.setText("Well Done! You did it!");
        scoreLabel.setText("You scored: " + score + " / " + total);
        messageLabel.setText("Great work! You can check your progress on all and other quizzes from the dashboard.");
    }

    public void setQuizStage(Stage quizStage) {
        this.quizStage = quizStage;
    }

    @FXML
    private void closePopup() {
        Stage popupStage = (Stage) closeButton.getScene().getWindow();
        popupStage.close();

        if (quizStage != null) {
            quizStage.close();
        }
    }
}
