package studyapp.util;

import java.sql.*;

public class UserAnswerDAO {

    // 1. Get or create progress_id when quiz starts
    public static int getOrCreateProgressId(int userId, int quizId) throws SQLException {
        String selectSql = "SELECT id FROM user_quiz_progress WHERE user_id = ? AND quiz_id = ? AND status = 'in_progress'";
        String insertSql = "INSERT INTO user_quiz_progress (user_id, quiz_id) VALUES (?, ?)";

        try (Connection conn = DatabaseManager.connect()) {
            PreparedStatement selectStmt = conn.prepareStatement(selectSql);
            selectStmt.setInt(1, userId);
            selectStmt.setInt(2, quizId);
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) return rs.getInt("id");

            PreparedStatement insertStmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setInt(1, userId);
            insertStmt.setInt(2, quizId);
            insertStmt.executeUpdate();
            ResultSet generated = insertStmt.getGeneratedKeys();
            return generated.next() ? generated.getInt(1) : -1;
        }
    }

    // 2. Save answer per question
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

    // 3. Mark quiz as completed
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
