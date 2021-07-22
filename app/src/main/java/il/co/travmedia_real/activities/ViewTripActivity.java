package il.co.travmedia_real.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Objects;

import il.co.travmedia_real.R;

public class ViewTripActivity extends AppCompatActivity {

    Intent data;
    TextView titleTv, contentTv;
    ImageView tripIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trip);

        data = getIntent();

        titleTv = findViewById(R.id.tripTitle);
        contentTv = findViewById(R.id.tripContent);
        tripIv = findViewById(R.id.tripIv);

        titleTv.setText(Objects.requireNonNull(data.getExtras()).getString("title"));
        contentTv.setText(Objects.requireNonNull(data.getExtras()).getString("content"));

        Uri url = Uri.parse(data.getExtras().getString("imageLink"));
        Glide.with(this).load(url).into(tripIv);
    }
}
