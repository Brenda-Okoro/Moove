package com.carbon.moove.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.carbon.core.models.data.Movies
import com.carbon.moove.R
import com.carbon.moove.ui.adapter.MovieAdapter
import com.carbon.moove.ui.viewmodel.MovieViewModel
import com.carbon.moove.util.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListActivity : AppCompatActivity(), MovieAdapter.OnItemClickListener {

    private val movieViewModel: MovieViewModel by viewModels()
    val mAdapter = MovieAdapter().apply { listener = this@ListActivity }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        val list: RecyclerView = findViewById(R.id.list)

        list.adapter = mAdapter

        setupObserver()

        val refresh = findViewById<View>(R.id.refresh)
        refresh.setOnClickListener {
            movieViewModel.pullMovies()
        }
    }

    override fun onMovieItemClick(
        itemView: View,
        position: Int,
        movie: Movies
    ) {
        val bundle = Bundle()
        bundle.putSerializable("movie", movie)
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun setupObserver() {
        val refreshBtn: Button = findViewById(R.id.refresh)
        val progressBar: ProgressBar = findViewById(R.id.progressBar)
        movieViewModel.movies.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.VISIBLE
                    refreshBtn.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.GONE
                    refreshBtn.visibility = View.INVISIBLE
                }
                Status.ERROR -> {
                    refreshBtn.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
            it.data?.let { movieList -> renderList(movieList) }
        }
    }

    private fun renderList(movies: List<Movies>?) {
        val movieList = movies?.map { movie ->
            Movies(
                movie.id,
                movie.original_title,
                movie.overview,
                movie.poster_path,
                movie.release_date,
                movie.title,
                movie.vote_average
            )
        }
        mAdapter.submitList(movieList)

    }

}
