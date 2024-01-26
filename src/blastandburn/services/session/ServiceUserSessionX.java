/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.services.session;

import blastandburn.entities.session.PaidSession;
import blastandburn.entities.session.Session;
import blastandburn.entities.session.UserSessionX;
import blastandburn.iservices.session.IServiceUserSession;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;

/**
 *
 * @author Admin
 */
public class ServiceUserSessionX implements IServiceUserSession {

    Connection con;
    private static String projectPath = System.getProperty("user.dir").replace("\\", "/");
    ServiceSessionCategory stc = new ServiceSessionCategory();

    public ServiceUserSessionX() {
        con = MyConnection.getInstance().getConnection();
    }

    @Override
    public void participer(int idUser, int idSession) {
        try {
            Statement st = con.createStatement();
            String query = "INSERT INTO user_Session( user_id,Session_id) "
                    + "VALUES ('" + idUser + "','" + idSession + "');";
            System.out.println(query);
            st.executeUpdate(query);
            System.out.println("Session actions ajouter ");
        } catch (SQLException ex) {
            System.out.println("erreur lors de l'ajout " + ex);
        }
    }

    @Override
    public ObservableList<Session> ListerSessionsByIdUser(int id) {
        //select * from Session t join user_Session ut on t.Session_id=ut.Session_id where ut.user_id=1 and t.Session_id not in (select tt.Session_id from Session tt natural join paid_Session pt join user_Session ut on pt.Session_id=ut.Session_id where ut.user_id=1)
        ObservableList<Session> l = FXCollections.observableArrayList();
        ImageView img = null;
        String query = "select * from Session t join user_Session ut on t.Session_id=ut.Session_id where ut.user_id=" + id + " and t.Session_id not in (select tt.Session_id from Session tt natural join paid_Session pt join user_Session ut on pt.Session_id=ut.Session_id where ut.user_id=" + id + ")";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Session Session = new Session();
                Session.setsessionId(rs.getInt("Session_id"));
                Session.setDescription(rs.getString("description"));
                Session.setTitle(rs.getString("title"));
                Session.setMinUsers(rs.getInt("min_users"));
                Session.setMaxUsers(rs.getInt("max_users"));
                Session.setCategory(stc.searchSessionCategoryById(rs.getInt("cat_id")));
                String url = "file:///" + projectPath + "/src/blastandburn/resources/images/Sessions/" + rs.getString("img_url");
                img = new ImageView(url);
                Session.setImg(img);

                l.add(Session);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return l;
    }

    @Override
    public ObservableList<PaidSession> ListerPaidSessionsByIdUser(int id) {
        ImageView img = null;
        ObservableList<PaidSession> l = FXCollections.observableArrayList();
        String query = "select * from Session natural join paid_Session pt join user_Session ut on pt.Session_id=ut.Session_id where ut.user_id=" + id;
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                PaidSession pt = new PaidSession();
                pt.setsessionId(rs.getInt("Session_id"));
                pt.setTitle(rs.getString("title"));
                pt.setDescription(rs.getString("description"));
                pt.setMinUsers(rs.getInt("min_users"));
                pt.setMaxUsers(rs.getInt("max_users"));
                pt.setPrice(rs.getDouble("price"));
                pt.setCategory(stc.searchSessionCategoryById(rs.getInt("cat_id")));
                String url = "file:///" + projectPath + "/src/blastandburn/resources/images/Sessions/" + rs.getString("img_url");
                img = new ImageView(url);
                pt.setImg(img);
                l.add(pt);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return l;
    }

    ServiceSession serviceT = new ServiceSession();

    public UserSessionX getUserSession(int idU, int idT) {
        UserSessionX ut = new UserSessionX();
        try {

            String query = "select * from user_Session where user_id=" + idU + " and Session_id=" + idT;
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                UserSessionX u = new UserSessionX();
                u.setsession(serviceT.getSession(rs.getInt("Session_id")));
                u.setUser(serviceT.getUserById(rs.getInt("user_id")));
                ut = u;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ut;
    }

    @Override
    public Session ListerOneSessionsByIdUser(int id) {
    
        ImageView img = null;
        Session l = new Session();
        String query = "select * from Session t join user_Session ut on t.Session_id=ut.Session_id where ut.user_id=" + id + " and t.Session_id not in (select tt.Session_id from Session tt natural join paid_Session pt join user_Session ut on pt.Session_id=ut.Session_id where ut.user_id=" + id + ")   LIMIT 1";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Session Session = new Session();
                Session.setsessionId(rs.getInt("Session_id"));
                Session.setDescription(rs.getString("description"));
                Session.setTitle(rs.getString("title"));
                Session.setMinUsers(rs.getInt("min_users"));
                Session.setMaxUsers(rs.getInt("max_users"));
                Session.setCategory(stc.searchSessionCategoryById(rs.getInt("cat_id")));
                String url = "file:///" + projectPath + "/src/blastandburn/resources/images/Sessions/" + rs.getString("img_url");
                img = new ImageView(url);
                Session.setImg(img);

                l=Session;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return l;
    }

    @Override
    public PaidSession ListerOnePaidSessionsByIdUser(int id) {
    
        ImageView img = null;
        PaidSession l = new PaidSession();
        String query = "select * from Session natural join paid_Session pt join user_Session ut on pt.Session_id=ut.Session_id where ut.user_id=" + id+"   LIMIT 1";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                PaidSession pt = new PaidSession();
                pt.setsessionId(rs.getInt("Session_id"));
                pt.setTitle(rs.getString("title"));
                pt.setDescription(rs.getString("description"));
                pt.setMinUsers(rs.getInt("min_users"));
                pt.setMaxUsers(rs.getInt("max_users"));
                pt.setPrice(rs.getDouble("price"));
                pt.setCategory(stc.searchSessionCategoryById(rs.getInt("cat_id")));
                String url = "file:///" + projectPath + "/src/blastandburn/resources/images/Sessions/" + rs.getString("img_url");
                img = new ImageView(url);
                pt.setImg(img);
                l=pt;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return l;
    }
    
    
    public List<UserSessionX> getNbrParticipateByDate(){
        List<UserSessionX> listUserSessions=new ArrayList();
        String query="SELECT count(user_id) as nb , Date(created_at) as date FROM `user_Session` GROUP BY Date(created_at)";
        try {                            
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                UserSessionX ut=new UserSessionX();
                ut.setNbr(rs.getInt("nb"));
                ut.setCreatedAt(rs.getDate("date"));
                listUserSessions.add(ut);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return listUserSessions;
    }

  

  
}
