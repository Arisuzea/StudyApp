package studyapp.util;

import studyapp.model.Quiz;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizDAO {
    // Inserts a quiz into the database
    public int insertQuiz(String title, String description) throws SQLException {
        String sql = "INSERT INTO quizzes (title, description, created_at, updated_at) VALUES (?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, title);
            stmt.setString(2, description);
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }
    // Updates a quiz in the database
    public void updateQuiz(int id, String title, String description) throws SQLException {
        String sql = "UPDATE quizzes SET title = ?, description = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.setString(2, description);
            stmt.setInt(3, id);
            stmt.executeUpdate();
        }
    }
    // Retrieves all quizzes from the database
    public List<Quiz> getAllQuizzes() throws SQLException {
        List<Quiz> list = new ArrayList<>();
        String sql = "SELECT id, title, description FROM quizzes ORDER BY created_at ASC";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Quiz(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("description")
                ));
            }
        }
        return list;
    }
    // Retrieves a quiz by its ID
    public Quiz getQuizById(int quizId) throws SQLException {
        String sql = "SELECT id, title, description FROM quizzes WHERE id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quizId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Quiz(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description")
                    );
                }
            }
        }
        return null;
    }
    // Deletes a quiz by its ID
    public boolean deleteQuizById(int quizId) throws SQLException {
        String sql = "DELETE FROM quizzes WHERE id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quizId);
            int affected = stmt.executeUpdate();
            return affected > 0;
        }
    }
}
