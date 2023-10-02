package com.example.reccify;

public class CurrentUserFile {
    private static UserFile currentUser;

    public static void setCurrentUser(UserFile theCurrentUser) {
        currentUser = theCurrentUser;
    }

    public static void removeCurrentUser() {
        currentUser = null;
    }

    public static UserFile getCurrentUser() {
        return currentUser;
    }
}
