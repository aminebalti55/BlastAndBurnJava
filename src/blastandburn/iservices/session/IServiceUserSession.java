/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.iservices.session;

import blastandburn.entities.session.PaidSession;
import blastandburn.entities.session.Session;
import blastandburn.entities.session.UserSessionX;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author Admin
 */
public interface IServiceUserSession {

    public void participer(int idUser, int idsession);

public ObservableList<Session> ListerSessionsByIdUser(int id);
    
public ObservableList<PaidSession> ListerPaidSessionsByIdUser(int id) ;
  public UserSessionX getUserSession(int idU, int idT);
      public Session ListerOneSessionsByIdUser(int id) ;
          public PaidSession ListerOnePaidSessionsByIdUser(int id) ;
              public List<UserSessionX> getNbrParticipateByDate();



}
