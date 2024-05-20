package com.project.getexample

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.project.chambaapp.api_services.CoordinatesManager
import com.project.chambaapp.data.Entities.ContratistaItem
import com.project.chambaapp.databinding.ItemJobBinding

class JobViewHolder(view: View) : RecyclerView.ViewHolder(view)  {

    val binding = ItemJobBinding.bind(view)

    fun render(contratistaModel : ContratistaItem){
        binding.tvName.text = contratistaModel.nombre
        binding.tvUsername.text = contratistaModel.usuario
        binding.rbListJob.rating = contratistaModel.rating
        binding.distanciaTextView.text = contratistaModel.distancia?.let {
            CoordinatesManager.distanceCategory(it)
        }
        binding.textDisponibilidad.text = contratistaModel.disponibilidad?.let{
            getAvailabilityCode(it)
        }
    }

    fun getAvailabilityCode(code: Int) : String{
        return when(code){
            0-> "No Disponible"
            1-> "Disponible"
            2-> "En Servicio"
            else-> "En Servicio y Disponible"
        }
    }
}