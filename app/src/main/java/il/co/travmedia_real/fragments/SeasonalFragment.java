package il.co.travmedia_real.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
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

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import il.co.travmedia_real.R;

public class SeasonalFragment extends Fragment {

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

}
