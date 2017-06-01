package com.example.kaka.androidbakery.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.kaka.androidbakery.R;
import com.example.kaka.androidbakery.data.Recipe;
import com.example.kaka.androidbakery.utilities.Utils;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.kaka.androidbakery.ui.MainFragment.BAKERY_BASE_URL;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetDataProvider implements
        RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private List<Recipe> recipeList;
    private Intent intent;

    public RecipeWidgetDataProvider(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
    }

    @Override
    public void onCreate() {
        initRequest();
    }

    @Override
    public void onDataSetChanged() {
        initRequest();
    }

    private void initRequest() {
        final long identityToken = Binder.clearCallingIdentity();
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

        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {
        recipeList.clear();
    }

    @Override
    public int getCount() {
        return recipeList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.item_widget);
        remoteViews.setEmptyView(R.id.lv_widgetRecipeList, R.id.tv_widgetEmptyView);
        Recipe recipe = recipeList.get(position);
        switch (recipe.getName()) {
            case "Nutella Pie":
                remoteViews.setImageViewResource(R.id.iv_widget_recipe_image, R.drawable.nutella_pie);
                break;
            case "Brownies":
                remoteViews.setImageViewResource(R.id.iv_widget_recipe_image, R.drawable.brownie);
                break;
            case "Yellow Cake":
                remoteViews.setImageViewResource(R.id.iv_widget_recipe_image, R.drawable.yellow_cake);
                break;
            default:
                remoteViews.setImageViewResource(R.id.iv_widget_recipe_image, R.drawable.cheesecake);
                break;
        }
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
}