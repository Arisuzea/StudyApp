package studyapp.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import studyapp.util.UIUtil;

public class UserController {
    @FXML private Button btnLessons;
    @FXML private Button btnQuizzes;
    @FXML private Button btnProgress;
    @FXML private StackPane contentPane;
    @FXML private Label welcomeLabel;
    @FXML private Label lblTitle;
    @FXML private Button btnLogout;

    // Initializes the user dashboard and sets up event handlers
    @FXML
    public void initialize() {
        btnLessons.setOnAction(e -> showLessons());
        btnQuizzes.setOnAction(e -> showQuizzes());
        btnProgress.setOnAction(e -> showProgress());
        lblTitle.setOnMouseClicked(e -> resetToMainContent());
        btnLogout.setOnAction(e -> logout());
    }

    // Loads the lessons view for the user
    private void showLessons() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/User - Lessons.fxml"));
            Parent lessonsView = loader.load();
            UserLessonsController controller = loader.getController();
            controller.setContentPane(contentPane);
            contentPane.getChildren().setAll(lessonsView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Loads the quizzes view for the user
    private void showQuizzes() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/User - Quizzes.fxml"));
            Parent quizzesView = loader.load();
            contentPane.getChildren().setAll(quizzesView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Loads the progress view for the user
    private void showProgress() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/User - UserProgress.fxml"));
            Parent progressView = loader.load();
            contentPane.getChildren().setAll(progressView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Resets the content pane to the main dashboard view
    private void resetToMainContent() {
        welcomeLabel.setText("Welcome Admin!");
    }

    // Logs out the user and returns to the login screen
    private void logout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/General - Login.fxml"));
            Parent loginView = loader.load();
            Stage currentStage = (Stage) btnLogout.getScene().getWindow();
            currentStage.close();
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
