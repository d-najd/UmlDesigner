package com.example.umldesigner;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.umldesigner.UmlActivity.Views.UmlArrowView;
import com.example.umldesigner.UmlActivity.Views.UmlBackground;
import com.example.umldesigner.UmlActivity.UmlListeners;
import com.example.umldesigner.UmlActivity.UmlSingleton;

public class MainActivity extends AppCompatActivity implements ReceiverInterface{
    private ViewGroup container;
    public UmlListeners umlListeners;
    public static float dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dp = getResources().getDisplayMetrics().density;
        container = findViewById(R.id.container);

        addGrid();

        float spacing = UmlSingleton.spacing;

        UmlArrowView umlArrowView = new UmlArrowView(container,
                spacing * (2 + 1.5f) * dp, spacing * (15) * dp, //(210 + 45) * dp, (210) * dp
                spacing * (8 + 1.5f) * dp,  spacing * 2 * dp + 110 * dp + spacing*dp/2,
                null, null, umlListeners); //(120 + 45) * dp,  (450) * dp)
        container.addView(umlArrowView);
    }

    //didnt want the main activity to start another activity so I went with this
    private void addGrid(){
        UmlBackground umlBackground = new UmlBackground(this);
        umlBackground.setMinimumWidth(50000);
        umlBackground.setMinimumHeight(50000);
        container.addView(umlBackground);

        umlListeners = new UmlListeners(umlBackground);

        ImageButton gridColliders = new ImageButton(this);
        gridColliders.setMinimumWidth(50000);
        gridColliders.setMinimumHeight(50000);
        gridColliders.setBackgroundColor(Color.parseColor("#00000000"));
        gridColliders.setTag("gridColliders");
        gridColliders.setOnDragListener(umlListeners);
        gridColliders.setPadding(150, 150, 0, 0);
        container.addView(gridColliders);
    }

    @Override
    public boolean receiveData(Object sentData) {
        return false;
    }
}