package studyapp.util;

import java.sql.*;
import java.util.*;
import studyapp.model.QuizProgress;

public class ProgressDAO {
    public static List<QuizProgress> getProgressByUser(int userId) {
        List<QuizProgress> progressList = new ArrayList<>();
        String sql = """
            SELECT q.title, p.score, p.status, p.completed_at
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
                    rs.getString("completed_at")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return progressList;
    }
}
