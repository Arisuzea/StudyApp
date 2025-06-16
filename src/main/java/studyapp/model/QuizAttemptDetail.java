package studyapp.model;

public class QuizAttemptDetail {
    private final String questionText;
    private final String correctOptionLabel;
    private final String selectedOptionLabel;
    private final String correctOptionText;
    private final String selectedOptionText;

    public QuizAttemptDetail(String questionText,
                              String correctOptionLabel,
                              String selectedOptionLabel,
                              String correctOptionText,
                              String selectedOptionText) {
        this.questionText = questionText;
        this.correctOptionLabel = correctOptionLabel;
        this.selectedOptionLabel = selectedOptionLabel;
        this.correctOptionText = correctOptionText;
        this.selectedOptionText = selectedOptionText;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getCorrectOptionLabel() {
        return correctOptionLabel;
    }

    public String getSelectedOptionLabel() {
        return selectedOptionLabel;
    }

    public String getCorrectOptionText() {
        return correctOptionText;
    }

    public String getSelectedOptionText() {
        return selectedOptionText;
    }
}
