/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.controllers.ui.frontoffice.event;

import blastandburn.entities.event.Event;
import blastandburn.services.event.ServiceEvent;
import blastandburn.services.user.UserSession;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author user
 */
public class AddEventController implements Initializable {

    @FXML
    private JFXTextField tftitle;
    @FXML
    private JFXTextField tfloca;
    @FXML
    private JFXTextArea tfdescription;
    @FXML
    private ToggleGroup type;
    @FXML
    private JFXDatePicker stId;
    
    private JFXTextArea nbr;
    private ServiceEvent se = new ServiceEvent();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    
    
    
    ///////////////////BADALT HEREE
    @FXML
    private void addEventAction(ActionEvent event) {
         Event e = new Event();
        e.setUserId(UserSession.getUser_id());
        e.setTitle(tftitle.getText());
        e.setDescription(tfdescription.getText());
        e.setStartDate(java.sql.Date.valueOf(stId.getValue()));
        e.setEndDate(java.sql.Date.valueOf(stId.getValue()));
        e.setLocation(tfloca.getText());
        ///////NEWWWW///////////////////////////////////////////////////
        e.setMaxUsers(Integer.parseInt(nbr.getText()));
        e.setType(((RadioButton)type.getSelectedToggle()).getText());
        se.AddEvent(e);
    }

    @FXML
    private void closeAction(MouseEvent event) {
           Stage stage = new Stage();
        stage = (Stage) tfloca.getScene().getWindow();
        stage.close();
    }
    
}
