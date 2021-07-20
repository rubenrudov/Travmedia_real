package il.co.travmedia_real.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import il.co.travmedia_real.R;


public class RegisterActivity extends AppCompatActivity {

    // UI components
    EditText emailEditText, usernameEditText, passwordEditText;
    Button submitButton;
    TextView transferLink;

    // User Auth
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener stateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        stateListener = firebaseAuth -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                 Intent intent = new Intent(getApplication(), HomeActivity.class);
                 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                 startActivity(intent);
                 finish();
            }
        };

        firebaseAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.emailEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        submitButton = findViewById(R.id.submitButton);
        transferLink = findViewById(R.id.transferPage);

        transferLink.setOnClickListener((View v) -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });

        submitButton.setOnClickListener((View v) -> {
            final String username = usernameEditText.getText().toString();
            final String email = emailEditText.getText().toString();
            final String password = passwordEditText.getText().toString();
            signUp(username, email, password);
        });
    }

    private void signUp(final String username, final String email, final String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()){
                    Toast.makeText(getApplication(), "Sign up error", Toast.LENGTH_SHORT).show();
                }

                else{
                    String userId = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
                    Map<String, Object> userInfo = new HashMap<>();
                    userInfo.put("email", email);
                    userInfo.put("username", username);
                    userInfo.put("password", password);
                    userInfo.put("profileImageUrl", "defaultImgUrl");
                    userRef.updateChildren(userInfo);
                }
            }
        });
    }

    // AuthStateListener adding and removing
    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(stateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(stateListener);
    }
}
