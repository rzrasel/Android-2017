package com.rz.gridviewdynamicadapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class ActSplash extends AppCompatActivity {
    private Activity activity;
    private Context context;
    private GridView sysGridViewDynamicAdapter;
    private ArrayList<ModelInit> listModelItems = new ArrayList<ModelInit>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_splash);
        activity = this;
        context = this;
        new GridViewHelper(context);
        /*float scalefactor = getResources().getDisplayMetrics().density * 100;
        int number = getWindowManager().getDefaultDisplay().getWidth();
        int columns = (int) ((float) number / (float) scalefactor);
        gridView.setNumColumns(columns);*/
        refreshGridView();
    }

    private void refreshGridView() {

        /*DisplayMetrics displaymetrics = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        int screenHeight = displaymetrics.heightPixels;*/

        /*int gridViewEntrySize = getResources().getDimensionPixelSize(R.dimen.grip_view_entry_size);
        int gridViewSpacing = getResources().getDimensionPixelSize(R.dimen.grip_view_spacing);*/

        /*WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();

        int numColumns = (display.getWidth() - gridViewSpacing) / (gridViewEntrySize + gridViewSpacing);*/


        float gridViewEntrySize = convertDpToPixel(context, 60f);
        float gridViewSpacing = convertDpToPixel(context, 1.0f);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float floatNumColumns = (int) (displayMetrics.widthPixels - gridViewSpacing) / (gridViewEntrySize + gridViewSpacing);
        int numColumns = (int) floatNumColumns;
        System.out.println("|----|------------|90_DP_IS|----|" + gridViewEntrySize);
        System.out.println("|----|------------|GRID_COLUMNS|----|" + numColumns);
        sysGridViewDynamicAdapter.setNumColumns(numColumns);
    }

    public static float convertDpToPixel(Context argContext, float argDensityIndependentPixels) {
        Resources resources = argContext.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = argDensityIndependentPixels * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    public static float convertPixelsToDp(Context argContext, float argPixel) {
        Resources resources = argContext.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float densityIndependentPixels = argPixel / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return densityIndependentPixels;
    }

    @Override
    public void onConfigurationChanged(Configuration argNewConfig) {
        super.onConfigurationChanged(argNewConfig);
        refreshGridView();
        /*if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.lay_vertical);
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.lay_horizontal);
        }*/
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshGridView();
    }

    private class GridViewHelper {
        private Activity activity;
        private Context context;

        GridViewHelper(Context argContext) {
            this.context = argContext;
            onSetModelData();
        }

        private void onSetModelData() {
            for (int i = 0; i < 150; i++) {
                int value = i + 1;
                listModelItems.add(onGetSetModelGridViewData("Title " + value, "Description " + value));
            }
            onIniGridView();
        }

        private void onIniGridView() {
            sysGridViewDynamicAdapter = (GridView) findViewById(R.id.sysGridViewDynamicAdapter);
            //sysvDynamicAdapter.setAdapter(new com.sm.cmdss.AdapterInit<>(this));
            AdapterInit adapterInit = new AdapterInit(context, R.layout.lay_row_grid, listModelItems);
            ArrayList<AdapterInit.ModelRowViewHolder> listRowViewFields = null;
            listRowViewFields = new ArrayList<AdapterInit.ModelRowViewHolder>();
            listRowViewFields.add(adapterInit.onGetSetModelRowViewData(new TextView(context), "sysTvRowTitle", ""));
            listRowViewFields.add(adapterInit.onGetSetModelRowViewData(new TextView(context), "sysTvRowDesc", ""));
            adapterInit.onSetListRowViewFields(new AdapterInit.OnFieldListenerHandler() {
                @Override
                public void onSetFieldValue(ArrayList<AdapterInit.ModelRowViewHolder> argListRowViewFields, Object argObject) {
                    if (argObject instanceof ModelInit) {
                        //System.out.println("|----|------------|GET_POSITION|----|");
                        ModelInit item = (ModelInit) argObject;
                        TextView rowField = null;
                        if (argListRowViewFields.size() > 0) {
                            rowField = (TextView) argListRowViewFields.get(0).getFieldObject();
                            rowField.setText(item.getTitle());
                            rowField = (TextView) argListRowViewFields.get(1).getFieldObject();
                            rowField.setText(item.getDescription());
                        }
                    }
                }
            }, listRowViewFields);
            sysGridViewDynamicAdapter.setAdapter(adapterInit);
        }

        public ModelInit onGetSetModelGridViewData(String argTitle, String argDescription) {
            return new ModelInit(argTitle, argDescription);
        }
    }
}
/*
DynamicVariable dynamicVariable = new DynamicVariable();
dynamicVariable.onSet(context);
dynamicVariable.onGet();




Android GridView
https://www.mkyong.com/android/android-gridview-example/
*/
