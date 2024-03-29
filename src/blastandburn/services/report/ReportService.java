/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.services.report;

//import blastandburn.entities.report.BookReport;
import blastandburn.entities.report.EventReport;
import blastandburn.entities.report.RecipeReport;
import blastandburn.entities.report.Report;
import blastandburn.entities.report.SessionReport;
import blastandburn.entities.report.UserReport;
import blastandburn.iservices.report.IReportService;
import blastandburn.services.user.UserSession;
import blastandburn.utils.MyConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author user
 */
public class ReportService implements IReportService {

    Connection cnx;

    public ReportService() {
        cnx = MyConnection.getInstance().getConnection();
    }

    @Override
    public void addReport(Report r, int id) {
        String query;
        if (r instanceof EventReport) {
            try {
                Statement stm = cnx.createStatement();
                query = "INSERT INTO report(reporter_id,title,type,note) VALUES (" + r.getReporterId() + ",'" + r.getTitle() + "','event', '" + r.getNote() + "')";
                stm.executeUpdate(query);
                query = "INSERT INTO event_report(report_id,event_id) VALUES (LAST_INSERT_ID(), " + id + ")";
                stm.executeUpdate(query);
                query = "INSERT INTO report_notification(user_id,event_id,report_id) VALUES ("+UserSession.getUser_id()+", " + id + ",LAST_INSERT_ID())";
                stm.executeUpdate(query);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        } else if (r instanceof UserReport) {
            try {
                Statement stm = cnx.createStatement();
                query = "INSERT INTO report(reporter_id,title,type,note) VALUES (" + r.getReporterId() + ",'" + r.getTitle() + "','user', '" + r.getNote() + "')";
                stm.executeUpdate(query);
                query = "INSERT INTO user_report(report_id,user_id) VALUES (LAST_INSERT_ID(), " + id + ")";               
                stm.executeUpdate(query);
                query = "INSERT INTO report_notification(user_id,user_id,report_id) VALUES ("+UserSession.getUser_id()+", " + id + ",LAST_INSERT_ID())";
                stm.executeUpdate(query);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }  else if (r instanceof SessionReport) {
            try {
                Statement stm = cnx.createStatement();
                query = "INSERT INTO report(reporter_id,title,type,note) VALUES (" + r.getReporterId() + ",'" + r.getTitle() + "','session', '" + r.getNote() + "')";
                stm.executeUpdate(query);
                query = "INSERT INTO session_report(report_id,session_id) VALUES (LAST_INSERT_ID(), " + id + ")";
                stm.executeUpdate(query);
                query = "INSERT INTO report_notification(user_id,session_id,report_id) VALUES ("+UserSession.getUser_id()+", " + id + ",LAST_INSERT_ID())";
                stm.executeUpdate(query);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        } else if (r instanceof RecipeReport) {
            try {
                Statement stm = cnx.createStatement();
                query = "INSERT INTO report(reporter_id,title,type,note) VALUES (" + r.getReporterId() + ",'" + r.getTitle() + "','recipe', '" + r.getNote() + "')";
                stm.executeUpdate(query);
                query = "INSERT INTO recipe_report(report_id,recipe_id) VALUES (LAST_INSERT_ID(), " + id + ")";
                stm.executeUpdate(query);
                query = "INSERT INTO report_notification(user_id,recipe_id,report_id) VALUES ("+UserSession.getUser_id()+", " + id + ",LAST_INSERT_ID())";
                stm.executeUpdate(query);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    @Override
    public void closeReport(String title) {
        try {
            String query = "update report set is_closed=1 where title='" + title + "'";
            Statement stm = cnx.createStatement();
            stm.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void banUser(int userId) {
        try {
            String query = "update user set is_limited=1 where user_id=" + userId + "";
            Statement stm = cnx.createStatement();
            stm.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void unbanUser(int userId) {
        try {
            String query = "update user set is_limited=0 where user_id=" + userId + "";
            Statement stm = cnx.createStatement();
            stm.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Report> allReportsList() {
        List<Report> reports = new ArrayList();
        try {
            Statement stm = cnx.createStatement();
            String query = "select * from report order by created_at desc";
            ResultSet rst = stm.executeQuery(query);
            while (rst.next()) {
                Report r = new EventReport();
                r.setReportId(rst.getInt("report_id"));
                r.setReporterId(rst.getInt("reporter_id"));
                r.setTitle(rst.getString("title"));
                r.setType(rst.getString("type"));
                r.setNote(rst.getString("note"));
                r.setIsClosed(rst.getBoolean("is_closed"));
                r.setCreatedAt(rst.getTimestamp("created_at"));
                reports.add(r);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return reports;
    }
    
    public ObservableList<Report> allReportsListObsv() {
        ObservableList<Report> reports = FXCollections.observableArrayList();
        try {
            Statement stm = cnx.createStatement();
            String query = "select * from report order by created_at desc";
            ResultSet rst = stm.executeQuery(query);
            while (rst.next()) {
                Report r = new EventReport();
                r.setReportId(rst.getInt("report_id"));
                r.setReporterId(rst.getInt("reporter_id"));
                r.setTitle(rst.getString("title"));
                r.setType(rst.getString("type"));
                r.setNote(rst.getString("note"));
                r.setIsClosed(rst.getBoolean("is_closed"));
                r.setCreatedAt(rst.getTimestamp("created_at"));
                reports.add(r);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return reports;
    }

    @Override
    public List<Report> reportsList(String type) {
        ObservableList<Report> reports = FXCollections.observableArrayList();
        try {
            Statement stm = cnx.createStatement();
            String query = "select y.title, x.report_id, y.reporter_id, y.note, y.is_closed, y.created_at from report y, "+type+" x where x.report_id=y.report_id";
            ResultSet rst = stm.executeQuery(query);
            while (rst.next()) {
                Report r = new EventReport();
                r.setTitle(rst.getString("title"));
                r.setReportId(rst.getInt("report_id"));
                r.setReporterId(rst.getInt("reporter_id"));
                r.setNote(rst.getString("note"));
                r.setIsClosed(rst.getBoolean("is_closed"));
                r.setCreatedAt(rst.getTimestamp("created_at"));
                reports.add(r);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return reports;
    }

    @Override
    public List<Report> closedReportsList() {
        ObservableList<Report> reports = FXCollections.observableArrayList();
        try {
            Statement stm = cnx.createStatement();
            String query = "select x.report_id, y.reporter_id, y.note, y.is_closed, y.created_at from report y, event_report x where x.report_id=y.report_id and y.is_closed=1";
            ResultSet rst = stm.executeQuery(query);
            while (rst.next()) {
                Report r = new EventReport();
                r.setReportId(rst.getInt("report_id"));
                r.setReporterId(rst.getInt("reporter_id"));
                r.setNote(rst.getString("note"));
                r.setIsClosed(rst.getBoolean("is_closed"));
                r.setCreatedAt(rst.getTimestamp("created_at"));
                reports.add(r);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return reports;
    }

    @Override
    public List<Report> openReportsList() {
        ObservableList<Report> reports = FXCollections.observableArrayList();
        try {
            Statement stm = cnx.createStatement();
            String query = "select x.report_id, y.reporter_id, y.note, y.is_closed, y.created_at from report y, event_report x where x.report_id=y.report_id and y.is_closed=0";
            ResultSet rst = stm.executeQuery(query);
            while (rst.next()) {
                Report r = new EventReport();
                r.setReportId(rst.getInt("report_id"));
                r.setReporterId(rst.getInt("reporter_id"));
                r.setNote(rst.getString("note"));
                r.setIsClosed(rst.getBoolean("is_closed"));
                r.setCreatedAt(rst.getTimestamp("created_at"));
                reports.add(r);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return reports;
    }
    
    public List<Report> reportsCount() {
        ObservableList<Report> reports = FXCollections.observableArrayList();
        try {
            Statement stm = cnx.createStatement();
            String query = "select *, count(*) as total from report where is_closed=0 group by title having total>4 order by total desc limit 3";
            ResultSet rst = stm.executeQuery(query);
            while (rst.next()) {
                Report r = new EventReport();
                r.setReportId(rst.getInt("report_id"));
                r.setReporterId(rst.getInt("reporter_id"));
                r.setTitle(rst.getString("title"));
                r.setType(rst.getString("type"));
                r.setNote(rst.getString("note"));
                r.setIsClosed(rst.getBoolean("is_closed"));
                r.setCreatedAt(rst.getTimestamp("created_at"));
                r.setTotal(rst.getInt("total"));
                reports.add(r);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return reports;
    }

}
