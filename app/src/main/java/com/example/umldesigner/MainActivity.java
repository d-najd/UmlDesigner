package com.example.umldesigner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.umldesigner.uml_activity.recycler.UmlAdapter;
import com.example.umldesigner.uml_activity.recycler.UmlAdapterData;
import com.example.umldesigner.uml_activity.views.UmlArrowView;
import com.example.umldesigner.uml_activity.views.UmlBackground;
import com.example.umldesigner.uml_activity.UmlListeners;
import com.example.umldesigner.uml_activity.logic.UmlSingleton;

import java.util.ArrayList;

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
    
        RecyclerView umlTableRecyclerView = container.findViewById(R.id.uml_table_recyclerView);
        ConstraintLayout umlTableLayout = container.findViewById(R.id.includeTest);
   
        umlTableLayout.setElevation(UmlSingleton.TABLE_ELEVATION);
        
        ArrayList<UmlAdapterData> umlAdapterDataArrayList = new ArrayList<>();
        umlAdapterDataArrayList.add(new UmlAdapterData("- ProductId: Int"));
        umlAdapterDataArrayList.add(new UmlAdapterData("- ProductName: String"));
        
        UmlAdapter adapter = new UmlAdapter(umlAdapterDataArrayList, this);
    
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        umlTableRecyclerView.setLayoutManager(layoutManager);
        umlTableRecyclerView.setAdapter(adapter);
    }

    //didn't want the main activity to start another activity so I went with this
    private void addGrid(){
       UmlSingleton.getInstance();
        
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