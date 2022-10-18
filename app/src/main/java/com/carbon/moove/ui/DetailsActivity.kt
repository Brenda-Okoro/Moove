package com.carbon.moove.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.carbon.core.models.data.Movies
import com.carbon.moove.R
//import com.carbon.moove.ui.viewmodel.local.MovieLocalViewModel
import com.carbon.moove.ui.viewmodel.MovieViewModel
import com.carbon.moove.util.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private val movieViewModel: MovieViewModel by viewModels()

    val titleTv: TextView by lazy { findViewById(R.id.title) }
    val ratingTv: TextView by lazy { findViewById(R.id.rating) }
    val releaseDateTv: TextView by lazy { findViewById(R.id.release_date) }
    val descriptionTv: TextView by lazy { findViewById(R.id.description) }
    val posterImg: ImageView by lazy { findViewById(R.id.posters_img) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        setupObserver()

        val intent = this.intent
        val bundle = intent.extras

        val movie: Movies = bundle!!.getSerializable("movie") as Movies? ?: return

        titleTv.text = movie.title
        releaseDateTv.text = movie.release_date
        ratingTv.text = "${movie.vote_average}"
        descriptionTv.text = movie.overview
        Glide.with(posterImg.context)
            .load(movie.posterImageUrl)
            .into(posterImg)
    }

    private fun setupObserver() {
        movieViewModel.movieDetail.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data
                }
                Status.LOADING -> {
                }
                Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}
