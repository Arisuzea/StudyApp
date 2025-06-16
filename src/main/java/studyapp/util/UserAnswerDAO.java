package studyapp.util;

import java.sql.*;

public class UserAnswerDAO {
    // Gets or creates a progress record for a quiz attempt
    public static int resetAndCreateProgress(int userId, int quizId) throws SQLException {
        String fetchExistingProgressSql = "SELECT id FROM user_quiz_progress WHERE user_id = ? AND quiz_id = ?";
        String deleteAnswersSql = "DELETE FROM user_question_answers WHERE progress_id = ?";
        String deleteProgressSql = "DELETE FROM user_quiz_progress WHERE id = ?";
        String insertSql = "INSERT INTO user_quiz_progress (user_id, quiz_id) VALUES (?, ?)";

        try (Connection conn = DatabaseManager.connect()) {
            conn.setAutoCommit(false);

            int existingProgressId = -1;
            try (PreparedStatement fetchStmt = conn.prepareStatement(fetchExistingProgressSql)) {
                fetchStmt.setInt(1, userId);
                fetchStmt.setInt(2, quizId);
                ResultSet rs = fetchStmt.executeQuery();
                if (rs.next()) existingProgressId = rs.getInt("id");
            }

            if (existingProgressId != -1) {
                try (PreparedStatement delAns = conn.prepareStatement(deleteAnswersSql)) {
                    delAns.setInt(1, existingProgressId);
                    delAns.executeUpdate();
                }
                try (PreparedStatement delProg = conn.prepareStatement(deleteProgressSql)) {
                    delProg.setInt(1, existingProgressId);
                    delProg.executeUpdate();
                }
            }

            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                insertStmt.setInt(1, userId);
                insertStmt.setInt(2, quizId);
                insertStmt.executeUpdate();
                ResultSet keys = insertStmt.getGeneratedKeys();
                if (keys.next()) {
                    conn.commit();
                    return keys.getInt(1);
                } else {
                    conn.rollback();
                    throw new SQLException("Failed to insert new progress.");
                }
            }
        }
    }

    // Saves an answer for a question in a quiz attempt
    public static void saveAnswer(int progressId, int questionId, Integer selectedOptionId, boolean isCorrect) throws SQLException {
        String sql = """
            INSERT INTO user_question_answers (progress_id, question_id, selected_option, is_correct)
            VALUES (?, ?, ?, ?)
            """;

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, progressId);
            stmt.setInt(2, questionId);
            if (selectedOptionId != null) stmt.setInt(3, selectedOptionId);
            else stmt.setNull(3, Types.INTEGER);
            stmt.setBoolean(4, isCorrect);
            stmt.executeUpdate();
        }
    }

    // Marks a quiz attempt as completed
    public static void completeProgress(int progressId, int score) throws SQLException {
        String sql = "UPDATE user_quiz_progress SET status = 'completed', completed_at = CURRENT_TIMESTAMP, score = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, score);
            stmt.setInt(2, progressId);
            stmt.executeUpdate();
        }
    }
}
