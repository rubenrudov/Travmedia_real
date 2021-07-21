package il.co.travmedia_real.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.ReceiverCallNotAllowedException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import il.co.travmedia_real.R;
import il.co.travmedia_real.models.Comment;
import il.co.travmedia_real.models.CommentAdapter;

public class ViewPostActivity extends AppCompatActivity {

    Intent data;
    TextView titleTv, authorTv, contentTv;
    ImageView postImage;
    CircleImageView civ;
    CircleImageView civCurrentUser;
    EditText commentEditText;
    Button submitButton;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    RecyclerView commentsRecycler;
    CommentAdapter commentAdapter;
    ArrayList<Comment> comments;
    String key;
    static String COMMENT_KEY = "comments" ;

    TextView userTv, picTv;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);
        Objects.requireNonNull(getSupportActionBar()).hide();

        data = getIntent();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        titleTv = findViewById(R.id.titleTv);
        authorTv = findViewById(R.id.authorTv);
        contentTv = findViewById(R.id.contentTv);
        postImage = findViewById(R.id.postImage);
        civ = findViewById(R.id.civProfile);


        civCurrentUser = findViewById(R.id.currentUserImg);
        userTv = findViewById(R.id.currentUsernameTv);
        picTv = findViewById(R.id.picTv);
        RelativeLayout goneLay = findViewById(R.id.relCreds);

        String userKey = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        findUserByUid(userKey);

        goneLay.setVisibility(View.GONE);

        titleTv.setText(Objects.requireNonNull(data.getExtras()).getString("title"));
        authorTv.setText(data.getExtras().getString("author"));
        contentTv.setText(data.getExtras().getString("content"));

        commentsRecycler = findViewById(R.id.recyclerComments);

        Glide.with(getApplicationContext()).load(data.getExtras().getString("imageLink")).into(postImage);

        if (data.getExtras().get("profileImage").equals("defaultImgUrl")) {
            Glide.with(getApplicationContext()).load(R.mipmap.ic_launcher).into(civ);
        }

        else
            Glide.with(getApplicationContext()).load(data.getExtras().get("profileImage")).into(civ);

        key = data.getExtras().getString("title");
        commentEditText = findViewById(R.id.commentEditText);
        submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitButton.setVisibility(View.INVISIBLE);
                DatabaseReference commentReference = firebaseDatabase.getReference(COMMENT_KEY).child(key).push();
                String comment_content = commentEditText.getText().toString();
                String uid = firebaseUser.getUid();
                String username = userTv.getText().toString();
                String link = picTv.getText().toString();
                Comment comment = new Comment(comment_content, uid, username, link);

                commentReference.setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Comment added", Toast.LENGTH_SHORT).show();
                        commentEditText.setText("");
                        submitButton.setVisibility(View.VISIBLE);
                    }
                }).addOnFailureListener(e -> Log.d("COMMENT", "EXCEPTION"));
            }
        });

        iniRvComment();
    }

    private void findUserByUid(String userKey) {
        String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        Log.d("User", userId);
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userKey).child("username");

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userTv.setText((String) snapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference picRef = FirebaseDatabase.getInstance().getReference().child("users").child(userKey).child("profileImageUrl");
        picRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                picTv.setText((String) snapshot.getValue());

                if (Objects.equals(snapshot.getValue(), "defaultImgUrl")) {
                    Glide.with(getApplicationContext()).load(R.mipmap.ic_launcher).into(civCurrentUser);
                }

                else
                    Glide.with(getApplicationContext()).load(Objects.requireNonNull(snapshot.getValue())).into(civCurrentUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void iniRvComment() {

        commentsRecycler.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference commentRefUname = firebaseDatabase.getReference(COMMENT_KEY).child(key);
        commentRefUname.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                comments = new ArrayList<>();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Comment comment = snap.getValue(Comment.class);
                    comments.add(comment);
                }
                commentAdapter = new CommentAdapter(comments, getApplicationContext());
                commentsRecycler.setAdapter(commentAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
