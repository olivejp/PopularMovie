package com.orlanth23.popularmovie.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orlanth23.popularmovie.R;
import com.orlanth23.popularmovie.dialog.ReviewDialog;
import com.orlanth23.popularmovie.model.Review;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private static final String TAG = ReviewAdapter.class.getSimpleName();

    private ArrayList<Review> reviews;
    private Activity context;

    public ReviewAdapter(Activity p_context, ArrayList<Review> p_reviews) {
        context = p_context;
        reviews = p_reviews;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.cardview_review, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Review review = reviews.get(position);
        holder.reviewAuthor.setText(review.getAuthor());
        holder.reviewContent.setText(review.getContent());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReviewDialog reviewDialog = ReviewDialog.newInstance(review);
                reviewDialog.show(context.getFragmentManager(), TAG);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.review_author) TextView reviewAuthor;
        @BindView(R.id.review_content) TextView reviewContent;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
