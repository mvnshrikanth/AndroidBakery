package com.example.kaka.androidbakery.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Kaka on 5/28/2017.
 */

public class RecipeWidgetRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeWidgetDataProvider(this, intent);
    }
}
