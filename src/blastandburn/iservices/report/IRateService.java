/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.iservices.report;


import blastandburn.entities.rate.Rate;
import java.util.List;

/**
 *
 * @author user
 */
public interface IRateService {
    public void addRate(Rate r, int id);
    public List<Rate> allRatesList();
    public List<Rate> ratesList(String type);
    public boolean isRated(int id, int userId, String type);
}
