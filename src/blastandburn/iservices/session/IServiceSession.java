/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.iservices.session;

import blastandburn.entities.session.Session;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface IServiceSession {
    public void createSession(int coachId,String title,Session t);
    public List<Session> ListSession();
    public void updateSession(Session t,int idt);
    public void deleteSession(int idt);
    public Session getSession(int idT);
    public int getCountSession();
    public List<Session> searchSessionByName(String title);
}
