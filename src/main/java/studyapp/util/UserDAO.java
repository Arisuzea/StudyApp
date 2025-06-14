package studyapp.util;

import java.sql.*;
import java.util.*;
import studyapp.model.User;

public class UserDAO {
    public static List<User> getAllNonAdminUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, username FROM users WHERE is_admin = 0";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                users.add(new User(rs.getInt("id"), rs.getString("username")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
