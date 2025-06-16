package studyapp.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.web.HTMLEditor;

import studyapp.model.Lesson;
import studyapp.util.LessonDAO;

public class LessonCreationController {

    @FXML private TextField titleField;
    @FXML private TextArea shortDescField;
    @FXML private HTMLEditor contentEditor;
    @FXML private Button btnCancel;
    @FXML private Button btnSave;
    @FXML private Button btnDelete;

    private boolean isEditMode = false;
    private int lessonId = -1;

    @FXML private TextField topicField;

    @FXML
    private ComboBox<String> difficultyBox;

    @FXML
    public void initialize() {
        btnCancel.setOnAction(e -> closeWindow());
        btnSave.setOnAction(e -> saveLesson());
        difficultyBox.setItems(FXCollections.observableArrayList("Easy", "Intermediate", "Difficult"));
    }

    private void saveLesson() {
        String title = safeTrim(titleField.getText());
        String shortDesc = safeTrim(shortDescField.getText());
        String content = contentEditor.getHtmlText().trim();
        String topic = safeTrim(topicField.getText());
        String difficulty = difficultyBox.getValue();

        if (title.isEmpty() || shortDesc.isEmpty() || content.isEmpty() || topic.isEmpty() || difficulty == null) {
            System.out.println("All fields are required.");
            return;
        }

        Lesson lesson = isEditMode
            ? new Lesson(lessonId, title, shortDesc, content, topic, difficulty)
            : new Lesson(title, shortDesc, content, topic, difficulty);

        boolean success = isEditMode
            ? LessonDAO.updateLesson(lesson)
            : LessonDAO.saveLesson(lesson);

        if (success) {
            System.out.println(isEditMode ? "Lesson updated." : "Lesson saved.");
            closeWindow();
        } else {
            System.out.println("Failed to save lesson.");
        }
    }

    public void loadLessonForEditing(Lesson lesson) {
    isEditMode = true;
    lessonId = lesson.getId();

    titleField.setText(lesson.getTitle());
    shortDescField.setText(lesson.getShortDescription());
    contentEditor.setHtmlText(lesson.getContent());
    topicField.setText(lesson.getTopic());
    difficultyBox.setValue(lesson.getDifficulty());

    btnDelete.setVisible(true);
    btnDelete.setDisable(false);

    btnDelete.setOnAction(e -> {
        boolean success = LessonDAO.deleteLessonById(lessonId);
        if (success) {
            System.out.println("Lesson deleted.");
            closeWindow();
        } else {
            System.out.println("Failed to delete lesson.");
        }
    });
}


    private void closeWindow() {
        ((Stage) btnCancel.getScene().getWindow()).close();
    }

    private String safeTrim(String s) {
        return s == null ? "" : s.trim();
    }
}
