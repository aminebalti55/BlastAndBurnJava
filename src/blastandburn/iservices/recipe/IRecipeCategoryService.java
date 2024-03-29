/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.iservices.recipe;

import blastandburn.entities.recipe.Recipe;
import blastandburn.entities.recipe.RecipeCategory;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author HP
 */
public interface IRecipeCategoryService {

    public void Create_RecipeCategory(RecipeCategory RC);

    public List<RecipeCategory> Afficher_RecipeCategory();

    public void Update_RecipeCategory(RecipeCategory RC, int id);

    public void Delete_RecipeCategory(int idc);

    public ObservableList<RecipeCategory> RechercheCatAvance(String n) throws SQLException;

    public RecipeCategory RechercherRecipeCategory(String n);

    public List<Recipe> AfficherRecipesByIdCatg(String n);

    public RecipeCategory RechercherRecipeCategoryById(int id);

}
