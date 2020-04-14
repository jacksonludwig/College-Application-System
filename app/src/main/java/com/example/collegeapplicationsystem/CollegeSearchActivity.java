package com.example.collegeapplicationsystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeapplicationsystem.JSONParsing.College;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class CollegeSearchActivity extends AppCompatActivity {
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String activityType;
    private String searchText;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collegeRef = db.collection("colleges");
    private CollectionReference favoritesRef = db.collection("userdata")
            .document(user.getEmail())
            .collection("favorites");
    private RecyclerView recyclerView;
    private CollegeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_search);

        setSearchQuery();

        startRecycler();
    }

    private Query chooseQueryType() {
        String queryType = getIntent().getStringExtra("queryType");
        switch (queryType) {
            case "name":
                activityType = "name";
                return collegeRef
                        .whereGreaterThanOrEqualTo("schoolName", searchText)
                        .whereLessThan("schoolName", getEndOfQuery(searchText))
                        .orderBy("schoolName", Query.Direction.ASCENDING);
            case "id":
                activityType = "id";
                return collegeRef
                        .whereEqualTo("id", Integer.parseInt(searchText));
            case "state":
                activityType = "state";
                return collegeRef
                        .whereEqualTo("schoolState", searchText)
                        .orderBy("schoolName", Query.Direction.ASCENDING);
            case "favorites":
                activityType = "favorites";
                return favoritesRef
                        .orderBy("schoolName", Query.Direction.ASCENDING);
            default:
                activityType = "id";
                return collegeRef
                        .whereEqualTo("id", 100654);
        }

    }

    private void startRecycler() {
        Query query = chooseQueryType();

        Toast.makeText(this, "Query Attempted...", Toast.LENGTH_SHORT).show();

        FirestoreRecyclerOptions<College> options = new FirestoreRecyclerOptions.Builder<College>()
                .setQuery(query, College.class)
                .build();

        adapter = new CollegeAdapter(options);

        recyclerView = findViewById(R.id.college_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new CollegeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                College college = documentSnapshot.toObject(College.class);
                Intent intent = new Intent(getApplicationContext(), ViewCollegeDetailsActivity.class);
                intent.putExtra("activitySenderType", activityType);
                intent.putExtra("clickedCollege", college);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (activityType.equals("favorites"))
            adapter.notifyDataSetChanged();
    }

    private void setSearchQuery() {
        searchText = getIntent().getStringExtra("search");
        if (searchText != null) {
            searchText = searchText.trim();
        }
        if (searchText.equals(" ") || searchText.equals("")) {
            searchText = "text not searchable";
        }
    }

    private String getEndOfQuery(String search) {
        search = search.trim();
        char lastCharIterated = search.charAt(search.length() - 1);
        search = search.substring(0, search.length() - 1);
        search = search + (char) (lastCharIterated + 1);

        return search;
    }
}
