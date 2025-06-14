package studyapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class UserController {

    @FXML private Button btnLessons;
    @FXML private Button btnQuizzes;
    @FXML private Button btnProgress;
    @FXML private StackPane contentPane;
    @FXML private Label welcomeLabel;
    @FXML private Label lblTitle;

    @FXML
    public void initialize() {
        btnLessons.setOnAction(e -> showLessons());
        btnQuizzes.setOnAction(e -> showQuizzes());
        btnProgress.setOnAction(e -> showProgress());
        lblTitle.setOnMouseClicked(e -> resetToMainContent());
    }

    private void showLessons() {
        welcomeLabel.setText("Lessons will be here.");
    }

    private void showQuizzes() {
        welcomeLabel.setText("Quizzes will be here.");
    }

    private void showProgress() {
        welcomeLabel.setText("Progress will be here.");
    }
    private void resetToMainContent() {
        welcomeLabel.setText("Welcome Admin!");
}
}
