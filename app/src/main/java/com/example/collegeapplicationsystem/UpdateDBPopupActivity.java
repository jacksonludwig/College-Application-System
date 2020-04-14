package com.example.collegeapplicationsystem;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.collegeapplicationsystem.JSONParsing.College;
import com.example.collegeapplicationsystem.JSONParsing.Holder;
import com.example.collegeapplicationsystem.JSONParsing.JSONRetriever;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

public class UpdateDBPopupActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private boolean wasUpdated = false;

    private Thread updateThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_db_pop_up_activity);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int) (width * .9), (int) (height * .4));

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.x = 0;
        layoutParams.y = 20;

        getWindow().setAttributes(layoutParams);

        getDataFromGovAPI();
    }

    private void getDataFromGovAPI() {
        updateThread = new Thread(new Runnable() {
            @Override
            public void run() {
                JSONRetriever jsonRetriever = new JSONRetriever();
                WriteBatch collegeUpdateBatch = db.batch();

                Holder holder = null;
                try {
                    holder = jsonRetriever.mapAllPagesToObjects();
                } catch (Exception e) {
                    finish();
                }
                if (holder != null) {
                    for (College college : holder.getColleges()) {
                        DocumentReference updatedCollege = db.collection("colleges")
                                .document(String.valueOf(college.getId()));
                        collegeUpdateBatch.set(updatedCollege, college);
                        System.out.println(college.getSchoolName() + " updated/added");
                    }
                    collegeUpdateBatch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                wasUpdated = true;
                                finish();
                            } else {
                                finish();
                            }
                        }
                    });
                }
            }
        });
        updateThread.setPriority(Thread.MAX_PRIORITY);
        updateThread.start();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (wasUpdated) {
            Toast.makeText(UpdateDBPopupActivity.this, "Database updated!"
                    , Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(UpdateDBPopupActivity.this, "Database update failed"
                    , Toast.LENGTH_SHORT).show();
        }
        updateThread.interrupt();
        updateThread = null;
        stopService(getIntent());
    }
}
