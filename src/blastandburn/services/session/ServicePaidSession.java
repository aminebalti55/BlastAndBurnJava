/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.services.session;

import blastandburn.entities.session.PaidSession;
import blastandburn.entities.session.Session;
import blastandburn.entities.session.SessionCategory;
import blastandburn.iservices.session.IServicePaidSession;
import blastandburn.utils.MyConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.ImageView;

/**
 *
 * @author Admin
 */
public class ServicePaidSession implements IServicePaidSession {

    Connection con;
    ServiceSession servicet = new ServiceSession();
    private static String projectPath = System.getProperty("user.dir").replace("\\", "/");
    ServiceSessionCategory stc = new ServiceSessionCategory();

    public ServicePaidSession() {
        con = MyConnection.getInstance().getConnection();
    }

    public void addPaidSession(int idCoach, String title, PaidSession p) {
        servicet.createSession(idCoach, title, p);
        Session t = new Session();
        try {
            Statement st = con.createStatement();
            String id = "select Session_id from Session where title='" + p.getTitle() + "' and description='" + p.getDescription() + "'";
            ResultSet rs = st.executeQuery(id);
            while (rs.next()) {
                t.setsessionId(Integer.valueOf(rs.getInt("Session_id")));
            }
            String query = "insert into paid_Session(Session_id,price) values('" + t.getsessionId() + "','" + p.getPrice() + "');";
            st.executeUpdate(query);
            System.out.println("Paid Session ajouter ");
        } catch (SQLException ex) {
            System.out.println("erreur lors de l'ajout " + ex);
        }
    }

    public List<PaidSession> listPaidSession() {
        List<PaidSession> pt = new ArrayList<PaidSession>();
        ImageView img = null;
        try {
            String query = "SELECT * FROM `paid_Session` p join Session t on p.Session_id=t.Session_id where t.is_deleted=0 order by t.created_at DESC";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                PaidSession p = new PaidSession();
                p.setPrice(rs.getDouble("price"));
                p.setsessionId(rs.getInt("Session_id"));
                p.setTitle(rs.getString("title"));
                p.setMaxUsers(rs.getInt("max_users"));
                p.setDescription(rs.getString("description"));
                p.setMinUsers(rs.getInt("min_users"));
                p.setCategory(stc.searchSessionCategoryById(rs.getInt("cat_id")));
                String url = "file:///" + projectPath + "/src/blastandburn/resources/images/Sessions/" + rs.getString("img_url");
                img = new ImageView(url);
                p.setImg(img);

                pt.add(p);
            }

        } catch (SQLException ex) {
            System.out.println("erreur lors de l'affichage " + ex);
        }
        return pt;
    }

    public void updatePaidSession(PaidSession t, int idpt) {
        try {
           // servicet.updateSession((Session)t, idpt);
            String query = "UPDATE  paid_Session set price=" + t.getPrice() + " where Session_id=" + idpt + ";";
            Statement st = con.createStatement();
            st.executeUpdate(query);
            System.out.println("modification avec succes");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void deletePaidSession(int idpt) {
        try {
            String query = "delete from  paid_Session  where Session_id=" + idpt;
            Statement st = con.createStatement();
            st.executeUpdate(query);
            servicet.deleteSession(idpt);
            System.out.println("paid Session supprimer");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public PaidSession getPaidSession(int idPT) {
        PaidSession t = null;
        ImageView img = null;
        try {

            Statement st = con.createStatement();
            String query = "select Session_id,cat_id,img_url,title,description,min_users,max_users,created_at,price from Session NATURAL JOIN paid_Session where Session_id=" + idPT + " and is_deleted=0 ;";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                PaidSession Session = new PaidSession();
                Session.setsessionId(rs.getInt("session_id"));
                Session.setDescription(rs.getString("description"));
                Session.setTitle(rs.getString("title"));
                Session.setMinUsers(rs.getInt("min_users"));
                Session.setMaxUsers(rs.getInt("max_users"));
                Session.setImgUrl(rs.getString("img_url"));
                Session.setPrice(rs.getDouble("price"));
                Session.setCategory(stc.searchSessionCategoryById(rs.getInt("cat_id")));
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

    public void makeFreeSession(int idPT) {
        try {
            String query = "delete from  paid_Session  where Session_id=" + idPT;
            Statement st = con.createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void makePaidSession(int idPT, double price) {
        try {
            String query = "insert into paid_Session(Session_id,price) values('" + idPT + "','" + price + "');";
            Statement st = con.createStatement();
            st.executeUpdate(query);
            System.out.println("Paid Session ajouter ");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    
    public List<PaidSession> listPaidSessionByIdUser(int idU) {
        List<PaidSession> pt = new ArrayList<PaidSession>();
        ImageView img = null;
        try {
            String query = "SELECT * FROM `paid_Session` p join Session t on p.Session_id=t.Session_id where t.u_id="+idU +" and  t.is_deleted=0  order by t.created_at DESC";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                PaidSession p = new PaidSession();
                p.setPrice(rs.getDouble("price"));
                p.setsessionId(rs.getInt("Session_id"));
                p.setTitle(rs.getString("title"));
                p.setMaxUsers(rs.getInt("max_users"));
                p.setDescription(rs.getString("description"));
                p.setMinUsers(rs.getInt("min_users"));
                p.setCategory(stc.searchSessionCategoryById(rs.getInt("cat_id")));
                String url = "file:///" + projectPath + "/src/blastandburn/resources/images/Sessions/" + rs.getString("img_url");
                img = new ImageView(url);
                p.setImg(img);

                pt.add(p);
            }

        } catch (SQLException ex) {
            System.out.println("erreur lors de l'affichage " + ex);
        }
        return pt;
    }

    public int getCountPaidSession() {
    int nbr=0;
        try {
            String query = "SELECT COUNT(p.Session_id) as n FROM `paid_Session` p join Session t on p.Session_id=t.Session_id where t.is_deleted=0";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                int p=rs.getInt("n");
                nbr=p;
            }

        } catch (SQLException ex) {
            System.out.println("erreur lors de l'affichage " + ex);
        }
        return nbr;   
    }

    public List<PaidSession> searchPaidSessionByName(String title) {
        List<PaidSession> pt = new ArrayList<PaidSession>();
        ImageView img = null;
        try {
            String query = "SELECT * FROM `paid_Session` p join Session t on p.Session_id=t.Session_id where t.is_deleted=0 and t.title like '"+title+"%'  order by t.created_at DESC";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                PaidSession p = new PaidSession();
                p.setPrice(rs.getDouble("price"));
                p.setsessionId(rs.getInt("session_id"));
                p.setTitle(rs.getString("title"));
                p.setMaxUsers(rs.getInt("max_users"));
                p.setDescription(rs.getString("description"));
                p.setMinUsers(rs.getInt("min_users"));
                p.setCategory(stc.searchSessionCategoryById(rs.getInt("cat_id")));
                String url = "file:///" + projectPath + "/src/blastandburn/resources/images/Sessions/" + rs.getString("img_url");
                img = new ImageView(url);
                p.setImg(img);

                pt.add(p);
            }

        } catch (SQLException ex) {
            System.out.println("erreur lors de l'affichage " + ex);
        }
        return pt;
    }
}
