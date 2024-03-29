/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.controllers.ui.frontoffice.task;

import animatefx.animation.ZoomIn;
import blastandburn.entities.session.SessionCategory;
import blastandburn.services.session.ServiceSessionCategory;
import blastandburn.services.user.UserSession;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class AllCategoriesController implements Initializable {

    @FXML
    private ScrollPane CategoryPane;
    @FXML
    private GridPane CategoryGrid;
    private ServiceSessionCategory st = new ServiceSessionCategory();
    @FXML
    private Pagination pagination;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CategoryPane.setVvalue(0);
        new ZoomIn(CategoryPane).play();
        pagination.setPageFactory((pageindex) -> grid(pageindex));
    }

    @FXML
    private void backAction(MouseEvent event) throws IOException {

        AnchorPane pageHolder = (AnchorPane) CategoryPane.getParent();
        pageHolder.getChildren().removeAll(pageHolder.getChildren());
        pageHolder.getChildren().add(FXMLLoader.load(getClass().getResource("/blastandburn/views/ui/frontoffice/task/TaskPage.fxml")));

    }

    public GridPane grid(int pageindex) {

        GridPane pane = null;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/blastandburn/views/ui/frontoffice/task/GridTaskCategory.fxml"));
        try {
            pane = loader.load();
            GridTaskCategoryController c = loader.getController();
            c.setData(pageindex);
            pagination.setPageCount(c.pageCount);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return pane;
    }

}
