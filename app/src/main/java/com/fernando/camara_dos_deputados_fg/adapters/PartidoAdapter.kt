package com.fernando.camara_dos_deputados_fg.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fernando.camara_dos_deputados_fg.databinding.ViewHolderPartidoBinding
import com.fernando.camara_dos_deputados_fg.models.Partido

class PartidoAdapter(private val partidos: List<Partido>) : RecyclerView.Adapter<PartidoAdapter.PartidoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartidoViewHolder {
        val binding = ViewHolderPartidoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PartidoViewHolder(binding)
    }

    override fun getItemCount(): Int = partidos.size

    override fun onBindViewHolder(viewHolder: PartidoViewHolder, position: Int) {
        val partido = partidos[position]
        viewHolder.bind(partido)
    }

    inner class PartidoViewHolder(private val binding: ViewHolderPartidoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(partido: Partido) {
            binding.apply {
                partidoNomeTextView.text = partido.nome
                partidoSiglaTextView.text = partido.sigla
            }
        }
    }


}