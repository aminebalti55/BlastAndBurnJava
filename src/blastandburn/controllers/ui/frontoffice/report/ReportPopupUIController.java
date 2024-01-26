/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.controllers.ui.frontoffice.report;

import animatefx.animation.ZoomIn;
import blastandburn.entities.report.EventReport;
import blastandburn.entities.report.RecipeReport;
import blastandburn.entities.report.Report;
import blastandburn.entities.report.SessionReport;
import blastandburn.services.report.ReportService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author user
 */
public class ReportPopupUIController implements Initializable {

    private int id, userId;
    private String s, recipeTitle;
        
    @FXML
    private TextArea noteId;
    @FXML
    private AnchorPane anchor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new ZoomIn(anchor).play();
    }
    public int getId() {
        return id;
    }

    public void setData(int id, int userId, String s, String recipeTitle) {
        this.id = id;
        this.userId = userId;
        this.s = s;
        this.recipeTitle = recipeTitle;
    }

    @FXML
    private void reportAction(MouseEvent event) {
        Report r;
        Stage stage;
        ReportService rs = new ReportService();
        switch (s) {
            
            case "Recipe":
                r = new RecipeReport();
                r.setReporterId(userId);
                r.setNote(noteId.getText());
                r.setTitle(recipeTitle);
                rs.addReport(r, id);
                stage = (Stage) noteId.getScene().getWindow();
                stage.close();
                break;
          
            case "Event":
                r = new EventReport();
                r.setReporterId(userId);
                r.setNote(noteId.getText());
                r.setTitle(recipeTitle);
                rs.addReport(r, id);
                stage = (Stage) noteId.getScene().getWindow();
                stage.close();
                break;
            case "Session":
                r = new SessionReport();
                r.setReporterId(userId);
                r.setNote(noteId.getText());
                r.setTitle(recipeTitle);
                rs.addReport(r, id);
                stage = (Stage) noteId.getScene().getWindow();
                stage.close();
                break;
        }
    }

    @FXML
    private void cancelAction(MouseEvent event) {
        Stage stage = (Stage) noteId.getScene().getWindow();
        stage.close();
    }

}
