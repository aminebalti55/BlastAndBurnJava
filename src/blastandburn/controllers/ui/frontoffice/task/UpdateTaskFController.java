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
 * @author Admin
 */
public class UpdateTaskFController implements Initializable {

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
    private JFXComboBox<String> comboCatg;
    
    @FXML
    private ImageView image;
    private ToggleGroup group = new ToggleGroup();
    private ServicePaidSession spt = new ServicePaidSession();
    private PaidSession pt;
    private File f = null;
    private ServiceSessionCategory stc = new ServiceSessionCategory();
    private String cat = "";
    private SessionCategory tc;
    private static String projectPath = System.getProperty("user.dir").replace("/", "\\");
    private Session task;
    TaskHolder th = TaskHolder.getINSTANCE();
    private ServiceSession st = new ServiceSession();
    private boolean titleV = false, descriptionV = false, priceV = false;

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
        if (spt.getPaidSession(th.getId()) != null) {
            pt = spt.getPaidSession(th.getId());
            Titre.setText(pt.getTitle());
            Description.setText(pt.getDescription());
            image.setImage(pt.getImg().getImage());
            paid.setSelected(true);
            price.setDisable(false);
            price.setText(String.valueOf(pt.getPrice()));
            comboCatg.getSelectionModel().select(pt.getCategory().getName());
            if (free.isSelected()) {
                price.setDisable(true);
            }
        } else {
            task = st.getSession(th.getId());
            Titre.setText(task.getTitle());
            image.setImage(task.getImg().getImage());
            Description.setText(task.getDescription());
            free.setSelected(true);
            price.disableProperty().bind(free.selectedProperty());
            comboCatg.getSelectionModel().select(task.getCategory().getName());
        }

        titleValidator();
        priceValidator();
        descriptionValidator();
    }

    @FXML
    private void paidAction(ActionEvent event) {
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
        stage = (Stage) close1.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void listCategoryCombo(ActionEvent event) {
        cat = comboCatg.getValue();
    }

    @FXML
    private void addImage(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        f = fileChooser.showOpenDialog(null);
        Image img = new Image("file:///" + f);
        image.setImage(img);
        if (task != null) {
            task.setImgUrl(f.getName());
        } else if (pt != null) {
            pt.setImgUrl(f.getName());
        }
    }

    @FXML
    private void updateTaskAction(MouseEvent event) {
        Window owner = Titre.getScene().getWindow();
        // if(titleV && descriptionV  && daysV && comboCatg.getValue()!=""){
        if (pt != null) {
            if (paid.isSelected() && price.getText() != "") {
                pt.setTitle(Titre.getText());
                pt.setDescription(Description.getText());
//                pt.setMaxUsers(Integer.valueOf(maxUsers.getText()));
//                pt.setMinUsers(Integer.valueOf(minUsers.getText()));
                pt.setPrice(Double.valueOf(price.getText()));
                File dest = null;
                if (f != null) {
                    dest = new File(projectPath + "/src/blastandburn/resources/images/tasks/" + pt.getImgUrl());
                } else {
                    System.out.println("vide");
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
                    System.out.println(e.getMessage());
                }
                if (cat != "") {
                    tc = stc.searchSessionCategory(cat);
                    pt.setCategory(tc);
                }
                st.updateSession(pt, th.getId());
                spt.updatePaidSession(pt, th.getId());
                AlertBox(Alert.AlertType.CONFIRMATION, owner, "Confirmation",
                    "Task updated successfully!");

            } else if (free.isSelected()) {
                pt.setTitle(Titre.getText());
                pt.setDescription(Description.getText());
//                pt.setMaxUsers(Integer.valueOf(maxUsers.getText()));
//                pt.setMinUsers(Integer.valueOf(minUsers.getText()));
                File dest = null;
                if (f != null) {
                    dest = new File(projectPath + "/src/blastandburn/resources/images/tasks/" + pt.getImgUrl());
                } else {
                    System.out.println("vide");
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
                    System.out.println(e.getMessage());
                }
                if (cat != "") {
                    tc = stc.searchSessionCategory(cat);
                    pt.setCategory(tc);
                }
                spt.makeFreeSession(th.getId());
                st.updateSession((Session) pt, th.getId());
                AlertBox(Alert.AlertType.CONFIRMATION, owner, "Confirmation",
                    "Session updated successfully!");
            }

        } else if (task != null) {
            if (free.isSelected()) {
                task.setTitle(Titre.getText());
                task.setDescription(Description.getText());
//                task.setMaxUsers(Integer.valueOf(maxUsers.getText()));
//                task.setMinUsers(Integer.valueOf(minUsers.getText()));
                File dest = null;
                if (f != null) {
                    dest = new File(projectPath + "/src/blastandburn/resources/images/tasks/" + task.getImgUrl());
                } else {
                    System.out.println("vide");
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
                    System.out.println(e.getMessage());
                }
                if (cat != "") {
                    tc = stc.searchSessionCategory(cat);
                    task.setCategory(tc);
                }
                st.updateSession(task, th.getId());
                AlertBox(Alert.AlertType.CONFIRMATION, owner, "Confirmation",
                    "Session updated successfully!");
            } else if (paid.isSelected() && price.getText() != "") {
                task.setTitle(Titre.getText());
                task.setDescription(Description.getText());
//                task.setMaxUsers(Integer.valueOf(maxUsers.getText()));
//                task.setMinUsers(Integer.valueOf(minUsers.getText()));
                File dest = null;
                if (f != null) {
                    dest = new File(projectPath + "/src/blastandburn/resources/images/tasks/" + task.getImgUrl());
                } else {
                    System.out.println("vide");
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
                    System.out.println(e.getMessage());
                }

                if (cat != "") {
                    tc = stc.searchSessionCategory(cat);
                    task.setCategory(tc);
                }
                st.updateSession(task, th.getId());
                spt.makePaidSession(th.getId(), Double.valueOf(price.getText()));
                AlertBox(Alert.AlertType.CONFIRMATION, owner, "Confirmation",
                    "Session updated successfully!");
            }

        }
       // }else AlertBox(Alert.AlertType.ERROR, owner, "Erreur",
               //     "Task not updated !");
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
