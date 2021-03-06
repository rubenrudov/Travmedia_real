package il.co.travmedia_real.fragments;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.Objects;

import il.co.travmedia_real.R;



public class TrackFragment extends Fragment {

    public TrackFragment() {

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
        View view = layoutInflater.inflate(R.layout.fragment_plan, viewGroup, false);


        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.options_menu, menu);
        menu.findItem(R.id.newPost).setVisible(true);
        menu.findItem(R.id.searchPost).setVisible(false);
        menu.findItem(R.id.signOut).setVisible(false);
        menu.findItem(R.id.profileSettings).setVisible(false);
        menu.findItem(R.id.aboutUs).setVisible(true);
    }

    /**
     * TODO:
     *  1) Add google map
     *  2) Add save button
     *  3) Save city to database by last location
     *  4) Refresh saved markers
     */
}
