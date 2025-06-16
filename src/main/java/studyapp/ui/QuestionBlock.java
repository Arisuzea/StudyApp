package studyapp.ui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.List;

public class QuestionBlock extends VBox {
    private final TextField questionField;
    private final TextField[] optionFields;
    private final ToggleGroup toggleGroup;

    // Constructs a new question block UI component
    public QuestionBlock() {
        setSpacing(10);
        setPadding(new Insets(10));
        setStyle("-fx-border-color: #bdc3c7; -fx-border-width: 2px; -fx-background-color: #ecf0f1; -fx-border-radius: 10; -fx-background-radius: 10;");

        Label questionLabel = new Label("Question:");
        questionField = new TextField();
        questionField.setPromptText("Enter question text...");
        getChildren().addAll(questionLabel, questionField);

        optionFields = new TextField[4];
        toggleGroup = new ToggleGroup();

        for (int i = 0; i < 4; i++) {
            char label = (char) ('A' + i);
            HBox optionBox = new HBox(10);
            optionBox.setPadding(new Insets(5));

            RadioButton radioButton = new RadioButton(String.valueOf(label));
            radioButton.setToggleGroup(toggleGroup);

            TextField optionField = new TextField();
            optionField.setPromptText("Option " + label);

            optionFields[i] = optionField;

            optionBox.getChildren().addAll(radioButton, optionField);
            getChildren().add(optionBox);
        }
    }

    // Returns the entered question text
    public String getQuestionText() {
        return questionField.getText().trim();
    }

    // Returns the entered options as a string array
    public String[] getOptions() {
        String[] options = new String[4];
        for (int i = 0; i < 4; i++) {
            options[i] = optionFields[i].getText().trim();
        }
        return options;
    }

    // Returns the selected correct option label
    public char getCorrectOption() {
        for (Toggle toggle : toggleGroup.getToggles()) {
            RadioButton rb = (RadioButton) toggle;
            if (rb.isSelected()) {
                return rb.getText().charAt(0); // 'A', 'B', etc.
            }
        }
        return ' '; // return blank if none selected
    }

    // Sets the question text for editing
    public void setQuestionText(String text) {
        questionField.setText(text);
    }

    // Sets the options for editing (array version)
    public void setOptions(String[] options) {
        for (int i = 0; i < 4 && i < options.length; i++) {
            optionFields[i].setText(options[i]);
        }
    }

    // Sets the options for editing (list version)
    public void setOptions(List<String> options) {
        setOptions(options.toArray(new String[0]));
    }

    // Sets the correct option for editing
    public void setCorrectOption(char label) {
        for (Toggle toggle : toggleGroup.getToggles()) {
            RadioButton rb = (RadioButton) toggle;
            if (rb.getText().charAt(0) == label) {
                rb.setSelected(true);
                break;
            }
        }
    }
}
