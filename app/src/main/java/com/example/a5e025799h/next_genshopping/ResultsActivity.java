package com.example.a5e025799h.next_genshopping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{


    ListView listView;
    ArrayList<Results> results;
    ProgressBar progressBar;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        listView = (ListView) findViewById(R.id.listView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        hideProgressBar();

        String searchTerm = getIntent().getStringExtra("searchTerm");
        String postalCode = getIntent().getStringExtra("postalCode");

        Downloader downloader = new Downloader(this);
        downloader.execute(searchTerm, postalCode);

        auth = FirebaseAuth.getInstance();
    }

    public void displayProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
    }
    public void setProgressBarProgress(int progress){
        progressBar.setProgress(progress);
        if(progress==100){
            hideProgressBar();
        }
    }

    public void hideProgressBar(){
        progressBar.setVisibility(View.GONE);
    }


    public void drawListView(ArrayList<Results> resultsArray){
        results = new ArrayList<Results>();
        results = resultsArray;
        ResultsAdapter adapter = new ResultsAdapter(this, resultsArray);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Results result = results.get(position);
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("result", result);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.logoutMenu:
                logoutUser();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logoutUser(){
        auth.signOut();
        if(auth.getCurrentUser()==null){
            startActivity(new Intent(ResultsActivity.this, MainActivity.class));
            finish();
        }

    }
}
