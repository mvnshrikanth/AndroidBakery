package com.example.kaka.androidbakery.widget;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.kaka.androidbakery.R;

/**
 * Created by Kaka on 5/31/2017.
 */

public class RecipeWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
        Intent intent = new Intent(context, RecipeWidgetRemoteViewsService.class);
        remoteViews.setRemoteAdapter(R.id.lv_widgetRecipeList, intent);
        remoteViews.setEmptyView(R.id.lv_widgetRecipeList, R.id.tv_widgetEmptyView);


        PendingIntent pendingIntent = TaskStackBuilder.create(context)
                .addNextIntentWithParentStack(intent)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setPendingIntentTemplate(R.id.lv_widgetRecipeList, pendingIntent);

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.lv_widgetRecipeList);
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    private void setRemoteAdapter(Context context, RemoteViews remoteViews) {
        remoteViews.setRemoteAdapter(R.id.lv_widgetRecipeList, new Intent(context, RecipeWidgetRemoteViewsService.class));
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }
}
