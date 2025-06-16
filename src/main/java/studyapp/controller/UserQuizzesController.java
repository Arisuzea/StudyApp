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

    private VBox createCard(String titleText, String color, javafx.event.EventHandler<MouseEvent> onClick) {
        VBox card = new VBox();
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(243, 243);
        card.setStyle("-fx-background-color: #2c3e50; -fx-background-radius: 15; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 2, 2);");

        Label title = new Label(titleText);
        title.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");
        title.setWrapText(true);
        title.setAlignment(Pos.CENTER);
        title.setMaxWidth(Double.MAX_VALUE);

        card.getChildren().add(title);
        card.setOnMouseClicked(onClick);
        return card;
    }


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
