package org.example.utils;

import org.example.dto.UserDTO;

public class UserHelper {
    
    private UserHelper() {
        
    }

    public static String formatUserName(UserDTO user) {
        return String.format("%s, %s", user.getFirstName(), user.getSecondName());
    }
}
