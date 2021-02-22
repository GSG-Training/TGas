package com.example.project_test.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.R;
import com.example.project_test.model.entitiy.Chats;
import com.example.project_test.model.utils.Company;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatHolder> {
    Context context;
    List<Chats> chats;
    private CompanyAdapter.OnItemClicked onItemClicked;


    public ChatAdapter(Context context, List<Chats> chats) {
        this.context = context;
        this.chats = chats;

    }

    @NonNull
    @Override
    public ChatAdapter.ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.right_cell_message_layout,parent,false);
        return new ChatAdapter.ChatHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ChatHolder holder, int position) {
     holder.txt_message.setText(chats.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }
    public class ChatHolder extends  RecyclerView.ViewHolder{
        TextView txt_message;

        public ChatHolder(@NonNull View itemView) {
            super(itemView);
            txt_message=itemView.findViewById(R.id.txt_message);

        }
    }
}
