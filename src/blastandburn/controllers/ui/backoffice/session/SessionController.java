/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.controllers.ui.backoffice.session;

import blastandburn.controllers.ui.frontoffice.HomePageHolderController;
import blastandburn.entities.session.PaidSession;
import blastandburn.entities.session.Session;
import blastandburn.entities.session.SessionCategory;
import blastandburn.entities.session.UserSessionX;
import blastandburn.services.session.ServicePaidSession;
import blastandburn.services.session.ServiceSession;
import blastandburn.services.session.ServiceUserSessionX;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.controlsfx.control.table.TableFilter;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class SessionController implements Initializable {

    @FXML
    private AnchorPane moderationPane;
    @FXML
    private FontAwesomeIconView close;
    @FXML
    private TableColumn<?, String> titleCol;
    @FXML
    private TableColumn<?, String> descriptionCol;
    @FXML
    private TableColumn<?, String> DaysCol;
    @FXML
    private TableColumn<?, ?> catgCol;
    @FXML
    private TableColumn<?, String> priceCol;
    @FXML
    private TableView<Session> SessionTable;
    ServiceSession st = new ServiceSession();
    Session t = new Session();
    ServicePaidSession spt = new ServicePaidSession();
    PaidSession pt = new PaidSession();
    double xOffset, yOffset;
    @FXML
    private PieChart pieChart;
    @FXML
    private LineChart<?, ?> lineChart;
    private ServiceUserSessionX sut = new ServiceUserSessionX();
    private Pagination pagination;
    private int itemsPerPage = 5;
    private int from, to, size;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //----------------PieChart----------
        ObservableList<PieChart.Data> valueList = FXCollections.observableArrayList(
                new PieChart.Data("Free Sessions", st.ListSession().size()),
                new PieChart.Data("Paid Sessions", spt.listPaidSession().size()));
        pieChart.setTitle("Sessions");
        pieChart.setData(valueList);
        pieChart.getData().forEach(data -> {
            String percentage = String.format("%.2f%%", (data.getPieValue() / 100));
            Tooltip toolTip = new Tooltip(percentage);
            Tooltip.install(data.getNode(), toolTip);
        });
        //----------------LineChart----------
        lineChart.setTitle("Number of participations by date");
        XYChart.Series dataSeries = new XYChart.Series();
        dataSeries.setName("test");
        for (int i = 0; i < sut.getNbrParticipateByDate().size(); i++) {
            dataSeries.getData().add(new XYChart.Data(String.valueOf(sut.getNbrParticipateByDate().get(i).getCreatedAt()), sut.getNbrParticipateByDate().get(i).getNbr()));
        }
        lineChart.getData().add(dataSeries);

        init();

    }

    public void init() {
        List<?> Sessions;
        Sessions = Stream.concat(st.ListSession().stream(), spt.listPaidSession().stream())
                .collect(Collectors.toList());
        ObservableList<?> l = FXCollections.observableList(Sessions);
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        DaysCol.setCellValueFactory(new PropertyValueFactory<>("numOfDays"));
        catgCol.setCellValueFactory(new PropertyValueFactory<>("cat"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        SessionTable.setItems((ObservableList<Session>) l);
        TableFilter filter = new TableFilter(SessionTable);
//        size = (Sessions.size() / itemsPerPage) + 1;
//        pagination.setPageCount(size);
//        pagination.setPageFactory((pageIndex) -> {
//            SessionTable.getItems().clear();
//            from = pageIndex * itemsPerPage;
//            to = itemsPerPage;
//            int page = pageIndex * itemsPerPage;
//
//            for (int i = page; i < page + itemsPerPage; i++) {
//
//                if (i >= Sessions.size()) {
//                    return SessionTable;
//                }
//                SessionTable.getItems().add((Session) Sessions.get(i));
//
//            }
//            //TableFilter filter = new TableFilter(SessionTable);
//            return SessionTable;
//        });
    }

    @FXML
    private void closeAction(MouseEvent event) {
        Stage stage = new Stage();
        stage = (Stage) SessionTable.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void minAction(MouseEvent event) {
        Stage stage = new Stage();
        stage = (Stage) SessionTable.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void addCategoryAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/blastandburn/views/ui/backoffice/Session/SessionCategoryF.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
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
    private void deleteSessionAction(MouseEvent event) {
        Window owner = SessionTable.getScene().getWindow();
        Session Session = SessionTable.getSelectionModel().getSelectedItem();
        st.deleteSession(Session.getsessionId());
        AlertBox(Alert.AlertType.CONFIRMATION, owner, "Confirmation", "Session deleted successfully!");
        //SessionTable.getItems().clear();
        init();
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
