/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.iservices.session;

import blastandburn.entities.session.PaidSession;
import blastandburn.entities.session.Session;
import blastandburn.entities.session.SessionCategory;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author Admin
 */
public interface IServiceSessionCategory {

    public void createSessionCategory(SessionCategory t);

    public List<SessionCategory> ListSessionCategory();

    public void updateSessionCategory(SessionCategory t);

    public void deleteSessionCategory(int idTC);

    public SessionCategory searchSessionCategory(String titre);

    public List<Session> ListerSessionsByIdCatg(String title);

    public List<PaidSession> ListerPaidSessionsByIdCatg(String title);

}
