package studyapp.model;

import javafx.beans.property.*;

public class QuizProgress {
    private final StringProperty quizTitle = new SimpleStringProperty();
    private final IntegerProperty score = new SimpleIntegerProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty dateTaken = new SimpleStringProperty();

    public QuizProgress(String title, int score, String status, String date) {
        this.quizTitle.set(title);
        this.score.set(score);
        this.status.set(status);
        this.dateTaken.set(date);
    }

    public StringProperty quizTitleProperty() { return quizTitle; }
    public IntegerProperty scoreProperty() { return score; }
    public StringProperty statusProperty() { return status; }
    public StringProperty dateTakenProperty() { return dateTaken; }
}
