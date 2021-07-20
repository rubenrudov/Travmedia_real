package il.co.travmedia_real.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Map;
import java.util.Objects;

import il.co.travmedia_real.R;

public class ProfileSettingsActivity extends AppCompatActivity {

    Intent act;
    TextView username;
    ImageView profileImage;
    Button uploadNewButton, submitButton, submitUname;
    EditText newUsername;
    String userId;
    private Uri picturePath;
    private String pictureUrl;
    private String[] permissionsArray =  new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
    private int requestCode = 12;
    private int validationErrorCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);

        Objects.requireNonNull(getSupportActionBar()).hide();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissionsArray, requestCode);
        }

        else {
            Log.d("RUBY", "Granted");
        }

        userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        act = getIntent();
        username = findViewById(R.id.usernameTextView);
        username.setText(Objects.requireNonNull(act.getExtras()).getString("username"));

        uploadNewButton = findViewById(R.id.uploadNewButton);
        uploadNewButton.setOnClickListener(v -> {
            pickPicture();
        });

        submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(v -> {
            uploadPictureToStorage();
        });

        newUsername = findViewById(R.id.newUsername);
        submitUname = findViewById(R.id.submitUname);
        submitUname.setOnClickListener(v -> {
            saveUsername();
        });

        profileImage = findViewById(R.id.profileImage);
        getProfileImage();
    }

    private void saveUsername() {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("username");
        userRef.setValue(newUsername.getText().toString());
        username.setText(newUsername.getText().toString());
        newUsername.setText("");

        // TODO: Save username in prev posts too.
    }

    private void getProfileImage() {
        Log.d("User", userId);
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("profileImageUrl");

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("ValueSnap", (String) Objects.requireNonNull(snapshot.getValue()));
                if (!Objects.equals(snapshot.getValue(), "defaultImgUrl"))
                    Glide.with(getApplicationContext()).load(snapshot.getValue()).override(200, 200).into(profileImage);
                else
                    Glide.with(getApplicationContext()).load(R.mipmap.ic_launcher).override(200, 200).into(profileImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void pickPicture() {
        Intent selection = new Intent(Intent.ACTION_PICK);
        selection.setType("image/*");
        startActivityForResult(selection, requestCode + 1);
        // someActivityResultLauncher.launch(selection);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;

        if (resultCode == RESULT_OK) {
            Log.d("RUBY", Objects.requireNonNull(data.getData()).toString());
            picturePath = data.getData();
            profileImage.setImageURI(picturePath);
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
            pictureUrl = getDownloadUri.getResult().toString();
            Log.d("RUBY", pictureUrl);
            putData(pictureUrl);
        }).addOnFailureListener(e -> Log.d("RUBY", e.toString()));
    }

    private void putData(String url) {
        FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("profileImageUrl").setValue(url)
                .addOnSuccessListener(aVoid -> Log.d("RUBY", "WENT WELL"))
                .addOnFailureListener(e -> Log.d("RUBY", "PROBLEM"));
    }
}
