package studyapp.model;

import javafx.beans.property.*;

public class User {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty username = new SimpleStringProperty();
    private final BooleanProperty isAdmin = new SimpleBooleanProperty();

    // Constructs a user with id, username, and admin status
    public User(int id, String username, boolean isAdmin) {
        this.id.set(id);
        this.username.set(username);
        this.isAdmin.set(isAdmin);
    }

    // Returns the user ID
    public int getId() { return id.get(); }
    // Returns the username
    public String getUsername() { return username.get(); }
    // Returns whether the user is an admin
    public boolean isAdmin() { return isAdmin.get(); }
    // Returns the ID property
    public IntegerProperty idProperty() { return id; }
    // Returns the username property
    public StringProperty usernameProperty() { return username; }
    // Returns the admin property
    public BooleanProperty isAdminProperty() { return isAdmin; }
    // Returns the username for display
    @Override
    public String toString() {
        return getUsername();
    }
}
