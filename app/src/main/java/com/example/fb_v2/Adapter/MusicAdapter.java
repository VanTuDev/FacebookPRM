package com.example.fb_v2.Adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fb_v2.Model.Music;
import com.example.fb_v2.R;
import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicViewHolder> {

    private List<Music> musicList;
    private MediaPlayer mediaPlayer;
    private int playingPosition = -1;

    public MusicAdapter(List<Music> musicList) {
        this.musicList = musicList;
    }

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_music, parent, false);
        return new MusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder holder, int position) {
        Music music = musicList.get(position);
        holder.title.setText(music.getTitle());
        holder.artist.setText(music.getArtist());

        // Set the play button state based on whether the song is currently playing
        if (position == playingPosition) {
            holder.playButton.setImageResource(R.drawable.ic_pause); // Show pause icon if playing
        } else {
            holder.playButton.setImageResource(R.drawable.ic_play); // Show play icon if not playing
        }

        holder.playButton.setOnClickListener(v -> {
            // Stop any currently playing music
            if (mediaPlayer != null && position == playingPosition) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
                playingPosition = -1;
                notifyItemChanged(position);
            } else {
                // Start playing the selected music
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    notifyItemChanged(playingPosition); // Update previous playing item
                }

                mediaPlayer = MediaPlayer.create(v.getContext(), music.getFileResId());
                mediaPlayer.setOnCompletionListener(mp -> {
                    playingPosition = -1;
                    notifyItemChanged(position);
                });

                mediaPlayer.start();
                playingPosition = position;
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    static class MusicViewHolder extends RecyclerView.ViewHolder {
        TextView title, artist;
        ImageView playButton;

        public MusicViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.musicTitle);
            artist = itemView.findViewById(R.id.musicArtist);
            playButton = itemView.findViewById(R.id.playButton);
        }
    }
}
