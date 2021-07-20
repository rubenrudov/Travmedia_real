package il.co.travmedia_real.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import il.co.travmedia_real.R;

public class NewPostActivity extends AppCompatActivity {

    EditText titleEditText, contentEditText;
    Button submitButton, uploadNewButton;
    ImageView previewIv;

    Intent creds;
    String author;
    String userId;
    Uri picturePath;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        // userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        creds = getIntent();
        author = Objects.requireNonNull(creds.getExtras()).getString("username");

        titleEditText = findViewById(R.id.titleEditText);
        contentEditText = findViewById(R.id.contentEditText);
        previewIv = findViewById(R.id.previewImage);

        uploadNewButton = findViewById(R.id.uploadNewButton);
        submitButton = findViewById(R.id.submitButton);

        uploadNewButton.setOnClickListener(v -> {
            pickPicture();
        });

        submitButton.setOnClickListener(v -> {
            if (picturePath != null) {
                uploadPictureToStorage();
            }

            else {
                Toast.makeText(this, "Must select a picture", Toast.LENGTH_SHORT).show();
            }

            finish();
        });
    }

    private void pickPicture() {
        Intent selection = new Intent(Intent.ACTION_PICK);
        selection.setType("image/*");
        startActivityForResult(selection,1);
        // someActivityResultLauncher.launch(selection);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;

        if (resultCode == RESULT_OK) {
            Log.d("RUBY", Objects.requireNonNull(data.getData()).toString());
            picturePath = data.getData();
            previewIv.setImageURI(picturePath);
        }
    }

    private void uploadPictureToStorage() {
        assert picturePath.getLastPathSegment() != null;

        StorageReference pictureRef = FirebaseStorage.getInstance().getReference().child(picturePath.getLastPathSegment());
        pictureRef.putFile(picturePath).addOnSuccessListener(taskSnapshot -> {
            Task getDownloadUri = taskSnapshot.getStorage().getDownloadUrl();

            while (!getDownloadUri.isSuccessful()) {
                Log.d("TASK", "RUNNING PROGRESS");
            }

            assert getDownloadUri.getResult() != null;
            url = getDownloadUri.getResult().toString();
            Log.d("RUBY", url);
            putData(url);
        }).addOnFailureListener(e -> Log.d("RUBY", e.toString()));
    }

    private void putData(String url) {
        DatabaseReference postsRef = FirebaseDatabase.getInstance().getReference().child("posts");

        Map<String, Object> credentials = new HashMap<>();
        credentials.put("title", titleEditText.getText().toString());
        credentials.put("author", author);
        credentials.put("content", contentEditText.getText().toString());
        credentials.put("imageLink", url);

        postsRef.child(titleEditText.getText().toString()).setValue(credentials)
                .addOnSuccessListener(aVoid -> Log.d("RUBY", "WENT WELL"))
                .addOnFailureListener(e -> Log.d("RUBY", "PROBLEM"));
    }
}
