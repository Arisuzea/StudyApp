package studyapp.model;

public class Question {
    private int id;
    private String questionText;
    private char correctOption;

    public Question(int id, String questionText, char correctOption) {
        this.id = id;
        this.questionText = questionText;
        this.correctOption = correctOption;
    }

    public int getId() { return id; }
    public String getQuestionText() { return questionText; }
    public char getCorrectOption() { return correctOption; }
}
