package com.project.getexample

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.project.chambaapp.data.Entities.ContratistaItem
import com.project.chambaapp.databinding.ItemJobBinding

class JobViewHolder(view: View) : RecyclerView.ViewHolder(view)  {

    val binding = ItemJobBinding.bind(view)

    fun render(contratistaModel : ContratistaItem){
        binding.tvName.text = contratistaModel.nombre
        binding.tvUsername.text = contratistaModel.usuario
        binding.rbListJob.rating = contratistaModel.rating
    }
}