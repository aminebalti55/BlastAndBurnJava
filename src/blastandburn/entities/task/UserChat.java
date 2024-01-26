/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.entities.task;

import blastandburn.entities.session.*;
import blastandburn.entities.user.User;

/**
 *
 * @author Admin
 */
public class UserChat {
    private User user;
    private SessionChat chat;

    public UserChat() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SessionChat getChat() {
        return chat;
    }

    public void setChat(SessionChat chat) {
        this.chat = chat;
    }
    
    
    
}
