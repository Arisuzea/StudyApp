package studyapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class QuizCreationController {

    @FXML
    private TextField quizTitleField;

    @FXML
    private TextArea quizDescriptionField;

    @FXML
    private TextField numQuestionsField;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;

    @FXML
    private void initialize() {
        btnCancel.setOnAction(e -> {
            btnCancel.getScene().getWindow().hide();
        });

        btnSave.setOnAction(e -> {
            System.out.println("Saving quiz: " + quizTitleField.getText());
        });
    }
}
