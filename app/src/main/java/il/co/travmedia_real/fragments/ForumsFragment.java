package il.co.travmedia_real.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.inputmethod.EditorInfoCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import il.co.travmedia_real.R;
import il.co.travmedia_real.activities.MainActivity;
import il.co.travmedia_real.activities.NewPostActivity;
import il.co.travmedia_real.activities.ProfileSettingsActivity;
import il.co.travmedia_real.models.Post;
import il.co.travmedia_real.models.PostsAdapter;
import android.view.inputmethod.EditorInfo;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;


public class ForumsFragment extends Fragment {

    // UI components
    private TextView textView;
    private RecyclerView recyclerView;
    private PostsAdapter postsAdapter;
    private ArrayList<Post> posts;

    public ForumsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState){
        setHasOptionsMenu(true);

        View view = layoutInflater.inflate(R.layout.fragment_forums, viewGroup, false);
        textView = view.findViewById(R.id.text);
        recyclerView = view.findViewById(R.id.recyclerView);

        posts = new ArrayList<>();
        postsAdapter = new PostsAdapter(posts, getContext());
        getPosts();
        Log.d("LIST", posts.toString());
        setRecycler();

        findUserByUid();
        return view;
    }

    private void getPosts() {
        DatabaseReference postsRef = FirebaseDatabase.getInstance().getReference().child("posts");

        postsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1: snapshot.getChildren()){
                    Post post = snapshot1.getValue(Post.class);
                    assert post != null;
                    posts.add(post);
                    postsAdapter.notifyDataSetChanged();
                    // postsAdapter.updateData(posts);  TODO: Filter options in adapter !
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void findUserByUid() {
        String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        Log.d("User", userId);
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("username");

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                textView.setText("Welcome " + snapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.options_menu, menu);
        MenuItem signOut = menu.findItem(R.id.signOut);
        signOut.setVisible(false);
        menu.findItem(R.id.aboutUs).setVisible(false);

        MenuItem searchItem = menu.findItem(R.id.searchPost);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() == 0){
                    getPosts();
                }
                
                return false;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                postsAdapter.getFilter().filter(query);

                if (query.length() == 0){
                    getPosts();
                }

                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.profileSettings) {
            Intent intent = new Intent(getActivity(), ProfileSettingsActivity.class);
            intent.putExtra("username", textView.getText().toString().replace("Welcome ", ""));
            startActivity(intent);
        }

        else if (item.getItemId() == R.id.newPost) {
            Intent toNewPost = new Intent(getActivity(), NewPostActivity.class);
            toNewPost.putExtra("username", textView.getText().toString().replace("Welcome ", ""));
            startActivity(toNewPost);
        }

        return super.onOptionsItemSelected(item);
    }

    public void setRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(postsAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

//        if (postsAdapter != null && posts != null) {
//            getPosts();
//            postsAdapter = new PostsAdapter(posts, getContext());
//            setRecycler();
//        }
    }
}
