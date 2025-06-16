package studyapp.util;

import java.sql.*;
import java.util.*;
import studyapp.model.QuizProgress;

public class ProgressDAO {
    // Retrieves quiz progress for a user
    public static List<QuizProgress> getProgressByUser(int userId) {
        List<QuizProgress> progressList = new ArrayList<>();
        String sql = """
            SELECT q.title, p.score, p.status, p.completed_at,
                (SELECT COUNT(*) FROM questions WHERE quiz_id = p.quiz_id) AS total_questions,
                p.quiz_id
            FROM user_quiz_progress p
            JOIN quizzes q ON q.id = p.quiz_id
            WHERE p.user_id = ?
            """;

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                progressList.add(new QuizProgress(
                    rs.getString("title"),
                    rs.getInt("score"),
                    rs.getString("status"),
                    rs.getString("completed_at"),
                    rs.getInt("total_questions"),
                    rs.getInt("quiz_id")
                ));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return progressList;
    }

    public static boolean hasUserTakenQuiz(int userId, int quizId) {
        String sql = "SELECT 1 FROM user_quiz_progress WHERE user_id = ? AND quiz_id = ? LIMIT 1";

        try (Connection conn = DatabaseManager.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, quizId);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // true if a result is found
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
