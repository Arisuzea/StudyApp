package studyapp.model;

import java.util.List;
import java.util.ArrayList;

public class Question {
    private int id;
    private String questionText;
    private char correctOption;
    private List<String> options = new ArrayList<>();

    /**
     * Constructs a question with id, text, and correct option
     */
    public Question(int id, String questionText, char correctOption) {
        this.id = id;
        this.questionText = questionText;
        this.correctOption = correctOption;
    }

    /**
     * Constructs a question with options
     */
    public Question(int id, String questionText, char correctOption, List<String> options) {
        this(id, questionText, correctOption);
        this.options = (options != null) ? options : new ArrayList<>();
    }

    /**
     * Returns the question ID
     */
    public int getId() { return id; }

    /**
     * Returns the question text
     */
    public String getQuestionText() { return questionText; }

    /**
     * Returns the correct option label
     */
    public char getCorrectOption() { return correctOption; }

    /**
     * Returns the list of options
     */
    public List<String> getOptions() { return options; }

    /**
     * Sets the list of options
     */
    public void setOptions(List<String> options) {
        this.options = (options != null) ? options : new ArrayList<>();
    }
}
