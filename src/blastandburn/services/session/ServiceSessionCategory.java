/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.services.session;

import blastandburn.entities.session.PaidSession;
import blastandburn.entities.session.Session;
import blastandburn.entities.session.SessionCategory;
import blastandburn.iservices.session.IServiceSessionCategory;
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
public class ServiceSessionCategory implements IServiceSessionCategory {

    Connection con;
    private static String projectPath = System.getProperty("user.dir").replace("\\", "/");

    public ServiceSessionCategory() {
        con = MyConnection.getInstance().getConnection();
    }

    @Override
    public void createSessionCategory(SessionCategory t) {
        try {
            Calendar calendar = Calendar.getInstance();
            Timestamp d = new Timestamp(calendar.getTime().getTime());
            String query = "INSERT INTO Session_category( name, img_url,created_at) "
                    + "VALUES ('" + t.getName() + "','" + t.getImgUrl() + "','" + d + "');";
            Statement st = con.createStatement();
            st.executeUpdate(query);
            System.out.println("Session category ajouter ");
        } catch (SQLException ex) {
            System.out.println("erreur lors de l'ajout " + ex);
        }
    }

    @Override
    public List<SessionCategory> ListSessionCategory() {
        List<SessionCategory> t = new ArrayList();

        ImageView img = null;
        try {

            Statement st = con.createStatement();
            String query = "select cat_id,name,img_url from Session_category where is_deleted=0";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                SessionCategory SessionCategory = new SessionCategory();
                SessionCategory.setCatgid(rs.getInt("cat_id"));
                SessionCategory.setName(rs.getString("name"));
                SessionCategory.setImgUrl(rs.getString("img_url"));
                String url = "file:///" + projectPath + "/src/blastandburn/resources/images/Sessions/" + rs.getString("img_url");
                img = new ImageView(url);
                img.setFitHeight(70);
                img.setFitWidth(70);
                SessionCategory.setImg(img);
                t.add(SessionCategory);
            }
        } catch (SQLException ex) {
            System.out.println("erreur lors de l'affichage" + ex.getMessage());
        }
        return t;
    }

    @Override
    public void updateSessionCategory(SessionCategory t) {
        try {
            Calendar calendar = Calendar.getInstance();
            Timestamp d = new Timestamp(calendar.getTime().getTime());
            String query = "UPDATE  Session_category set name='" + t.getName() + "', img_url='" + t.getImgUrl() + "' where cat_id=" + t.getCatgid() + ";";
           
            Statement st = con.createStatement();
            st.executeUpdate(query);
            System.out.println("modification avec succes");

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteSessionCategory(int idTC) {
        try {
            Calendar calendar = Calendar.getInstance();
            Timestamp d = new Timestamp(calendar.getTime().getTime());
            String query = "UPDATE  Session_category set  is_deleted=" + 1 + ",deleted_at='" + d + "' where cat_id=" + idTC + ";";
            Statement st = con.createStatement();
            st.executeUpdate(query);
            System.out.println("suppression avec succes");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public SessionCategory searchSessionCategory(String titre) {
        String query = "select * from Session_category where name='" + titre + "';";
        SessionCategory categ = null;
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                categ = new SessionCategory();
                categ.setCatgid(rs.getInt("cat_id"));
                categ.setName(rs.getString("name"));

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return categ;
    }

    @Override
    public List<Session> ListerSessionsByIdCatg(String title) {
        List<Session> l = new ArrayList<>() ;
        ImageView img = null;
        try {
            Statement st = con.createStatement();
            String selectCategoryId = "select * from Session_category where name='" + title + "';";
            ResultSet rs = st.executeQuery(selectCategoryId);
            int id = 0;
            while (rs.next()) {
                id = rs.getInt("cat_id");
            }

            String query = "select Session_id,cat_id,img_url,title,description,min_users,max_users from Session where is_deleted=0 and cat_id=" + id + " and Session_id not in(select t.Session_id from paid_Session t natural join Session );";
            ResultSet rst = st.executeQuery(query);
            while (rst.next()) {
                Session Session = new Session();
                Session.setsessionId(rst.getInt("session_id"));
                Session.setDescription(rst.getString("description"));
                Session.setTitle(rst.getString("title"));
                Session.setMinUsers(rst.getInt("min_users"));
                Session.setMaxUsers(rst.getInt("max_users"));
                
                String url = "file:///" + projectPath + "/src/blastandburn/resources/images/Sessions/" + rst.getString("img_url");
                img = new ImageView(url);
                Session.setImg(img);
                l.add(Session);
            }
        } catch (SQLException ex) {
            System.out.println("erreur lors de l'affichage " + ex.getMessage());
        }
        return l;

    }

    
    public SessionCategory searchSessionCategoryById(int id) {
        String query = "select * from Session_category where cat_id=" + id + ";";
        SessionCategory categ = null;
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                categ = new SessionCategory();
                categ.setCatgid(rs.getInt("cat_id"));
                categ.setName(rs.getString("name"));

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return categ;
    }

    public List<PaidSession> ListerPaidSessionsByIdCatg(String title) {
         List<PaidSession> l = new ArrayList<>();
        ImageView img = null;
        try {
            Statement st = con.createStatement();
            String selectCategoryId = "select * from Session_category where name='" + title + "';";
            ResultSet rs = st.executeQuery(selectCategoryId);
            int id = 0;
            while (rs.next()) {
                id = rs.getInt("cat_id");
            }

            String query = "select Session_id,cat_id,img_url,title,description,min_users,max_users,price from Session natural join paid_Session where is_deleted=0 and cat_id=" + id + ";";
            ResultSet rst = st.executeQuery(query);
            while (rst.next()) {
                PaidSession Session = new PaidSession();
                Session.setsessionId(rst.getInt("Session_id"));
                Session.setDescription(rst.getString("description"));
                Session.setTitle(rst.getString("title"));
                Session.setMinUsers(rst.getInt("min_users"));
                Session.setMaxUsers(rst.getInt("max_users"));
                Session.setPrice(rst.getDouble("price"));
                String url = "file:///" + projectPath + "/src/blastandburn/resources/images/Sessions/" + rst.getString("img_url");
                img = new ImageView(url);
                Session.setImg(img);
                l.add(Session);
            }
        } catch (SQLException ex) {
            System.out.println("erreur lors de l'affichage " + ex.getMessage());
        }
        return l;
    }
/*
    @Override
    public ObservableList<Session> ListerSessionsByIdCatg(String title) {
        ObservableList<Session> l = FXCollections.observableArrayList();
        try {
            Statement st = con.createStatement();
            String selectCategoryId = "select * from Session_category where name='" + title + "';";
            ResultSet rs = st.executeQuery(selectCategoryId);
            int id = 0;
            while (rs.next()) {
                id = rs.getInt("cat_id");
            }

            String query = "select Session_id,cat_id,img_url,title,description,num_of_days,min_users,max_users from Session where is_deleted=0 and cat_id=" + id + ";";
            System.out.println(query);
            ResultSet rst = st.executeQuery(query);
            while (rst.next()) {
                l.add(new Session(rst.getInt("Session_id"), rst.getInt("cat_id"), rst.getString("title"), rst.getString("description"), rst.getInt("num_of_days"), rst.getInt("min_users"), rst.getInt("max_users")));
            }
            System.out.println(l);
        } catch (SQLException ex) {
            System.out.println("erreur lors de l'affichage");
        }
        return l;

    }*/
}
