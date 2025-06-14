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

    private int quizId = -1;
    private boolean isEditMode = false;

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

                var qDAO = new QuestionDAO();
                var oDAO = new OptionDAO();

                if (isEditMode) {
                    new QuizDAO().updateQuiz(quizId, title, quizDescriptionField.getText());
                    qDAO.deleteQuestionsByQuizId(quizId);
                } else {
                    quizId = new QuizDAO().insertQuiz(title, quizDescriptionField.getText());
                }

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

    public void loadQuizForEditing(int quizId, String title, String description) {
        this.quizId = quizId;
        this.isEditMode = true;

        quizTitleField.setText(title);
        quizDescriptionField.setText(description);

        quizContainer.getChildren().clear(); // Prevent duplicate blocks

        var qDAO = new QuestionDAO();
        var oDAO = new OptionDAO();

        try {
            var questions = qDAO.getQuestionsByQuizId(quizId);
            for (var question : questions) {
                var options = oDAO.getOptionsByQuestionId(question.getId());

                QuestionBlock qb = new QuestionBlock();
                qb.setQuestionText(question.getQuestionText());
                qb.setCorrectOption(question.getCorrectOption());
                qb.setOptions(options);

                quizContainer.getChildren().add(qb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
