package com.example.kaka.androidbakery.utilities;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.example.kaka.androidbakery.data.Ingredient;
import com.example.kaka.androidbakery.data.Recipe;
import com.example.kaka.androidbakery.data.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kaka on 5/13/2017.
 */

public class Utils {

    private static final String RECIPE_ID = "id";
    private static final String RECIPE_NAME = "name";
    private static final String RECIPE_INGREDIENTS_LIST = "ingredients";
    private static final String RECIPE_SERVINGS = "servings";
    private static final String RECIPE_IMAGE = "image";
    private static final String RECIPE_QUANTITY = "quantity";
    private static final String RECIPE_MEASURE = "measure";
    private static final String RECIPE_INGREDIENT_NAME = "ingredient";
    private static final String RECIPE_STEPS_LIST = "steps";
    private static final String RECIPE_STEPS_ID = "id";
    private static final String RECIPE_SHORT_DESCRIPTION = "shortDescription";
    private static final String RECIPE_DESCRIPTION = "description";
    private static final String RECIPE_VIDEO_URL = "videoURL";
    private static final String RECIPE_THUMBNAIL_URL = "thumbnailURL";

    public static List<Recipe> getRecipeListFromJsonString(String recipeJsonStr) throws JSONException {
        List<Recipe> recipeList = new ArrayList<>();


        JSONArray jsonArrayRoot = new JSONArray(recipeJsonStr);
        for (int i = 0; i < jsonArrayRoot.length(); i++) {

            List<Ingredient> ingredientList = new ArrayList<>();
            List<Step> stepList = new ArrayList<>();

            JSONObject jsonObjectRecipe = jsonArrayRoot.getJSONObject(i);
            JSONArray jsonArrayIngredients = jsonObjectRecipe.getJSONArray(RECIPE_INGREDIENTS_LIST);

            for (int n = 0; n < jsonArrayIngredients.length(); n++) {
                JSONObject jsonObjectIngredient = jsonArrayIngredients.getJSONObject(n);

                ingredientList.add(new Ingredient(jsonObjectIngredient.getInt(RECIPE_QUANTITY),
                        jsonObjectIngredient.getString(RECIPE_MEASURE),
                        jsonObjectIngredient.getString(RECIPE_INGREDIENT_NAME)));
            }

            JSONArray jsonArraySteps = jsonObjectRecipe.getJSONArray(RECIPE_STEPS_LIST);
            for (int l = 0; l < jsonArraySteps.length(); l++) {
                JSONObject jsonObjectStep = jsonArraySteps.getJSONObject(l);

                stepList.add(new Step(jsonObjectStep.getInt(RECIPE_STEPS_ID),
                        jsonObjectStep.getString(RECIPE_SHORT_DESCRIPTION),
                        jsonObjectStep.getString(RECIPE_DESCRIPTION),
                        jsonObjectStep.getString(RECIPE_VIDEO_URL),
                        jsonObjectStep.getString(RECIPE_THUMBNAIL_URL)));
            }

            recipeList.add(new Recipe(jsonObjectRecipe.getInt(RECIPE_ID),
                    jsonObjectRecipe.getString(RECIPE_NAME),
                    ingredientList,
                    stepList,
                    jsonObjectRecipe.getInt(RECIPE_SERVINGS),
                    jsonObjectRecipe.getString(RECIPE_IMAGE)));
        }
        return recipeList;
    }

    public static boolean isLargeScreen(Context context) {
        int screenSize = context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;

        switch (screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                return true;
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                return true;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                return false;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                return false;
            default:
                return false;
        }
    }

    public static Bitmap convertToBitmap(Drawable drawable, int widthPixels, int heightPixels) {
        Bitmap mutableBitmap = Bitmap.createBitmap(widthPixels, heightPixels, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mutableBitmap);
        drawable.setBounds(0, 0, widthPixels, heightPixels);
        drawable.draw(canvas);

        return mutableBitmap;
    }

}
