/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.services.session;

import blastandburn.entities.session.SessionMessage;
import blastandburn.iservices.session.IServiceSessionMessage;
import blastandburn.utils.MyConnection;
import java.sql.Connection;
import java.util.List;
/**
 *
 * @author Admin
 */
public class ServiceSessionMessage implements IServiceSessionMessage{
     Connection con;

    public ServiceSessionMessage() {
        con = MyConnection.getInstance().getConnection();
    }
     
   @Override
    public void createSessionMessage(SessionMessage tm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SessionMessage> ListSessionMessage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateSessionMessage(SessionMessage tm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteSessionMessage(int idTM) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SessionMessage searchSessionMessage(int idTCM) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    } 
}
