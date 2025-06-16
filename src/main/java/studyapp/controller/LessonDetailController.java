package studyapp.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;

import studyapp.model.Lesson;

public class LessonDetailController {

    @FXML private Label lblTitle;
    @FXML private WebView webContent;
    @FXML private Node btnBack;

    private StackPane contentPane;
    private Parent previousView;  // Changed from Node to Parent for clarity

    public void setLesson(Lesson lesson, StackPane contentPane, Parent previousView) {
        this.contentPane = contentPane;
        this.previousView = previousView;

        lblTitle.setText(lesson.getTitle());

        WebEngine engine = webContent.getEngine();
        engine.loadContent(lesson.getContent());
    }

    @FXML
    private void initialize() {
        btnBack.setOnMouseClicked(e -> {
            if (contentPane != null && previousView != null) {
                contentPane.getChildren().setAll(previousView);
            } else {
                System.err.println("Back navigation failed: missing previous view or content pane.");
            }
        });
    }
}
