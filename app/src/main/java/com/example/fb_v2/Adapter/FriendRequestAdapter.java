package com.example.fb_v2.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fb_v2.Model.FriendRequest;
import com.example.fb_v2.R;
import java.util.List;

public class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestAdapter.FriendRequestViewHolder> {

    private List<FriendRequest> friendRequests;
    private Context context;

    public FriendRequestAdapter(Context context, List<FriendRequest> friendRequests) {
        this.context = context;
        this.friendRequests = friendRequests;
    }

    @NonNull
    @Override
    public FriendRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_friend_request, parent, false);
        return new FriendRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendRequestViewHolder holder, int position) {
        FriendRequest request = friendRequests.get(position);

        // Bind data to the views
        holder.tvName.setText(request.getName());
        holder.tvMutualFriends.setText(request.getMutualFriendsCount() + " bạn chung");
        holder.tvTimestamp.setText(request.getTimestamp());
        Glide.with(context).load(request.getProfileImageUrl()).into(holder.ivProfilePicture);

        // Check if the friend request has been accepted to update the UI
        if (request.isAccepted()) {
            holder.btnAccept.setVisibility(View.GONE);
            holder.btnDelete.setVisibility(View.GONE);
            holder.tvAccepted.setVisibility(View.VISIBLE);
            holder.tvAccepted.setText("Lời mời được chấp nhận");
        } else {
            holder.btnAccept.setVisibility(View.VISIBLE);
            holder.btnDelete.setVisibility(View.VISIBLE);
            holder.tvAccepted.setVisibility(View.GONE);
        }

        // Handle accept button click
        holder.btnAccept.setOnClickListener(v -> {
            request.setAccepted(true);
            notifyItemChanged(position);
            Toast.makeText(context, "Friend request accepted for " + request.getName(), Toast.LENGTH_SHORT).show();
        });

        // Handle delete button click
        holder.btnDelete.setOnClickListener(v -> {
            showDeleteConfirmationDialog(position, request);
        });
    }

    @Override
    public int getItemCount() {
        return friendRequests.size();
    }

    // Show confirmation dialog for deleting a friend request
    private void showDeleteConfirmationDialog(int position, FriendRequest request) {
        new AlertDialog.Builder(context)
                .setTitle("Xóa lời mời kết bạn")
                .setMessage("Bạn có chắc chắn muốn xóa " + request.getName() + " khỏi danh sách lời mời kết bạn không?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    friendRequests.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(context, "Đã xóa lời mời kết bạn của " + request.getName(), Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                .show();
    }

    static class FriendRequestViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProfilePicture;
        TextView tvName, tvMutualFriends, tvAccepted, tvTimestamp;
        Button btnAccept, btnDelete;

        public FriendRequestViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfilePicture = itemView.findViewById(R.id.ivProfilePicture);
            tvName = itemView.findViewById(R.id.tvName);
            tvMutualFriends = itemView.findViewById(R.id.tvMutualFriends);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
            tvAccepted = itemView.findViewById(R.id.tvAccepted);  // New TextView to show "Lời mời được chấp nhận"
            btnAccept = itemView.findViewById(R.id.btnAccept);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
