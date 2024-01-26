/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.iservices.report;


import blastandburn.entities.report.Report;
import java.util.List;

/**
 *
 * @author user
 */
public interface IReportService {
    public void addReport(Report r, int id);
    public void closeReport(String title);
    public void banUser(int userId);
    public void unbanUser(int userId);
    public List<Report> allReportsList();
    public List<Report> reportsList(String type);
    public List<Report> closedReportsList();
    public List<Report> openReportsList();
}
