/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.controllers.ui.backoffice.report;

import blastandburn.controllers.ui.frontoffice.report.UrgentReportItemController;
import blastandburn.entities.report.Report;
import blastandburn.services.report.ReportService;
import blastandburn.services.user.UserSession;
import blastandburn.utils.MyConnection;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javafx.beans.property.ReadOnlyObjectWrapper;
import java.io.File;
import javafx.application.Platform;
import javafx.stage.FileChooser;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author user
 */
public class ModerationPageController implements Initializable {

    ReportService rs = new ReportService();
    @FXML
    private FontAwesomeIconView close;
    @FXML
    private AnchorPane moderationPane;
    @FXML
    private TableView<Report> allReportsTable;
    @FXML
    private TableColumn<Report, String> typeCol;
    @FXML
    private TableColumn<Report, String> noteCol;
    @FXML
    private TableColumn<Report, Boolean> statusCol;
    @FXML
    private TableColumn<Report, Timestamp> dateAndTimeCol;
    @FXML
    private TableColumn<?, ?> titleCol;
   
    @FXML
    private TableView<Report> recipesReportsTable;
    @FXML
    private TableColumn<?, ?> titleCol11;
    @FXML
    private TableColumn<?, ?> noteCol11;
    @FXML
    private TableColumn<?, ?> statusCol11;
    @FXML
    private TableColumn<?, ?> dateAndTimeCol11;
    
    @FXML
    private TableView<Report> eventsReportsTable;
    @FXML
    private TableColumn<?, ?> titleCol13;
    @FXML
    private TableColumn<?, ?> noteCol13;
    @FXML
    private TableColumn<?, ?> statusCol13;
    @FXML
    private TableColumn<?, ?> dateAndTimeCol13;
    @FXML
    private TableView<Report> sessionsReportsTable;
    @FXML
    private TableColumn<?, ?> titleCol14;
    @FXML
    private TableColumn<?, ?> noteCol14;
    @FXML
    private TableColumn<?, ?> statusCol14;
    @FXML
    private TableColumn<?, ?> dateAndTimeCol14;
    @FXML
    private VBox urgentReportsVBox;
    ReportService reportService = new ReportService();
    @FXML
    private Label noUrgReportsLabel;
    @FXML
    private PieChart pieChart;
    @FXML
    private JFXButton printili;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        noteCol.setCellValueFactory(new PropertyValueFactory<>("note"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("isClosed"));
        dateAndTimeCol.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        allReportsTable.setItems((ObservableList<Report>) rs.allReportsListObsv());
        recipeTable();
        eventTable();
        sessionTable();
        urgentReports();
        pieChart();
        
    }

    public void pieChart() {
        ObservableList<PieChart.Data> valueList = FXCollections.observableArrayList(
                new PieChart.Data("Recipes Reports", rs.reportsList("recipe_report").size()),
                new PieChart.Data("Events Reports", rs.reportsList("event_report").size()),
                new PieChart.Data("Sessions Reports", rs.reportsList("session_report").size())
        );
        pieChart.setTitle("Reports");
        pieChart.setData(valueList);
        pieChart.getData().forEach(data -> {
            String percentage = String.format("%.2f%%", (data.getPieValue() / 100));
            Tooltip toolTip = new Tooltip(percentage);
            Tooltip.install(data.getNode(), toolTip);
        });
        final Label caption = new Label("");
        caption.setTextFill(Color.DARKORANGE);
        caption.setStyle("-fx-font: 24 arial;");
        for (final PieChart.Data data : pieChart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    caption.setTranslateX(e.getSceneX());
                    caption.setTranslateY(e.getSceneY());
                    caption.setText(String.valueOf(data.getPieValue()) + "%");
                }
            });
        }
    }
//ordre by date limit 2
    //
    public void urgentReports() {
        List<Report> reportsL = reportService.reportsCount();
        if (reportsL.isEmpty()) {
            noUrgReportsLabel.setVisible(true);
        } else {
            noUrgReportsLabel.setVisible(false);
            for (int i = 0; i < reportsL.size(); i++) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/blastandburn/views/ui/frontoffice/report/UrgentReportItem.fxml"));
                try {
                    AnchorPane pane = loader.load();
                    UrgentReportItemController c = loader.getController();
                    c.setData(reportsL.get(i));
                    urgentReportsVBox.getChildren().add(pane);

                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    @FXML
    private void closeAction(MouseEvent event) {
        Stage stage = new Stage();
        stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void minAction(MouseEvent event) {
        Stage stage = new Stage();
        stage = (Stage) close.getScene().getWindow();
        stage.setIconified(true);
    }

    

    public void recipeTable() {
        titleCol11.setCellValueFactory(new PropertyValueFactory<>("title"));
        noteCol11.setCellValueFactory(new PropertyValueFactory<>("note"));
        statusCol11.setCellValueFactory(new PropertyValueFactory<>("isClosed"));
        dateAndTimeCol11.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        recipesReportsTable.setItems((ObservableList<Report>) rs.reportsList("recipe_report"));
    }



    public void eventTable() {
        titleCol13.setCellValueFactory(new PropertyValueFactory<>("title"));
        noteCol13.setCellValueFactory(new PropertyValueFactory<>("note"));
        statusCol13.setCellValueFactory(new PropertyValueFactory<>("isClosed"));
        dateAndTimeCol13.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        eventsReportsTable.setItems((ObservableList<Report>) rs.reportsList("event_report"));
    }

    public void sessionTable() {
        titleCol14.setCellValueFactory(new PropertyValueFactory<>("title"));
        noteCol14.setCellValueFactory(new PropertyValueFactory<>("note"));
        statusCol14.setCellValueFactory(new PropertyValueFactory<>("isClosed"));
        dateAndTimeCol14.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        sessionsReportsTable.setItems((ObservableList<Report>) rs.reportsList("session_report"));
    }

    public static void exportData(String fileName, String tabName, double[][] data) throws FileNotFoundException, IOException
    {
        //Create new workbook and tab
        Workbook wb = new HSSFWorkbook();
        FileOutputStream fileOut = new FileOutputStream(fileName);
        Sheet sheet = wb.createSheet(tabName);
 
        //Create 2D Cell Array
        Row[] row = new Row[data.length];
        Cell[][] cell = new Cell[row.length][];
 
        //Define and Assign Cell Data from Given
        for(int i = 0; i < row.length; i ++)
        {
            row[i] = sheet.createRow(i);
            cell[i] = new Cell[data[i].length];
 
            for(int j = 0; j < cell[i].length; j ++)
            {
                cell[i][j] = row[i].createCell(j);
                cell[i][j].setCellValue(data[i][j]);
            }
 
        }
 
        //Export Data
        wb.write(fileOut);
        fileOut.close();
 
    }
    
    
    
    @FXML
    private void printreport(ActionEvent event)  throws Exception  {
        
          HSSFWorkbook workbook = new HSSFWorkbook();
	HSSFSheet spreadsheet = workbook.createSheet("sample");

	HSSFRow row = null;

	for (int i = 0; i < allReportsTable.getItems().size(); i++) {
		row = spreadsheet.createRow(i);
		for (int j = 0; j < allReportsTable.getColumns().size(); j++) {
			row.createCell(j).setCellValue(allReportsTable.getColumns().get(j).getCellData(i).toString());
		}
	}
	
	try {
		FileOutputStream fileOut = new FileOutputStream("workbook.xls");
		try {
			workbook.write(fileOut);
			fileOut.close();
		//	Platform.exit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
                                        
    }
        
        
          
                      
                      
                      
                      




