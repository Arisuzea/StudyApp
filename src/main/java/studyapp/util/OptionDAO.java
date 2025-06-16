package studyapp.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OptionDAO {
    // Inserts an option for a question
    public void insertOption(int questionId, char optionLabel, String optionText) throws SQLException {
        String sql = "INSERT INTO options (question_id, option_label, option_text) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, questionId);
            stmt.setString(2, String.valueOf(optionLabel));
            stmt.setString(3, optionText);
            stmt.executeUpdate();
        }
    }
    // Retrieves all options for a question
    public List<String> getOptionsByQuestionId(int questionId) throws SQLException {
        List<String> options = new ArrayList<>();
        String sql = "SELECT option_text FROM options WHERE question_id = ? ORDER BY option_label ASC";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, questionId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                options.add(rs.getString("option_text"));
            }
        }
        return options;
    }
    // Deletes all options for a question
    public void deleteOptionsByQuestionId(int questionId) throws SQLException {
        String sql = "DELETE FROM options WHERE question_id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, questionId);
            stmt.executeUpdate();
        }
    }
}
