package com.phonepe.phonepemovies.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.phonepe.phonepemovies.R
import com.phonepe.phonepemovies.databinding.ActivityMovieDetailBinding

class MovieDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let {
            val title = it.getString("original_title")
            binding.title.text = title


            val releaseDateValue = it.getString("releaseDateValue")
            binding.releaseDateValue.text = releaseDateValue


            val ratingValue = it.getString("ratingValue")
            binding.ratingValue.text = ratingValue


            val popularityValue = it.getString("popularityValue")
            binding.popularityValue.text = popularityValue

            val overView = it.getString("overView")
            binding.overview.text = overView


            val image = it.getString("image")

            //Glide.with(this).load(image).into(binding.image)

        }
    }
}