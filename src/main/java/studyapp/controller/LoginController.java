package studyapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import studyapp.util.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Username and password cannot be empty.");
            return;
        }

        try (Connection conn = DatabaseManager.connect()) {
            String query = "SELECT is_admin FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                stmt.setString(2, password);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        boolean isAdmin = rs.getBoolean("is_admin");
                        errorLabel.setText(""); 

                        String fxmlPath = isAdmin ? "/view/AdminDashboard.fxml" : "/view/MainApp.fxml";
                        String cssPath = isAdmin ? "/css/AdminDashboard.css" : "/css/MainApp.css";

                        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                        Parent root = loader.load();
                        Scene scene = new Scene(root);
                        scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());

                        Stage stage = (Stage) usernameField.getScene().getWindow();
                        stage.setScene(scene);
                        stage.setTitle(isAdmin ? "StudyApp - Admin Dashboard" : "StudyApp - OOP Learning");
                        stage.setMaximized(true);
                        javafx.geometry.Rectangle2D screenBounds = javafx.stage.Screen.getPrimary().getVisualBounds();
                        stage.setX(screenBounds.getMinX());
                        stage.setY(screenBounds.getMinY());
                        stage.setWidth(screenBounds.getWidth());
                        stage.setHeight(screenBounds.getHeight());
                        stage.centerOnScreen();
                    } else {
                        errorLabel.setText("Invalid username or password.");
                    }
                }
            }
        } catch (Exception e) {
            errorLabel.setText("Login failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void goToRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Register.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/Login.css").toExternalForm());

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("StudyApp - Register");
            stage.setMaximized(true);
            javafx.geometry.Rectangle2D screenBounds = javafx.stage.Screen.getPrimary().getVisualBounds();
            stage.setX(screenBounds.getMinX());
            stage.setY(screenBounds.getMinY());
            stage.setWidth(screenBounds.getWidth());
            stage.setHeight(screenBounds.getHeight());
            stage.centerOnScreen();
        } catch (Exception e) {
            errorLabel.setText("Could not load register page.");
            e.printStackTrace();
        }
    }
}
