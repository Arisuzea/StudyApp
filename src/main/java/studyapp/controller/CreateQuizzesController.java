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
import javafx.scene.image.ImageView;
import studyapp.model.Quiz;
import studyapp.util.QuizDAO;
import studyapp.util.UIUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javafx.event.EventHandler;

public class CreateQuizzesController {

    @FXML
    private FlowPane quizContainer;

    @FXML
    private void initialize() {
        refreshQuizList();
    }

    private void refreshQuizList() {
        quizContainer.getChildren().clear();
        addCreateQuizCard();
        loadQuizCards();
    }

    private VBox createCardWithIcon(String imagePath, EventHandler<MouseEvent> clickHandler) {
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setPrefSize(243, 243);
        box.getStyleClass().add("quiz-card");

        ImageView imageView = new ImageView(getClass().getResource(imagePath).toExternalForm());
        imageView.setFitWidth(48); // adjust as needed
        imageView.setFitHeight(48); // adjust as needed

        box.getChildren().add(imageView);
        if (clickHandler != null) box.setOnMouseClicked(clickHandler);
        return box;
    }


    private void addCreateQuizCard() {
        VBox card = createCardWithIcon("/icons/Plus Icon.png", event -> openQuizCreationWindow());
        card.getStyleClass().add("quiz-card");
        quizContainer.getChildren().add(card);
    }


    private void loadQuizCards() {
        try {
            List<Quiz> quizzes = new QuizDAO().getAllQuizzes();
            for (Quiz quiz : quizzes) {
                VBox quizCard = createCard(quiz.getTitle(), event -> openQuizDetailsWindow(quiz));
                quizCard.getStyleClass().add("quiz-card");
                quizContainer.getChildren().add(quizCard);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private VBox createCard(String text, EventHandler<MouseEvent> clickHandler) {
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setPrefSize(243, 243);

        Label label = new Label(text);
        label.getStyleClass().add("quiz-title");

        box.getChildren().add(label);
        if (clickHandler != null) box.setOnMouseClicked(clickHandler);
        return box;
    }

    private void openQuizCreationWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Admin - QuizCreation.fxml"));
            Parent root = loader.load();

            QuizCreationController controller = loader.getController();
            controller.setOnRefresh(this::refreshQuizList);

            Stage stage = new Stage();
            UIUtil.applyAppIcon(stage);
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/Admin - QuizCreation.css").toExternalForm());

            stage.setScene(scene);
            stage.setTitle("StudyApp - Create New Quiz");
            stage.setMaximized(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openQuizDetailsWindow(Quiz quiz) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Admin - QuizCreation.fxml"));
            Parent root = loader.load();

            QuizCreationController controller = loader.getController();
            controller.loadQuizForEditing(quiz.getId(), quiz.getTitle(), quiz.getDescription());
            controller.setOnRefresh(this::refreshQuizList);

            Stage stage = new Stage();
            UIUtil.applyAppIcon(stage);
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/Admin - QuizCreation.css").toExternalForm());

            stage.setScene(scene);
            stage.setTitle("StudyApp - Edit Quiz");
            stage.setMaximized(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
