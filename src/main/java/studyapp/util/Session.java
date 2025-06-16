package studyapp.util;

import studyapp.model.User;

public class Session {
    private static User loggedInUser;

    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void clear() {
        loggedInUser = null;
    }
}
