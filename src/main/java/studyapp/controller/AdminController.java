package studyapp.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class AdminController {

    @FXML private Button btnCreateLessons;
    @FXML private Button btnCreateQuizzes;
    @FXML private Button btnUserProgress;
    @FXML private StackPane contentPane;
    @FXML private Label welcomeLabel;
    @FXML private Label lblTitle;

    @FXML
    public void initialize() {
        btnCreateLessons.setOnAction(e -> loadCreateLessons());
        btnCreateQuizzes.setOnAction(e -> loadCreateQuizzes());
        btnUserProgress.setOnAction(e -> showProgress());
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

    private void loadCreateQuizzes() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CreateQuizzes.fxml"));
            Parent createQuizzesView = loader.load();
            contentPane.getChildren().setAll(createQuizzesView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showProgress() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UserProgress.fxml"));
        Parent userProgressView = loader.load();
        contentPane.getChildren().setAll(userProgressView);
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    private void resetToMainContent() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminDashboard.fxml"));
        Parent userProgressView = loader.load();
        contentPane.getChildren().setAll(userProgressView);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}

