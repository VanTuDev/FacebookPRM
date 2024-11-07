package com.example.fb_v2.Activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fb_v2.Adapter.FriendRequestAdapter;
import com.example.fb_v2.Model.FriendRequest;
import com.example.fb_v2.R;

import java.util.ArrayList;
import java.util.List;

public class FriendRequestsActivity extends AppCompatActivity {

    private RecyclerView rvFriendRequests;
    private FriendRequestAdapter adapter;
    private List<FriendRequest> friendRequests;
    private List<FriendRequest> filteredRequests;  // List for filtered results
    private EditText searchBar;
    private ImageView searchIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_requests);

        // Initialize Views
        searchIcon = findViewById(R.id.searchIcon);
        searchBar = findViewById(R.id.searchBar);
        rvFriendRequests = findViewById(R.id.rvFriendRequests);

        // Initialize RecyclerView
        rvFriendRequests.setLayoutManager(new LinearLayoutManager(this));

        // Load the friend requests data
        friendRequests = loadFriendRequests();
        filteredRequests = new ArrayList<>(friendRequests);  // Initially show all requests

        // Initialize Adapter
        adapter = new FriendRequestAdapter(this, filteredRequests);
        rvFriendRequests.setAdapter(adapter);

        // Toggle search bar visibility on search icon click
        searchIcon.setOnClickListener(v -> {
            if (searchBar.getVisibility() == View.GONE) {
                searchBar.setVisibility(View.VISIBLE);
                searchBar.requestFocus();
            } else {
                searchBar.setVisibility(View.GONE);
                searchBar.setText("");  // Clear search text when hiding
                filteredRequests.clear();
                filteredRequests.addAll(friendRequests);  // Reset list to all items
                adapter.notifyDataSetChanged();
            }
        });

        // Set up search functionality
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterFriendRequests(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    // Method to filter friend requests based on the search query
    private void filterFriendRequests(String query) {
        filteredRequests.clear();
        if (query.isEmpty()) {
            filteredRequests.addAll(friendRequests);
        } else {
            for (FriendRequest request : friendRequests) {
                if (request.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredRequests.add(request);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    // Sample data for friend requests
    private List<FriendRequest> loadFriendRequests() {
        List<FriendRequest> requests = new ArrayList<>();
        requests.add(new FriendRequest("Bảo Vy", 3, R.drawable.avata, "3 ngày"));
        requests.add(new FriendRequest("Hương Huỳnh", 1, R.drawable.avata, "1 năm"));
        requests.add(new FriendRequest("Hoàng Đức Hạnh", 125, R.drawable.avata, "11 tuần"));
        requests.add(new FriendRequest("Emma Diễm", 1, R.drawable.avata, "2 tuần"));
        requests.add(new FriendRequest("Huyền Trần", 6, R.drawable.avata, "1 năm"));
        requests.add(new FriendRequest("Nguyễn Thị Thùy Duyên", 1, R.drawable.avata, "2 tuần"));
        return requests;
    }
}
