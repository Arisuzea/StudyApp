package studyapp.util;

import studyapp.model.Lesson;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LessonDAO {

    public static List<Lesson> getAllLessons() {
        List<Lesson> lessons = new ArrayList<>();

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM lessons");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String shortDescription = rs.getString("short_description");
                String content = rs.getString("content");

                lessons.add(new Lesson(id, title, shortDescription, content));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lessons;
    }
}
