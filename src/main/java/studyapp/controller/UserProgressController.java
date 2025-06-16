package studyapp.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.beans.value.ObservableValue;
import studyapp.model.User;
import studyapp.model.QuizProgress;
import studyapp.util.UserDAO;
import studyapp.util.ProgressDAO;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import java.util.List;

public class UserProgressController {

    @FXML private TextField searchField;
    @FXML private ListView<User> userList;
    @FXML private Label userNameLabel;
    @FXML private TableView<QuizProgress> quizTable;
    @FXML private TableColumn<QuizProgress, String> quizCol;
    @FXML private TableColumn<QuizProgress, Integer> scoreCol;
    @FXML private TableColumn<QuizProgress, String> statusCol;
    @FXML private TableColumn<QuizProgress, String> dateCol;
    @FXML private Button btnViewDetails;

    private List<User> allUsers;
    private User currentUser;

    private final DateTimeFormatter dbFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy h:mm a");


    @FXML
    public void initialize() {
        configureTableColumns();
        loadUsers();

        userList.getSelectionModel().selectedItemProperty().addListener(
            (ObservableValue<? extends User> obs, User oldVal, User selectedUser) -> {
                if (selectedUser != null && !selectedUser.equals(currentUser)) {
                    currentUser = selectedUser;
                    userNameLabel.setText(selectedUser.getUsername());
                    loadProgressForUser(selectedUser.getId());
                }
            }
        );

        searchField.setOnKeyReleased(this::filterUserList);

        btnViewDetails.setOnAction(e -> {
            // Optional: implement detailed view later
        });
    }

    private void configureTableColumns() {
        quizCol.setCellValueFactory(data -> data.getValue().quizTitleProperty());
        scoreCol.setCellValueFactory(data -> data.getValue().scoreProperty().asObject());
        statusCol.setCellValueFactory(data -> data.getValue().statusProperty());
        dateCol.setCellValueFactory(data -> {
            String dateStr = data.getValue().getDateTaken();
            String formatted = "—";
            if (dateStr != null && !dateStr.isEmpty()) {
                try {
                    LocalDateTime parsed = LocalDateTime.parse(dateStr, dbFormatter); // 👈 use custom parser
                    formatted = parsed.format(dateFormatter); // 👈 pretty format
                } catch (Exception e) {
                    formatted = "Invalid Date";
                }
            }
            return new javafx.beans.property.SimpleStringProperty(formatted);
        });
    }

    private void loadUsers() {
        allUsers = UserDAO.getAllNonAdminUsers();
        userList.setItems(FXCollections.observableArrayList(allUsers));
    }

    private void loadProgressForUser(int userId) {
        List<QuizProgress> progressList = ProgressDAO.getProgressByUser(userId);
        quizTable.setItems(FXCollections.observableArrayList(progressList));
    }

    private void filterUserList(KeyEvent event) {
        String filter = searchField.getText().toLowerCase();
        var filtered = allUsers.stream()
            .filter(u -> u.getUsername().toLowerCase().contains(filter))
            .toList();
        userList.setItems(FXCollections.observableArrayList(filtered));
    }
}
