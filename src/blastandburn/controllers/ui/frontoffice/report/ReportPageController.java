/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.controllers.ui.frontoffice.report;

import animatefx.animation.ZoomIn;
import animatefx.animation.ZoomOut;
import blastandburn.entities.report.Report;
import blastandburn.services.report.ReportService;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author user
 */
public class ReportPageController implements Initializable {

    AnchorPane  eventItem, sessionItem, recipeItem, openItem, closedItem, allItem;
    private boolean filterIsDisplayed = false;
    ReportService reportService = new ReportService();
    List<Report> reportsList, filteredReportsList, fList;

    @FXML
    private AnchorPane reportPane;
    @FXML
    private Label noUrgReportsLabel;
    @FXML
    private Label noNewReportsLabel;
    @FXML
    private FlowPane filtersPane;
    @FXML
    private AnchorPane filterDialogPane;
    
    @FXML
    private JFXCheckBox eventsCheckBox;
    @FXML
    private JFXCheckBox sessionsCheckBox;
    @FXML
    private JFXCheckBox recipesCheckBox;
    @FXML
    private JFXCheckBox openCheckBox;
    @FXML
    private JFXCheckBox closedCheckBox;
    @FXML
    private Pagination pagination;
    @FXML
    private JFXTextField searchBytitle;
    @FXML
    private VBox urgentReportsVBox;
    @FXML
    private VBox recentReportsVBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        urgentReports();
        recentReports();
        new ZoomOut(filterDialogPane).play();
        filteredReportsList = new ArrayList();
        fList = new ArrayList();
        checkBoxListeners();
        reportsList = reportService.allReportsList();
        pagination.setPageFactory((pageindex) -> grid(pageindex, reportsList));
    }

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
    
    public void recentReports() {
        List<Report> reportsL = reportService.allReportsList();
        reportsL = reportsL.stream().filter(r -> !r.isIsClosed()).collect(Collectors.toList());
        System.out.println();
        if (reportsL.isEmpty()) {
            noNewReportsLabel.setVisible(true);
        } else {
            int x = 0;
            if(reportsL.size()==1) x=1;
            else x=2;
            noNewReportsLabel.setVisible(false);
            for (int i = 0; i < x; i++) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/blastandburn/views/ui/frontoffice/report/RecentReportItem.fxml"));
                try {
                    AnchorPane pane = loader.load();
                    RecentReportItemController c = loader.getController();
                    c.setData(reportsL.get(i));
                    recentReportsVBox.getChildren().add(pane);

                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    public GridPane grid(int pageindex, List<Report> reportsList) {
        GridPane pane = null;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/blastandburn/views/ui/frontoffice/report/ReportGrid.fxml"));
        try {
            pane = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ReportPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ReportGridController c = loader.getController();
        c.setData(pageindex, reportsList);
        pagination.setPageCount(c.pageCount);
        return pane;
    }

    @FXML
    private void filtreAction(MouseEvent event) throws InterruptedException {
        if (filterIsDisplayed) {
            filterIsDisplayed = false;
            new ZoomOut(filterDialogPane).play();
            filterDialogPane.setDisable(true);
        } else {
            filterDialogPane.setDisable(false);
            filterDialogPane.setVisible(true);
            filterIsDisplayed = true;
            new ZoomIn(filterDialogPane).setSpeed(2).play();
        }
    }

    @FXML
    private void clearFiltersAction(MouseEvent event) {
        eventsCheckBox.setSelected(false);
        sessionsCheckBox.setSelected(false);
        recipesCheckBox.setSelected(false);
        openCheckBox.setSelected(false);
        closedCheckBox.setSelected(false);
        filteredReportsList = new ArrayList();
        fList = new ArrayList();
        reportsList = reportService.allReportsList();
        pagination.setPageFactory((pageindex) -> grid(pageindex, reportsList));

    }

    public void checkBoxListeners() {

        
        eventsCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/blastandburn/views/ui/frontoffice/report/FilterItem.fxml"));

                    try {
                        eventItem = loader.load();
                    } catch (IOException ex) {
                        Logger.getLogger(ReportPageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    FilterItemController c = loader.getController();
                    c.setData("Events");
                    filtersPane.getChildren().add(eventItem);
                    if (closedCheckBox.isSelected()) {
                        reportsList = reportService.allReportsList();
                        reportsList = reportsList.stream()
                                .filter(r -> r.getType().contains("event")).filter(r -> r.isIsClosed())
                                .collect(Collectors.toList());
                        fList.addAll(reportsList);
                        pagination.setPageFactory((pageindex) -> grid(pageindex, fList));
                    } else if (openCheckBox.isSelected()) {
                        reportsList = reportService.allReportsList();
                        reportsList = reportsList.stream()
                                .filter(r -> r.getType().contains("event")).filter(r -> !r.isIsClosed())
                                .collect(Collectors.toList());
                        filteredReportsList.addAll(reportsList);
                        pagination.setPageFactory((pageindex) -> grid(pageindex, filteredReportsList));
                    } else {
                        reportsList = reportService.allReportsList();
                        reportsList = reportsList.stream()
                                .filter(r -> r.getType().contains("event"))
                                .collect(Collectors.toList());
                        filteredReportsList.addAll(reportsList);
                        fList.addAll(filteredReportsList);
                        pagination.setPageFactory((pageindex) -> grid(pageindex, filteredReportsList));
                    }
                } else {
                    filtersPane.getChildren().remove(eventItem);
                    if (!isChecked()) {
                        filteredReportsList = new ArrayList();
                        reportsList = reportService.allReportsList();
                        pagination.setPageFactory((pageindex) -> grid(pageindex, reportsList));
                    } else {
                        if (closedCheckBox.isSelected()) {
                            fList = fList.stream()
                                    .filter(r -> !r.getType().contains("event")).filter(r -> r.isIsClosed())
                                    .collect(Collectors.toList());
                            pagination.setPageFactory((pageindex) -> grid(pageindex, fList));
                            filteredReportsList = filteredReportsList.stream()
                                    .filter(r -> !r.getType().contains("event"))
                                    .collect(Collectors.toList());
                        } else {
                            filteredReportsList = filteredReportsList.stream()
                                    .filter(r -> !r.getType().contains("event"))
                                    .collect(Collectors.toList());
                            pagination.setPageFactory((pageindex) -> grid(pageindex, filteredReportsList));
                        }
                    }
                }
            }
        });
        sessionsCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/blastandburn/views/ui/frontoffice/report/FilterItem.fxml"));

                    try {
                        sessionItem = loader.load();
                    } catch (IOException ex) {
                        Logger.getLogger(ReportPageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    FilterItemController c = loader.getController();
                    c.setData("Sessions");
                    filtersPane.getChildren().add(sessionItem);
                    if (closedCheckBox.isSelected()) {
                        reportsList = reportService.allReportsList();
                        reportsList = reportsList.stream()
                                .filter(r -> r.getType().contains("session")).filter(r -> r.isIsClosed())
                                .collect(Collectors.toList());
                        fList.addAll(reportsList);
                        pagination.setPageFactory((pageindex) -> grid(pageindex, fList));
                    } else if (openCheckBox.isSelected()) {
                        reportsList = reportService.allReportsList();
                        reportsList = reportsList.stream()
                                .filter(r -> r.getType().contains("session")).filter(r -> !r.isIsClosed())
                                .collect(Collectors.toList());
                        filteredReportsList.addAll(reportsList);
                        pagination.setPageFactory((pageindex) -> grid(pageindex, filteredReportsList));
                    } else {
                        reportsList = reportService.allReportsList();
                        reportsList = reportsList.stream()
                                .filter(r -> r.getType().contains("session"))
                                .collect(Collectors.toList());
                        filteredReportsList.addAll(reportsList);
                        fList.addAll(filteredReportsList);
                        pagination.setPageFactory((pageindex) -> grid(pageindex, filteredReportsList));
                    }
                } else {
                    filtersPane.getChildren().remove(sessionItem);
                    if (!isChecked()) {
                        filteredReportsList = new ArrayList();
                        reportsList = reportService.allReportsList();
                        pagination.setPageFactory((pageindex) -> grid(pageindex, reportsList));
                    } else {
                        if (closedCheckBox.isSelected()) {
                            fList = fList.stream()
                                    .filter(r -> !r.getType().contains("session")).filter(r -> r.isIsClosed())
                                    .collect(Collectors.toList());
                            pagination.setPageFactory((pageindex) -> grid(pageindex, fList));
                            filteredReportsList = filteredReportsList.stream()
                                    .filter(r -> !r.getType().contains("session"))
                                    .collect(Collectors.toList());
                        } else {
                            filteredReportsList = filteredReportsList.stream()
                                    .filter(r -> !r.getType().contains("session"))
                                    .collect(Collectors.toList());
                            pagination.setPageFactory((pageindex) -> grid(pageindex, filteredReportsList));
                        }
                    }
                }
            }
        });
        recipesCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/blastandburn/views/ui/frontoffice/report/FilterItem.fxml"));

                    try {
                        recipeItem = loader.load();
                    } catch (IOException ex) {
                        Logger.getLogger(ReportPageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    FilterItemController c = loader.getController();
                    c.setData("Recipes");
                    filtersPane.getChildren().add(recipeItem);
                    if (closedCheckBox.isSelected()) {
                        reportsList = reportService.allReportsList();
                        reportsList = reportsList.stream()
                                .filter(r -> r.getType().contains("recipe")).filter(r -> r.isIsClosed())
                                .collect(Collectors.toList());
                        fList.addAll(reportsList);
                        pagination.setPageFactory((pageindex) -> grid(pageindex, fList));
                    } else if (openCheckBox.isSelected()) {
                        reportsList = reportService.allReportsList();
                        reportsList = reportsList.stream()
                                .filter(r -> r.getType().contains("recipe")).filter(r -> !r.isIsClosed())
                                .collect(Collectors.toList());
                        filteredReportsList.addAll(reportsList);
                        pagination.setPageFactory((pageindex) -> grid(pageindex, filteredReportsList));
                    } else {
                        reportsList = reportService.allReportsList();
                        reportsList = reportsList.stream()
                                .filter(r -> r.getType().contains("recipe"))
                                .collect(Collectors.toList());
                        filteredReportsList.addAll(reportsList);
                        fList.addAll(filteredReportsList);
                        pagination.setPageFactory((pageindex) -> grid(pageindex, filteredReportsList));
                    }
                } else {
                    filtersPane.getChildren().remove(recipeItem);
                    if (!isChecked()) {
                        filteredReportsList = new ArrayList();
                        reportsList = reportService.allReportsList();
                        pagination.setPageFactory((pageindex) -> grid(pageindex, reportsList));
                    } else {
                        if (closedCheckBox.isSelected()) {
                            fList = fList.stream()
                                    .filter(r -> !r.getType().contains("recipe")).filter(r -> r.isIsClosed())
                                    .collect(Collectors.toList());
                            pagination.setPageFactory((pageindex) -> grid(pageindex, fList));
                            filteredReportsList = filteredReportsList.stream()
                                    .filter(r -> !r.getType().contains("recipe"))
                                    .collect(Collectors.toList());
                        } else {
                            filteredReportsList = filteredReportsList.stream()
                                    .filter(r -> !r.getType().contains("recipe"))
                                    .collect(Collectors.toList());
                            pagination.setPageFactory((pageindex) -> grid(pageindex, filteredReportsList));
                        }
                    }
                }
            }
        });
        openCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/blastandburn/views/ui/frontoffice/report/FilterItem.fxml"));
                    closedCheckBox.setSelected(false);
                    try {
                        openItem = loader.load();
                    } catch (IOException ex) {
                        Logger.getLogger(ReportPageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    FilterItemController c = loader.getController();
                    c.setData("Open");
                    filtersPane.getChildren().add(openItem);
                    if (typeIsChecked()) {
                        reportsList = reportService.allReportsList();
                        reportsList = filteredReportsList.stream()
                                .filter(r -> !r.isIsClosed())
                                .collect(Collectors.toList());
                        pagination.setPageFactory((pageindex) -> grid(pageindex, reportsList));
                    } else {
                        reportsList = reportService.allReportsList();
                        reportsList = reportsList.stream()
                                .filter(r -> !r.isIsClosed())
                                .collect(Collectors.toList());
                        pagination.setPageFactory((pageindex) -> grid(pageindex, reportsList));
                    }
                } else {
                    filtersPane.getChildren().remove(openItem);
                    if (typeIsChecked()) {
                        pagination.setPageFactory((pageindex) -> grid(pageindex, filteredReportsList));
                    } else {
                        reportsList = reportService.allReportsList();
                        reportsList = reportsList.stream()
                                .filter(r -> !r.isIsClosed())
                                .collect(Collectors.toList());
                        pagination.setPageFactory((pageindex) -> grid(pageindex, reportsList));
                    }
                }
            }
        });
        closedCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/blastandburn/views/ui/frontoffice/report/FilterItem.fxml"));
                    openCheckBox.setSelected(false);
                    try {
                        closedItem = loader.load();
                    } catch (IOException ex) {
                        Logger.getLogger(ReportPageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    FilterItemController c = loader.getController();
                    c.setData("Closed");
                    filtersPane.getChildren().add(closedItem);
                    if (typeIsChecked()) {
                        reportsList = reportService.allReportsList();
                        reportsList = filteredReportsList.stream()
                                .filter(r -> r.isIsClosed())
                                .collect(Collectors.toList());
                        pagination.setPageFactory((pageindex) -> grid(pageindex, reportsList));
                    } else {
                        reportsList = reportService.allReportsList();
                        reportsList = reportsList.stream()
                                .filter(r -> r.isIsClosed())
                                .collect(Collectors.toList());
                        pagination.setPageFactory((pageindex) -> grid(pageindex, reportsList));
                    }
                } else {
                    filtersPane.getChildren().remove(closedItem);
                    if (typeIsChecked()) {
                        pagination.setPageFactory((pageindex) -> grid(pageindex, filteredReportsList));
                    } else {
                        reportsList = reportService.allReportsList();
                        reportsList = reportsList.stream()
                                .filter(r -> r.isIsClosed())
                                .collect(Collectors.toList());
                        pagination.setPageFactory((pageindex) -> grid(pageindex, reportsList));
                    }
                }
            }
        });

    }

    public boolean isChecked() {
        return eventsCheckBox.isSelected() || sessionsCheckBox.isSelected() || recipesCheckBox.isSelected() || closedCheckBox.isSelected() || openCheckBox.isSelected();
    }

    public boolean typeIsChecked() {
        return  eventsCheckBox.isSelected() || sessionsCheckBox.isSelected() || recipesCheckBox.isSelected();
    }

    @FXML
    private void isTyping(KeyEvent event) {
        if (isChecked()) {
            if (closedCheckBox.isSelected() || openCheckBox.isSelected()) {
                System.out.println(fList.size());
                pagination.setPageFactory((pageindex) -> grid(pageindex, fList.stream()
                        .filter(r -> r.getTitle().toLowerCase().contains(searchBytitle.getText().toLowerCase()))
                        .collect(Collectors.toList())));
            } else {
                System.out.println(filteredReportsList.size());
                pagination.setPageFactory((pageindex) -> grid(pageindex, filteredReportsList.stream()
                        .filter(r -> r.getTitle().toLowerCase().contains(searchBytitle.getText().toLowerCase()))
                        .collect(Collectors.toList())));
            }
        } else {
            pagination.setPageFactory((pageindex) -> grid(pageindex, reportsList.stream()
                    .filter(r -> r.getTitle().toLowerCase().contains(searchBytitle.getText().toLowerCase()))
                    .collect(Collectors.toList())));
        }
    }

    private void isTyping1(KeyEvent event) {
        if (isChecked()) {
            if (closedCheckBox.isSelected() || openCheckBox.isSelected()) {
                System.out.println(fList.size());
                pagination.setPageFactory((pageindex) -> grid(pageindex, fList.stream()
                        .filter(r -> r.getTitle().toLowerCase().contains(searchBytitle.getText().toLowerCase()))
                        .collect(Collectors.toList())));
            } else {
                System.out.println(filteredReportsList.size());
                pagination.setPageFactory((pageindex) -> grid(pageindex, filteredReportsList.stream()
                        .filter(r -> r.getTitle().toLowerCase().contains(searchBytitle.getText().toLowerCase()))
                        .collect(Collectors.toList())));
            }
        } else {
            pagination.setPageFactory((pageindex) -> grid(pageindex, reportsList.stream()
                    .filter(r -> r.getTitle().toLowerCase().contains(searchBytitle.getText().toLowerCase()))
                    .collect(Collectors.toList())));
        }
    }
}
