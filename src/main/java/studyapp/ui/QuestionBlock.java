package studyapp.ui;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class QuestionBlock extends VBox {

    private final TextField questionField = new TextField();
    private final TextField[] optionFields = new TextField[4];
    private final ToggleGroup toggleGroup = new ToggleGroup();

    public QuestionBlock() {
        setSpacing(10);
        getStyleClass().add("question-block");

        questionField.setPromptText("Question text");
        questionField.getStyleClass().add("text-field");
        getChildren().add(new Label("Question:"));
        getChildren().add(questionField);
        getChildren().add(new Label("Options:"));
        char label = 'A';
        for (int i = 0; i < 4; i++) {
            HBox optionRow = new HBox(10);
            optionRow.setAlignment(Pos.CENTER_LEFT);

            RadioButton radio = new RadioButton(String.valueOf(label));
            radio.setToggleGroup(toggleGroup);
            radio.setMinWidth(30);

            TextField option = new TextField();
            option.setPromptText("Option " + label);
            option.getStyleClass().add("text-field");
            optionFields[i] = option;

            optionRow.getChildren().addAll(radio, option);
            getChildren().add(optionRow);
            label++;
        }
    }

    public String getQuestionText() {
        return questionField.getText();
    }

    public String[] getOptions() {
        String[] opts = new String[4];
        for (int i = 0; i < 4; i++) {
            opts[i] = optionFields[i].getText();
        }
        return opts;
    }

    public char getCorrectOption() {
        for (Toggle toggle : toggleGroup.getToggles()) {
            if (toggle.isSelected()) {
                RadioButton rb = (RadioButton) toggle;
                return rb.getText().charAt(0);
            }
        }
        return ' ';
    }
}
