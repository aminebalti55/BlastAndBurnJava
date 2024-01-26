/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.entities.task;

import blastandburn.entities.session.*;
import blastandburn.entities.user.User;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class SessionMessage {
    
   
    private int msgId;
    private SessionChat sessionChat;
    private List<User> users= new ArrayList<>();
    private String msg;
    private Timestamp createdAt;
    private Timestamp modifiedAt;

   
    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    public SessionChat getsessionChat() {
        return sessionChat;
    }

    public void setsessionChat(SessionChat sessionChat) {
        this.sessionChat = sessionChat;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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

    @Override
    public String toString() {
        return "sessionMessage{" + "msgId=" + msgId + ", sessionChat=" + sessionChat /*+ ", users=" + users */+ ", msg=" + msg + ", createdAt=" + createdAt + ", modifiedAt=" + modifiedAt + '}';
    }
    
}
