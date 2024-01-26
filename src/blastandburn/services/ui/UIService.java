/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.services.ui;

import blastandburn.entities.event.Event;
import blastandburn.entities.event.EventCategory;
import blastandburn.entities.recipe.Recipe;
import blastandburn.entities.recipe.RecipeCategory;
import blastandburn.entities.session.Session;
import blastandburn.entities.session.SessionCategory;
import blastandburn.entities.session.Session;
import blastandburn.entities.session.SessionCategory;

import static blastandburn.services.recipe.Constants.projectPath;
import blastandburn.utils.MyConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;

/**
 *
 * @author user
 */
public class UIService {

    Connection cnx;
    private static String projectPath = System.getProperty("user.dir").replace("\\", "/");

    public UIService() {
        cnx = MyConnection.getInstance().getConnection();
    }

   

    public ObservableList<Event> upcomingEvent() throws SQLException {

        ImageView img = null;
        Statement stm = cnx.createStatement();
        String query = "select * from `event` where is_deleted=0 order by created_at desc limit 2";

        ResultSet rst = stm.executeQuery(query);

        ObservableList<Event> Events = FXCollections.observableArrayList();

        while (rst.next()) {
            Event e = new Event();
            e.setEventId(rst.getInt("event_id"));
            e.setTitle(rst.getString("title"));
            e.setDescription(rst.getString("description"));

            //e.setStartTime(rst.getTime("start_time"));
            //e.setEndTime(rst.getTime("end_time"));
            e.setStartDate(rst.getDate("start_date"));
            e.setEndDate(rst.getDate("end_date"));
            e.setLocation(rst.getString("location"));
            e.setType(rst.getString("type"));
            String url = "file:///" + projectPath + "/src/blastandburn/resources/images/events/" + rst.getString("img_url");
            img = new ImageView(url);
            e.setImg(img);
            Events.add(e);
        }

        return Events;

    }

    

    public ObservableList<Recipe> topThreeRecipe() {
        ImageView img = null;
        ObservableList<Recipe> ListR = FXCollections.observableArrayList();
        try {

            Statement st = cnx.createStatement();
            String query = "select r.* , x.recipe_id, round(avg(y.score),2) as score from rate y, recipe_rate x, recipe r where x.rate_id=y.rate_id and x.recipe_id=r.recipe_id and r.is_deleted=0 group by x.recipe_id order by score desc limit 3";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Recipe r = new Recipe();
                RecipeCategory rc = new RecipeCategory();
                r.setRecipeId(rs.getInt("r.recipe_id"));
                r.setUserId(rs.getInt("r.user_id"));
                // r.setCat(rs.getInt("rc.cat_id"));
                r.setTitle(rs.getString("r.title"));
                r.setDescription(rs.getString("r.description"));
                r.setDuration(rs.getInt("r.duration"));
                r.setPersons(rs.getInt("r.persons"));
                r.setCalories(rs.getInt("r.calories"));



                String url = "file:///" + projectPath + "/src/blastandburn/resources/images/recipes/" + rs.getString("img_url");
                img = new ImageView(url);
                r.setImg(img);
                ListR.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ListR;
    }

   public ObservableList<Session> topThreeSession() {
        ObservableList<Session> t = FXCollections.observableArrayList();
        ImageView img = null;
        try {

            Statement st = cnx.createStatement();
            String query = "select t.* , x.Session_id, round(avg(y.score),2) as score from rate y, Session_rate x, Session t where x.rate_id=y.rate_id and x.Session_id=t.Session_id and t.is_deleted=0 group by x.Session_id order by score desc limit 3";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Session session = new Session();
                session.setsessionId(rs.getInt("session_id"));
                session.setDescription(rs.getString("description"));
                session.setTitle(rs.getString("title"));
                session.setMinUsers(rs.getInt("min_users"));
                session.setMaxUsers(rs.getInt("max_users"));
                String url = "file:///" + projectPath + "/src/blastandburn/resources/images/tasks/" + rs.getString("img_url");
                img = new ImageView(url);
                session.setImg(img);
                t.add(session);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return t;
    }


    public String planning(int sessionId) throws SQLException {

        Statement stm = cnx.createStatement();
        String query = "select u.first_name, u.last_name from user u, session s where s.is_deleted=0 and u.user_id=s.coach_id and s.session_id=" + sessionId + "";
        ResultSet rst = stm.executeQuery(query);
        String user = "";
        while (rst.next()) {
            user = rst.getString("u.first_name") + " " + rst.getString("u.last_name");
        }

        return user;
    }

   

  

    public ObservableList<Event> ListerEventsByIdCatg(String title) {
        ObservableList<Event> l = FXCollections.observableArrayList();
        ImageView img = null;
        try {
            Statement st = cnx.createStatement();
            String selectCategoryId = "select * from event_category where name='" + title + "';";
            ResultSet rs = st.executeQuery(selectCategoryId);
            int id = 0;
            while (rs.next()) {
                id = rs.getInt("cat_id");
            }
            String query = "select * from event where is_deleted=0 and start_date> sysdate() and cat_id=" + id + ";";
            ResultSet rst = st.executeQuery(query);
            while (rst.next()) {
                Event e = new Event();
                e.setEventId(rst.getInt("event_id"));
                e.setTitle(rst.getString("title"));
                e.setDescription(rst.getString("description"));
                e.setStartDate(rst.getDate("start_date"));
                e.setEndDate(rst.getDate("end_date"));
                e.setLocation(rst.getString("location"));
                e.setType(rst.getString("type"));
                e.setCatId(rst.getInt("cat_id"));
                e.setPrice(rst.getDouble("price"));
                String url = "file:///" + projectPath + "/src/blastandburn/resources/images/events/" + rst.getString("img_url");
                img = new ImageView(url);
                e.setImg(img);
                l.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return l;

    }

    public ObservableList<EventCategory> topThreeEventCatg() {
        ImageView img = null;
        ObservableList<EventCategory> data = FXCollections.observableArrayList();
        try {

            Statement st = cnx.createStatement();
            String query = "SELECT bc.cat_id, bc.name, bc.img_url, count(*) as total_events FROM event b,event_category bc WHERE b.cat_id=bc.cat_id and bc.is_deleted=0 group by bc.cat_id ORDER by total_events desc limit 3";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                EventCategory bc = new EventCategory();
                bc.setCatId(rs.getInt("cat_id"));
                bc.setName(rs.getString("name"));
                bc.setImgUrl(rs.getString("img_url"));
                String url = "file:///" + projectPath + "/src/blastandburn/resources/images/events/" + rs.getString("img_url");
                img = new ImageView(url);
                bc.setImg(img);

                data.add(bc);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return data;
    }

    public ObservableList<RecipeCategory> topThreeRecCatg() {
        ImageView img = null;
        ObservableList<RecipeCategory> data = FXCollections.observableArrayList();
        try {

            Statement st = cnx.createStatement();
            String query = "SELECT bc.cat_id, bc.name, bc.img_url, count(*) as total_recipes FROM recipe b,recipe_category bc WHERE b.cat_id=bc.cat_id and bc.is_deleted=0 group by bc.cat_id ORDER by total_recipes desc limit 3";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                RecipeCategory bc = new RecipeCategory();
                bc.setCatId(rs.getInt("cat_id"));
                bc.setName(rs.getString("name"));
                bc.setImgUrl(rs.getString("img_url"));
                String url = "file:///" + projectPath + "/src/blastandburn/resources/images/recipes/" + rs.getString("img_url");
                img = new ImageView(url);
                bc.setImg(img);

                data.add(bc);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return data;
    }

    public ObservableList<Recipe> ListerRecipesByIdCatg(String title) {
        ObservableList<Recipe> l = FXCollections.observableArrayList();
        try {
            Statement st = cnx.createStatement();
            String selectCategoryId = "select * from recipe_category where name='" + title + "';";
            ResultSet rs = st.executeQuery(selectCategoryId);
            int id = 0;
            while (rs.next()) {
                id = rs.getInt("cat_id");
            }
            String query = "select * from recipe where is_deleted=0 and cat_id=" + id + ";";
            Statement st2 = cnx.createStatement();
            ResultSet rst = st2.executeQuery(query);
            while (rst.next()) {
                Recipe r = new Recipe();
                r.setRecipeId(rst.getInt("recipe_id"));
                r.setUserId(rst.getInt("user_id"));
                // r.setCat(rst.getInt("cat_id"));
                r.setTitle(rst.getString("title"));
                r.setDescription(rst.getString("description"));
                l.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return l;
    }
    
     public ObservableList<SessionCategory> topThreeCatg() {
        ImageView img = null;
        ObservableList<SessionCategory> t = FXCollections.observableArrayList();
        try {

            Statement st = cnx.createStatement();
            String query = "SELECT tc.cat_id, tc.name, tc.img_url, count(*) as total_Sessions FROM Session t,Session_category tc WHERE t.cat_id=tc.cat_id and tc.is_deleted=0 group by tc.cat_id ORDER by total_Sessions desc limit 3";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                SessionCategory sessionategory = new SessionCategory();
                sessionategory.setCatgid(rs.getInt("cat_id"));
                sessionategory.setName(rs.getString("name"));
                String urlc = "file:///" + projectPath + "/src/blastandburn/resources/images/tasks/" + rs.getString("img_url");
                img = new ImageView(urlc);
                sessionategory.setImg(img);
                t.add(sessionategory);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return t;
    }
}
