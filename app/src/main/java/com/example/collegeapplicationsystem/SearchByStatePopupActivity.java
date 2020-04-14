package com.example.collegeapplicationsystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.collegeapplicationsystem.textFiltering.States;

import java.util.ArrayList;

public class SearchByStatePopupActivity extends AppCompatActivity {

    Spinner spinner;
    private ArrayList<String> statesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_state_popup);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int) (width * .9), (int) (height * .3));

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.x = 0;
        layoutParams.y = 20;

        getWindow().setAttributes(layoutParams);

        spinner = findViewById(R.id.state_spinner);
        connectSpinner();
    }

    public void acceptStateChoice(View view) {
        Spinner spinner = findViewById(R.id.state_spinner);
        Intent intent = getIntent();
        intent.putExtra("chosenState", spinner.getSelectedItem().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    public void cancel(View view) {
        finish();
    }

    private void setStatesList() {
        for (States state : States.values()) {
            statesList.add(state.getANSIAbbreviation());
        }
    }

    private void connectSpinner() {
        setStatesList();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, statesList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }
}
