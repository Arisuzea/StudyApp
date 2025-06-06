package studyapp.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class AdminController {

    @FXML private Button btnLessons;
    @FXML private Button btnQuizzes;
    @FXML private Button btnProgress;
    @FXML private StackPane contentPane;
    @FXML private Label welcomeLabel;
    @FXML private Label lblTitle;

    @FXML
    public void initialize() {
        btnLessons.setOnAction(e -> loadCreateLessons());
        btnQuizzes.setOnAction(e -> showQuizzes());
        btnProgress.setOnAction(e -> showProgress());
        lblTitle.setOnMouseClicked(e -> resetToMainContent());
    }

    private void loadCreateLessons() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CreateLessons.fxml"));
            Parent createLessonsView = loader.load();
            contentPane.getChildren().setAll(createLessonsView);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
