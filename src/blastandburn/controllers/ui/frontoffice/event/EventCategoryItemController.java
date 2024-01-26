/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.controllers.ui.frontoffice.event;


import blastandburn.entities.event.EventCategory;
import blastandburn.services.ui.UIService;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author user
 */
public class EventCategoryItemController implements Initializable {

    @FXML
    private ImageView eventCatgImg;
    @FXML
    private Label eventCatgTitle;
    @FXML
    private Label eventCatgTotalEvents;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void setData(EventCategory tc){
        UIService stc=new UIService();
        eventCatgTitle.setText(tc.getName());
        eventCatgTotalEvents.setText(String.valueOf(stc.ListerEventsByIdCatg(tc.getName()).size())+" Events");
        eventCatgImg.setImage(tc.getImg().getImage());
    }

    @FXML
    private void showSessionsAction(MouseEvent event) throws IOException {
        EventCategoryHolder holder = EventCategoryHolder.getINSTANCE();
        holder.setName(eventCatgTitle.getText());
        AnchorPane pageHolder = (AnchorPane) eventCatgTitle.getParent().getParent().getParent().getParent().getParent().getParent().getParent().getParent();
        pageHolder.getChildren().removeAll(pageHolder.getChildren());
        pageHolder.getChildren().add(FXMLLoader.load(getClass().getResource("/blastandburn/views/ui/frontoffice/event/EventsByCategory.fxml")));

    }

}
