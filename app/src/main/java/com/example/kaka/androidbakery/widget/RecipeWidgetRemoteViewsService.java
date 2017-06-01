package com.example.kaka.androidbakery.widget;

import android.content.Intent;
import android.os.IBinder;
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
 * Created by Kaka on 5/28/2017.
 */

public class RecipeWidgetRemoteViewsService extends RemoteViewsService {

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new RemoteViewsFactory() {
            private List<Recipe> recipeList = new ArrayList<Recipe>();

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
                RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.item_widget);
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
        };
    }
}
