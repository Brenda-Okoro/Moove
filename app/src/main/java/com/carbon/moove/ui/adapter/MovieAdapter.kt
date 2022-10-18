package com.carbon.moove.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.carbon.core.models.data.Movies
import com.carbon.moove.R

class MovieAdapter : ListAdapter<Movies, MovieAdapter.MovieViewHolder>(MovieDiffCallback()) {
    var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onMovieItemClick(
            itemView: View,
            position: Int,
            movie: Movies
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(
            inflater.inflate(
                R.layout.movie_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie, listener)

    }

    class MovieViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {


        val titleTv: TextView = itemView.findViewById(R.id.title)
        val ratingTv: TextView = itemView.findViewById(R.id.rating)
        val releaseDateTv: TextView = itemView.findViewById(R.id.release_date)
        val posterImage: ImageView = itemView.findViewById(R.id.posters_img)

        fun bind(movie: Movies, listener: OnItemClickListener?) {
            titleTv.text = movie.title
            releaseDateTv.text = movie.release_date
            ratingTv.text = "${movie.vote_average}"
            Glide.with(posterImage.context)
                .load(movie.posterImageUrl)
                .into(posterImage)
            itemView.setOnClickListener {
                // Triggers click upwards to the adapter on click
                if (listener != null) {
                    if (layoutPosition != RecyclerView.NO_POSITION) {
                        listener.onMovieItemClick(itemView, layoutPosition, movie)
                    }
                }
            }

        }
    }


    class MovieDiffCallback : DiffUtil.ItemCallback<Movies>() {
        override fun areItemsTheSame(oldItem: Movies, newItem: Movies): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movies, newItem: Movies): Boolean {
            return oldItem == newItem
        }
    }
}
