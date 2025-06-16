package studyapp.util;

import studyapp.model.User;

public class Session {
    private static User loggedInUser;

    // Sets the currently logged-in user
    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    // Returns the currently logged-in user
    public static User getLoggedInUser() {
        return loggedInUser;
    }

    // Clears the session (logs out the user)
    public static void clear() {
        loggedInUser = null;
    }
}
