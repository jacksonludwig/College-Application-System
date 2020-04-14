package com.example.collegeapplicationsystem;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.collegeapplicationsystem.JSONParsing.College;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewCollegeDetailsActivity extends AppCompatActivity {
    private static final String TAG = "COMPARISON";
    private static final String TAG2 = "UPDATE_FAVORITIES";

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private College clickedCollege;
    private TextView collegeID;
    private TextView collegeName;
    private TextView collegeReadingScore25;
    private TextView collegeReadingScore75;
    private TextView collegeMathScore25;
    private TextView collegeMathScore75;

    private TextView userReadingScore;
    private TextView userMathScore;
    private TextView userAcceptanceChance;

    private Button favoritesButton;
    private Button deleteButton;

    private HashMap<String, College> favoritesMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_college_details);

        getCollegeFromIntent();
        checkIfInFavoritiesView();

        setCollegeViews();
        setUserScoreView();
        fetchFavorites();
    }

    private void getCollegeFromIntent() {
        clickedCollege = (College) getIntent().getSerializableExtra("clickedCollege");
        if (clickedCollege.getLatestAdmissionsSatScores25thPercentileCriticalReading() == null) {
            clickedCollege.setLatestAdmissionsSatScores25thPercentileCriticalReading(Float.valueOf(0));
        }
        if (clickedCollege.getLatestAdmissionsSatScores75thPercentileCriticalReading() == null) {
            clickedCollege.setLatestAdmissionsSatScores75thPercentileCriticalReading(Float.valueOf(0));
        }
        if (clickedCollege.getLatestAdmissionsSatScores25thPercentileMath() == null) {
            clickedCollege.setLatestAdmissionsSatScores25thPercentileMath(Float.valueOf(0));
        }
        if (clickedCollege.getLatestAdmissionsSatScores75thPercentileMath() == null) {
            clickedCollege.setLatestAdmissionsSatScores75thPercentileMath(Float.valueOf(0));
        }
    }

    private void checkIfInFavoritiesView() {
        favoritesButton = findViewById(R.id.add_to_fav_button);
        deleteButton = findViewById(R.id.delete_from_fav_button);

        String viewOrDeleteCheck = getIntent().getStringExtra("activitySenderType");
        if (viewOrDeleteCheck != null && viewOrDeleteCheck.equals("favorites")) {
            favoritesButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.VISIBLE);
        } else {
            favoritesButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.GONE);
        }
    }

    private void setCollegeViews() {
        collegeID = findViewById(R.id.college_id_details);
        collegeID.setText(String.valueOf(clickedCollege.getId()));
        collegeName = findViewById(R.id.college_name);
        collegeName.setText(clickedCollege.getSchoolName());
        collegeReadingScore25 = findViewById(R.id.college_reading25);
        collegeReadingScore25.setText(String.valueOf(Math.round(clickedCollege.getLatestAdmissionsSatScores25thPercentileCriticalReading())));
        collegeReadingScore75 = findViewById(R.id.college_reading75);
        collegeReadingScore75.setText(String.valueOf(Math.round(clickedCollege.getLatestAdmissionsSatScores75thPercentileCriticalReading())));
        collegeMathScore25 = findViewById(R.id.college_math25);
        collegeMathScore25.setText(String.valueOf(Math.round(clickedCollege.getLatestAdmissionsSatScores25thPercentileMath())));
        collegeMathScore75 = findViewById(R.id.college_math75);
        collegeMathScore75.setText(String.valueOf(Math.round(clickedCollege.getLatestAdmissionsSatScores75thPercentileMath())));
    }

    private void setUserScoreView() {
        userReadingScore = findViewById(R.id.user_reading_score);
        userMathScore = findViewById(R.id.user_math_score);
        db.collection("userdata")
                .document(user.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                userMathScore.setText(document.get("math").toString());
                                userReadingScore.setText(document.get("reading").toString());
                                setUserAcceptanceChanceView();
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
    }

    private void setUserAcceptanceChanceView() {
        userAcceptanceChance = findViewById(R.id.status_chance);
        int collegeCombinedScore25 = Math.round(Float.parseFloat(collegeMathScore25.getText().toString())) + Math.round(Float.parseFloat(collegeReadingScore25.getText().toString()));
        int collegeCombinedScore75 = Math.round(Float.parseFloat(collegeMathScore75.getText().toString())) + Math.round(Float.parseFloat(collegeReadingScore75.getText().toString()));

        int userMath = Math.round(Float.parseFloat(userMathScore.getText().toString()));
        int userReading = Math.round(Float.parseFloat(userReadingScore.getText().toString()));
        int userCombinedScore = userMath + userReading;

        if (collegeCombinedScore25 != 0 && collegeCombinedScore75 != 0 && userMath != 0 && userReading != 0) {
            if (userCombinedScore >= collegeCombinedScore75) {
                userAcceptanceChance.setText("Likely");
                userAcceptanceChance.setTextColor(Color.GREEN);
            } else if (userCombinedScore > collegeCombinedScore25) {
                userAcceptanceChance.setText("Somewhat Likely");
                userAcceptanceChance.setTextColor(Color.YELLOW);
            } else {
                userAcceptanceChance.setText("Unlikely");
                userAcceptanceChance.setTextColor(Color.RED);
            }
        } else {
            userAcceptanceChance.setText("UNKNOWN");
            userAcceptanceChance.setTextColor(Color.GRAY);
        }
    }

    private void fetchFavorites() {
        db.collection("userdata")
                .document(user.getEmail())
                .collection("favorites")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            favoritesMap = new HashMap<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                College college = document.toObject(College.class);
                                favoritesMap.put(String.valueOf(college.getId()), college);
                            }
                        } else {
                            Log.d(TAG2, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void addToFavorites(View view) {
        db.collection("colleges")
                .document(String.valueOf(clickedCollege.getId()))
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                College college = document.toObject(College.class);
                                if (!favoritesMap.containsKey(String.valueOf(college.getId()))) {
                                    favoritesMap.put(String.valueOf(college.getId()), college);
                                    updateFavorites();
                                }
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
    }

    private void updateFavorites() {
        ArrayList<College> collegesArrayList = new ArrayList<>();
        collegesArrayList.addAll(favoritesMap.values());

        for (College college : collegesArrayList) {
            db.collection("userdata")
                    .document(user.getEmail())
                    .collection("favorites")
                    .document(String.valueOf(college.getId()))
                    .set(college)
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG2, "Error writing document", e);
                        }
                    });
        }

        Toast.makeText(this, "Favorites database updated!", Toast.LENGTH_SHORT).show();
    }

    public void removeFromFavorites(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Favorite")
                .setMessage("Are you sure you want to remove this college from your favorites?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("I'm sure", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        db.collection("userdata")
                                .document(user.getEmail())
                                .collection("favorites")
                                .document(String.valueOf(clickedCollege.getId()))
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(ViewCollegeDetailsActivity.this, "Favorite deleted!", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ViewCollegeDetailsActivity.this, "Error deleting favorite", Toast.LENGTH_SHORT).show();
                                        Log.w(TAG2, "Error deleting document", e);
                                    }
                                });
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }
}
