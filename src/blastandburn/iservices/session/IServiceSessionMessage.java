/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.iservices.session;

import blastandburn.entities.session.SessionMessage;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface IServiceSessionMessage {
    public void createSessionMessage(SessionMessage tm);

    public List<SessionMessage> ListSessionMessage();

    public void updateSessionMessage(SessionMessage tm);

    public void deleteSessionMessage(int idTM);

    public SessionMessage searchSessionMessage(int idTCM);
}
