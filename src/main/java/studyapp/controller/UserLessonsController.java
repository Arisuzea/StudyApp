package studyapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
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
        VBox card = new VBox(10);
        card.setPrefWidth(200);
        card.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-border-color: #ccc; -fx-border-radius: 5;");

        Label title = new Label(lesson.getTitle());
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");

        Label desc = new Label(lesson.getShortDescription());
        desc.setWrapText(true);

        Button viewBtn = new Button("View Lesson");
        viewBtn.setOnAction(e -> openLessonDetail(lesson));

        card.getChildren().addAll(title, desc, viewBtn);
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
