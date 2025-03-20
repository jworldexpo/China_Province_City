package com.jx.iam;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Service for authentication and authorization.
 */
public class AuthService {
    private final Map<String, User> users = new HashMap<>();
    private final Map<String, String> sessions = new HashMap<>(); // sessionId -> userId

    /**
     * Registers a new user.
     * 
     * @param username The username
     * @param email The email address
     * @param password The password (should be hashed in a real implementation)
     * @return The created user
     */
    public User registerUser(String username, String email, String password) {
        // In a real implementation, password should be hashed
        String userId = UUID.randomUUID().toString();
        User user = new User(userId, username, email, UserRole.USER);
        users.put(userId, user);
        return user;
    }

    /**
     * Authenticates a user.
     * 
     * @param username The username
     * @param password The password
     * @return A session ID if authentication is successful, null otherwise
     */
    public String login(String username, String password) {
        // In a real implementation, this would check against a database
        // and verify the hashed password
        for (User user : users.values()) {
            if (user.getUsername().equals(username)) {
                // Password check would go here
                String sessionId = UUID.randomUUID().toString();
                sessions.put(sessionId, user.getId());
                return sessionId;
            }
        }
        return null;
    }

    /**
     * Logs out a user.
     * 
     * @param sessionId The session ID
     */
    public void logout(String sessionId) {
        sessions.remove(sessionId);
    }

    /**
     * Gets the current user from a session.
     * 
     * @param sessionId The session ID
     * @return The user, or null if the session is invalid
     */
    public User getCurrentUser(String sessionId) {
        String userId = sessions.get(sessionId);
        if (userId == null) {
            return null;
        }
        return users.get(userId);
    }

    /**
     * Checks if a user has a specific role.
     * 
     * @param sessionId The session ID
     * @param role The role to check
     * @return True if the user has the role, false otherwise
     */
    public boolean hasRole(String sessionId, UserRole role) {
        User user = getCurrentUser(sessionId);
        return user != null && user.getRole() == role;
    }
}