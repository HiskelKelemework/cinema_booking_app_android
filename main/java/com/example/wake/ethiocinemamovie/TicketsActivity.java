package com.example.wake.ethiocinemamovie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toolbar;

import com.example.wake.ethiocinemamovie.Adapters.TicketsListAdapter;
import com.example.wake.ethiocinemamovie.Models.User;
import com.example.wake.ethiocinemamovie.Models.UserAccManager;

public class TicketsActivity extends AppCompatActivity implements UserAccManager.OnAccountDetailLoadedListener {
    RecyclerView ticketsList;
    LinearLayout connProb;
    ProgressBar pb;

    TicketsListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.tickets_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tickets");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ticketsList = findViewById(R.id.tickets_recyl);
        pb = findViewById(R.id.tickets_loading_prog);
        connProb = findViewById(R.id.tickets_conn_prob);

        adapter = new TicketsListAdapter(User.getBookings(), this);
        ticketsList.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        ticketsList.setLayoutManager(layoutManager);

        Button retry = findViewById(R.id.tickets_retry);

        retry.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                connProb.setVisibility(View.GONE);
                setUpTickets();
            }
        });

//        setUpTickets();

    }

    public void setUpTickets() {
        pb.setVisibility(View.VISIBLE);
        if (User.isIsDetailLoaded()) {
            pb.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }
        else {
            UserAccManager.loadAccountDetailAndNotifyMe(this, this);
        }
    }

    @Override
    public void OnAccountDetailLoaded(boolean connection) {
        pb.setVisibility(View.GONE);
        if (connection && User.isIsDetailLoaded()) {
            adapter.notifyDataSetChanged();
        }
        else connProb.setVisibility(View.VISIBLE);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return true;
    }
}
