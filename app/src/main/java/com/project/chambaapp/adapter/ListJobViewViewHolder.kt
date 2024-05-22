package com.project.chambaapp.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.project.chambaapp.data.Entities.TrabajoItem
import com.project.chambaapp.databinding.ItemTheirjobsBinding

class ListJobViewViewHolder(view: View) : RecyclerView.ViewHolder(view){
    val binding = ItemTheirjobsBinding.bind(view)

    fun render(trabajoModel: TrabajoItem){
        binding.jobDescription.text = trabajoModel.Trabajo_descripcion
        binding.jobName.text = trabajoModel.Trabajo_nombre
        binding.jobPrice.text = String.format("$%.2f", trabajoModel.Trabajo_precio);

    }
}