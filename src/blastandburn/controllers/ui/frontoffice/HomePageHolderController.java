/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.controllers.ui.frontoffice;


import blastandburn.services.user.UserSession;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author user
 */
public class HomePageHolderController implements Initializable {

    private Label label;
    @FXML
    private AnchorPane slider;
    double xOffset, yOffset;
    private Stage stage;
   //@FXML
  // private AnchorPane SessionSideBar;
    @FXML
    private AnchorPane eventSideBar;
    @FXML
    private AnchorPane sessionSideBar;
    @FXML
    private AnchorPane recipeSideBar;
    @FXML
    private AnchorPane homeSideBar;
    @FXML
    private AnchorPane profileSlider;
    @FXML
    private AnchorPane pageHolder;
    @FXML
    private Label userName;
    @FXML
    private Label userEmail;
    @FXML
    private Label userName1;
    @FXML
    private AnchorPane profileSideBar;
    @FXML
    private AnchorPane reportsSideBar;
    @FXML
    private FontAwesomeIconView ela;
    @FXML
    private FontAwesomeIconView saker;
    @FXML
    private AnchorPane chatslidebar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        homeSideBar.getStyleClass().add("selectedMenu");
        eventSideBar.getStyleClass().add("unselectedMenu");
        sessionSideBar.getStyleClass().add("unselectedMenu");
        recipeSideBar.getStyleClass().add("unselectedMenu");
        profileSideBar.getStyleClass().add("unselectedMenu");
        reportsSideBar.getStyleClass().add("unselectedMenu");
        chatslidebar.getStyleClass().add("unselectedMenu");

        userName.setText(UserSession.getFirst_name() + " " + UserSession.getLast_name());
        userName1.setText(UserSession.getFirst_name() + " " + UserSession.getLast_name());
        userEmail.setText(UserSession.getEmail());
        if (UserSession.getRole().equals("ROLE_Moderator")) {
            reportsSideBar.setVisible(true);
        } else {
            reportsSideBar.setVisible(false);
        }
        try {
            pageHolder.getChildren().add(FXMLLoader.load(getClass().getResource("/blastandburn/views/ui/frontoffice/HomePage.fxml")));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void minAction(MouseEvent event) {
        Stage stage = new Stage();
        stage = (Stage) ela.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void closeAction(MouseEvent event) {
        Stage stage = new Stage();
        stage = (Stage) saker.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void homePageAction(MouseEvent event) throws IOException {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(slider);
        slide.setToY(0);
        slide.play();
        homeMenu();
    }



   

    @FXML
    private void eventPageAction(MouseEvent event) throws IOException {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(slider);
        slide.setToY(97);
        slide.play();
        eventMenu();
    }

    @FXML
    private void sessionPageAction(MouseEvent event) throws IOException {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(slider);
        slide.setToY(162);
        slide.play();
        sessionMenu();
    }

    @FXML
    private void recipePageAction(MouseEvent event) throws IOException {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(slider);
        slide.setToY(230);
        slide.play();
        recipeMenu();
    }
    
    @FXML
    private void reportPageAction(MouseEvent event) throws IOException {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(slider);
        slide.setToY(300);
        slide.play();
        reportMenu();
    }
    
    
     @FXML
    private void chatpageaction(MouseEvent event) throws IOException {
        
           TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(slider);
        slide.setToY(435);
        slide.play();
        chatMenu ();      
    }


    @FXML
    private void profilePageAction(MouseEvent event) throws IOException {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(slider);
        slide.setToY(490);
        slide.play();
        profileMenu();
    }

  
   

    public void eventMenu() throws IOException {
        reportsSideBar.getStyleClass().removeAll(reportsSideBar.getStyleClass());
        reportsSideBar.getStyleClass().add("menu");
        reportsSideBar.getStyleClass().add("unselectedMenu");
        
       // SessionSideBar.getStyleClass().removeAll(SessionSideBar.getStyleClass());
       // SessionSideBar.getStyleClass().add("menu");
       // SessionSideBar.getStyleClass().add("unselectedMenu");

        profileSideBar.getStyleClass().removeAll(profileSideBar.getStyleClass());
        profileSideBar.getStyleClass().add("menu");
        profileSideBar.getStyleClass().add("unselectedMenu");

        eventSideBar.getStyleClass().removeAll(eventSideBar.getStyleClass());
        eventSideBar.getStyleClass().add("menu");
        eventSideBar.getStyleClass().add("selectedMenu");

        sessionSideBar.getStyleClass().removeAll(sessionSideBar.getStyleClass());
        sessionSideBar.getStyleClass().add("menu");
        sessionSideBar.getStyleClass().add("unselectedMenu");

        recipeSideBar.getStyleClass().removeAll(recipeSideBar.getStyleClass());
        recipeSideBar.getStyleClass().add("menu");
        recipeSideBar.getStyleClass().add("unselectedMenu");

      
        homeSideBar.getStyleClass().removeAll(homeSideBar.getStyleClass());
        homeSideBar.getStyleClass().add("menu");
        homeSideBar.getStyleClass().add("unselectedMenu");

        pageHolder.getChildren().removeAll(pageHolder.getChildren());
        pageHolder.getChildren().add(FXMLLoader.load(getClass().getResource("/blastandburn/views/ui/frontoffice/event/EventPage.fxml")));
    }

  public void sessionMenu() throws IOException {
        reportsSideBar.getStyleClass().removeAll(reportsSideBar.getStyleClass());
        reportsSideBar.getStyleClass().add("menu");
        reportsSideBar.getStyleClass().add("unselectedMenu");
        
        sessionSideBar.getStyleClass().removeAll(sessionSideBar.getStyleClass());
        sessionSideBar.getStyleClass().add("menu");
        sessionSideBar.getStyleClass().add("selectedMenu");

        profileSideBar.getStyleClass().removeAll(profileSideBar.getStyleClass());
        profileSideBar.getStyleClass().add("menu");
        profileSideBar.getStyleClass().add("unselectedMenu");

        eventSideBar.getStyleClass().removeAll(eventSideBar.getStyleClass());
        eventSideBar.getStyleClass().add("menu");
        eventSideBar.getStyleClass().add("unselectedMenu");

        sessionSideBar.getStyleClass().removeAll(sessionSideBar.getStyleClass());
        sessionSideBar.getStyleClass().add("menu");
        sessionSideBar.getStyleClass().add("unselectedMenu");

        recipeSideBar.getStyleClass().removeAll(recipeSideBar.getStyleClass());
        recipeSideBar.getStyleClass().add("menu");
        recipeSideBar.getStyleClass().add("unselectedMenu");

      

        homeSideBar.getStyleClass().removeAll(homeSideBar.getStyleClass());
        homeSideBar.getStyleClass().add("menu");
        homeSideBar.getStyleClass().add("unselectedMenu");

        pageHolder.getChildren().removeAll(pageHolder.getChildren());
        pageHolder.getChildren().add(FXMLLoader.load(getClass().getResource("/blastandburn/views/ui/frontoffice/task/TaskPage.fxml")));

    }

    public void recipeMenu() throws IOException {
        reportsSideBar.getStyleClass().removeAll(reportsSideBar.getStyleClass());
        reportsSideBar.getStyleClass().add("menu");
        reportsSideBar.getStyleClass().add("unselectedMenu");
        
      //  SessionSideBar.getStyleClass().removeAll(SessionSideBar.getStyleClass());
      //  SessionSideBar.getStyleClass().add("menu");
       // SessionSideBar.getStyleClass().add("unselectedMenu");

        profileSideBar.getStyleClass().removeAll(profileSideBar.getStyleClass());
        profileSideBar.getStyleClass().add("menu");
        profileSideBar.getStyleClass().add("unselectedMenu");

        eventSideBar.getStyleClass().removeAll(eventSideBar.getStyleClass());
        eventSideBar.getStyleClass().add("menu");
        eventSideBar.getStyleClass().add("unselectedMenu");

        sessionSideBar.getStyleClass().removeAll(sessionSideBar.getStyleClass());
        sessionSideBar.getStyleClass().add("menu");
        sessionSideBar.getStyleClass().add("unselectedMenu");

        recipeSideBar.getStyleClass().removeAll(recipeSideBar.getStyleClass());
        recipeSideBar.getStyleClass().add("menu");
        recipeSideBar.getStyleClass().add("selectedMenu");

      

        homeSideBar.getStyleClass().removeAll(homeSideBar.getStyleClass());
        homeSideBar.getStyleClass().add("menu");
        homeSideBar.getStyleClass().add("unselectedMenu");

        pageHolder.getChildren().removeAll(pageHolder.getChildren());
        pageHolder.getChildren().add(FXMLLoader.load(getClass().getResource("/blastandburn/views/ui/frontoffice/recipe/RecipePage.fxml")));
    }

    public void profileMenu() throws IOException {       
        reportsSideBar.getStyleClass().removeAll(reportsSideBar.getStyleClass());
        reportsSideBar.getStyleClass().add("menu");
        reportsSideBar.getStyleClass().add("unselectedMenu");
        
      //  SessionSideBar.getStyleClass().removeAll(SessionSideBar.getStyleClass());
       // SessionSideBar.getStyleClass().add("menu");
       // SessionSideBar.getStyleClass().add("unselectedMenu");

        eventSideBar.getStyleClass().removeAll(eventSideBar.getStyleClass());
        eventSideBar.getStyleClass().add("menu");
        eventSideBar.getStyleClass().add("unselectedMenu");

        sessionSideBar.getStyleClass().removeAll(sessionSideBar.getStyleClass());
        sessionSideBar.getStyleClass().add("menu");
        sessionSideBar.getStyleClass().add("unselectedMenu");

        recipeSideBar.getStyleClass().removeAll(recipeSideBar.getStyleClass());
        recipeSideBar.getStyleClass().add("menu");
        recipeSideBar.getStyleClass().add("unselectedMenu");

        profileSideBar.getStyleClass().removeAll(profileSideBar.getStyleClass());
        profileSideBar.getStyleClass().add("menu");
        profileSideBar.getStyleClass().add("selectedMenu");

       // bookSideBar.getStyleClass().removeAll(bookSideBar.getStyleClass());
      //  bookSideBar.getStyleClass().add("menu");
      //  bookSideBar.getStyleClass().add("unselectedMenu");

        homeSideBar.getStyleClass().removeAll(homeSideBar.getStyleClass());
        homeSideBar.getStyleClass().add("menu");
        homeSideBar.getStyleClass().add("unselectedMenu");

        pageHolder.getChildren().removeAll(pageHolder.getChildren());
        pageHolder.getChildren().add(FXMLLoader.load(getClass().getResource("/blastandburn/views/ui/frontoffice/user/ProfileUser.fxml")));
    }
    
    
     public void chatMenu() throws IOException {       
        reportsSideBar.getStyleClass().removeAll(reportsSideBar.getStyleClass());
        reportsSideBar.getStyleClass().add("menu");
        reportsSideBar.getStyleClass().add("unselectedMenu");
        
      //  SessionSideBar.getStyleClass().removeAll(SessionSideBar.getStyleClass());
       // SessionSideBar.getStyleClass().add("menu");
       // SessionSideBar.getStyleClass().add("unselectedMenu");

        eventSideBar.getStyleClass().removeAll(eventSideBar.getStyleClass());
        eventSideBar.getStyleClass().add("menu");
        eventSideBar.getStyleClass().add("unselectedMenu");

        sessionSideBar.getStyleClass().removeAll(sessionSideBar.getStyleClass());
        sessionSideBar.getStyleClass().add("menu");
        sessionSideBar.getStyleClass().add("unselectedMenu");

        recipeSideBar.getStyleClass().removeAll(recipeSideBar.getStyleClass());
        recipeSideBar.getStyleClass().add("menu");
        recipeSideBar.getStyleClass().add("unselectedMenu");

        profileSideBar.getStyleClass().removeAll(profileSideBar.getStyleClass());
        profileSideBar.getStyleClass().add("menu");
        profileSideBar.getStyleClass().add("selectedMenu");

       // bookSideBar.getStyleClass().removeAll(bookSideBar.getStyleClass());
      //  bookSideBar.getStyleClass().add("menu");
      //  bookSideBar.getStyleClass().add("unselectedMenu");

        homeSideBar.getStyleClass().removeAll(homeSideBar.getStyleClass());
        homeSideBar.getStyleClass().add("menu");
        homeSideBar.getStyleClass().add("unselectedMenu");

        pageHolder.getChildren().removeAll(pageHolder.getChildren());
        pageHolder.getChildren().add(FXMLLoader.load(getClass().getResource("/blastandburn/views/ui/frontoffice/user/chat.fxml")));
    }

    public void homeMenu() throws IOException {
      //  SessionSideBar.getStyleClass().removeAll(SessionSideBar.getStyleClass());
       // SessionSideBar.getStyleClass().add("menu");
       // SessionSideBar.getStyleClass().add("unselectedMenu");

        profileSideBar.getStyleClass().removeAll(profileSideBar.getStyleClass());
        profileSideBar.getStyleClass().add("menu");
        profileSideBar.getStyleClass().add("unselectedMenu");

        eventSideBar.getStyleClass().removeAll(eventSideBar.getStyleClass());
        eventSideBar.getStyleClass().add("menu");
        eventSideBar.getStyleClass().add("unselectedMenu");

        sessionSideBar.getStyleClass().removeAll(sessionSideBar.getStyleClass());
        sessionSideBar.getStyleClass().add("menu");
        sessionSideBar.getStyleClass().add("unselectedMenu");

        recipeSideBar.getStyleClass().removeAll(recipeSideBar.getStyleClass());
        recipeSideBar.getStyleClass().add("menu");
        recipeSideBar.getStyleClass().add("unselectedMenu");

       // bookSideBar.getStyleClass().removeAll(bookSideBar.getStyleClass());
       // bookSideBar.getStyleClass().add("menu");
       // bookSideBar.getStyleClass().add("unselectedMenu");

        homeSideBar.getStyleClass().removeAll(homeSideBar.getStyleClass());
        homeSideBar.getStyleClass().add("menu");
        homeSideBar.getStyleClass().add("selectedMenu");
        
        reportsSideBar.getStyleClass().removeAll(reportsSideBar.getStyleClass());
        reportsSideBar.getStyleClass().add("menu");
        reportsSideBar.getStyleClass().add("unselectedMenu");

        pageHolder.getChildren().removeAll(pageHolder.getChildren());
        pageHolder.getChildren().add(FXMLLoader.load(getClass().getResource("/blastandburn/views/ui/frontoffice/HomePage.fxml")));
    }
    
    public void reportMenu() throws IOException {
    

        profileSideBar.getStyleClass().removeAll(profileSideBar.getStyleClass());
        profileSideBar.getStyleClass().add("menu");
        profileSideBar.getStyleClass().add("unselectedMenu");

        eventSideBar.getStyleClass().removeAll(eventSideBar.getStyleClass());
        eventSideBar.getStyleClass().add("menu");
        eventSideBar.getStyleClass().add("unselectedMenu");

        sessionSideBar.getStyleClass().removeAll(sessionSideBar.getStyleClass());
        sessionSideBar.getStyleClass().add("menu");
        sessionSideBar.getStyleClass().add("unselectedMenu");

        recipeSideBar.getStyleClass().removeAll(recipeSideBar.getStyleClass());
        recipeSideBar.getStyleClass().add("menu");
        recipeSideBar.getStyleClass().add("unselectedMenu");

      //  bookSideBar.getStyleClass().removeAll(bookSideBar.getStyleClass());
       // bookSideBar.getStyleClass().add("menu");
      //  bookSideBar.getStyleClass().add("unselectedMenu");

        homeSideBar.getStyleClass().removeAll(homeSideBar.getStyleClass());
        homeSideBar.getStyleClass().add("menu");
        homeSideBar.getStyleClass().add("unselectedMenu");
        
        reportsSideBar.getStyleClass().removeAll(reportsSideBar.getStyleClass());
        reportsSideBar.getStyleClass().add("menu");
        reportsSideBar.getStyleClass().add("selectedMenu");

        pageHolder.getChildren().removeAll(pageHolder.getChildren());
        pageHolder.getChildren().add(FXMLLoader.load(getClass().getResource("/blastandburn/views/ui/frontoffice/report/ReportPage.fxml")));
    }

    @FXML
    private void profileAction(MouseEvent event) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(profileSlider);
        slide.setToX(-324);
        slide.play();
    }

    @FXML
    private void backAction(MouseEvent event) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(profileSlider);
        slide.setToX(0);
        slide.play();
    }

    public AnchorPane getPageHolder() {
        return pageHolder;
    }

    @FXML
    private void userPorfileAction(MouseEvent event) throws IOException {
        profileMenu();
        TranslateTransition slide2 = new TranslateTransition();
        slide2.setDuration(Duration.seconds(0.4));
        slide2.setNode(profileSlider);
        slide2.setToX(0);
        slide2.play();
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(slider);
        slide.setToY(491);
        slide.play();
        profileMenu();
    }

    @FXML
    private void signOutAction(MouseEvent event) throws IOException {
        UserSession.cleanUserSession();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/blastandburn/views/ui/frontoffice/Login.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        HomePageHolderController hpc = new HomePageHolderController();
        hpc.setStage(stage);
        stage.show();
        Stage stage1 = (Stage) slider.getScene().getWindow();
        stage1.close();
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

    public void removeChildren() {
        pageHolder.getChildren().removeAll(pageHolder.getChildren());
    }

   
    

}
