package studyapp.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/User - Lessons.fxml"));
        Parent lessonsView = loader.load();

        // Inject the contentPane into UserLessonsController
        UserLessonsController controller = loader.getController();
        controller.setContentPane(contentPane);

        contentPane.getChildren().setAll(lessonsView);
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
