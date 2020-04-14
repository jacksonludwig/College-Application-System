package com.example.collegeapplicationsystem;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.collegeapplicationsystem.textFiltering.InputFilterMinMax;

public class EditMathScorePopupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_math_score_popup);

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

        EditText editText = (EditText) findViewById(R.id.mathScoreEditText);
        editText.setFilters(new InputFilter[]{new InputFilterMinMax("0", "800")});
    }

    public void acceptMathScoreEdit(View view) {
        EditText editText = (EditText) findViewById(R.id.mathScoreEditText);
        Intent intent = getIntent();
        intent.putExtra("mathScore", editText.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    public void cancelMathScoreEdit(View view) {
        finish();
    }
}
