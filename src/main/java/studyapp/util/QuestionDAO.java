package studyapp.util;

import studyapp.model.Question;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public void deleteQuestionsByQuizId(int quizId) throws SQLException {
        String sql = "DELETE FROM questions WHERE quiz_id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quizId);
            stmt.executeUpdate();
        }
    }

    public List<Question> getQuestionsByQuizId(int quizId) throws SQLException {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT id, question_text, correct_option FROM questions WHERE quiz_id = ?";

        try (Connection conn = DatabaseManager.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quizId);
            ResultSet rs = stmt.executeQuery();

            OptionDAO optionDAO = new OptionDAO(); // ✅ Fetch options

            while (rs.next()) {
                int id = rs.getInt("id");
                String text = rs.getString("question_text");
                char correct = rs.getString("correct_option").charAt(0);

                Question q = new Question(id, text, correct);

                // ✅ Set options from DB
                List<String> options = optionDAO.getOptionsByQuestionId(id);
                q.setOptions(options);

                questions.add(q);
            }
        }

        return questions;
    }


    public void deleteQuestionsAndOptionsByQuizId(int quizId) throws SQLException {
        String getQuestionsSql = "SELECT id FROM questions WHERE quiz_id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement getStmt = conn.prepareStatement(getQuestionsSql)) {
            getStmt.setInt(1, quizId);
            ResultSet rs = getStmt.executeQuery();

            OptionDAO oDAO = new OptionDAO();
            while (rs.next()) {
                int questionId = rs.getInt("id");
                oDAO.deleteOptionsByQuestionId(questionId);
            }
        }
        deleteQuestionsByQuizId(quizId);
    }
}
