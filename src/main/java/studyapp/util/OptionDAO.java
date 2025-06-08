package studyapp.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OptionDAO {

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
}
