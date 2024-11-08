package com.example.fb_v2.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fb_v2.Model.MenuItems;
import com.example.fb_v2.R;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private List<MenuItems> menuItems;
    private OnItemClickListener listener;

    public MenuAdapter(List<MenuItems> menuItems) {
        this.menuItems = menuItems;
    }

    // Define the interface for click events
    public interface OnItemClickListener {
        void onItemClick(MenuItems item);
    }

    // Setter for the click listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuItems menuItem = menuItems.get(position);
        holder.icon.setImageResource(menuItem.getIconResId());
        holder.name.setText(menuItem.getName());

        // Set a click listener on the entire item view
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(menuItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.menuItemIcon);
            name = itemView.findViewById(R.id.menuItemName);
        }
    }
}