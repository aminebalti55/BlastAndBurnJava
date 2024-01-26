/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.controllers.ui.frontoffice.event;

import blastandburn.entities.event.Event;
import blastandburn.entities.event.EventCategory;
import blastandburn.services.event.ServiceCategory;
import blastandburn.services.event.ServiceEvent;
import blastandburn.services.event.ServiceMail;
import blastandburn.services.recipe.ServiceMail2;
import blastandburn.services.user.UserSession;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import org.apache.commons.io.FileUtils;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author fatma
 */
public class AddEventFController implements Initializable {

    @FXML
    private JFXTextField price;
    @FXML
    private JFXRadioButton paid;
    @FXML
    private ToggleGroup type1;
    @FXML
    private JFXRadioButton free;
    @FXML
    private FontAwesomeIconView close1;
    @FXML
    private JFXTextField Titre;
    @FXML
    private JFXTextArea Description;
    @FXML
    private JFXComboBox<String> comboEventCatg;
    @FXML
    private ImageView image;
    @FXML
    private JFXDatePicker startDate;
    @FXML
    private JFXDatePicker endDate;
    @FXML
    private JFXTextField location;
    private ServiceEvent se = new ServiceEvent();
    File f = null;
    Event e = new Event();
    ServiceCategory sc = new ServiceCategory();
    private String cat = "";
    private ToggleGroup group = new ToggleGroup();
    private static String projectPath = System.getProperty("user.dir").replace("/", "\\");
    private boolean titleV = false, descriptionV = false, locationV = false, priceV = false;
  
    @FXML
    private JFXTextField nnbr;
  
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        paid.setToggleGroup(group);
        free.setToggleGroup(group);
        price.disableProperty().bind(free.selectedProperty());
        for (int i = 0; i < sc.AfficherCategoryEvent().size(); i++) {
            comboEventCatg.getItems().add(sc.AfficherCategoryEvent().get(i).getName());
        }

        titleValidator();
        priceValidator();
        locationValidator();
        descriptionValidator();
    }

    @FXML
    private void minAction(MouseEvent event) {
        Stage stage = new Stage();
        stage = (Stage) close1.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void closeAction(MouseEvent event) {
        Stage stage = new Stage();
        stage = (Stage) Titre.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void listCategoryCombo(ActionEvent event) {
        cat = comboEventCatg.getValue();
    }

    @FXML
    private void addImage(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        f = fileChooser.showOpenDialog(null);
        Image img = new Image("file:///" + f);
        image.setImage(img);
        if (f != null) {
            e.setImgUrl(f.getName());
        };
    }
    
    
    
    
    private void QRcode() throws FileNotFoundException, IOException {
        String contenue = "Titre: " + Titre.getText() + "\n" + "description: " + Description.getText() + "\n" + "Location: " + location.getText();
        ByteArrayOutputStream out = QRCode.from(contenue).to(ImageType.JPG).stream();
        File f = new File(projectPath + "\\src\\blastandburn\\resources\\images\\" + Titre.getText() + ".jpg");
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(out.toByteArray());
        fos.flush();

    }

    @FXML
    private void addEventAction(MouseEvent event) throws IOException {
        e.setUserId(UserSession.getUser_id());
        e.setTitle(Titre.getText());
        e.setDescription(Description.getText());
        e.setStartDate(java.sql.Date.valueOf(startDate.getValue()));
        e.setEndDate(java.sql.Date.valueOf(endDate.getValue()));
        e.setLocation(location.getText());
        int c = Integer.parseInt(nnbr.getText());
        e.setMaxUsers(c);
        EventCategory ec = sc.getEventByName(cat);
        if (paid.isSelected() && price.getText() != "") {
            e.setPrice(Double.valueOf(price.getText()));
        }
        e.setCatId(ec.getCatId());
        e.setCat(ec);
        if (paid.isSelected()) {
            e.setPrice(Double.valueOf(price.getText()));
            e.setType("paid");
        } else {
            e.setType("free");
            e.setPrice(0.0);
        }
        File dest = null;
        if (f != null) {
            dest = new File(projectPath + "/src/blastandburn/resources/images/events/" + f.getName());
        }
        try {
            if (dest != null) {
                if (FileUtils.contentEquals(f, dest)) {
                    System.out.println("existe");
                } else {
                    FileUtils.copyFile(f, dest);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        se.AddEvent(e);
        
        QRcode();
        ServiceMail2 SM = new ServiceMail2();

        String Msg = "Bonjour Mr/Mme , Il y'a un nouvel évènement bientôt , Veuillez consulter l'application pour plus de details  ";

        SM.sendmailfunc("ccandyxx1@gmail.com", Msg);
        
        TrayNotification tray = new TrayNotification();
        AnimationType type = AnimationType.POPUP;
        tray.setAnimationType(type);
        tray.setTitle("Success");
        tray.setMessage("Event added successfully!");
        tray.setNotificationType(NotificationType.SUCCESS);
        tray.showAndDismiss(Duration.millis(3000));
        
        
    }

    public void titleValidator() {
        RegexValidator valid = new RegexValidator();
        valid.setRegexPattern("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$");
        Titre.setValidators(valid);
        valid.setMessage("Title is not valid");
        Titre.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue) {
                    Titre.validate();
                    if (Titre.validate()) {
                        titleV = true;
                    } else {
                        titleV = false;
                    }
                }
            }
        });
        try {
            Image errorIcon = new Image(new FileInputStream("src/blastandburn/resources/images/cancel.png"));
            valid.setIcon(new ImageView(errorIcon));
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void descriptionValidator() {
        RegexValidator valid = new RegexValidator();
        valid.setRegexPattern("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$");
        Description.setValidators(valid);
        valid.setMessage("Description is not valid");
        Description.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue) {
                    Description.validate();
                    if (Description.validate()) {
                        descriptionV = true;
                    } else {
                        descriptionV = false;
                    }
                }
            }
        });
        try {
            Image errorIcon = new Image(new FileInputStream("src/blastandburn/resources/images/cancel.png"));
            valid.setIcon(new ImageView(errorIcon));
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void priceValidator() {
        RegexValidator valid = new RegexValidator();
        valid.setRegexPattern("^(0|[1-9][0-9]*)$");
        price.setValidators(valid);
        valid.setMessage("Not valid");
        price.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue) {
                    price.validate();
                    if (price.validate()) {
                        priceV = true;
                    } else {
                        priceV = false;
                    }
                }
            }
        });
        try {
            Image errorIcon = new Image(new FileInputStream("src/blastandburn/resources/images/cancel.png"));
            valid.setIcon(new ImageView(errorIcon));
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void locationValidator() {
        RegexValidator valid = new RegexValidator();
        valid.setRegexPattern("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$");
        location.setValidators(valid);
        valid.setMessage("Not valid");
        location.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue) {
                    location.validate();
                    if (location.validate()) {
                        locationV = true;
                    } else {
                        locationV = false;
                    }
                }
            }
        });
        try {
            Image errorIcon = new Image(new FileInputStream("src/blastandburn/resources/images/cancel.png"));
            valid.setIcon(new ImageView(errorIcon));
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    
        private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}
