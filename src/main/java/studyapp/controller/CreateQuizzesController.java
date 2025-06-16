package studyapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import studyapp.model.Quiz;
import studyapp.util.QuizDAO;
import studyapp.util.UIUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javafx.event.EventHandler;

public class CreateQuizzesController {

    @FXML
    private FlowPane quizContainer;

    @FXML
    private void initialize() {
        refreshQuizList();
    }

    // Extracted method to refresh the quiz cards
    private void refreshQuizList() {
        quizContainer.getChildren().clear();
        addCreateQuizCard();
        loadQuizCards();
    }

    private void addCreateQuizCard() {
        VBox card = createCard("+", "#2c3e50", event -> openQuizCreationWindow());
        quizContainer.getChildren().add(card);
    }

    private void openQuizCreationWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Admin - QuizCreation.fxml"));
            Parent root = loader.load();

            // Get controller and set onRefresh callback
            QuizCreationController controller = loader.getController();
            controller.setOnRefresh(this::refreshQuizList);

            Stage stage = new Stage();
            UIUtil.applyAppIcon(stage);
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/Admin - QuizCreation.css").toExternalForm());

            stage.setScene(scene);
            stage.setTitle("StudyApp - Create New Quiz");
            stage.setMaximized(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadQuizCards() {
        try {
            List<Quiz> quizzes = new QuizDAO().getAllQuizzes(); // Make sure this returns oldest first
            for (Quiz quiz : quizzes) {
                VBox quizCard = createCard(quiz.getTitle(), "#2c3e50", event -> openQuizDetailsWindow(quiz));
                quizContainer.getChildren().add(quizCard);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private VBox createCard(String text, String color, EventHandler<MouseEvent> clickHandler) {
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setPrefSize(243, 243);
        box.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 2, 2);");

        Label label = new Label(text);
        label.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");

        box.getChildren().add(label);
        if (clickHandler != null) box.setOnMouseClicked(clickHandler);
        return box;
    }

    private void openQuizDetailsWindow(Quiz quiz) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Admin - QuizCreation.fxml"));
            Parent root = loader.load();

            QuizCreationController controller = loader.getController();
            controller.loadQuizForEditing(quiz.getId(), quiz.getTitle(), quiz.getDescription());

            // Set auto-refresh callback on edit window as well
            controller.setOnRefresh(this::refreshQuizList);

            Stage stage = new Stage();
            UIUtil.applyAppIcon(stage);
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/Admin - QuizCreation.css").toExternalForm());

            stage.setScene(scene);
            stage.setTitle("StudyApp - Edit Quiz");
            stage.setMaximized(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
