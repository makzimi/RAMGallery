package com.makzimi.ramgallery.gallery.ui

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.makzimi.ramgallery.databinding.ItemGalleryBinding
import com.makzimi.ramgallery.model.CharacterEntity

class GalleryViewHolder(private val binding: ItemGalleryBinding):
    RecyclerView.ViewHolder(binding.root) {

    fun bind(characterEntity: CharacterEntity) {
        Glide.with(this.itemView).load(characterEntity.image).into(binding.uiImage)
        binding.uiName.text = characterEntity.name
    }
}