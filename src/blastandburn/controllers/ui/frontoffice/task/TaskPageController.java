/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.controllers.ui.frontoffice.task;

import animatefx.animation.ZoomIn;
import blastandburn.controllers.ui.frontoffice.HomePageHolderController;
import blastandburn.entities.session.Notification;
import blastandburn.entities.session.PaidSession;
import blastandburn.entities.session.Session;
import blastandburn.entities.session.SessionCategory;
import blastandburn.services.session.ServiceNotification;
import blastandburn.services.session.ServicePaidSession;
import blastandburn.services.session.ServiceSession;
import blastandburn.services.session.ServiceUserSessionX;
import blastandburn.services.ui.UIService;
import blastandburn.services.user.UserSession;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class TaskPageController implements Initializable {

    @FXML
    private HBox categoriesHBox;
    private UIService stc = new UIService();
    private ServiceSession st = new ServiceSession();
    @FXML
    private ScrollPane taskPane;

    double xOffset, yOffset;
    @FXML
    private JFXButton addBtn;
    ServicePaidSession spt = new ServicePaidSession();
    PaidSession pt = new PaidSession();
    @FXML
    private JFXComboBox<String> comboBox;
    String select = "";
    private ServiceUserSessionX sut = new ServiceUserSessionX();
    private static String projectPath = System.getProperty("user.dir").replace("\\", "/");
    @FXML
    private Pagination pagination;
    @FXML
    private JFXTextField rechercheText;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        taskPane.setVvalue(0);
        new ZoomIn(taskPane).play();
        comboBox.getItems().add("All");
        comboBox.getItems().add("Yours");
        comboBox.getSelectionModel().select("All");
        if (UserSession.getRole().equals("ROLE_Coach")) {
            addBtn.setVisible(true);
            ServiceNotification service = new ServiceNotification();
            for (int i = 0; i < service.listNotification(UserSession.getUser_id()).size(); i++) {
                Image notification = new Image("file:///" + projectPath + "/src/blastandburn/resources/images/tasks/alert.png");
                TrayNotification tray = new TrayNotification();
                tray.setTitle("participer");
                tray.setNotificationType(NotificationType.SUCCESS);
                tray.setMessage(service.listNotification(UserSession.getUser_id()).get(i).getMessage());
                //tray.setRectangleFill(Paint.valueOf("#2A9A84"));
//                tray.showAndWait();
                tray.showAndDismiss(Duration.seconds(5));
                service.deleteNotification(service.listNotification(UserSession.getUser_id()).get(i));
            }

        }
        List<SessionCategory> catTasks = stc.topThreeCatg();
        for (int i = 0; i < catTasks.size(); i++) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/blastandburn/views/ui/frontoffice/task/TaskCategoryItem.fxml"));
            try {
                AnchorPane pane = loader.load();
                TaskCategoryItemController c = loader.getController();
                c.setData(catTasks.get(i));
                categoriesHBox.getChildren().add(pane);

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

        pagination.setPageFactory((pageindex) -> grid(pageindex));

    }

    @FXML
    private void addTaskAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/blastandburn/views/ui/frontoffice/task/AddTaskF.fxml"));
        Parent root = loader.load();
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
    private void allCategories(MouseEvent event) throws IOException {

        AnchorPane pageHolder = (AnchorPane) comboBox.getParent().getParent().getParent().getParent().getParent();
        pageHolder.getChildren().removeAll(pageHolder.getChildren());
        pageHolder.getChildren().add(FXMLLoader.load(getClass().getResource("/blastandburn/views/ui/frontoffice/task/AllCategories.fxml")));

    }

    @FXML
    private void comboBoxAction(ActionEvent event) {
        pagination.setPageFactory((pageindex) -> comboGrid(pageindex));
    }

    public GridPane grid(int pageindex) {
        List<?> tasks;
        tasks = Stream.concat(st.ListSession().stream(), spt.listPaidSession().stream())
                .collect(Collectors.toList());
        GridPane pane = null;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/blastandburn/views/ui/frontoffice/task/GridTask.fxml"));
        try {
            pane = loader.load();
            GridTaskController c = loader.getController();
            c.setData(pageindex, tasks);
            pagination.setPageCount(c.pageCount);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return pane;
    }

    public GridPane comboGrid(int pageindex) {
        List<?> tasks = null;
        if (comboBox.getValue() == "Yours") {
            if (UserSession.getRole().equals("Role_Coach")) {
                tasks = Stream.concat(st.ListSessionByIdUser(UserSession.getUser_id()).stream(), spt.listPaidSessionByIdUser(UserSession.getUser_id()).stream())
                        .collect(Collectors.toList());
            } else {
                tasks = Stream.concat(sut.ListerSessionsByIdUser(UserSession.getUser_id()).stream(), sut.ListerPaidSessionsByIdUser(UserSession.getUser_id()).stream())
                        .collect(Collectors.toList());
            }

        } else if (comboBox.getValue() == "All") {
            tasks = Stream.concat(st.ListSession().stream(), spt.listPaidSession().stream())
                    .collect(Collectors.toList());

        }
        GridPane pane = null;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/blastandburn/views/ui/frontoffice/task/GridTask.fxml"));
        try {
            pane = loader.load();
            GridTaskController c = loader.getController();
            c.setData(pageindex, tasks);
            pagination.setPageCount(c.pageCount);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return pane;
    }

    @FXML
    private void rechercheAction(KeyEvent event) {
        pagination.setPageFactory((pageindex) -> {
            if(searchGrid(pageindex)!=null){
            return searchGrid((pageindex));}
            return null;
                });

    }

    public GridPane searchGrid(int pageindex) {
        List<?> tasks = null;
        if (!"".equals(rechercheText.getText())) {
            tasks = Stream.concat(st.searchSessionByName(rechercheText.getText()).stream(), spt.searchPaidSessionByName(rechercheText.getText()).stream())
                    .collect(Collectors.toList());
        }else{
          tasks = Stream.concat(st.ListSession().stream(), spt.listPaidSession().stream())
                    .collect(Collectors.toList());  
        }
        
        if(tasks!=null){
        GridPane pane = null;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/blastandburn/views/ui/frontoffice/task/GridTask.fxml"));
        try {
            pane = loader.load();
            GridTaskController c = loader.getController();
            c.setData(pageindex, tasks);
            pagination.setPageCount(c.pageCount);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return pane;
    }
        return null;
    }
}
