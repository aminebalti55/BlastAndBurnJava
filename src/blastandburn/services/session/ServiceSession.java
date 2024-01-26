/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.services.session;

import blastandburn.entities.session.Session;
import blastandburn.entities.session.SessionCategory;
import blastandburn.entities.user.User;
import blastandburn.iservices.session.IServiceSession;
import blastandburn.utils.MyConnection;
import java.sql.Connection;
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
import javafx.scene.image.ImageView;

/**
 *
 * @author Admin
 */
public class ServiceSession implements IServiceSession {

    private Connection con;
    private static String projectPath = System.getProperty("user.dir").replace("\\", "/");
    private ServiceSessionCategory stc = new ServiceSessionCategory();

    public ServiceSession() {
        con = MyConnection.getInstance().getConnection();
    }

    @Override
    public void createSession(int coachid, String title, Session t) {
        try {
            Calendar calendar = Calendar.getInstance();
            Timestamp d = new Timestamp(calendar.getTime().getTime());
            Statement st = con.createStatement();
            String selectCategoryId = "select * from Session_category where name='" + title + "';";
            ResultSet rs = st.executeQuery(selectCategoryId);
            while (rs.next()) {
                SessionCategory tc = new SessionCategory();
                tc.setCatgid(rs.getInt("cat_id"));
                t.setCategory(tc);
            }
            String query = "INSERT INTO session(u_id,cat_id, img_url, title, description, min_users, max_users, created_at) "
                    + "VALUES ('" + coachid + "','" + t.getCategory().getCatgid() + "','" + t.getImgUrl() + "','"
                    + t.getTitle() + "','" + t.getDescription() + "','" + t.getMinUsers() + "','"
                    + t.getMaxUsers() + "','" + d + "');";

            st.executeUpdate(query);

            System.out.println("Session ajouter ");
        } catch (SQLException ex) {
            System.out.println("erreur lors de l'ajout " + ex);
        }
    }

    @Override
    public List<Session> ListSession() {

        ArrayList<Session> t = new ArrayList();
        ImageView img = null;
        try {

            Statement st = con.createStatement();
            String query = "select Session_id,cat_id,img_url,title,description,min_users,max_users from Session  where is_deleted=0 and Session_id not in(select t.Session_id from paid_Session t natural join Session ) order by created_at DESC;";
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
                t.add(Session);
            }
        } catch (SQLException ex) {
            System.out.println("erreur lors de l'affichage");
        }
        return t;
    }

    @Override
    public void updateSession(Session t, int idt) {
        try {
            Calendar calendar = Calendar.getInstance();
            Timestamp d = new Timestamp(calendar.getTime().getTime());
            String query = "UPDATE  Session set cat_id=" + t.getCategory().getCatgid() + ", img_url='" + t.getImgUrl() + "', title='" + t.getTitle() + "', description='" + t.getDescription() + "', min_users=" + t.getMinUsers() + ", max_users=" + t.getMaxUsers() + " where Session_id=" + idt + ";";
            Statement st = con.createStatement();
            st.executeUpdate(query);
            System.out.println("modification avec succes");

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteSession(int idt) {

        try {
            Calendar calendar = Calendar.getInstance();
            Timestamp d = new Timestamp(calendar.getTime().getTime());
            String query = "UPDATE  Session set  is_deleted=" + 1 + ",deleted_at='" + d + "' where Session_id=" + idt;
            Statement st = con.createStatement();
            st.executeUpdate(query);
            System.out.println("suppression avec succes");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public Session getSession(int idT) {
        Session t = null;
        ImageView img = null;
        try {

            Statement st = con.createStatement();
            String query = "select Session_id,u_id,cat_id,img_url,title,description,min_users,max_users,created_at from Session  where Session_id=" + idT + " and is_deleted=0 ;";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Session Session = new Session();
                Session.setsessionId(rs.getInt("Session_id"));
                Session.setDescription(rs.getString("description"));
                Session.setTitle(rs.getString("title"));
                Session.setMinUsers(rs.getInt("min_users"));
                Session.setMaxUsers(rs.getInt("max_users"));
                Session.setCategory(stc.searchSessionCategoryById(rs.getInt("cat_id")));
                Session.setImgUrl(rs.getString("img_url"));
                User u=getUserById(rs.getInt("u_id"));
                Session.setUser(u);
                String url = "file:///" + projectPath + "/src/blastandburn/resources/images/Sessions/" + rs.getString("img_url");
                img = new ImageView(url);
                Session.setImg(img);
                Session.setCreatedAt(rs.getTimestamp("created_at"));
                t = Session;
            }

        } catch (SQLException ex) {
            System.out.println("erreur " + ex.getMessage());
        }
        return t;
    }
    
    public User getUserById(int idU){
        User u = null;
        String query="select * from user where user_id="+idU;
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                User user=new User();
                user.setUserId(rs.getInt("user_id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setBalance(rs.getDouble("balance"));
                u=user;
            }
        } catch (SQLException ex) {
            System.out.println("erreur " + ex.getMessage());
        }
        return u;
    }
    
    public List<Session> ListSessionByIdUser(int idU) {

        ArrayList<Session> t = new ArrayList();
        ImageView img = null;
        try {

            Statement st = con.createStatement();
            String query = "select Session_id,cat_id,img_url,title,description,min_users,max_users from Session  where is_deleted=0 and u_id="+idU+" and Session_id not in(select t.Session_id from paid_Session t natural join Session ) order by created_at DESC;";
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
                t.add(Session);
            }
        } catch (SQLException ex) {
            System.out.println("erreur lors de l'affichage");
        }
        return t;
    }

    @Override
    public int getCountSession() {
    
        int nbr=0;
        try {

            Statement st = con.createStatement();
            String query = "select COUNT(Session_id) as n from Session where is_deleted=0 and Session_id not in(select t.Session_id from paid_Session t natural join Session );";
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                int t=rs.getInt("n");
                nbr=t;
            }
        } catch (SQLException ex) {
            System.out.println("erreur lors de l'affichage");
        }
        return nbr;
    }
    
    public Session getLastSession(){
        Session Session =null;
        ImageView img = null;
        try {

            Statement st = con.createStatement();
            String query = "SELECT * FROM `Session` where is_deleted=0 ORDER BY created_at DESC limit 1";
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Session t = new Session();
                t.setsessionId(rs.getInt("Session_id"));
                t.setTitle(rs.getString("title"));
                t.setUser(getUserById(rs.getInt("u_id")));
                t.setCreatedAt(rs.getTimestamp("created_at"));
               Session=t;
            }
        } catch (SQLException ex) {
            System.out.println("erreur lors de l'affichage");
        }
        return Session;
    }

    @Override
    public List<Session> searchSessionByName(String title) {
    
          ArrayList<Session> t = new ArrayList();
        ImageView img = null;
        try {

            Statement st = con.createStatement();
            String query = "select * from Session  where is_deleted=0 and title like '"+title+"%' and Session_id not in(select t.Session_id from paid_Session t natural join Session ) order by created_at DESC;";
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Session Session = new Session();
                Session.setsessionId(rs.getInt("session_id"));
                Session.setDescription(rs.getString("description"));
                Session.setTitle(rs.getString("title"));
                Session.setMinUsers(rs.getInt("min_users"));
                Session.setMaxUsers(rs.getInt("max_users"));
                Session.setCategory(stc.searchSessionCategoryById(rs.getInt("cat_id")));
                String url = "file:///" + projectPath + "/src/blastandburn/resources/images/Sessions/" + rs.getString("img_url");
                img = new ImageView(url);
                Session.setImg(img);
                t.add(Session);
            }
        } catch (SQLException ex) {
            System.out.println("erreur lors de l'affichage");
        }
        return t;
    }

}
