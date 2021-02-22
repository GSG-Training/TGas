package com.example.project_test.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.R;
import com.example.project_test.model.entitiy.Comments;
import com.example.project_test.model.utils.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {
    List<Comments> comments;
    Context context;

    public CommentAdapter(List<Comments> comments, Context context) {
        this.comments = comments;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.comment_cell,parent,false);
        return new CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
     holder.txt_comments.setText(comments.get(position).getComment());
     //edit userName
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                  if(comments.get(position).getUsername().equals(dataSnapshot.getKey())){
                        Users user=dataSnapshot.getValue(Users.class);
                        holder.userName.setText(user.getFullName());

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
     holder.ratingBar.setRating(comments.get(position).getRate());
     SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
     String dateString = formatter.format(new Date(comments.get(position).getTime()));
     holder.txt_time.setText(dateString);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public  class CommentHolder extends RecyclerView.ViewHolder{
      TextView userName,txt_comments,txt_time;
      RatingBar ratingBar;
       public CommentHolder(@NonNull View itemView) {
           super(itemView);
           userName=itemView.findViewById(R.id.user_comment);
           txt_comments=itemView.findViewById(R.id.text_comment);
           txt_time=itemView.findViewById(R.id.date_comment);
           ratingBar=itemView.findViewById(R.id.user_rate_comment);



       }
   }
}
