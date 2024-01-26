/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.iservices.session;

import blastandburn.entities.session.SessionChat;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface IServiceSessionChat {
     public void createSessionChat(SessionChat tc);

    public List<SessionChat> ListSessionChat();

    public void updateSessionChat(SessionChat tc);

    public void deleteSessionChat(int idTCh);

    public SessionChat searchSessionChat(int idTCh);
}
