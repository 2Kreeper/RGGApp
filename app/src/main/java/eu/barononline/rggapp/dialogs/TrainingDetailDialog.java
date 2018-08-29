package eu.barononline.rggapp.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;

import eu.barononline.rggapp.MainActivity;
import eu.barononline.rggapp.R;

public class TrainingDetailDialog extends Dialog implements View.OnClickListener {

    private Context activity;

    public TrainingDetailDialog(@NonNull Context context) {
        super(context);

        this.activity = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_training_detail);
    }

    @Override
    public void onClick(View v) {
        Log.v(MainActivity.LOG_TAG, "Click!");
    }
}
