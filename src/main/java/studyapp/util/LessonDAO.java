package studyapp.util;

import studyapp.model.Lesson;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LessonDAO {

    public static boolean saveLesson(Lesson lesson) {
        String sql = "INSERT INTO lessons (title, short_description, content, topic, difficulty) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, lesson.getTitle());
            stmt.setString(2, lesson.getShortDescription());
            stmt.setString(3, lesson.getContent());
            stmt.setString(4, lesson.getTopic());
            stmt.setString(5, lesson.getDifficulty());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Lesson> getAllLessons() {
        List<Lesson> lessons = new ArrayList<>();
        String sql = "SELECT * FROM lessons";

        try (Connection conn = DatabaseManager.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Lesson lesson = new Lesson(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("short_description"),
                    rs.getString("content"),
                    rs.getString("topic"),
                    rs.getString("difficulty")
                );
                lessons.add(lesson);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lessons;
    }

    public static boolean updateLesson(Lesson lesson) {
        String sql = "UPDATE lessons SET title = ?, short_description = ?, content = ?, topic = ?, difficulty = ? WHERE id = ?";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, lesson.getTitle());
            stmt.setString(2, lesson.getShortDescription());
            stmt.setString(3, lesson.getContent());
            stmt.setString(4, lesson.getTopic());
            stmt.setString(5, lesson.getDifficulty());
            stmt.setInt(6, lesson.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteLessonById(int id) {
        String sql = "DELETE FROM lessons WHERE id = ?";

        try (Connection conn = DatabaseManager.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
