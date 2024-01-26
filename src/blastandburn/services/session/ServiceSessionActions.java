/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.services.session;

import blastandburn.entities.session.Session;
import blastandburn.entities.session.SessionActions;
import blastandburn.iservices.session.IServiceSessionActions;
import blastandburn.utils.MyConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class ServiceSessionActions implements IServiceSessionActions {

    Connection con;

    public ServiceSessionActions() {
        con = MyConnection.getInstance().getConnection();
    }

    @Override
    public void createSessionActions(int id, SessionActions t) {
        try {
            Statement st = con.createStatement();
            Calendar calendar = Calendar.getInstance();
            Timestamp d = new Timestamp(calendar.getTime().getTime());
            String query = "INSERT INTO Session_actions( Session_id, title, description) "
                    + "VALUES ('" + id + "','" + t.getTitle() + "','" + t.getDescription() + "');";
            st.executeUpdate(query);
            System.out.println("Session actions ajouter ");
        } catch (SQLException ex) {
            System.out.println("erreur lors de l'ajout " + ex);
        }
    }

    @Override
    public List<SessionActions> ListSessionActions() {
        ArrayList<SessionActions> t = new ArrayList();

        try {

            Statement st = con.createStatement();
            String query = "SELECT action_id, Session_id, title, description FROM Session_actions";
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                SessionActions SessionAction = new SessionActions();
                SessionAction.setActionId(rs.getInt("action_id"));
                SessionAction.setTitle(rs.getString("title"));
                //SessionAction.setSession(rs.getInt("Session_id"));
                SessionAction.setTitle(rs.getString("description"));

                t.add(SessionAction);
            }
        } catch (SQLException ex) {
            System.out.println("erreur lors de l'affichage");
        }
        return t;
    }

    @Override
    public void updateSessionActions(int id,SessionActions t) {
        try {

            String query = "UPDATE  Session_actions set title='" + t.getTitle() + "', description='" + t.getDescription() + "' where action_id="+id+";";
            Statement st = con.createStatement();
            st.executeUpdate(query);
            System.out.println("modification avec succes");

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteSessionActions(int idTA) {
        try {

            String query = "DELETE FROM Session_actions WHERE action_id=" + idTA + ";";
            Statement st = con.createStatement();
            st.executeUpdate(query);
            System.out.println("suppression avec succes");

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    @Override
    public SessionActions searchSessionActions(int idTA) {
       SessionActions ta = null;
        String query = "SELECT action_id, Session_id, title, description FROM Session_actions where action_id="+idTA;
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                SessionActions SessionAction = new SessionActions();
                SessionAction.setActionId(rs.getInt("action_id"));
                SessionAction.setTitle(rs.getString("title"));
                SessionAction.setDescription(rs.getString("description"));
                ta=SessionAction;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
       return ta;
    }

    @Override
    public List<SessionActions> ListSessionActionsBySessionId(int id) {
        ArrayList<SessionActions> t = new ArrayList();
        try {

            Statement st = con.createStatement();
            String query = "SELECT action_id, Session_id, title, description FROM Session_actions where Session_id=" + id;
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                SessionActions SessionAction = new SessionActions();
                SessionAction.setActionId(rs.getInt("action_id"));
                SessionAction.setTitle(rs.getString("title"));
                SessionAction.setDescription(rs.getString("description"));

                t.add(SessionAction);
            }
        } catch (SQLException ex) {
            System.out.println("erreur lors de l'affichage");
        }
        return t;
    }
}
