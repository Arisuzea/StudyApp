package studyapp.controller;

import javafx.fxml.FXML;
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
                        // TODO: load admin or user dashboard scene here
                        errorLabel.setText("");  // clear errors
                        System.out.println("Login successful. Admin? " + isAdmin);
                    } else {
                        errorLabel.setText("Invalid username or password.");
                    }
                }
            }
        } catch (Exception e) {
            errorLabel.setText("Login failed: " + e.getMessage());
        }
    }
}
