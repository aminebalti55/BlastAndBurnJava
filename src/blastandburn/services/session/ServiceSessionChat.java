/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.services.session;
import blastandburn.entities.session.SessionChat;
import blastandburn.iservices.session.IServiceSessionChat;
import blastandburn.utils.MyConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ServiceSessionChat implements IServiceSessionChat{
     private Connection con;

    public ServiceSessionChat() {
        con = MyConnection.getInstance().getConnection();
    }

     
    @Override
    public void createSessionChat(SessionChat tc) {
        try {
            String query = "INSERT INTO Session_chat( Session_id) "
                    + "VALUES ('" + tc.getPaidsession()  + "');";
            Statement st = con.createStatement();
            st.executeUpdate(query);
            System.out.println("Session chat ajouter ");
        } catch (SQLException ex) {
            System.out.println("erreur lors de l'ajout " + ex);
        }  
    }

    @Override
    public List<SessionChat> ListSessionChat() {
    ArrayList<SessionChat> t = new ArrayList();
        SessionChat SessionChat = new SessionChat();
        try {

            Statement st = con.createStatement();
            String query = "select * from Session_chat;";
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                SessionChat.setChatId(rs.getInt("chat_id"));
                
                //SessionChat.setPaidSession(rs.getInt("Session_id"));
               
                t.add(SessionChat);
            }
        } catch (SQLException ex) {
            System.out.println("erreur lors de l'affichage");
        }
        return t;    
    }

    @Override
    public void updateSessionChat(SessionChat tc) {
     try {
            
            String query = "UPDATE  Session_chat set chat_id=" + tc.getChatId()+ "',' Session_id=" + tc.getPaidsession().getsessionId() +  ";";
            Statement st = con.createStatement();
            st.executeUpdate(query);
            System.out.println("modification avec succes");

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }       
    }

    @Override
    public void deleteSessionChat(int idTCh) {
     
        try {
          
            String query = " DELETE FROM Session_chat WHERE chat_id="+ idTCh + ";";
            Statement st = con.createStatement();
            st.executeUpdate(query);
            System.out.println("suppression avec succes");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public SessionChat searchSessionChat(int idTCh) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
