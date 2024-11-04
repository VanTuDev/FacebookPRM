package com.example.fb_v2.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fb_v2.R;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<String> notifications;
    private OnNotificationDeleteListener deleteListener;

    public NotificationAdapter(List<String> notifications, OnNotificationDeleteListener deleteListener) {
        this.notifications = notifications;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        String notification = notifications.get(position);
        holder.notificationText.setText(notification);

        holder.deleteNotification.setOnClickListener(v -> {
            deleteListener.onDelete(position);
        });
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView notificationText;
        ImageView deleteNotification;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationText = itemView.findViewById(R.id.notificationText);
            deleteNotification = itemView.findViewById(R.id.deleteNotification);
        }
    }

    public interface OnNotificationDeleteListener {
        void onDelete(int position);
    }
}
