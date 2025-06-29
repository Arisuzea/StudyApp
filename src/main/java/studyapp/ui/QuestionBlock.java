package studyapp.ui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.List;
import javafx.scene.web.HTMLEditor;

public class QuestionBlock extends VBox {
    private final HTMLEditor questionEditor;
    private final TextField[] optionFields;
    private final ToggleGroup toggleGroup;

    public QuestionBlock() {
        setSpacing(10);
        setPadding(new Insets(10));
        setStyle("-fx-border-color: #bdc3c7; -fx-border-width: 2px; -fx-background-color: #ecf0f1; -fx-border-radius: 10; -fx-background-radius: 10;");

        Label questionLabel = new Label("Question:");
        questionEditor = new HTMLEditor();
        questionEditor.setPrefHeight(150);
        getChildren().addAll(questionLabel, questionEditor);

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

    public String getQuestionText() {
        return questionEditor.getHtmlText();
    }

    public void setQuestionText(String html) {
        questionEditor.setHtmlText(html);
    }

    public String[] getOptions() {
        String[] options = new String[4];
        for (int i = 0; i < 4; i++) {
            options[i] = optionFields[i].getText().trim();
        }
        return options;
    }

    public char getCorrectOption() {
        for (Toggle toggle : toggleGroup.getToggles()) {
            RadioButton rb = (RadioButton) toggle;
            if (rb.isSelected()) {
                return rb.getText().charAt(0);
            }
        }
        return ' ';
    }

    public void setOptions(String[] options) {
        for (int i = 0; i < 4 && i < options.length; i++) {
            optionFields[i].setText(options[i]);
        }
    }

    public void setOptions(List<String> options) {
        setOptions(options.toArray(new String[0]));
    }

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
