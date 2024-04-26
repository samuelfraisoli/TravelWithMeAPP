package com.example.travelwithmeapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.travelwithmeapp.R
import com.example.travelwithmeapp.models.Plan

class PlanificarFavoritosAdapter (private val context: Context, private val listaPlanesFav: ArrayList<Plan>) :
    RecyclerView.Adapter<PlanificarFavoritosAdapter.PlanViewHolder>(){

    inner class PlanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Parseo datos recyclerview
        val horaTextView: TextView = itemView.findViewById(R.id.text_hora_plan_fav) // datos texto viewholder
        val nombreTextView: TextView = itemView.findViewById(R.id.text_nombre_plan_fav)
        val descripcionTextView: TextView = itemView.findViewById(R.id.text_descripcion_plan_fav)
        val precioTextView: TextView = itemView.findViewById(R.id.text_precio_plan_fav)

        // Gestión de visibilidad y de pulsación de icono_fav del fragment planes
        val iconoRellenoFav: ImageView = itemView.findViewById(R.id.icono_relleno_fav)
        val iconoVacioFav: ImageView = itemView.findViewById(R.id.icono_vacio_fav)

        init {
            iconoRellenoFav.setOnClickListener {
                iconoRellenoFav.visibility = View.INVISIBLE
                iconoVacioFav.visibility = View.VISIBLE

                val plan = listaPlanesFav[adapterPosition]
                onFavoritoClickListener?.onFavoritoClick(plan)
            }
            iconoVacioFav.setOnClickListener {
                iconoVacioFav.visibility = View.INVISIBLE
                iconoRellenoFav.visibility = View.VISIBLE

                val plan = listaPlanesFav[adapterPosition]
                onFavoritoClickListener?.onFavoritoClick(plan)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.viewholder_planificar_favoritos, parent, false)
        return PlanViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PlanViewHolder, position: Int) {
        val currentPlan = listaPlanesFav[position]
        holder.horaTextView.text = currentPlan.horaPlan
        holder.nombreTextView.text = currentPlan.nombrePlan
        holder.descripcionTextView.text = currentPlan.descripcionPlan
    }

    override fun getItemCount(): Int {
        return listaPlanesFav.size
    }

    interface OnFavoritoClickListener {
        fun onFavoritoClick(plan: Plan)
    }

    var onFavoritoClickListener: PlanificarAdapter.OnFavoritoClickListener? = null
}