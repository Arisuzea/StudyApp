package studyapp.model;

import javafx.beans.property.*;

public class User {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty username = new SimpleStringProperty();

    public User(int id, String username) {
        this.id.set(id);
        this.username.set(username);
    }

    public int getId() { return id.get(); }
    public String getUsername() { return username.get(); }

    public IntegerProperty idProperty() { return id; }
    public StringProperty usernameProperty() { return username; }

    @Override
    public String toString() {
        return getUsername(); // For display in ListView
    }
}
