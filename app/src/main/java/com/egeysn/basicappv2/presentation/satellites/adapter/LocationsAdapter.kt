package com.egeysn.basicappv2.presentation.satellites.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.egeysn.basicappv2.R
import com.egeysn.basicappv2.common.extension.safeGet
import com.egeysn.basicappv2.databinding.RecyclerItemLocationsBinding
import com.egeysn.basicappv2.domain.models.SatelliteItem

class LocationsAdapter(
    private val listener: LocationsItemListener,
) : RecyclerView.Adapter<LocationsAdapter.ViewHolder>() {

    private val data = ArrayList<SatelliteItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerItemLocationsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return ViewHolder(binding, listener)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<SatelliteItem>) {
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

        private lateinit var result: SatelliteItem

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(item: SatelliteItem) {
            this.result = item
            binding.tvName.text = item.name.safeGet()
            if (item.active) {
                binding.tvStatus.text = binding.root.context.getString(R.string.active)
                binding.tvStatus.typeface =
                    ResourcesCompat.getFont(binding.root.context, R.font.texta_bold)
                binding.tvStatus.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.white
                    )
                )
                binding.tvName.typeface =
                    ResourcesCompat.getFont(binding.root.context, R.font.texta_bold)
                binding.tvName.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.white
                    )
                )
            } else {
                binding.tvStatus.text = binding.root.context.getString(R.string.passive)
                binding.tvStatus.typeface =
                    ResourcesCompat.getFont(binding.root.context, R.font.texta_regular)
                binding.tvStatus.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.white_50
                    )
                )
                binding.tvName.typeface =
                    ResourcesCompat.getFont(binding.root.context, R.font.texta_regular)
                binding.tvName.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.white_50
                    )
                )
            }
            val resource = if (item.active) R.drawable.ic_circle_green else R.drawable.ic_circle_red
            binding.ivStatus.setImageResource(resource)
        }

        override fun onClick(v: View?) = listener.onLocationsItemClicked(data[layoutPosition].id, data[layoutPosition].name)
    }

    interface LocationsItemListener {
        fun onLocationsItemClicked(id: Int, name: String)
    }
}
