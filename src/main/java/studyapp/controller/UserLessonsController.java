package studyapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import studyapp.model.Lesson;
import studyapp.util.LessonDAO;

import java.io.IOException;
import java.util.List;

public class UserLessonsController {
    @FXML private FlowPane lessonContainer;
    private StackPane contentPane;

    public void setContentPane(StackPane contentPane) {
        this.contentPane = contentPane;
    }

    @FXML
    public void initialize() {
        List<Lesson> lessons = LessonDAO.getAllLessons();

        for (Lesson lesson : lessons) {
            VBox lessonCard = createLessonCard(lesson);
            lessonContainer.getChildren().add(lessonCard);
        }
    }

    private VBox createLessonCard(Lesson lesson) {
        VBox card = new VBox();
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(243, 243);
        card.setSpacing(10);
        card.getStyleClass().add("lesson-card");

        Label title = new Label(lesson.getTitle());
        title.setWrapText(true);
        title.setAlignment(Pos.CENTER);
        title.setMaxWidth(200);
        title.getStyleClass().add("lesson-title");

        card.getChildren().add(title);
        card.setOnMouseClicked(e -> openLessonDetail(lesson));
        return card;
    }

    private void openLessonDetail(Lesson lesson) {
        try {
            // Load the lesson detail screen
            FXMLLoader detailLoader = new FXMLLoader(getClass().getResource("/view/User - LessonDetail.fxml"));
            Parent lessonDetail = detailLoader.load();
            LessonDetailController controller = detailLoader.getController();

            // Reload full User - Lessons.fxml to use as back view
            FXMLLoader previousLoader = new FXMLLoader(getClass().getResource("/view/User - Lessons.fxml"));
            Parent previousView = previousLoader.load();

            // Inject contentPane into the reloaded lessons controller
            UserLessonsController previousController = previousLoader.getController();
            previousController.setContentPane(contentPane);

            // Pass lesson and view references to lesson detail controller
            controller.setLesson(lesson, contentPane, previousView);

            // Show lesson detail
            contentPane.getChildren().setAll(lessonDetail);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
