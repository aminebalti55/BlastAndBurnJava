/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.entities.session;

import blastandburn.entities.user.User;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.ImageView;

/**
 *
 * @author Admin
 */
public class Session {
 private int sessionId;
    private List<UserSessionX> userssession = new ArrayList<UserSessionX>();
    private SessionCategory category;
    private String imgUrl;
    private String title;
    private String description;
    private int minUsers;
    private int maxUsers;
    private List<SessionActions> actions;
    private boolean isDeleted;
    private Timestamp deletedAt;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private ImageView img;
    private User user;
    private String cat;

    public Session() {
    }

    public Session(int idT,int idCat,String title, String description, int minUsers, int maxUsers) {
        this.sessionId=idT;
        
        this.title = title;
        this.description = description;
        this.minUsers = minUsers;
        this.maxUsers = maxUsers;
    }
    
    

    public int getsessionId() {
        return sessionId;
    }

    public void setsessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public List<UserSessionX> getUserssessions() {
        return userssession;
    }

    public void setUserssessions(List<UserSessionX> users) {
        this.userssession = users;
    }

    public SessionCategory getCategory() {
        return category;
    }

    public void setCategory(SessionCategory category) {
        this.category = category;
        cat=category.getName();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public int getMinUsers() {
        return minUsers;
    }

    public void setMinUsers(int minUsers) {
        this.minUsers = minUsers;
    }

    public int getMaxUsers() {
        return maxUsers;
    }

    public void setMaxUsers(int maxUsers) {
        this.maxUsers = maxUsers;
    }

    public List<SessionActions> getActions() {
        return actions;
    }

    public void setActions(List<SessionActions> actions) {
        this.actions = actions;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Timestamp modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    
    
    @Override
    public String toString() {
        return "session{" + "sessionId=" + sessionId + ", userssession=" + userssession + ", category=" + category + ", imgUrl=" + imgUrl + ", title=" + title + ", description=" + description + ", minUsers=" + minUsers + ", maxUsers=" + maxUsers + ", actions=" + actions + ", isDeleted=" + isDeleted + ", deletedAt=" + deletedAt + ", createdAt=" + createdAt + ", modifiedAt=" + modifiedAt + ", img=" + img + ", user=" + user + '}';
    }

    
    
   
}
