package studyapp.model;

import javafx.beans.property.*;

public class User {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty username = new SimpleStringProperty();
    private final BooleanProperty isAdmin = new SimpleBooleanProperty(); // ✅ NEW

    public User(int id, String username, boolean isAdmin) {
        this.id.set(id);
        this.username.set(username);
        this.isAdmin.set(isAdmin); // ✅ NEW
    }

    public int getId() { return id.get(); }
    public String getUsername() { return username.get(); }
    public boolean isAdmin() { return isAdmin.get(); } // ✅ NEW

    public IntegerProperty idProperty() { return id; }
    public StringProperty usernameProperty() { return username; }
    public BooleanProperty isAdminProperty() { return isAdmin; } // ✅ NEW

    @Override
    public String toString() {
        return getUsername(); // For display in ListView
    }
}
