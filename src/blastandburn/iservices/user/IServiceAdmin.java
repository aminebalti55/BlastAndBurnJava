/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.iservices.user;

import blastandburn.entities.user.Role;
import blastandburn.entities.user.User;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author fatma
 */
public interface IServiceAdmin {
    public ObservableList<User> GetListPersonnes();
    
    public ObservableList<Role> GetListRoles();
    
}
