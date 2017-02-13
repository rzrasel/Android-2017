package com.rz.overlaytwoimages;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private Activity activity;
    private ImageView sysIvOne;
    private ImageView sysIvTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;

        sysIvOne = (ImageView) findViewById(R.id.sysIvOne);
        sysIvTwo = (ImageView) findViewById(R.id.sysIvTwo);
        Resources resources = getResources();
        Drawable[] layers = new Drawable[2];
        /*layers[0] = resources.getDrawable(R.drawable.bubble_logo);
        layers[1] = resources.getDrawable(R.drawable.bubble);*/
        layers[0] = ContextCompat.getDrawable(activity, R.drawable.bubble_logo);
        layers[1] = ResourcesCompat.getDrawable(getResources(), R.drawable.bubble, null);
        LayerDrawable layerDrawable = new LayerDrawable(layers);
        sysIvOne.setImageDrawable(layerDrawable);

        //sysIvTwo.setImageDrawable(getResources().getDrawable(R.drawable.layer));
        sysIvTwo.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.layer));
    }
}