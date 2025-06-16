package studyapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import studyapp.ui.QuestionBlock;
import studyapp.util.OptionDAO;
import studyapp.util.QuestionDAO;
import studyapp.util.QuizDAO;

public class QuizCreationController {
    @FXML private TextField quizTitleField;
    @FXML private TextArea quizDescriptionField;
    @FXML private VBox quizContainer;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;
    @FXML private Button btnAddQuestion;
    @FXML private Button btnDeleteQuiz;

    private int quizId = -1;
    private boolean isEditMode = false;
    private Runnable onRefresh;

    // Initializes the quiz creation window and sets up event handlers
    @FXML
    private void initialize() {
        btnCancel.setOnAction(e -> closeWindow());
        btnAddQuestion.setOnAction(e -> quizContainer.getChildren().add(new QuestionBlock()));
        btnSave.setOnAction(e -> saveQuiz());
        btnDeleteQuiz.setVisible(false);
        btnDeleteQuiz.setDisable(true);
        btnDeleteQuiz.setOnAction(e -> deleteQuiz());
    }

    // Sets the callback to refresh the quiz list after changes
    public void setOnRefresh(Runnable onRefresh) {
        this.onRefresh = onRefresh;
    }

    // Saves the quiz and its questions to the database
    private void saveQuiz() {
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
                isEditMode = true;
                btnDeleteQuiz.setVisible(true);
                btnDeleteQuiz.setDisable(false);
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

            if (onRefresh != null) onRefresh.run();

            closeWindow();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Deletes the quiz and all associated questions and options
    private void deleteQuiz() {
        if (!isEditMode) return;

        try {
            var qDAO = new QuestionDAO();
            var oDAO = new OptionDAO();

            var questions = qDAO.getQuestionsByQuizId(quizId);
            for (var question : questions) {
                oDAO.deleteOptionsByQuestionId(question.getId());
            }
            qDAO.deleteQuestionsByQuizId(quizId);

            boolean success = new QuizDAO().deleteQuizById(quizId);
            if (success) {
                System.out.println("Quiz deleted.");

                if (onRefresh != null) onRefresh.run();

                closeWindow();
            } else {
                System.out.println("Failed to delete quiz.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Loads quiz data and questions for editing
    public void loadQuizForEditing(int quizId, String title, String description) {
        this.quizId = quizId;
        this.isEditMode = true;

        quizTitleField.setText(title);
        quizDescriptionField.setText(description);

        btnDeleteQuiz.setVisible(true);
        btnDeleteQuiz.setDisable(false);

        quizContainer.getChildren().clear();

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

    // Closes the quiz creation window
    private void closeWindow() {
        ((Stage) btnCancel.getScene().getWindow()).close();
    }
}
