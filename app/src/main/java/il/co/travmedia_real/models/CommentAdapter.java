package il.co.travmedia_real.models;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import il.co.travmedia_real.R;
import il.co.travmedia_real.activities.LoginActivity;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentView> {

    ArrayList<Comment> comments;
    Context context;

    public CommentAdapter(ArrayList<Comment> comments,  Context context) {
        this.comments = comments;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentAdapter.CommentView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommentAdapter.CommentView(LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CommentView holder, int position) {
        Log.d("COMMENT", comments.get(position).toString());
        Glide.with(context).load(comments.get(position).getUserImageLink()).into(holder.profileImg);
        holder.usernameTv.setText(comments.get(position).getUsername());
        holder.commentTv.setText(comments.get(position).getContent());
        holder.timeTv.setText(timestampToString((Long)comments.get(position).getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return this.comments.size();
    }

    static class CommentView extends RecyclerView.ViewHolder {

        ImageView profileImg;
        TextView usernameTv;
        TextView commentTv;
        TextView timeTv;

        CommentView(@NonNull View itemView) {
            super(itemView);
            this.profileImg = itemView.findViewById(R.id.civProfile);
            this.usernameTv = itemView.findViewById(R.id.authorTv);
            this.commentTv = itemView.findViewById(R.id.commentContent);
            this.timeTv = itemView.findViewById(R.id.timeTv);
        }
    }

    private String timestampToString(long time) {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        return DateFormat.format("hh:mm",calendar).toString();
    }
}
