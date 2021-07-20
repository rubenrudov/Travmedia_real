package il.co.travmedia_real.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import il.co.travmedia_real.R;

/**
 * The launching page, from here the user can chose between login and register, if FirebaseAuth is null,
 * If the user is connected the page will be skipped all the way to the home fragment
 */
public class MainActivity extends AppCompatActivity {

    Button toRegister, toLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        toLogin = findViewById(R.id.toLogin);
        toRegister = findViewById(R.id.toRegister);

        // TODO: Auto login if the FirebaseAuth.getInstance != null

        toLogin.setOnClickListener((View v) -> {
            // Transfer the user to login page
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        });

        toRegister.setOnClickListener((View v) -> {
            // Transfer the user to register page
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
        });
    }
}
