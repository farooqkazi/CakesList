package com.assignment.cakeslist.presentation.cakelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.assignment.cakeslist.R
import com.assignment.cakeslist.data.entity.Cake
import com.assignment.cakeslist.databinding.CakeItemLayoutBinding
import com.bumptech.glide.Glide


class CakeAdapter(private val onClickClb: (Cake) -> Unit) :
    ListAdapter<Cake, CakeAdapter.VH>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH.create(parent)

    override fun onBindViewHolder(holder: VH, position: Int) {
        val cake = getItem(position)
        holder.bind(cake)
        holder.itemView.setOnClickListener {
            onClickClb.invoke(cake)
        }
    }

    class VH(private val cakeItemBinding: CakeItemLayoutBinding) :
        RecyclerView.ViewHolder(cakeItemBinding.root) {

        companion object {
            fun create(parent: ViewGroup): VH {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CakeItemLayoutBinding.inflate(layoutInflater, parent, false)
                return VH(binding)
            }

        }

        fun bind(cake: Cake) {
            cakeItemBinding.textTitle.text = cake.title
            Glide.with(cakeItemBinding.imageCake).load(cake.image)
                .error(R.drawable.image_placeholder).into(cakeItemBinding.imageCake)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Cake>() {
        override fun areItemsTheSame(oldItem: Cake, newItem: Cake) =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: Cake, newItem: Cake) = oldItem == newItem
    }
}


