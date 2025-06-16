package studyapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import studyapp.model.Quiz;
import studyapp.util.QuizDAO;
import studyapp.util.UIUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserQuizzesController {
    @FXML private FlowPane quizContainer;

    /** Initializes the quiz list for the user */
    @FXML
    private void initialize() {
        loadQuizCards();
    }

    /** Loads all quizzes and displays them as cards */
    private void loadQuizCards() {
        try {
            List<Quiz> quizzes = new QuizDAO().getAllQuizzes();
            for (Quiz quiz : quizzes) {
                VBox card = createCard(quiz.getTitle(), "#3498db", event -> openQuizDetail(quiz));
                quizContainer.getChildren().add(card);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Creates a quiz card with the given title, color, and click handler */
    private VBox createCard(String titleText, String color, javafx.event.EventHandler<MouseEvent> onClick) {
        VBox card = new VBox();
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(243, 243);
        card.setSpacing(10);
        card.getStyleClass().add("lesson-card");

        Label title = new Label(titleText);
        title.setWrapText(true);
        title.setAlignment(Pos.CENTER);
        title.setMaxWidth(200);
        title.getStyleClass().add("quiz-title");


        card.getChildren().add(title);
        card.setOnMouseClicked(onClick);
        return card;
    }

    /** Opens the quiz detail window for the selected quiz */
    private void openQuizDetail(Quiz quiz) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/User - QuizDetail.fxml"));
            Parent root = loader.load();

            QuizDetailController controller = loader.getController();
            controller.setQuiz(quiz);  // Pass quiz info

            Stage stage = new Stage();
            UIUtil.applyAppIcon(stage);
            stage.setTitle("StudyApp - OOP Learning");
            stage.setScene(new Scene(root));
            stage.setMaximized(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
