package com.example.cmsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class postAdaptar extends RecyclerView.Adapter<postAdaptar.ViewHolder>{

    public Context mcontext;
    public List<Post> mPost;

    private FirebaseUser firebaseUser;

    public postAdaptar(Context mcontext, List<Post> mPost) {
        this.mcontext = mcontext;
        this.mPost = mPost;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.post_item, parent,false);
        return new postAdaptar.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.description.setText(mPost.get(position).getDesription());
        Glide.with(mcontext).load(mPost.get(position).getPostImage()).into(holder.post_image);
        holder.username.setText(mPost.get(position).getPublisher());
        holder.publisher.setText(mPost.get(position).getPublisher());

    }

    @Override
    public int getItemCount() {
        return mPost.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView post_image,comment;
        public TextView username,publisher,description,comments;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            post_image=itemView.findViewById(R.id.post_image);
            comment=itemView.findViewById(R.id.comment);
            username=itemView.findViewById(R.id.user_name);
            publisher=itemView.findViewById(R.id.publisher);
            description=itemView.findViewById(R.id.description);
            comments=itemView.findViewById(R.id.comments);
        }
    }
}
