package com.umldesigner;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.umldesigner.MainActivity;
import com.umldesigner.Message;
import com.umldesigner.R;
import com.umldesigner.uml_activity.CreateTableDialog;

public class MainActivityListener implements View.OnClickListener {
    MainActivity mainActivity;
    public MainActivityListener(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }
    
    @Override
    public void onClick(View view) {
        Resources resources = view.getResources();
        view.getResources().getString(R.string.createTableFab);
        if (resources.getString(R.string.createTableFab).equals(view.getTag())) {
            //AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            
            //https://stackoverflow.com/questions/13341560/how-to-create-a-custom-dialog-box-in-android
            
            //builder.setTitle("hello china");
            
    
            //AlertDialog alertDialog = builder.create();
            //alertDialog.show();
            
            CreateTableDialog dialog = new CreateTableDialog(mainActivity);
            dialog.show();
        } else {
            throw new IllegalStateException("invalid MainActivityListener tag " + view.getTag());
        }
    }
}
