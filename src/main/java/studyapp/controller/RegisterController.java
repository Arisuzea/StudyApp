package studyapp.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import studyapp.util.DatabaseManager;
import studyapp.util.UIUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.regex.*;

public class RegisterController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private VBox registerContainer;

    @FXML
    private void handleRegister() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        Stage currentStage = (Stage) usernameField.getScene().getWindow();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            UIUtil.showAlert(currentStage, Alert.AlertType.WARNING, "Validation Error",
                    "All fields are required.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            UIUtil.showAlert(currentStage, Alert.AlertType.WARNING, "Password Mismatch",
                    "Passwords do not match.");
            return;
        }

        if (!isStrongPassword(password)) {
            UIUtil.showAlert(currentStage, Alert.AlertType.WARNING, "Weak Password", """
                Password must be at least 6 characters long, and include:
            An uppercase letter
            A lowercase letter
            A number
            A special character (@ # $ % etc.)
            """.stripIndent());
            return;
        }

        try (Connection conn = DatabaseManager.connect()) {
            String checkQuery = "SELECT COUNT(*) FROM users WHERE username = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setString(1, username);
                var rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    UIUtil.showAlert(currentStage, Alert.AlertType.WARNING, "Username Taken",
                            "The username is already registered. Please choose a different one.");
                    return;
                }
            }

            String insertQuery = "INSERT INTO users (username, password, is_admin) VALUES (?, ?, 0)";
            try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.executeUpdate();
                goToLogin(); // Success: proceed to login
            }

        } catch (Exception e) {
            UIUtil.showAlert(currentStage, Alert.AlertType.ERROR, "Registration Error",
                    "Registration failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean isStrongPassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{6,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    @FXML
    private void goToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/General - Login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/General - Login.css").toExternalForm());

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("StudyApp - Login");

            waitForValidStageBounds(stage, () -> {
                stage.setMaximized(true);
                javafx.geometry.Rectangle2D screenBounds = javafx.stage.Screen.getPrimary().getVisualBounds();
                stage.setX(screenBounds.getMinX());
                stage.setY(screenBounds.getMinY());
                stage.setWidth(screenBounds.getWidth());
                stage.setHeight(screenBounds.getHeight());
                stage.centerOnScreen();
            });


        } catch (Exception e) {
            UIUtil.showAlert(usernameField.getScene().getWindow(), Alert.AlertType.ERROR,
                    "Navigation Error", "Could not load login page.");
            e.printStackTrace();
        }
    } 


    private void waitForValidStageBounds(Stage stage, Runnable action) {
        if (stage.getWidth() > 0 && stage.getHeight() > 0) {
            Platform.runLater(action);
        } else {
            Platform.runLater(() -> waitForValidStageBounds(stage, action));
        }
    }

}
