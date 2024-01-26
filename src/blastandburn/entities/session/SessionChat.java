/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.entities.session;

import blastandburn.entities.user.User;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class SessionChat {
private int chatId;
    private List<UserSessionX> usersession = new ArrayList<UserSessionX>();
    private List<SessionMessage> messages = new ArrayList<>();
    private PaidSession paidsession;

    public SessionChat() {
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public List<SessionMessage> getMessages() {
        return messages;
    }

    public List<UserSessionX> getUsersession() {
        return usersession;
    }

    public void setUsersession(List<UserSessionX> users) {
        this.usersession = users;
    }

    public void setMessages(List<SessionMessage> messages) {
        this.messages = messages;
    }

    public PaidSession getPaidsession() {
        return paidsession;
    }

    public void setPaidsession(PaidSession paidsession) {
        this.paidsession = paidsession;
    }
    
   

    @Override
    public String toString() {
        return "sessionChat{" + "chatId=" + chatId + ", usersession=" + usersession  + " message=" + messages+" paidsession="+paidsession + '}';
    }

}
