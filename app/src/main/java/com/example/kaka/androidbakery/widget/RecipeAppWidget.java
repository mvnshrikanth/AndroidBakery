package com.example.kaka.androidbakery.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

import com.example.kaka.androidbakery.R;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeAppWidget extends AppWidgetProvider {

    public static final String ACTION_SHOW_INGREDIENTS_STRING = "showIngredients";
    public static final String ACTION_BACK_TO_RECIPES_STRING = "backToRecipes";
    public static final String KEY_POSITION = "position";
    public static final String KEY_WIDGET_ID = "widgetId";
    private static final int ACTION_SHOW_RECIPES_INT = 1001;
    private static final int ACTION_SHOW_INGREDIENTS_INT = 1002;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, int action, Intent intentFromReceive) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_app_widget);

        switch (action) {
            case ACTION_SHOW_RECIPES_INT:
                views.setViewVisibility(R.id.rl_widget_recipe_layout, View.VISIBLE);
                views.setViewVisibility(R.id.ll_widget_ingredients_layout, View.GONE);

                Intent intentRecipeListWidget = new Intent(context, RecipeListWidgetService.class);
                views.setRemoteAdapter(R.id.lv_widgetRecipeList, intentRecipeListWidget);
                views.setEmptyView(R.id.lv_widgetRecipeList, R.id.tv_widgetEmptyView);


                Intent intentIngredient = new Intent(context, RecipeAppWidget.class);
                intentIngredient.setAction(ACTION_SHOW_INGREDIENTS_STRING);
                intentIngredient.putExtra(KEY_WIDGET_ID, appWidgetId);
                PendingIntent pendingIngredientIntent = PendingIntent.getBroadcast(context, 0, intentIngredient, PendingIntent.FLAG_UPDATE_CURRENT);
                views.setPendingIntentTemplate(R.id.lv_widgetRecipeList, pendingIngredientIntent);

                appWidgetManager.updateAppWidget(appWidgetId, views);
                break;
            case ACTION_SHOW_INGREDIENTS_INT:
                views.setViewVisibility(R.id.ll_widget_ingredients_layout, View.VISIBLE);
                views.setViewVisibility(R.id.rl_widget_recipe_layout, View.GONE);

                Intent intentIngredientListWidget = new Intent(context, IngredientListWidgetService.class);
                intentIngredientListWidget.putExtra(KEY_POSITION, intentFromReceive.getIntExtra(KEY_POSITION, 0));
                views.setRemoteAdapter(R.id.lv_widget_ingredients_view, intentIngredientListWidget);
                views.setEmptyView(R.id.lv_widget_ingredients_view, R.id.tv_widget_ingredients_empty);

                Intent intentRecipe = new Intent(context, RecipeAppWidget.class);
                intentRecipe.setAction(ACTION_BACK_TO_RECIPES_STRING);
                intentRecipe.putExtra(KEY_WIDGET_ID, appWidgetId);
                PendingIntent pendingRecipeIntent = PendingIntent.getBroadcast(context, 0, intentRecipe, PendingIntent.FLAG_UPDATE_CURRENT);
                views.setPendingIntentTemplate(R.id.lv_widget_ingredients_view, pendingRecipeIntent);
                views.setOnClickPendingIntent(R.id.tv_widget_back_button, pendingRecipeIntent);

                appWidgetManager.updateAppWidget(appWidgetId, views);
                break;
            default:
                throw (new UnsupportedOperationException());
        }

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        if (intent.getAction().equals(ACTION_SHOW_INGREDIENTS_STRING)) {
            int appWidgetId = intent.getIntExtra(KEY_WIDGET_ID, 0);
            updateAppWidget(context, appWidgetManager, appWidgetId, ACTION_SHOW_INGREDIENTS_INT, intent);
        } else if (intent.getAction().equals(ACTION_BACK_TO_RECIPES_STRING)) {
            int appWidgetId = intent.getIntExtra(KEY_WIDGET_ID, 0);
            updateAppWidget(context, appWidgetManager, appWidgetId, ACTION_SHOW_RECIPES_INT, intent);
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, ACTION_SHOW_RECIPES_INT, null);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

