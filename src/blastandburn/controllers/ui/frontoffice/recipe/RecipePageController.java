/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.controllers.ui.frontoffice.recipe;

import animatefx.animation.ZoomIn;
import blastandburn.controllers.ui.frontoffice.HomePageHolderController;
import blastandburn.entities.recipe.Recipe;
import blastandburn.entities.recipe.RecipeCategory;
import blastandburn.services.recipe.RecipeService;
import blastandburn.services.ui.UIService;
import blastandburn.services.user.UserSession;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import  blastandburn.services.recipe.BmiMath;
import   blastandburn.services.recipe.Lifestyles;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


/**
 * FXML Controller class
 *
 * @author fatma
 */
public class RecipePageController implements Initializable {

    private String searchWord = "", comboValue = "All";
    @FXML
    private HBox categoriesHBox;
    private RecipeService st = new RecipeService();
    private UIService stc = new UIService();
    @FXML
    private ScrollPane recipePane;
    private GridPane recipeGrid;
    double xOffset, yOffset;
    @FXML
    private JFXButton addBtn;
    @FXML
    private Pagination pagination;
    @FXML
    private JFXComboBox<String> ComboBox;
    @FXML
    private JFXTextField searchRecipe;
    @FXML
    private FontAwesomeIconView refreshBtn;
    @FXML
    private JFXButton bmi;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if (UserSession.getRole().equals("ROLE_Coach")) {
            addBtn.setVisible(true);
            ComboBox.setVisible(true);
            refreshBtn.setVisible(true);
            bmi.setVisible(false);
        }
        if (UserSession.getRole().equals("ROLE_USER")){
            bmi.setLayoutX(861);
            bmi.setLayoutY(426);
              addBtn.setVisible(false);
                          bmi.setVisible(true);

        }
        else if (UserSession.getRole().equals("ROLE_Moderator")){
           addBtn.setVisible(true);
                          bmi.setVisible(false);
                            ComboBox.setVisible(true);
            refreshBtn.setVisible(true);
        
        }

        new ZoomIn(recipePane).play();

        //ComboBox
        ComboBox.getItems().add("All");
        ComboBox.getItems().add("Yours");
        ComboBox.getSelectionModel().select("All");

        List<RecipeCategory> catRecipes = stc.topThreeRecCatg();
        System.out.println(catRecipes.size());

        for (int i = 0; i < catRecipes.size(); i++) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/blastandburn/views/ui/frontoffice/recipe/RecipeCategoryItem.fxml"));
            System.out.println(catRecipes.get(i));
            try {
                AnchorPane pane = loader.load();
                RecipeCategoryItemController c = loader.getController();
                c.setData(catRecipes.get(i));
                categoriesHBox.getChildren().add(pane);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

        pagination.setPageFactory((pageindex) -> grid(pageindex, searchWord, comboValue));
    }

    //to open GridRecipe.fxml + parameters: pageindex=current page & what we need else in the current page 
    public GridPane grid(int pageindex, String searchWord, String comboValue) {
        GridPane pane = null;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/blastandburn/views/ui/frontoffice/recipe/GridRecipe.fxml"));
        try {
            pane = loader.load();
            GridRecipeController c = loader.getController();
            c.setData(pageindex, searchWord, comboValue);
            pagination.setPageCount(c.pageCount);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return pane;
    }

    @FXML
    private void addRecipeAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/blastandburn/views/ui/frontoffice/recipe/AddRecipeF.fxml"));
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
        AnchorPane pageHolder = (AnchorPane) addBtn.getParent().getParent().getParent().getParent().getParent();
        pageHolder.getChildren().removeAll(pageHolder.getChildren());
        pageHolder.getChildren().add(FXMLLoader.load(getClass().getResource("/blastandburn/views/ui/frontoffice/recipe/AllCategories.fxml")));

    }

    @FXML
    private void ComboBoxRole(ActionEvent event) throws SQLException {

        if ("Yours".equals(ComboBox.getValue())) {
            comboValue = "Yours";
            pagination.setPageFactory((pageindex) -> grid(pageindex, searchWord, comboValue));

        } else if ("All".equals(ComboBox.getValue())) {
            comboValue = "All";
            pagination.setPageFactory((pageindex) -> grid(pageindex, searchWord, comboValue));

        }
        //Role of ComboBox in GridRecipeController
    }

    @FXML
    private void rechercheRecipe(KeyEvent event) throws SQLException {
        List<Recipe> recipes = new ArrayList();
        if (!"".equals(searchRecipe.getText())) {
            searchWord = searchRecipe.getText();
            pagination.setPageFactory((pageindex) -> grid(pageindex, searchWord, comboValue));
        }

    }

    @FXML
    private void refreshPage(MouseEvent event) {
        new ZoomIn(recipePane).play();

        ComboBox.getItems().add("All");
        ComboBox.getItems().add("Yours");
        ComboBox.getSelectionModel().select("All");

        List<RecipeCategory> catRecipes = stc.topThreeRecCatg();
        System.out.println(catRecipes.size());

        for (int i = 0; i < catRecipes.size(); i++) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/blastandburn/views/ui/frontoffice/recipe/RecipeCategoryItem.fxml"));
            System.out.println(catRecipes.get(i));
            try {
                AnchorPane pane = loader.load();
                RecipeCategoryItemController c = loader.getController();
                c.setData(catRecipes.get(i));
                categoriesHBox.getChildren().add(pane);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

        pagination.setPageFactory((pageindex) -> grid(pageindex, searchWord, comboValue));

    }
    
    //////*****BMI*****//////
          private VBox setupVBox()
    {
        // VBox is the root of all elements in the UI
        VBox layout = new VBox();
        layout.setPadding(new Insets(10));
        layout.setSpacing(8);
        layout.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
     
        return layout;
    }
            private HBox setupHBox()
    {
        // HBox will contain the two columns for data and results
        HBox layout = new HBox();
        layout.setPadding(new Insets(15, 12, 15, 12));
       
     //  layout.setStyle("-fx-padding: 10; -fx-background-color: cornsilk;");
        
        return layout;
    }
            
            
    private GridPane setupGrid()
    {
        // GridPane to the left of the HBox, represents user-inputted data
        GridPane layout = new GridPane();
        layout.setHgap(10);
        layout.setVgap(10);
        layout.setPadding(new Insets(0, 10, 0, 10));
        
        return layout;
    }
    
    private Label createTitle(String text)
    {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        label.setUnderline(true);
        
        return label;
    }

     private void setupLabels(GridPane pane, String[] labels, String title)
    {
        pane.add(createTitle(title + ":"), 0, 0);

        for (int i = 0; i < labels.length; i++)
        {
            pane.add(new Label(labels[i]), 0, (i + 1));
        }
    }
     
     private List<TextField> setupFields(GridPane pane, String[] labels)
    {
        List<TextField> fields = new ArrayList();
        
        for (int i = 0; i < labels.length; i++)
        {
            TextField field = new TextField();
            field.setPromptText(labels[i]);
            fields.add(field);
            pane.add(field, 1, (i + 1));
        }
        
        return fields;
    }
     
      private RadioButton setupRadio(GridPane data)
    {
        ToggleGroup group = new ToggleGroup();
        
        RadioButton gInput = new RadioButton("Male");
        gInput.setToggleGroup(group);
        gInput.setSelected(true);
        
        RadioButton gInput2 = new RadioButton("Female");
        gInput2.setToggleGroup(group);
        
        HBox radio = new HBox(10);
        radio.getChildren().addAll(gInput, gInput2);
        data.add(radio, 1, 4);
        
        return gInput;
    }
      
        private ChoiceBox setupCBox(GridPane data)
    {
        ChoiceBox ls = new ChoiceBox(FXCollections.observableArrayList(Lifestyles.values()));
        ls.getSelectionModel().selectFirst();
        data.add(ls, 1, 5);
        
        return ls;
    }
      private boolean isEmpty(List<TextField> dFields)
    {
        return (dFields.get(0).getText().equals("") ||
                dFields.get(1).getText().equals("") ||
                dFields.get(2).getText().equals(""));
    }
    
    private void fail(List<TextField> rFields)
    {
        for (TextField field : rFields)
        {
            field.setStyle("-fx-text-inner-color: red;");
            field.setText("Failed!");
        }
    }
    
    private void succeed(List<TextField> rFields, double[] out)
    {
        for (int i = 0; i < out.length; i++)
        {
            rFields.get(i).setStyle("-fx-text-inner-color: black;");
            rFields.get(i).setText(String.valueOf(out[i]));
        }
    }
    
    private boolean isGoodInput(List<TextField> dFields)
    {
        for (TextField field : dFields)
        {
            try
            {
                Double.parseDouble(field.getText());
            }
            catch (NumberFormatException e)
            {
                return false;
            }
        }
        
        return true;
    }
    
    @FXML
    private void showbmi(ActionEvent event ) {
        
   
        VBox root = setupVBox();
        HBox columns = setupHBox();
        GridPane data = setupGrid();
        GridPane results = setupGrid();
         // Left column - Data
        String[] dLabels = {"Size in cm", "Weight in kg", "Age in years",
        "Gender", "Lifestyle"};
        setupLabels(data, dLabels, "Data");
        // Subset of dLabels since Gender and Lifestyle are not TextFields
        List<TextField> dFields = setupFields(data, Arrays.copyOfRange(dLabels, 0, 3));
        RadioButton gender = setupRadio(data);
        ChoiceBox lifestyle = setupCBox(data);
        
        
        
        // Right column - Results
        String[] rLabels = {"BMR Results", "Calories expenses"};
        setupLabels(results, rLabels, "Results");
        List<TextField> rFields = setupFields(results, rLabels);
        
        // Create calculate button
        Button btn = new Button();
        btn.setStyle("-fx-background-color: #FF0000");
        HBox.setHgrow(btn, Priority.ALWAYS);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setText("Calculate BMI");
        Font font = Font.font("Roboto", FontWeight.BOLD, 25);
        btn.setFont(font);

        btn.setStyle("-fx-text-fill: #000000");
 

        

        // We add all the respective elements to their parent layouts
        columns.getChildren().addAll(data, results);
        root.getChildren().addAll(columns, btn);
        
        btn.setOnAction((ActionEvent myevent) ->
        {
            // We reject empty or non-double input
            if (isEmpty(dFields) || !isGoodInput(dFields.subList(0, 3)))
            {
                fail(rFields);
            }
            else
            {
                // Parse input
                double s = Double.parseDouble(dFields.get(0).getText());
                double w = Double.parseDouble(dFields.get(1).getText());
                double a = Double.parseDouble(dFields.get(2).getText());
                
                // Get selectable values
                Lifestyles ls = (Lifestyles) lifestyle.getValue();
                double multiplier = ls.getMult();
                boolean g = gender.selectedProperty().get();
                
                BmiMath bib = new BmiMath();
                // Calculate bmi and calories expenses
                double bmi = BmiMath.getBmi(s, w, a, g);
                double cal = BmiMath.getCal(bmi, multiplier);
                
                // Display bmi and calories expenses
                succeed(rFields, new double[] {bmi, cal});
                
            }
        });
        
         
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


}
