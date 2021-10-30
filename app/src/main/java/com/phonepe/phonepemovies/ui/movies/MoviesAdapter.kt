package com.phonepe.phonepemovies.ui.movies

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.google.gson.Gson
import com.phonepe.phonepemovies.R
import com.phonepe.phonepemovies.data.entities.Movie
import com.phonepe.phonepemovies.databinding.ItemMovieBinding


/**
 * [RecyclerView.Adapter] that can display a Movies.
 */
class MoviesAdapter(
    private val activity: FragmentActivity,
    private val onMovieClicked: MoviesViewHolder.OnMovieClicked
) :
    RecyclerView.Adapter<MoviesViewHolder>() {


    private var items = ArrayList<Movie>()
    private var itemsFiltered: ArrayList<Movie> = items

    fun setItems(items: ArrayList<Movie>) {
        this.items = items
        itemsFiltered = items
        Log.d("MovieTest", "setItems:${items.size}")
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        Log.d("MovieTest", "onCreateViewHolder:")
        val binding: ItemMovieBinding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MoviesViewHolder(binding, activity,onMovieClicked)
    }

    override fun getItemCount(): Int {
        Log.d("MovieTest", "getItemCount:${items.size}")
        return itemsFiltered.size
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        Log.d("MovieTest", "onBindViewHolder:")
        holder.bind(itemsFiltered[position])
    }

    fun getFilter(): Filter? {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults? {
                val charString = charSequence.toString()
                //eventListFiltered.clear()
                itemsFiltered = if (charString.isEmpty()) {
                    items
                } else {
                    val filteredList: ArrayList<Movie> = ArrayList()
                    for (row in items) {
                        if (Gson().toJson(row).toLowerCase().contains(charString.toLowerCase())
                        ) {
                            filteredList.add(row)
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = itemsFiltered
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence?,
                filterResults: FilterResults
            ) {
                itemsFiltered = filterResults.values as ArrayList<Movie>
                // refresh the list with filtered data
                notifyDataSetChanged()
            }
        }
    }
}

class MoviesViewHolder(
    private val itemBinding: ItemMovieBinding,
    private val activity: FragmentActivity,
    private val onMovieClicked: OnMovieClicked
) : RecyclerView.ViewHolder(itemBinding.root) {

    private lateinit var movie: Movie

    fun bind(item: Movie) {
        this.movie = item
        itemBinding.title.text = item.original_title
        itemBinding.overview.text = item.overview

        itemBinding.favText.text = "0"

        itemBinding.share.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, item.overview)
            sendIntent.type = "text/plain"
            Intent.createChooser(sendIntent, "Share via")
            activity.startActivity(sendIntent)
        }


        itemBinding.likeView.setOnClickListener {

            if (itemBinding.likeView.tag == ClickState.UnClicked) {
                itemBinding.likeView.tag = ClickState.Clicked

                itemBinding.fav.setImageResource(R.drawable.ic_heart_color)
            } else {

                itemBinding.likeView.tag = ClickState.UnClicked
                itemBinding.fav.setImageResource(R.drawable.ic_heart_black)
            }
        }

//        val url = GlideUrl(item.poster_path)
//        Glide.with(itemBinding.root)
//            .load(url)
//            .override(SIZE_ORIGINAL, SIZE_ORIGINAL)
//            .transform(CircleCrop())
//            .into(itemBinding.image)

        itemBinding.root.setOnClickListener {
            onMovieClicked.onMovieClicked(item)
        }

    }

    enum class ClickState {
        Clicked,
        UnClicked
    }

    interface OnMovieClicked {
        fun onMovieClicked(movie: Movie)
    }

}

