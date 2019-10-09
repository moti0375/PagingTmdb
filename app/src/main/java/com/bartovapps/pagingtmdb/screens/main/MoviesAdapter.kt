package com.bartovapps.pagingtmdb.screens.main

import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bartovapps.pagingtmdb.R
import com.bartovapps.pagingtmdb.network.ApiService
import com.bartovapps.pagingtmdb.network.model.response.Movie
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.movie_item.view.*
import timber.log.Timber

class MoviesAdapter(val adapterClickListener: AdapterClickListener) : ListAdapter<Movie, RecyclerView.ViewHolder>(MovieDiffUtilCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MoviesViewHolder).bind(getItem(position))
    }



    inner class MoviesViewHolder(private val viewItem : View) : RecyclerView.ViewHolder(viewItem){
        private val options = RequestOptions().apply(
            RequestOptions.placeholderOf(ActivityCompat.getDrawable(viewItem.context, R.drawable.loading_drawable))
        ).apply(
            RequestOptions.fitCenterTransform()
        )
        fun bind(item : Movie?){
            if(item != null){
                val path = "https://${ApiService.TMDB_IMAGE_AUTHORITY}${item.posterPath}"
                Timber.i("Image Uri: $path" )
                Glide.with(itemView.context).
                    load(path).
                    apply(options).
                    into(itemView.movie_image)
                itemView.movie_title.text = item.title
                itemView.movie_date.text = item.releaseDate

                viewItem.setOnClickListener {
                    adapterClickListener.onItemClicked(item.id)
                }
            }
        }
    }

     class MovieDiffUtilCallback : DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldMovie: Movie, newMovie: Movie): Boolean {
            return oldMovie.id == newMovie.id
        }

        override fun areContentsTheSame(oldMovie:  Movie, newMovie: Movie): Boolean {
            return oldMovie == newMovie
        }
    }


    interface AdapterClickListener{
        fun onItemClicked(id: Int)
    }
}