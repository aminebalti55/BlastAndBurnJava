/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.controllers.ui.frontoffice.recipe;

/**
 *
 * @author HP
 */
public class RecipeHolder {

    private int id;
    private final static RecipeHolder INSTANCE = new RecipeHolder();

    public static RecipeHolder getINSTANCE() {
        return INSTANCE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
