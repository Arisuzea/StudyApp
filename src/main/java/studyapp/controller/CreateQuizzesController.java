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

import java.io.IOException;

import javafx.event.EventHandler;

public class CreateQuizzesController {

    @FXML
    private FlowPane quizContainer;

    @FXML
    private void initialize() {
        addCreateQuizCard();
        loadQuizCards();
    }

    private void addCreateQuizCard() {
        VBox card = createCard("+", "#2c3e50", event -> openQuizCreationWindow());
        quizContainer.getChildren().add(card);
    }

    private void openQuizCreationWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/QuizCreation.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/QuizCreation.css").toExternalForm());

            stage.setScene(scene);
            stage.setTitle("StudyApp - Create New Quiz");
            stage.setMaximized(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadQuizCards() {
        for (int i = 0; i < 5; i++) {
            VBox quizCard = createCard("Quiz " + (i + 1), "#2c3e50", null);
            quizContainer.getChildren().add(quizCard);
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
}
