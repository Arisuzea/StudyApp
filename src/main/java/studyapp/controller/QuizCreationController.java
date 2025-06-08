package studyapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import studyapp.ui.QuestionBlock;
import studyapp.util.QuizDAO;
import studyapp.util.QuestionDAO;
import studyapp.util.OptionDAO;

public class QuizCreationController {

    @FXML private TextField quizTitleField;
    @FXML private TextArea quizDescriptionField;
    @FXML private VBox quizContainer;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;
    @FXML private Button btnAddQuestion;

    @FXML
    private void initialize() {
        btnCancel.setOnAction(e -> btnCancel.getScene().getWindow().hide());

        btnAddQuestion.setOnAction(e -> {
            QuestionBlock qb = new QuestionBlock();
            quizContainer.getChildren().add(qb);
        });

        btnSave.setOnAction(e -> {
            try {
                String title = quizTitleField.getText();
                if (title.isBlank()) return;
                int quizId = new QuizDAO().insertQuiz(title);
                var qDAO = new QuestionDAO();
                var oDAO = new OptionDAO();

                for (var node : quizContainer.getChildren()) {
                    if (node instanceof QuestionBlock qb) {
                        int qid = qDAO.insertQuestion(quizId, qb.getQuestionText(), qb.getCorrectOption());
                        String[] options = qb.getOptions();
                        for (int i = 0; i < 4; i++) {
                            oDAO.insertOption(qid, (char) ('A' + i), options[i]);
                        }
                    }
                }
                btnCancel.getScene().getWindow().hide();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}