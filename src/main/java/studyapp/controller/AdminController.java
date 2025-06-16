package studyapp.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import studyapp.util.UIUtil;


public class AdminController {

    @FXML private Button btnCreateLessons;
    @FXML private Button btnCreateQuizzes;
    @FXML private Button btnUserProgress;
    @FXML private Button btnLogout;  // Added logout button

    @FXML private StackPane contentPane;
    @FXML private Label welcomeLabel;
    @FXML private Label lblTitle;

    @FXML
    public void initialize() {
        btnCreateLessons.setOnAction(e -> loadCreateLessons());
        btnCreateQuizzes.setOnAction(e -> loadCreateQuizzes());
        btnUserProgress.setOnAction(e -> showProgress());
        lblTitle.setOnMouseClicked(e -> resetToMainContent());

        btnLogout.setOnAction(e -> logout());
    }

    private void loadCreateLessons() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Admin - CreateLessons.fxml"));
            Parent createLessonsView = loader.load();
            contentPane.getChildren().setAll(createLessonsView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCreateQuizzes() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Admin - CreateQuizzes.fxml"));
            Parent createQuizzesView = loader.load();
            contentPane.getChildren().setAll(createQuizzesView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showProgress() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Admin - UserProgress.fxml"));
            Parent userProgressView = loader.load();
            contentPane.getChildren().setAll(userProgressView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void resetToMainContent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Admin - Dashboard.fxml"));
            Parent dashboardView = loader.load();
            contentPane.getChildren().setAll(dashboardView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logout() {
        try {
            // Load login FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/General - Login.fxml"));
            Parent loginView = loader.load();

            // Get current stage and close it
            Stage currentStage = (Stage) btnLogout.getScene().getWindow();
            currentStage.close();

            // Open a new stage for login
            Stage loginStage = new Stage();
            UIUtil.applyAppIcon(loginStage);
            Scene loginScene = new Scene(loginView);
            loginScene.getStylesheets().add(getClass().getResource("/css/General - Login.css").toExternalForm());

            loginStage.setTitle("StudyApp - Login");
            loginStage.setScene(loginScene);
            loginStage.setMaximized(true);
            loginStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
