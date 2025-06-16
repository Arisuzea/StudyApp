package studyapp.model;

import javafx.beans.property.*;

public class QuizProgress {
    private final StringProperty quizTitle = new SimpleStringProperty();
    private final IntegerProperty score = new SimpleIntegerProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty dateTaken = new SimpleStringProperty();
    private final IntegerProperty totalQuestions = new SimpleIntegerProperty();
    private final IntegerProperty quizId = new SimpleIntegerProperty();

    public QuizProgress(String title, int score, String status, String date, int totalQuestions, int quizId) {
        this.quizTitle.set(title);
        this.score.set(score);
        this.status.set(status);
        this.dateTaken.set(date);
        this.totalQuestions.set(totalQuestions);
        this.quizId.set(quizId); 
    }

    public StringProperty quizTitleProperty() { return quizTitle; }
    public IntegerProperty scoreProperty() { return score; }
    public StringProperty statusProperty() { return status; }
    public StringProperty dateTakenProperty() { return dateTaken; }
    public IntegerProperty totalQuestionsProperty() { return totalQuestions; }
    public IntegerProperty quizIdProperty() { return quizId; } 

    public String getQuizTitle() { return quizTitle.get(); }
    public int getScore() { return score.get(); }
    public String getStatus() { return status.get(); }
    public String getDateTaken() { return dateTaken.get(); }
    public int getTotalQuestions() { return totalQuestions.get(); }
    public int getQuizId() { return quizId.get(); } 
}
