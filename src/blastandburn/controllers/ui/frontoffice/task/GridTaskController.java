/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.controllers.ui.frontoffice.task;

import blastandburn.entities.session.PaidSession;
import blastandburn.entities.session.Session;
import blastandburn.services.session.ServicePaidSession;
import blastandburn.services.session.ServiceSession;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class GridTaskController implements Initializable {

    @FXML
    private GridPane taskGrid;
    public int pageCount,currentPage;
    ServicePaidSession spt = new ServicePaidSession();
    private ServiceSession st = new ServiceSession();
    private final int NUM=9;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
     public void setData(int index,List<?> sessions){
        currentPage=index;
        int y = 0;
        int x = 0;
        pageCount=sessions.size()/NUM;
        if(sessions.size()%NUM>0){
            pageCount++;
        }
        int a,b;
        a=currentPage*NUM;
        if(currentPage==(pageCount-1))
            b=sessions.size();
        else
            b=a+NUM;
        for (int i = a; i < b; i++) {
            if (sessions.get(i) instanceof PaidSession) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/blastandburn/views/ui/frontoffice/task/TaskItem.fxml"));
                try {
                    Pane pane = loader.load();
                    TaskItemController c = loader.getController();
                    c.setPaidSessionData((PaidSession) sessions.get(i));
                    if (x > 2) {
                        y++;
                        x = 0;
                    }
                    taskGrid.add(pane, x, y);
                    x++;
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }

            } else {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/blastandburn/views/ui/frontoffice/task/TaskItem.fxml"));
                try {
                    Pane pane = loader.load();
                    TaskItemController c = loader.getController();
                    c.setData((Session) sessions.get(i));
                    if (x > 2) {
                        y++;
                        x = 0;
                    }
                    taskGrid.add(pane, x, y);
                    x++;
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
    
}
