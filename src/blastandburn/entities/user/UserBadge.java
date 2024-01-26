package blastandburn.entities.user;

import java.sql.Timestamp;

/**
 *
 * @author fatma
 */
public class UserBadge {
    private User user;
    private Badge badge;
    private Timestamp createdAt;
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Badge getBadge() {
        return badge;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    
}
