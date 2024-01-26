/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.entities.task;

import blastandburn.entities.session.*;

/**
 *
 * @author Admin
 */
public class SessionActions {
     private int actionId;
    private Session session;
    private String title;
    private String description;

    public SessionActions(int actionId, String title, String description) {
        this.actionId = actionId;
        this.title = title;
        this.description = description;
    }

    public SessionActions() {
    }

   

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public Session getsession() {
        return session;
    }

    public void setsession(Session session) {
        this.session = session;
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

    @Override
    public String toString() {
        return "sessionActions{" + "actionId=" + actionId + ", session=" + session + ", title=" + title + ", description=" + description + '}';
    }
    
}
