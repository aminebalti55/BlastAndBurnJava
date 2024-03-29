/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.controllers.ui.frontoffice.task;

import blastandburn.entities.session.SessionCategory;
import blastandburn.services.session.ServiceSessionCategory;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author BilelxOS
 */
public class TaskCategoryItemController implements Initializable {

    @FXML
    private Label taskCatgTitle;
    @FXML
    private Label taskCatgTotalTasks;
    @FXML
    private ImageView taskCatgImg;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setData(SessionCategory tc) {
        ServiceSessionCategory stc = new ServiceSessionCategory();
        taskCatgTitle.setText(tc.getName());
        int n=stc.ListerSessionsByIdCatg(tc.getName()).size()+stc.ListerPaidSessionsByIdCatg(tc.getName()).size();
        taskCatgTotalTasks.setText(n + " Sessions");
        taskCatgImg.setImage(tc.getImg().getImage());
    }

    @FXML
    private void showTasksAction(MouseEvent event) throws IOException {
        TaskCategoryHolder holder = TaskCategoryHolder.getINSTANCE();
        holder.setName(taskCatgTitle.getText());
        AnchorPane pageHolder = (AnchorPane) taskCatgTitle.getParent().getParent().getParent().getParent().getParent().getParent().getParent().getParent().getParent().getParent();
        if(pageHolder==null){
             pageHolder = (AnchorPane) taskCatgTitle.getParent().getParent().getParent().getParent().getParent().getParent().getParent().getParent();
        }
        pageHolder.getChildren().removeAll(pageHolder.getChildren());
        pageHolder.getChildren().add(FXMLLoader.load(getClass().getResource("/blastandburn/views/ui/frontoffice/task/TasksByCategory.fxml")));

    }

}
