package studyapp.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.event.EventHandler;

public class CreateLessonsController {

    @FXML
    private FlowPane lessonContainer;

    @FXML
    private void initialize() {
        addCreateLessonCard();
        loadLessonCards();
    }

    private void addCreateLessonCard() {
        VBox card = createCard("+", "#2c3e50", event -> {
            System.out.println("Create new lesson clicked");
        });
        lessonContainer.getChildren().add(card);
    }

    private void loadLessonCards() {
        for (int i = 0; i < 5; i++) {
            VBox lessonCard = createCard("Lesson " + (i + 1), "#2c3e50", null);
            lessonContainer.getChildren().add(lessonCard);
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
