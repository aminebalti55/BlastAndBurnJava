/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.controllers.ui.frontoffice.task;

import blastandburn.entities.session.PaidSession;
import blastandburn.entities.session.Session;
import blastandburn.entities.session.SessionCategory;
import blastandburn.services.session.ServicePaidSession;
import blastandburn.services.session.ServiceSession;
import blastandburn.services.session.ServiceSessionCategory;
import blastandburn.services.user.UserSession;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.commons.io.FileUtils;

/**
 * FXML Controller class
 *
 * @author BilelxOS
 */
public class AddTaskFController implements Initializable {
    private Session session = null;
    private ServiceSession st = new ServiceSession();
    private Session t = new Session();
    private ServiceSessionCategory stc = new ServiceSessionCategory();
    private String cat = "";
    private File f = null;
    private static String projectPath = System.getProperty("user.dir").replace("/", "\\");
    private ToggleGroup group = new ToggleGroup();
    private ServicePaidSession spt = new ServicePaidSession();
    private PaidSession pt = new PaidSession();
    @FXML
    private ToggleGroup type1;
    @FXML
    private FontAwesomeIconView close1;
    @FXML
    private JFXTextField price;
    @FXML
    private JFXRadioButton free;
    @FXML
    private JFXTextField Titre;
    @FXML
    private JFXTextArea Description;
    @FXML
    private JFXComboBox<String> comboCatg;
   
    @FXML
    private JFXRadioButton paid;
    @FXML
    private ImageView image;
    private boolean titleV = false,descriptionV = false,priceV = false ;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SessionCategory tc = new SessionCategory();
        for (int i = 0; i < stc.ListSessionCategory().size(); i++) {
            comboCatg.getItems().add(stc.ListSessionCategory().get(i).getName());
        }
        paid.setToggleGroup(group);
        free.setToggleGroup(group);
        free.setSelected(true);
        price.disableProperty().bind(free.selectedProperty());
        titleValidator();
        priceValidator();
        descriptionValidator();
    }    

    @FXML
    private void closeAction(MouseEvent event) {
        Stage stage = new Stage();
        stage = (Stage) close1.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void minAction(MouseEvent event) {
        Stage stage = new Stage();
        stage = (Stage) close1.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void paidAction(ActionEvent event) {
    }

    @FXML
    private void listCategoryCombo(ActionEvent event) {
        cat = comboCatg.getValue();
    }

    @FXML
    private void addImage(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        f = fileChooser.showOpenDialog(null);
        Image img = new Image("file:///"+f);
        image.setImage(img);
        if(f!=null){
        t.setImgUrl(f.getName());
        pt.setImgUrl(f.getName());
        }
    }

    @FXML
    private void addTaskAction(MouseEvent event) {
        Window owner = Titre.getScene().getWindow();
        if(titleV && descriptionV && cat!=""){
        if (paid.isSelected() && price != null) {
            pt.setTitle(Titre.getText());
            pt.setDescription(Description.getText());
            pt.setMaxUsers(1);
            pt.setMinUsers(1);
            pt.setPrice(Double.valueOf(price.getText()));
            File dest = null;
            if (f != null) {
                dest = new File(projectPath + "/src/blastandburn/resources/images/tasks/" + f.getName());
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
            spt.addPaidSession(UserSession.getUser_id(), cat, pt);
            AlertBox(Alert.AlertType.CONFIRMATION, owner, "Confirmation",
                    "Session added successfully!");
        } else {

            t.setTitle(Titre.getText());
            t.setDescription(Description.getText());
            t.setMaxUsers(1);
            t.setMinUsers(1);
            File dest = null;
            if (f != null) {
                dest = new File(projectPath + "/src/blastandburn/resources/images/tasks/" + f.getName());
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
            st.createSession(UserSession.getUser_id(), cat, t);
            AlertBox(Alert.AlertType.CONFIRMATION, owner, "Confirmation",
                    "session added successfully!");
        }
        }else AlertBox(Alert.AlertType.ERROR, owner, "Erreur",
                    "session not added !");
    }
    private static void AlertBox(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
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
    
   
}
