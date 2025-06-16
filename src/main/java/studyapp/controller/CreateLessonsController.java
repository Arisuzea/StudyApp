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
import javafx.scene.image.ImageView;
import studyapp.model.Lesson;
import studyapp.util.LessonDAO;
import studyapp.util.UIUtil;

import java.io.IOException;
import java.util.List;

public class CreateLessonsController {
    @FXML private FlowPane lessonContainer;

    @FXML
    private void initialize() {
        addCreateLessonCard();
        loadLessonCards();
    }

    private void addCreateLessonCard() {
        VBox card = new VBox();
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(243, 243);
        card.getStyleClass().add("lesson-card");

        ImageView icon = new ImageView(getClass().getResource("/icons/Plus Icon.png").toExternalForm());
        icon.setFitWidth(48);
        icon.setFitHeight(48);

        card.getChildren().add(icon);

        card.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Admin - LessonCreation.fxml"));
                Parent root = loader.load();

                Stage stage = new Stage();
                UIUtil.applyAppIcon(stage);
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
            VBox lessonCard = createCard(lesson.getTitle(), "#2c3e50", event -> openLessonEditor(lesson));
            lessonContainer.getChildren().add(lessonCard);
        }
    }

    private VBox createCard(String text, String color, EventHandler<MouseEvent> clickHandler) {
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setPrefSize(243, 243);
        box.getStyleClass().add("lesson-card");

        Label label = new Label(text);
        label.getStyleClass().add("lesson-title");
        label.setWrapText(true);
        label.setAlignment(Pos.CENTER);
        label.setMaxWidth(200);

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
            UIUtil.applyAppIcon(stage);
            stage.setTitle("Edit Lesson");
            stage.setScene(new Scene(root));
            stage.setMaximized(true);
            stage.show();

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
