package com.orlanth23.popularmovie.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.orlanth23.popularmovie.R;
import com.orlanth23.popularmovie.model.Review;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewDialog extends DialogFragment {

    @BindView(R.id.dialog_text_review)
    TextView dialogTextReview;
    @BindView(R.id.dialog_author_review)
    TextView dialogAuthorReview;

    public static final String REVIEW_TEXT_ARG = "REVIEW_TEXT_ARG";
    public static final String REVIEW_AUTHOR_ARG = "REVIEW_AUTHOR_ARG";

    private String reviewText;
    private String reviewAuthor;

    public static ReviewDialog newInstance(Review review) {
        ReviewDialog reviewDialog = new ReviewDialog();
        Bundle args = new Bundle();
        args.putString(REVIEW_AUTHOR_ARG, review.getAuthor());
        args.putString(REVIEW_TEXT_ARG, review.getContent());
        reviewDialog.setArguments(args);
        return reviewDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reviewAuthor = getArguments().getString(REVIEW_AUTHOR_ARG);
        reviewText = getArguments().getString(REVIEW_TEXT_ARG);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.dialog_review, null);

        ButterKnife.bind(this, dialogView);

        dialogAuthorReview.setText(reviewAuthor);
        dialogTextReview.setText(reviewText);

        builder.setView(dialogView);
        Dialog dialog = builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
}
