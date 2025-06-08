package studyapp.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionDAO {

    public int insertQuestion(int quizId, String questionText, char correctOption) throws SQLException {
        String sql = "INSERT INTO questions (quiz_id, question_text, correct_option) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, quizId);
            stmt.setString(2, questionText);
            stmt.setString(3, String.valueOf(correctOption));
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); 
            }
        }
        return -1;
    }
}
