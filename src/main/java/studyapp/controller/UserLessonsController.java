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

    // This is injected from UserController
    private StackPane contentPane;

    // Called by UserController to inject the main layout pane
    public void setContentPane(StackPane contentPane) {
        this.contentPane = contentPane;
    }

    @FXML
    public void initialize() {
        List<Lesson> lessons = LessonDAO.getAllLessons(); // Pull lessons from DB

        for (Lesson lesson : lessons) {
            VBox lessonCard = createLessonCard(lesson);
            lessonContainer.getChildren().add(lessonCard);
        }
    }

    private VBox createLessonCard(Lesson lesson) {
    VBox card = new VBox();
    card.setAlignment(Pos.CENTER);
    card.setPrefSize(243, 243);
    card.setStyle("-fx-background-color: #2c3e50; -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 2, 2);");

    Label title = new Label(lesson.getTitle());
    title.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");
    title.setWrapText(true);
    title.setAlignment(Pos.CENTER);
    title.setMaxWidth(Double.MAX_VALUE);

    card.getChildren().add(title);
    card.setOnMouseClicked(e -> openLessonDetail(lesson));

    return card;
}

    private void openLessonDetail(Lesson lesson) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/User - LessonDetail.fxml"));
            Parent lessonDetail = loader.load();

            LessonDetailController controller = loader.getController();
            controller.setLesson(lesson, contentPane, lessonContainer.getParent());
            contentPane.getChildren().setAll(lessonDetail);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
