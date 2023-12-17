package com.fernando.camara_dos_deputados_fg.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fernando.camara_dos_deputados_fg.databinding.ViewHolderCardSkeletonBinding

class CardSkeletonAdapter : RecyclerView.Adapter<CardSkeletonAdapter.CardSkeletonViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardSkeletonViewHolder {
        val binding = ViewHolderCardSkeletonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CardSkeletonViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: CardSkeletonViewHolder, position: Int) {

    }
    inner class CardSkeletonViewHolder(binding: ViewHolderCardSkeletonBinding) : RecyclerView.ViewHolder(binding.root)
}