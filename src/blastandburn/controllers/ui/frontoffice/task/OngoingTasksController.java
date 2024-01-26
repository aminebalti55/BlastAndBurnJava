/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.controllers.ui.frontoffice.task;

import animatefx.animation.ZoomIn;
import blastandburn.entities.session.PaidSession;
import blastandburn.entities.session.Session;
import blastandburn.services.session.ServiceUserSessionX;
import blastandburn.services.user.UserSession;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class OngoingTasksController implements Initializable {

    @FXML
    private ScrollPane tasksPane;
    private GridPane tasksGrid;
    private ServiceUserSessionX sut = new ServiceUserSessionX();
    @FXML
    private Pagination pagination;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new ZoomIn(tasksPane).play();
        //pagination.setPageFactory((pageindex) -> grid(pageindex));
        pagination.setPageFactory((pageindex) -> {
            if (grid(pageindex) != null) {
                return grid((pageindex));
            }
            return null;
        });
    }

    public GridPane grid(int pageindex) {
        List<?> sessions;
        sessions = Stream.concat(sut.ListerSessionsByIdUser(UserSession.getUser_id()).stream(), sut.ListerPaidSessionsByIdUser(UserSession.getUser_id()).stream())
                .collect(Collectors.toList());
        if (sessions != null) {
            GridPane pane = null;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/blastandburn/views/ui/frontoffice/task/GridTask.fxml"));
            try {
                pane = loader.load();
                GridTaskController c = loader.getController();
                c.setData(pageindex, sessions);
                pagination.setPageCount(c.pageCount);

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            return pane;
        }
        return null;
    }

    @FXML
    private void backAction(MouseEvent event) throws IOException {
        AnchorPane pageHolder = (AnchorPane) tasksPane.getParent();
        pageHolder.getChildren().removeAll(pageHolder.getChildren());
        pageHolder.getChildren().add(FXMLLoader.load(getClass().getResource("/blastandburn/views/ui/frontoffice/task/TaskPage.fxml")));

    }

}
