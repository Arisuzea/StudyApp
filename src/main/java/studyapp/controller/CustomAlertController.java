package studyapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CustomAlertController {
    @FXML private Label titleText;    
    @FXML private Label messageText; 
    @FXML private Button alertButton;

    private Stage stage;

    public void setContent(String title, String message) {
        titleText.setText("StudyApp - " + title);
        messageText.setText(message);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void closeAlert() {
        if (stage != null) {
            stage.close();
        }
    }
}

