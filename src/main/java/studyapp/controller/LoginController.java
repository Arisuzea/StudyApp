package studyapp.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import studyapp.model.User;
import studyapp.util.DatabaseManager;
import studyapp.util.Session;
import studyapp.util.UIUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        Window window = usernameField.getScene().getWindow();

        if (username.isEmpty() || password.isEmpty()) {
            UIUtil.showAlert(window, Alert.AlertType.WARNING, "Validation Error",
                    "Username and password cannot be empty.");
            return;
        }

        try (Connection conn = DatabaseManager.connect()) {
            String query = "SELECT id, is_admin FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                stmt.setString(2, password);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int userId = rs.getInt("id");
                        boolean isAdmin = rs.getBoolean("is_admin");

                        User user = new User(userId, username, isAdmin);
                        Session.setLoggedInUser(user);

                        String fxmlPath = isAdmin ? "/view/Admin - Dashboard.fxml" : "/view/User - Dashboard.fxml";
                        String cssPath = isAdmin ? "/css/Admin - Dashboard.css" : "/css/User - Dashboard.css";

                        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                        Parent root = loader.load();
                        Scene scene = new Scene(root);
                        scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());

                        Stage stage = (Stage) usernameField.getScene().getWindow();
                        stage.setScene(scene);
                        stage.setTitle(isAdmin ? "StudyApp - Admin Dashboard" : "StudyApp - OOP Learning");
                        stage.setMaximized(true);
                        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                        stage.setX(screenBounds.getMinX());
                        stage.setY(screenBounds.getMinY());
                        stage.setWidth(screenBounds.getWidth());
                        stage.setHeight(screenBounds.getHeight());
                        stage.centerOnScreen();
                    } else {
                        UIUtil.showAlert(window, Alert.AlertType.ERROR, "Login Failed",
                                "Invalid username or password.");
                    }
                }
            }
        } catch (Exception e) {
            UIUtil.showAlert(window, Alert.AlertType.ERROR, "Login Failed",
                    "An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void goToRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/General - Register.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/General - Login.css").toExternalForm());

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("StudyApp - Register");

            waitForValidStageBounds(stage, () -> {
                stage.setMaximized(true);
                Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                stage.setX(screenBounds.getMinX());
                stage.setY(screenBounds.getMinY());
                stage.setWidth(screenBounds.getWidth());
                stage.setHeight(screenBounds.getHeight());
                stage.centerOnScreen();
            });

        } catch (Exception e) {
            UIUtil.showAlert(usernameField.getScene().getWindow(), Alert.AlertType.ERROR,
                    "Navigation Error", "Could not load register page.");
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
