package com.egeysn.basicappv2.presentation.locations.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.egeysn.basicappv2.common.extension.safeGet
import com.egeysn.basicappv2.databinding.RecyclerItemLocationsBinding
import com.egeysn.basicappv2.domain.models.LocationItem

class LocationsAdapter(
    private val listener: LocationsItemListener,
) : RecyclerView.Adapter<LocationsAdapter.ViewHolder>() {

    private val data = ArrayList<LocationItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerItemLocationsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return ViewHolder(binding, listener)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<LocationItem>) {
        val diffCallback = DiffCallback(data, items)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.data.clear()
        this.data.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        with(holder) { bind(data[position]) }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(
        private val binding: RecyclerItemLocationsBinding,
        private val listener: LocationsItemListener,
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private lateinit var result: LocationItem

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(item: LocationItem) {
            this.result = item
            binding.tvName.text = item.name.safeGet()
            binding.tvStatus.text = item.active.toString()
        }

        override fun onClick(v: View?) = listener.onLocationsItemClicked(data[layoutPosition].id)
    }

    interface LocationsItemListener {
        fun onLocationsItemClicked(id: Int)
    }
}
