package com.onefootball.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.onefootball.R

@BindingAdapter("loadImage")
fun bindingImage(imgView: ImageView, imgUrl: String?) {
    val options = RequestOptions()
        .error(R.mipmap.ic_launcher)

    Glide.with(imgView.context)
        .setDefaultRequestOptions(options)
        .load(imgUrl)
        .into(imgView)

}