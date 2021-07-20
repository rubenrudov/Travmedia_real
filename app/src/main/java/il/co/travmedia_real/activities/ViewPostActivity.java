package il.co.travmedia_real.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import il.co.travmedia_real.R;

public class ViewPostActivity extends AppCompatActivity {

    Intent data;
    TextView titleTv, authorTv, contentTv;
    ImageView postImage;
    CircleImageView civ;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        data = getIntent();

        titleTv = findViewById(R.id.titleTv);
        authorTv = findViewById(R.id.authorTv);
        contentTv = findViewById(R.id.contentTv);
        postImage = findViewById(R.id.postImage);
        civ = findViewById(R.id.civProfile);

        titleTv.setText(Objects.requireNonNull(data.getExtras()).getString("title"));
        authorTv.setText(data.getExtras().getString("author"));
        contentTv.setText(data.getExtras().getString("content"));

        Glide.with(getApplicationContext()).load(data.getExtras().getString("imageLink")).into(postImage);

        if (data.getExtras().get("profileImage").equals("defaultImgUrl")) {
            Glide.with(getApplicationContext()).load(R.mipmap.ic_launcher).into(civ);
        }

        else
            Glide.with(getApplicationContext()).load(data.getExtras().get("profileImage")).into(civ);

    }
}
