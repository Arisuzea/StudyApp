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
import javafx.event.EventHandler;
import studyapp.model.Lesson;
import studyapp.util.LessonDAO;

import java.io.IOException;
import java.util.List;

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
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Admin - LessonCreation.fxml"));
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setTitle("Create New Lesson");
                stage.setScene(new Scene(root));
                stage.setMaximized(true);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        lessonContainer.getChildren().add(card);
    }

    private void loadLessonCards() {
        List<Lesson> lessons = LessonDAO.getAllLessons();

        for (Lesson lesson : lessons) {
            // Add click handler to open editor for this lesson
            VBox lessonCard = createCard(lesson.getTitle(), "#2c3e50", event -> openLessonEditor(lesson));
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
        label.setWrapText(true);
        label.setAlignment(Pos.CENTER);
        label.setMaxWidth(Double.MAX_VALUE);

        box.getChildren().add(label);
        if (clickHandler != null) box.setOnMouseClicked(clickHandler);
        return box;
    }

    private void openLessonEditor(Lesson lesson) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Admin - LessonCreation.fxml"));
            Parent root = loader.load();

            LessonCreationController controller = loader.getController();
            controller.loadLessonForEditing(lesson);

            Stage stage = new Stage();
            stage.setTitle("Edit Lesson");
            stage.setScene(new Scene(root));
            stage.setMaximized(true);
            stage.show();

            // Refresh lesson cards on editor close
            stage.setOnHidden(e -> {
                lessonContainer.getChildren().clear();
                addCreateLessonCard();
                loadLessonCards();
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
