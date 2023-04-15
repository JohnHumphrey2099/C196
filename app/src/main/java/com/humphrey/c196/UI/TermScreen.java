package com.humphrey.c196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.humphrey.c196.Database.Repository;
import com.humphrey.c196.Entity.Term;
import com.humphrey.c196.R;

import java.util.ArrayList;
import java.util.List;

public class TermScreen extends AppCompatActivity {
    private Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_screen);
        RecyclerView recyclerView = findViewById(R.id.termsRecyclerView);
        final TermAdapter termAdapter = new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        repository = new Repository(getApplication());
        List<Term> allTerms = repository.getALlTerms();
        termAdapter.setTermList(allTerms);
    }

    public void goToTermDetailScreen(View view) {
        Intent intent = new Intent(TermScreen.this, TermDetail.class);
        startActivity(intent);

    }
}