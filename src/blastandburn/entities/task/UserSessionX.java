/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.entities.task;

import blastandburn.entities.session.*;
import blastandburn.entities.user.User;
import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author Admin
 */
public class UserSessionX {

    private User user;
    private Session session;
    private Date createdAt;
    private int nbr;

    public UserSessionX() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Session getsession() {
        return session;
    }

    public void setsession(Session session) {
        this.session = session;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getNbr() {
        return nbr;
    }

    public void setNbr(int nbr) {
        this.nbr = nbr;
    }

    
}
