/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.controllers.ui.frontoffice.recipe;

import blastandburn.entities.recipe.Recipe;
import blastandburn.services.recipe.RecipeCategoryService;
import blastandburn.services.recipe.RecipeService;
import blastandburn.services.user.UserSession;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.teknikindustries.bulksms.SMS;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
 * @author user
 */
public class AddRecipeFController implements Initializable {

    File f = null;
    Recipe r = new Recipe();
    private static String projectPath = System.getProperty("user.dir").replace("/", "\\");
    RecipeService rs = new RecipeService();
    String cat = "";
    @FXML
    private FontAwesomeIconView close1;
    @FXML
    private ImageView image;
    @FXML
    private JFXTextField TitleTF;
    @FXML
    private JFXTextArea DescTF;
    @FXML
    private JFXComboBox<String> CatBox;
    @FXML
    private JFXTextField PersonsTF;
    @FXML
    private FontAwesomeIconView addRecipeBT;
    @FXML
    private JFXTextArea IngredientsTF;
    @FXML
    private JFXTextArea StepsTF;
    @FXML
    private JFXTextField CaloriesTF;
    @FXML
    private JFXTextField DurationTF;

    //test textfield 
    private boolean title = false, cal = false, dur = false, per = false, desc = false, ingred = false, steps = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //comboBox
        RecipeCategoryService rcs = new RecipeCategoryService();
        for (int i = 0; i < rcs.Afficher_RecipeCategory().size(); i++) {
            CatBox.getItems().add(rcs.Afficher_RecipeCategory().get(i).getName());
        }

        //appel contrôle saisie
        titleValidator();
        descValidator();
        ingredValidator();
        stepsValidator();
        caloriesValidator();
        durationValidator();
        personsValidator();
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
    private void addImage(MouseEvent event) {
        FileChooser chooser = new FileChooser();
        f = chooser.showOpenDialog(null);
        Image img = new Image("file:///" + f);
        image.setImage(img);
        //imgTF.setText(f.getName());
        System.out.println(f.getName());
        r.setImgUrl(f.getName());
        System.out.println(r.getImgUrl());
    }

    @FXML
    private void ListCategoriesBox(ActionEvent event) {
        cat = CatBox.getValue();
    }
    
    private void QRcode() throws FileNotFoundException, IOException {
        String contenue = "Titre: " + TitleTF.getText() + "\n" + "description: " + DescTF.getText() + "\n" + "Description: " + IngredientsTF.getText();
        ByteArrayOutputStream out = QRCode.from(contenue).to(ImageType.JPG).stream();
        File f = new File(projectPath + "\\src\\blastandburn\\resources\\images\\" + TitleTF.getText() + ".jpg");
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(out.toByteArray());
        fos.flush();

    }
    @FXML
    private void addRecipeAction(MouseEvent event) throws IOException {
        javafx.stage.Window owner = addRecipeBT.getScene().getWindow();
        if (TitleTF.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Error!",
                    "Enter a title for your recipe!");
            return;

        }
        if (DescTF.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Error!",
                    "Describe your recipe!");
            return;
        }
        if (IngredientsTF.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Error!",
                    "Enter the required ingredients for your recipe !");
            return;
        }
        if (StepsTF.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Error!",
                    "Enter the steps of preparing of your recipe !");
            return;
        }
        if (CaloriesTF.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Error!",
                    "Enter the number of calories of your recipe !");
            return;
        }
        if (DurationTF.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Error!",
                    "Enter the  required ingredients for your recipe !");
            return;
        }
        if (PersonsTF.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Error!",
                    "Enter the number of persons of your recipe !");
            return;
        }

        r.setTitle(TitleTF.getText());
        r.setDescription(DescTF.getText());
        r.setIngredients(IngredientsTF.getText());
        r.setSteps(StepsTF.getText());
        int c = Integer.parseInt(CaloriesTF.getText());
        r.setCalories(c);
        int d = Integer.parseInt(DurationTF.getText());
        r.setDuration(d);
        int p = Integer.parseInt(PersonsTF.getText());
        r.setPersons(p);

        File dest = new File(projectPath + "/src/blastandburn/resources/images/recipes/" + f.getName());
        try {
            FileUtils.copyFile(f, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        rs.Create_Recipe(UserSession.getUser_id(), cat, r);
//  showAlert(Alert.AlertType.CONFIRMATION, owner, "Confirmation!",
//                "Recipe added successfully!");
         //SMS sms=new SMS();
      //  sms.SendSMS("mohamedamine12", "azertyQSD1", "hajoura test", "21692358690", "https://bulksms.vsms.net/eapi/submission/send_sms/2/2.0");
        //Notification
       // QRcode();
        TrayNotification tray = new TrayNotification();
        AnimationType type = AnimationType.POPUP;
        tray.setAnimationType(type);
        tray.setTitle("Success");
        tray.setMessage("Recipe added successfully!");
        tray.setNotificationType(NotificationType.SUCCESS);
        tray.showAndDismiss(Duration.millis(3000));
        
        
   
        
        
        
        
    }

    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    //contrôle saisie 
    public void titleValidator() { //title empty string
        RegexValidator valid = new RegexValidator();
        valid.setRegexPattern("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$");
        TitleTF.setValidators(valid);
        valid.setMessage("Enter the title");
        TitleTF.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue) {
                    TitleTF.validate();
                    if (TitleTF.validate()) {
                        title = true;
                    } else {
                        title = false;
                    }
                }
            }
        });
        try {
            Image errorIcon = new Image(new FileInputStream("src/blastandburn/resources/images/cancel.png"));
            valid.setIcon(new ImageView(errorIcon));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AddRecipeFController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void descValidator() { //description empty string
        RegexValidator valid = new RegexValidator();
        valid.setRegexPattern("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$");
        DescTF.setValidators(valid);
        valid.setMessage("Enter the description");
        DescTF.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue) {
                    DescTF.validate();
                    if (DescTF.validate()) {
                        desc = true;
                    } else {
                        desc = false;
                    }
                }
            }
        });
        try {
            Image errorIcon = new Image(new FileInputStream("src/blastandburn/resources/images/cancel.png"));
            valid.setIcon(new ImageView(errorIcon));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AddRecipeFController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ingredValidator() { //ingredients empty string
        RegexValidator valid = new RegexValidator();
        valid.setRegexPattern("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$");
        IngredientsTF.setValidators(valid);
        valid.setMessage("Enter the description");
        IngredientsTF.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue) {
                    IngredientsTF.validate();
                    if (IngredientsTF.validate()) {
                        ingred = true;
                    } else {
                        ingred = false;
                    }
                }
            }
        });
        try {
            Image errorIcon = new Image(new FileInputStream("src/blastandburn/resources/images/cancel.png"));
            valid.setIcon(new ImageView(errorIcon));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AddRecipeFController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void stepsValidator() { //steps empty string
        RegexValidator valid = new RegexValidator();
        valid.setRegexPattern("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$");
        StepsTF.setValidators(valid);
        valid.setMessage("Enter the description");
        StepsTF.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue) {
                    StepsTF.validate();
                    if (StepsTF.validate()) {
                        steps = true;
                    } else {
                        steps = false;
                    }
                }
            }
        });
        try {
            Image errorIcon = new Image(new FileInputStream("src/blastandburn/resources/images/cancel.png"));
            valid.setIcon(new ImageView(errorIcon));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AddRecipeFController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void caloriesValidator() { //calories only numbers
        RegexValidator valid = new RegexValidator();
        valid.setRegexPattern("^(0|[1-9][0-9]*)$");
        CaloriesTF.setValidators(valid);
        valid.setMessage("not valid");
        CaloriesTF.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue) {
                    CaloriesTF.validate();
                    if (CaloriesTF.validate()) {
                        cal = true;
                    } else {
                        cal = false;
                    }
                }
            }
        });
        try {
            Image errorIcon = new Image(new FileInputStream("src/blastandburn/resources/images/cancel.png"));
            valid.setIcon(new ImageView(errorIcon));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AddRecipeFController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void durationValidator() { //duration only numbers
        RegexValidator valid = new RegexValidator();
        valid.setRegexPattern("^(0|[1-9][0-9]*)$");
        DurationTF.setValidators(valid);
        valid.setMessage("not valid");
        DurationTF.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue) {
                    DurationTF.validate();
                    if (DurationTF.validate()) {
                        dur = true;
                    } else {
                        dur = false;
                    }
                }
            }
        });
        try {
            Image errorIcon = new Image(new FileInputStream("src/blastandburn/resources/images/cancel.png"));
            valid.setIcon(new ImageView(errorIcon));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AddRecipeFController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void personsValidator() { //persons only numbers
        RegexValidator valid = new RegexValidator();
        valid.setRegexPattern("^(0|[1-9][0-9]*)$");
        PersonsTF.setValidators(valid);
        valid.setMessage("not valid");
        PersonsTF.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue) {
                    PersonsTF.validate();
                    if (PersonsTF.validate()) {
                        per = true;
                    } else {
                        per = false;
                    }
                }
            }
        });
        try {
            Image errorIcon = new Image(new FileInputStream("src/blastandburn/resources/images/cancel.png"));
            valid.setIcon(new ImageView(errorIcon));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AddRecipeFController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
