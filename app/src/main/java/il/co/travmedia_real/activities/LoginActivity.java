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

import il.co.travmedia_real.R;


public class LoginActivity extends AppCompatActivity {

    // UI components
    EditText emailEditText, passwordEditText;
    Button submitButton;
    TextView transferLink;

    // User Auth
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener stateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        stateListener = firebaseAuth -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        };

        firebaseAuth = FirebaseAuth.getInstance();


        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        submitButton = findViewById(R.id.submitButton);
        transferLink = findViewById(R.id.transferPage);

        transferLink.setOnClickListener((View v) -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });


        submitButton.setOnClickListener(v ->
                signIn(emailEditText.getText().toString(), passwordEditText.getText().toString())
        );
    }

    private void signIn(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                this,
                task -> {
                    if (!task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this,
                                "Log in failed",
                                Toast.LENGTH_SHORT
                        ).show();
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
