package studyapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import studyapp.model.Question;
import studyapp.model.Quiz;
import studyapp.util.QuestionDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class QuizDetailController {

    @FXML private Label titleLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label itemCountLabel;
    @FXML private Button startButton;

    private Quiz quiz;
    private List<Question> questionList;

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
        titleLabel.setText(quiz.getTitle());
        descriptionLabel.setText(quiz.getDescription());

        try {
            questionList = new QuestionDAO().getQuestionsByQuizId(quiz.getId());
            itemCountLabel.setText("Items: " + questionList.size());
            startButton.setDisable(questionList.isEmpty());
        } catch (SQLException e) {
            itemCountLabel.setText("Error loading questions");
            startButton.setDisable(true);
            e.printStackTrace();
        }
    }

    @FXML
    private void startQuiz() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/User - QuizTaking.fxml"));
            Parent root = loader.load();

            QuizTakingController controller = loader.getController();
            controller.beginQuizSession(quiz, questionList);

            Stage stage = new Stage();
            stage.setTitle("Quiz: " + quiz.getTitle());
            stage.setScene(new Scene(root));
            stage.setMaximized(true);
            stage.show();

            // Optionally close the detail screen
            startButton.getScene().getWindow().hide();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
