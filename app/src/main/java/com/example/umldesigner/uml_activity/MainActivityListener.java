package com.example.umldesigner.uml_activity;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.example.umldesigner.Message;
import com.example.umldesigner.R;

public class MainActivityListener implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        Resources resources = view.getResources();
        view.getResources().getString(R.string.createTableFab);
        if (resources.getString(R.string.createTableFab).equals(view.getTag())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            
            //https://stackoverflow.com/questions/13341560/how-to-create-a-custom-dialog-box-in-android
            
            
            builder.setTitle("hello china");
    
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
    
        } else {
            throw new IllegalStateException("invalid MainActivityListener tag " + view.getTag());
        }
    }
}
