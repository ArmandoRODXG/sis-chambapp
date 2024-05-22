package com.project.chambaapp.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.project.chambaapp.data.Entities.TrabajoItem
import com.project.chambaapp.databinding.ItemMyjobsBinding

class ListJobViewHolder (view: View) : RecyclerView.ViewHolder(view){

    val binding = ItemMyjobsBinding.bind(view)
    fun render(trabajoModel: TrabajoItem){
        binding.username.text = trabajoModel.OficioNombre
        binding.jobQuote.text = String.format("$%.2f", trabajoModel.Trabajo_precio);
        binding.workName.text = trabajoModel.Trabajo_nombre
        binding.jobDescription.text = trabajoModel.Trabajo_descripcion
    }
}