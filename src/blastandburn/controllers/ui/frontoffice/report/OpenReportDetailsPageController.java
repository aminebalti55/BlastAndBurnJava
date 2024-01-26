/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.controllers.ui.frontoffice.report;

import animatefx.animation.ZoomIn;
import blastandburn.controllers.ui.frontoffice.HomePageHolderController;
import blastandburn.entities.report.Report;
import blastandburn.services.event.ServiceMail;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author user
 */
public class OpenReportDetailsPageController implements Initializable {

    double xOffset, yOffset;
    @FXML
    private Label title;
    @FXML
    private Label note;
    @FXML
    private FontAwesomeIconView icon;
    Report report = null;
    @FXML
    private AnchorPane anchor;
    @FXML
    private JFXTextArea rep;
    @FXML
    private JFXButton sendmaill;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new ZoomIn(anchor).play();
    }

    @FXML
    private void close(MouseEvent event) {
        Stage stage = (Stage) title.getScene().getWindow();
        stage.close();
    }

    public void setData(Report r) {
        report = r;
        switch (r.getType()) {
                    
            case "event":
                icon.setGlyphName("CALENDAR");
                title.setText(r.getTitle());
                note.setText(r.getNote());
                break;
            case "session":
                icon.setGlyphName("STAR");
                title.setText(r.getTitle());
                note.setText(r.getNote());
                break;
            case "recipe":
                icon.setGlyphName("APPLE");
                title.setText(r.getTitle());
                note.setText(r.getNote());
                break;
        }
    }

    @FXML
    private void openAlert(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/blastandburn/views/ui/frontoffice/report/AlertUI.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(OpenReportItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
       AlertUIController c = loader.getController();
        c.setData(title.getText(), (Stage) title.getScene().getWindow());
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        HomePageHolderController hpc = new HomePageHolderController();
        hpc.setStage(stage);
        stage.show();
        root.setOnMousePressed((MouseEvent mouseEvent) -> {
            xOffset = mouseEvent.getSceneX();
            yOffset = mouseEvent.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent mouseEvent) -> {
            stage.setX(mouseEvent.getScreenX() - xOffset);
            stage.setY(mouseEvent.getScreenY() - yOffset);
            stage.setOpacity(0.85f);
        });
        root.setOnMouseReleased((MouseEvent mouseEvent) -> {
            stage.setOpacity(1.0f);
        });
    }

    @FXML
    private void mailling(ActionEvent event) {
            String reww= rep.getText();
                 ServiceMail SM = new ServiceMail();


              SM.sendmailfunc("ccandyxx1@gmail.com", reww);
        
        
        
    }
}
