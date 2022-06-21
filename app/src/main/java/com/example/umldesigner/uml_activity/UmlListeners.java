package com.example.umldesigner.uml_activity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

import com.example.umldesigner.uml_activity.logic.UmlSingleton;
import com.example.umldesigner.uml_activity.views.UmlBackground;

import java.util.Objects;

public class UmlListeners implements View.OnTouchListener, View.OnLongClickListener, View.OnDragListener {
    public UmlBackground umlBackground;

    public UmlListeners(UmlBackground umlBackground){
        this.umlBackground = umlBackground;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        startDrag(v);
        return false;
    }

    @Override
    public boolean onLongClick(View v) {
        startDrag(v);
        return false;
    }

    private void startDrag(View v){
        View.DragShadowBuilder mShadow = new View.DragShadowBuilder(v);
        ClipData.Item item = new ClipData.Item(v.getId() + "");
        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
        ClipData data = new ClipData(v.getId() + "", mimeTypes, item);

        v.startDragAndDrop(data, mShadow, null, 0);
    }

    @Override
    public boolean onDrag(View v, DragEvent event)
    {
        String clipData;
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                return true;
            case DragEvent.ACTION_DROP:
                clipData = event.getClipDescription().getLabel().toString();
                Integer int_clipData = Integer.parseInt(clipData);
                //moving the UmlObject
                Objects.requireNonNull(UmlSingleton.allExistingViewTags.get(int_clipData)).move(event.getX(), event.getY());
                return true;
            default:
                return false;
        }
    }
}
