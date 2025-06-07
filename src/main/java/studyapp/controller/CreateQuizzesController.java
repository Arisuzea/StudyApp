package studyapp.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
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
        VBox card = createCard("+", "#3b82f6", event -> {
            System.out.println("Create new quiz clicked");
        });
        quizContainer.getChildren().add(card);
    }

    private void loadQuizCards() {
        for (int i = 0; i < 5; i++) {
            VBox quizCard = createCard("Quiz " + (i + 1), "#60a5fa", null);
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
