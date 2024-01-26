/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.entities.session;

import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Admin
 */
public class PaidSession extends Session{
    private Double price;
     private SessionChat sessionChat;

  


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public SessionChat getsessionChat() {
        return sessionChat;
    }

    public void setsessionChat(SessionChat sessionChat) {
        this.sessionChat = sessionChat;
    }

    
    @Override
    public String toString() {
        return super.toString()+" Paidsession{" + "price=" + price +" sessionChat="+sessionChat+ '}';
    }
    
}
