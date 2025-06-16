package studyapp.model;

import java.util.List;
import java.util.ArrayList;

public class Question {
    private int id;
    private String questionText;
    private char correctOption;
    private List<String> options = new ArrayList<>(); // ✅ Always initialized

    public Question(int id, String questionText, char correctOption) {
        this.id = id;
        this.questionText = questionText;
        this.correctOption = correctOption;
    }

    public Question(int id, String questionText, char correctOption, List<String> options) {
        this(id, questionText, correctOption);
        this.options = (options != null) ? options : new ArrayList<>();
    }

    public int getId() { return id; }
    public String getQuestionText() { return questionText; }
    public char getCorrectOption() { return correctOption; }

    public List<String> getOptions() { return options; }
    public void setOptions(List<String> options) {
        this.options = (options != null) ? options : new ArrayList<>();
    }
}
