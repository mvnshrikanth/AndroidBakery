package com.example.kaka.androidbakery.widget;

import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.kaka.androidbakery.R;
import com.example.kaka.androidbakery.data.Recipe;
import com.example.kaka.androidbakery.utilities.Utils;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.kaka.androidbakery.ui.MainFragment.BAKERY_BASE_URL;

/**
 * Created by Kaka on 6/1/2017.
 */

public class RecipeListWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            private List<Recipe> recipeList = new ArrayList<Recipe>();

            @Override
            public void onCreate() {

            }

            @Override
            public void onDataSetChanged() {
                initRequest();
            }

            @Override
            public void onDestroy() {
                recipeList.clear();
            }

            @Override
            public int getCount() {
                if (recipeList != null) {
                    return recipeList.size();
                } else {
                    return 0;
                }
            }

            @Override
            public RemoteViews getViewAt(int position) {
                RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.item_recipe_widget);
                Recipe recipe = recipeList.get(position);
                remoteViews.setTextViewText(R.id.tv_widget_recipe, recipe.getName());

                Intent fillIntent = new Intent();
                fillIntent.putExtra(RecipeAppWidget.KEY_POSITION, position);
                remoteViews.setOnClickFillInIntent(R.id.tv_widget_recipe, fillIntent);
                return remoteViews;
            }

            @Override
            public RemoteViews getLoadingView() {
                return null;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }

            private void initRequest() {

                OkHttpClient client = new OkHttpClient();
                HttpUrl.Builder builder = HttpUrl.parse(BAKERY_BASE_URL).newBuilder();

                String url = builder.toString();

                final Request request = new Request.Builder()
                        .url(url)
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    recipeList = Utils.getRecipeListFromJsonString(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
