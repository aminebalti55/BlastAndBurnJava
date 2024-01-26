/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.iservices.session;

import blastandburn.entities.session.PaidSession;
//import blastandburn.entities.Session.Session;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface IServicePaidSession {

    public void addPaidSession(int idCoach, String title, PaidSession p);

    public List<PaidSession> listPaidSession();

    public void updatePaidSession(PaidSession t, int idpt);

    public void deletePaidSession(int idpt);

    public PaidSession getPaidSession(int idPT);

    public void makeFreeSession(int idPT);

    public void makePaidSession(int idPT, double price);

    public int getCountPaidSession();
     public List<PaidSession> searchPaidSessionByName(String title);
}
