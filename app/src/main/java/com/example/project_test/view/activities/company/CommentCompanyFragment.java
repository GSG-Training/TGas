package com.example.project_test.view.activities.company;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_test.R;
import com.example.project_test.model.entitiy.Comments;
import com.example.project_test.view.adapter.CommentAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentCompanyFragment extends Fragment {
    RecyclerView recyclerView;
    List<Comments> comments=new ArrayList<>();
    FirebaseUser user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_comment_company, container, false);
        user= FirebaseAuth.getInstance().getCurrentUser();
        recyclerView=view.findViewById(R.id.recycle_comments);
        CommentAdapter commentAdapter=new CommentAdapter(comments,getContext());
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Comments");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Comments comment = postSnapshot.getValue(Comments.class);
                    if (comment != null) {
                        if(comment.getCompanyName().equals(user.getUid())){
                            comments.add(comment);
                            commentAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(commentAdapter);
        return view;
    }
}