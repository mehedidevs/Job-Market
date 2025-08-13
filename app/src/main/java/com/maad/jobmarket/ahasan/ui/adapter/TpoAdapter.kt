package com.maad.jobmarket.ahasan.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maad.jobmarket.ahasan.domain.model.Tpo
import com.maad.jobmarket.databinding.TpoCardLayoutBinding

class TpoAdapter : RecyclerView.Adapter<TpoAdapter.TpoViewHolder>() {

    private var tpoList: MutableList<Tpo> = mutableListOf()

    inner class TpoViewHolder(
        private val binding: TpoCardLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tpo: Tpo) {

            binding.tpoName.text = tpo.username
            binding.tvTpoEmail.text = tpo.email
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TpoViewHolder {
        val binding = TpoCardLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TpoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TpoViewHolder, position: Int) {
        holder.bind(tpoList[position])
    }

    override fun getItemCount(): Int = tpoList.size


    fun setData(tpoList: List<Tpo>) {
        this.tpoList = tpoList.toMutableList()
        notifyDataSetChanged()
    }
}