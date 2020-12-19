package com.example.project_test.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.R;

import java.util.List;

public class viewPagerguideAdapter extends RecyclerView.Adapter<viewPagerguideAdapter.guideHolder> {
    Context mcontext;
    List<guide> guideList;

    public viewPagerguideAdapter(Context mcontext, List<guide> guideList) {
        this.mcontext = mcontext;
        this.guideList = guideList;
    }

    @NonNull
    @Override
    public guideHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.image_guide_cell, parent, false);

        return new guideHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull guideHolder holder, int position) {
        holder.imageView.setImageResource(guideList.get(position).getImage());
        holder.textView.setText(guideList.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return guideList.size();
    }

    public class guideHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public guideHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_guide);
            textView = itemView.findViewById(R.id.text_guide);
        }

    }
}
