/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.iservices.session;

import blastandburn.entities.session.UserChat;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface IServiceUserChat {
    public void ajouterUserChat(UserChat uc);
    public List<UserChat> listUserChat();
}
