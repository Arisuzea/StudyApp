package studyapp.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import studyapp.model.Lesson;

public class LessonDetailController {

    @FXML private Label lblTitle;
    @FXML private Label lblContent;
    @FXML private Node btnBack;

    private StackPane contentPane;
    private Node previousView;

    // Set lesson content and the container to return back
    public void setLesson(Lesson lesson, StackPane contentPane, Node previousView) {
        this.contentPane = contentPane;
        this.previousView = previousView;

        lblTitle.setText(lesson.getTitle());
        lblContent.setText(lesson.getContent());
    }

    @FXML
    private void initialize() {
        btnBack.setOnMouseClicked(e -> {
            if (contentPane != null && previousView != null) {
                contentPane.getChildren().setAll(previousView);
            }
        });
    }
}
