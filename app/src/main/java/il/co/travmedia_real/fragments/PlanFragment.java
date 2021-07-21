package il.co.travmedia_real.fragments;

import android.annotation.SuppressLint;
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

import il.co.travmedia_real.R;

public class PlanFragment extends Fragment {
    public PlanFragment() {

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
        menu.findItem(R.id.newPost).setVisible(false);
        menu.findItem(R.id.searchPost).setVisible(false);
        menu.findItem(R.id.signOut).setVisible(false);
        menu.findItem(R.id.profileSettings).setVisible(true);
        menu.findItem(R.id.aboutUs).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.profileSettings) {

        }


        return super.onOptionsItemSelected(item);
    }
}
