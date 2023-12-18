package com.fernando.camara_dos_deputados_fg.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fernando.camara_dos_deputados_fg.databinding.ViewHolderDeputadoBinding
import com.fernando.camara_dos_deputados_fg.interfaces.OnClickAdapterItemListener
import com.fernando.camara_dos_deputados_fg.models.Deputado
import com.fernando.camara_dos_deputados_fg.models.Partido

class DeputadoAdapter(private val deputados: List<Deputado>) : RecyclerView.Adapter<DeputadoAdapter.DeputadoViewHolder>() {
    private lateinit var onClickAdapterItemListener: (deputado: Deputado) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeputadoViewHolder {
        val binding = ViewHolderDeputadoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeputadoViewHolder(binding)
    }

    override fun getItemCount(): Int = deputados.size

    override fun onBindViewHolder(viewHolder: DeputadoViewHolder, position: Int) {
        val deputado = deputados[position]
        viewHolder.bind(deputado)

    }

    fun setOnClickAdapterItemListener(onClickAdapterItemListener: (deputado: Deputado) -> Unit) {
        this.onClickAdapterItemListener = onClickAdapterItemListener
    }


    inner class DeputadoViewHolder(private val binding : ViewHolderDeputadoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(deputado: Deputado) {
            binding.apply {
                deputadoNomeTextView.text = deputado.nome
                deputadoSiglaTextView.text = deputado.siglaPartido
                Glide.with(deputadoPhotoImageView.context)
                    .load(deputado.urlFoto)
                    .into(deputadoPhotoImageView)
                root.setOnClickListener {
                    onClickAdapterItemListener(deputado)
                }
            }
        }
    }

}