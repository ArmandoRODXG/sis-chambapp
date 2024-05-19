package com.project.chambaapp.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.project.chambaapp.data.Entities.ResenaItem
import com.project.chambaapp.databinding.ItemReviewBinding

class ReviewViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val binding = ItemReviewBinding.bind(view)

    fun render(resenaItem: ResenaItem ){
        binding.tvReviewDescription.text = resenaItem.Descripcion
        binding.tvDateReview.text = resenaItem.Fecha
        binding.rbReview.rating = resenaItem.Valoracion.toFloat()
    }
}