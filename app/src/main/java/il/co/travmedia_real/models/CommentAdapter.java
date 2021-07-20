package il.co.travmedia_real.models;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import il.co.travmedia_real.R;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentView> {
    @NonNull
    @Override
    public CommentAdapter.CommentView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CommentView holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class CommentView extends RecyclerView.ViewHolder {

        ImageView profileImg;
        TextView usernameTv;
        TextView commentTv;
        TextView timeTv;

        public CommentView(@NonNull View itemView) {
            super(itemView);
        }
    }
}
