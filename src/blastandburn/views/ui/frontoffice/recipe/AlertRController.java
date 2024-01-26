/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.views.ui.frontoffice.recipe;

import blastandburn.views.ui.frontoffice.event.*;
import animatefx.animation.ZoomIn;
import blastandburn.entities.event.Event;
import blastandburn.entities.recipe.Recipe;
import blastandburn.services.event.ServiceEvent;
import blastandburn.services.event.ServiceMail;
import blastandburn.services.recipe.RecipeService;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author PC_hp
 */
public class AlertRController implements Initializable {

    @FXML
    private AnchorPane anchor;
    @FXML
    private FontAwesomeIconView ex;
int idr;
Stage s;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new ZoomIn(anchor).play();
    }    

    @FXML
    private void cancelAction(ActionEvent event) {
    Stage stage = (Stage) ex.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void closeReport(ActionEvent event) {
    
     RecipeService E =new RecipeService() ;
      E.Delete_Recipe(idr);
      Recipe A =new Recipe() ;
      A = E.getRecipe(idr) ;
        
           TrayNotification tray = new TrayNotification();
        AnimationType type = AnimationType.POPUP;
        tray.setAnimationType(type);
        tray.setTitle("Success");
        tray.setMessage("recipe deleted successfully!");
        tray.setNotificationType(NotificationType.SUCCESS);
        tray.showAndDismiss(Duration.millis(3000));
        Stage stage = (Stage) ex.getScene().getWindow();
        stage.close();
        
        
       
        
        
    }
    public void setData(int id){
        idr=id;
       
    }
    
    }
    

