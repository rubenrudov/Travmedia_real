package il.co.travmedia_real.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.Objects;

import il.co.travmedia_real.R;
import il.co.travmedia_real.activities.ProfileSettingsActivity;
import il.co.travmedia_real.activities.ViewTripActivity;
import il.co.travmedia_real.models.Trips;

public class SeasonalFragment extends Fragment {

    private TextView textView;
    private Trips trips;

    private ImageView firstImg, secondImg, thirdImg;
    private TextView firstTv, secondTv, thirdTv;
    private ConstraintLayout first, second, third;

    private static final String[] seasons = {
            "winter", "winter", "spring", "spring", "summer", "summer",
            "summer", "summer", "fall", "fall", "winter", "winter"
    };

    private String getSeason(Date date) {
        return seasons[ date.getMonth() - 1];
    }

    public SeasonalFragment() {

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
        View view = layoutInflater.inflate(R.layout.fragment_seasonal, viewGroup, false);
        textView = view.findViewById(R.id.text);
        textView.setVisibility(View.GONE);
        findUserByUid();

        trips = new Trips(getSeason(new Date()));

        first = view.findViewById(R.id.first);

        firstTv = view.findViewById(R.id.tripTitle);
        firstImg = first.findViewById(R.id.tripImage);

        firstTv.setText(trips.getTrips()[0].getTitle());
        Glide.with(requireContext()).load(trips.getTrips()[0].getImageLink()).override(Resources.getSystem().getDisplayMetrics().widthPixels).into(firstImg);

        first.setOnClickListener(v -> {
            Intent firstOption = new Intent(getActivity(), ViewTripActivity.class);
            firstOption.putExtra("title", trips.getTrips()[0].getTitle());
            firstOption.putExtra("content", trips.getTrips()[0].getDescription());
            firstOption.putExtra("imageLink", trips.getTrips()[0].getImageLink());
            startActivity(firstOption);
        });

        second = view.findViewById(R.id.second);

        secondTv = second.findViewById(R.id.tripTitle);
        secondImg = second.findViewById(R.id.tripImage);

        secondTv.setText(trips.getTrips()[1].getTitle());
        Glide.with(requireContext()).load(trips.getTrips()[1].getImageLink()).override(Resources.getSystem().getDisplayMetrics().widthPixels).into(secondImg);

        second.setOnClickListener(v -> {
            Intent firstOption = new Intent(getActivity(), ViewTripActivity.class);
            firstOption.putExtra("title", trips.getTrips()[1].getTitle());
            firstOption.putExtra("content", trips.getTrips()[1].getDescription());
            firstOption.putExtra("imageLink", trips.getTrips()[1].getImageLink());
            startActivity(firstOption);
        });

        third = view.findViewById(R.id.third);
        thirdTv = third.findViewById(R.id.tripTitle);
        thirdImg = third.findViewById(R.id.tripImage);

        thirdTv.setText(trips.getTrips()[2].getTitle());
        Glide.with(requireContext()).load(trips.getTrips()[2].getImageLink()).override(Resources.getSystem().getDisplayMetrics().widthPixels).into(thirdImg);

        third.setOnClickListener(v -> {
            Intent firstOption = new Intent(getActivity(), ViewTripActivity.class);
            firstOption.putExtra("title", trips.getTrips()[2].getTitle());
            firstOption.putExtra("content", trips.getTrips()[2].getDescription());
            firstOption.putExtra("imageLink", trips.getTrips()[2].getImageLink());
            startActivity(firstOption);
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.options_menu, menu);
        menu.findItem(R.id.searchPost).setVisible(false);
        menu.findItem(R.id.newPost).setVisible(false);
        menu.findItem(R.id.profileSettings).setVisible(true);
        menu.findItem(R.id.signOut).setVisible(true);
        menu.findItem(R.id.aboutUs).setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.profileSettings) {
            Intent intent = new Intent(getActivity(), ProfileSettingsActivity.class);
            intent.putExtra("username", textView.getText().toString().replace("Welcome ", ""));
            startActivity(intent);
        }

        else if (item.getItemId() == R.id.signOut) {
            FirebaseAuth.getInstance().signOut();
            requireActivity().finish();
        }


        else if (item.getItemId() == R.id.aboutUs) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://github.com/Travmedia-il/Travmedia-il"));
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
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
}
