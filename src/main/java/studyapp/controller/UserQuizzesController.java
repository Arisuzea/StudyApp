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

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserQuizzesController {

    @FXML private FlowPane quizContainer;

    @FXML
    private void initialize() {
        loadQuizCards();
    }

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

    private VBox createCard(String text, String color, javafx.event.EventHandler<MouseEvent> onClick) {
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setPrefSize(243, 243);
        box.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 2, 2);");

        Label label = new Label(text);
        label.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");

        box.getChildren().add(label);
        box.setOnMouseClicked(onClick);
        return box;
    }

    private void openQuizDetail(Quiz quiz) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/User - QuizDetail.fxml"));
            Parent root = loader.load();

            QuizDetailController controller = loader.getController();
            controller.setQuiz(quiz);  // Pass quiz info

            Stage stage = new Stage();
            stage.setTitle("Quiz Detail");
            stage.setScene(new Scene(root));
            stage.setMaximized(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
