package com.example.codepath.moviesretrofit.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.codepath.moviesretrofit.MovieApiDB;
import com.example.codepath.moviesretrofit.R;
import com.example.codepath.moviesretrofit.rest.Movie;
import com.example.codepath.moviesretrofit.rest.Review;
import com.example.codepath.moviesretrofit.rest.Video;
import com.example.codepath.moviesretrofit.rest.ErrorApi;
import com.example.codepath.moviesretrofit.rest.MovieResponse;
import com.example.codepath.moviesretrofit.rest.VideoResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VideoReviewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VideoReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoReviewFragment extends Fragment implements MovieApiDB.MovieListener, MovieApiDB.VideoListener{

    static final String LOG_TAG = VideoReviewFragment.class.getSimpleName();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String EXTRA_MOVIE = "movie";

    Movie mMovie;

    MovieApiDB mApiDB;

    MovieFragmentAdapter mAdapter;

    @Bind(R.id.recyclerFragment_video_reviews)
    RecyclerView mRecyclerView;

    private OnFragmentInteractionListener mListener;

    public VideoReviewFragment() {
        // Required empty public constructor
    }



    // TODO: Rename and change types and number of parameters
    public static VideoReviewFragment newInstance(Movie movie) {
        VideoReviewFragment fragment = new VideoReviewFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_MOVIE, movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMovie = getArguments().getParcelable(EXTRA_MOVIE);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_video_review, null);
        ButterKnife.bind(this, view);

        mAdapter = new MovieFragmentAdapter();

        mApiDB = MovieApiDB.getInstance(getString(R.string.api_key));
        if(mMovie != null){
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
            mApiDB.requestMovieVideos(mMovie.getId(), this);

        }



        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void success(MovieResponse response) {

    }

    @Override
    public void success(VideoResponse response) {
        List<Video> trailers = response.getYoutubeTrailers();
        Log.d(LOG_TAG, "Number of trailers: " + trailers.size());
        mAdapter.setTrailers(trailers);

    }

    @Override
    public void error(ErrorApi errorApi) {
        Log.e(LOG_TAG, "Error retrieving data from API: " + errorApi.getReason());

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class MovieFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        static final int HEADER_VIEW_TYPE = 1;
        static final int TRAILER_VIEW_TYPE = 2;
        static final int REVIEW_VIEW_TYPE = 3;

        Movie mMovie;

        List<Review> reviews = new ArrayList<>();
        List<Video> trailers = new ArrayList<>();

        public void setReviews(List<Review> reviews) {
            this.reviews.clear();
            this.reviews.addAll(reviews);
            this.notifyDataSetChanged();
        }

        public void setTrailers(List<Video> trailers) {
            this.trailers.clear();
            this.trailers.addAll(trailers);
            this.notifyDataSetChanged();
        }


        public Uri getFirstTrailerUri() {
            return !trailers.isEmpty() ? trailers.get(0).getYoutubeURL() : null;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder holder = null;
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            switch (viewType) {
                case HEADER_VIEW_TYPE:
                    View headerView = inflater.inflate(R.layout.activity_movie_detail, parent, false);
                    holder = new MovieHeaderViewHolder(headerView);
                    break;
                case TRAILER_VIEW_TYPE:
                    View trailerView = inflater.inflate(R.layout.item_video, parent, false);
                    holder = new MovieTrailerViewHolder(trailerView);
                    break;
                case REVIEW_VIEW_TYPE:
                    View reviewView = inflater.inflate(R.layout.item_review, parent, false);
                    holder = new MovieReviewViewHolder(reviewView);
                    break;
            }

            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            switch (holder.getItemViewType()) {
                case HEADER_VIEW_TYPE:
                    MovieHeaderViewHolder headerViewHolder = (MovieHeaderViewHolder) holder;

                    Picasso.with(headerViewHolder.poster.getContext())
                            .load(mMovie.getPoster())
                            .placeholder(R.drawable.ic_local_movies_black_24dp)
                            .into(headerViewHolder.poster);
                    headerViewHolder.description.setText(mMovie.getDescription());
                    break;
                case TRAILER_VIEW_TYPE:
                    Video trailer = trailers.get(position - 1);
                    MovieTrailerViewHolder trailerViewHolder = (MovieTrailerViewHolder) holder;
                    trailerViewHolder.videoTitle.setText(trailer.getName());
                    break;
                case REVIEW_VIEW_TYPE:
                    Review review = reviews.get(position - trailers.size() - 1);
                    MovieReviewViewHolder reviewViewHolder = (MovieReviewViewHolder) holder;
                    reviewViewHolder.author.setText(review.getAuthor());
                    reviewViewHolder.content.setText("\"" + review.getContent() + "\"");
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return 1 + reviews.size() + trailers.size();
        }

        @Override
        public int getItemViewType(int position) {
            int viewType;

            if (mMovie == null)
                return -1;

            if (position == 0) {
                viewType = HEADER_VIEW_TYPE;
            } else if (position < trailers.size() + 1) {
                viewType =  TRAILER_VIEW_TYPE;
            } else {
                viewType =  REVIEW_VIEW_TYPE;
            }

            //Log.d(TAG, "getItemViewType - position:" + position + " type: " + viewType);
            return viewType;
        }

        class MovieHeaderViewHolder extends RecyclerView.ViewHolder {

            @Bind(R.id.backdrop)
            ImageView backdrop;

            @Bind(R.id.detail_poster)
            ImageView poster;


            @Bind(R.id.detail_description)
            TextView description;

            @Bind(R.id.detail_title)
            TextView title;

            public MovieHeaderViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

        class MovieTrailerViewHolder extends RecyclerView.ViewHolder {

            @Bind(R.id.video_name)
            TextView videoTitle;

            @Bind(R.id.view_play_button)
            ImageButton playButton;

            public MovieTrailerViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            @OnClick({R.id.view_play_button, R.id.video_name})
            public void playTrailer(View v) {
                Uri url = trailers.get(getAdapterPosition() - 1).getYoutubeURL();
                Log.d(LOG_TAG, "Play url: " + url);
                startActivity(new Intent(Intent.ACTION_VIEW, url));
            }

        }


        class MovieReviewViewHolder extends RecyclerView.ViewHolder {

            @Bind(R.id.review_author)
            TextView author;

            @Bind(R.id.review_content)
            TextView content;

            public MovieReviewViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            @OnClick(R.id.review_content)
            public void openReview() {
                Review review = reviews.get(getAdapterPosition() - trailers.size() - 1);
                Log.d(LOG_TAG, "Display complete review: " + mMovie.getTitle());
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(review.getUrl())));
            }
        }
    }


}

