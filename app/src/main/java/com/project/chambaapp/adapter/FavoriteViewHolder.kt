package com.project.chambaapp.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.project.chambaapp.data.Entities.FavoritoItem
import com.project.chambaapp.databinding.ItemFavoriteBinding

class FavoriteViewHolder(view: View): RecyclerView.ViewHolder(view) {

    val binding = ItemFavoriteBinding.bind(view)

    fun render(favoritoModel: FavoritoItem){
        binding.username.text = favoritoModel.nombre
        binding.ratingBar.rating = favoritoModel.rating.toFloat()
    }
}