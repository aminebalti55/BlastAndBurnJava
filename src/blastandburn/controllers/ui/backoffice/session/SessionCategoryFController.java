/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.controllers.ui.backoffice.session;

import blastandburn.entities.session.SessionCategory;
import blastandburn.services.session.ServiceSessionCategory;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.commons.io.FileUtils;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class SessionCategoryFController implements Initializable {

    @FXML
    private Pagination pagination;
    @FXML
    private JFXTextField name;
    @FXML
    private FontAwesomeIconView close1;
    @FXML
    private ImageView image;
    @FXML
    private TableView<SessionCategory> categoryTable;
    @FXML
    private TableColumn<SessionCategory, ?> nameCol;
    @FXML
    private TableColumn<SessionCategory, ?> imgCol;
    @FXML
    private FontAwesomeIconView deleteButton;
    @FXML
    private FontAwesomeIconView updateButton;
    private static String projectPath = System.getProperty("user.dir").replace("/", "\\");
    private boolean titleV = false;
    ServiceSessionCategory st = new ServiceSessionCategory();
    SessionCategory tc = new SessionCategory();
    File f = null;
    @FXML
    private Circle add;
    private int itemsPerPage = 2;
    private int  size;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          init();
        categoryTable.setRowFactory(tv -> {
            TableRow<SessionCategory> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    SessionCategory rowData = row.getItem();
                    name.setText(rowData.getName());
                    image.setImage(rowData.getImg().getImage());
                }
            });
            return row;
        });
        titleValidator();
    }

    public void init() {
        ObservableList<SessionCategory> l = FXCollections.observableList(st.ListSessionCategory());
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        imgCol.setCellValueFactory(new PropertyValueFactory<>("img"));
        //categoryTable.getItems().addAll(l);
        size = (l.size() / itemsPerPage) ;
        if(l.size()%itemsPerPage !=0){
            size++;
        }
        pagination.setPageCount(size);
        pagination.setPageFactory((pageIndex) -> {
            categoryTable.getItems().clear();
            int page = pageIndex * itemsPerPage;

            for (int i = page; i < page + itemsPerPage; i++) {

                if (i >= l.size()) {
                    return categoryTable;
                }
                categoryTable.getItems().add( l.get(i));

            }
            return categoryTable;
        });
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
        stage = (Stage) categoryTable.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void addImage(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        f = fileChooser.showOpenDialog(null);
        Image img = new Image("file:///" + f);
        image.setImage(img);
        if (f != null) {
            tc.setImgUrl(f.getName());
        }
    }

    @FXML
    private void deleteCategoryAction(MouseEvent event) {
        Window owner = add.getScene().getWindow();
        SessionCategory Sessionc = null;
        Sessionc = categoryTable.getSelectionModel().getSelectedItem();
        st.deleteSessionCategory(Sessionc.getCatgid());
        AlertBox(Alert.AlertType.CONFIRMATION, owner, "Confirmation", "Category deleted successfully!");
        categoryTable.getItems().clear();
        init();
    }

    @FXML
    private void updateCategoryAction(MouseEvent event) {
        Window owner = add.getScene().getWindow();
        SessionCategory Sessionc = null;
        Sessionc = categoryTable.getSelectionModel().getSelectedItem();
        tc.setCatgid(Sessionc.getCatgid());
        tc.setName(name.getText());
        File dest = null;
        if (f != null) {
            dest = new File(projectPath + "/src/blastandburn/resources/images/Sessions/" + f.getName());
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
        st.updateSessionCategory(tc);
        AlertBox(Alert.AlertType.CONFIRMATION, owner, "Confirmation", "Category updated successfully!");
        categoryTable.getItems().clear();
        init();
    }

    @FXML
    private void addCategoryAction(MouseEvent event) {
        Window owner = add.getScene().getWindow();
        tc.setName(name.getText());
        File dest = null;
        if (f != null) {
            dest = new File(projectPath + "/src/blastandburn/resources/images/Sessions/" + f.getName());
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
        st.createSessionCategory(tc);
        AlertBox(Alert.AlertType.CONFIRMATION, owner, "Confirmation", "Category added successfully!");
        categoryTable.getItems().clear();
        init();
    }

    public void titleValidator() {
        RegexValidator valid = new RegexValidator();
        valid.setRegexPattern("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$");
        name.setValidators(valid);
        valid.setMessage("Name is not valid");
        name.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue) {
                    name.validate();
                    if (name.validate()) {
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

    private static void AlertBox(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

}
