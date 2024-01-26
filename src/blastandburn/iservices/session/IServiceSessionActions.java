/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.iservices.session;

import blastandburn.entities.session.SessionActions;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface IServiceSessionActions {
     public void createSessionActions(int id,SessionActions t);
    public List<SessionActions> ListSessionActions();
    public void updateSessionActions(int id,SessionActions t);
    public void deleteSessionActions(int idTA);
    public SessionActions searchSessionActions(int idTA);
    public List<SessionActions> ListSessionActionsBySessionId(int id);

}
