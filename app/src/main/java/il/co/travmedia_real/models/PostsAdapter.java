package il.co.travmedia_real.models;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import il.co.travmedia_real.R;
import il.co.travmedia_real.activities.ViewPostActivity;

public class PostsAdapter extends RecyclerView.Adapter <PostsAdapter.PostHolder> {

    private ArrayList<Post> posts, postsDuplicate;
    private Context context;
    private final String[] profileImg = new String[]{"defaultImgUrl"};

    public PostsAdapter(ArrayList<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
        this.postsDuplicate = new ArrayList<>(posts);
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostsAdapter.PostHolder(LayoutInflater.from(context).inflate(R.layout.posts_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {

        Post post = posts.get(position);

        holder.title.setText(post.getTitle());
        holder.author.setText(post.getAuthor());

        getProfileImg(post.getAuthor());

        holder.itemView.setOnClickListener(v -> {
            Intent viewPost = new Intent(context, ViewPostActivity.class);
            viewPost.putExtra("title", post.getTitle());
            viewPost.putExtra("author", post.getAuthor());
            viewPost.putExtra("profileImage", profileImg[0]);
            viewPost.putExtra("content", post.getContent());
            viewPost.putExtra("imageLink", post.getImageLink());
            PostsAdapter.this.context.startActivity(viewPost);
        });
    }

    private void getProfileImg(String author) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot user: snapshot.getChildren()) {
                    HashMap<Object, Object> creds = (HashMap<Object, Object>) user.getValue();
                    assert creds != null;
                    Log.d("User", user.getValue().toString());
                    if (author.equals(creds.get("username")))
                        profileImg[0] = (String) creds.get("profileImageUrl");
                    assert profileImg[0] != null;
                    Log.d("User1", profileImg[0]);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return this.posts.size();
    }

    static class PostHolder extends RecyclerView.ViewHolder {

        TextView title, author;

        public PostHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTv);
            author = itemView.findViewById(R.id.authorTv);
        }
    }

    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Post> filtered = new ArrayList<>();
            if (constraint == null || constraint.length() == 0){
                filtered.addAll(PostsAdapter.this.postsDuplicate);
            }
            else {
                String pattern = constraint.toString().toLowerCase().trim();
                for (Post post: PostsAdapter.this.posts) {
                    if(post.getTitle().toLowerCase().contains(pattern) || post.getAuthor().toLowerCase().contains(pattern)){
                        filtered.add(post);
                    }
                    else if (constraint.length() == 0){
                        filtered.clear();
                        filtered.addAll(PostsAdapter.this.postsDuplicate);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filtered;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            PostsAdapter.this.posts.clear();
            PostsAdapter.this.posts.addAll((ArrayList<Post>) results.values);
            notifyDataSetChanged();
        }
    };

    public void updateData(ArrayList<Post> posts) {
        postsDuplicate.clear();
        postsDuplicate.addAll(posts);
        notifyDataSetChanged();
    }
}
