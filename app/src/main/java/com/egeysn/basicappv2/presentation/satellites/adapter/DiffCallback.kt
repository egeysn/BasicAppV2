package com.egeysn.basicappv2.presentation.satellites.adapter

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.egeysn.basicappv2.domain.models.SatelliteItem

class DiffCallback(oldEmployeeList: List<SatelliteItem>, newEmployeeList: List<SatelliteItem>) :
    DiffUtil.Callback() {
    private val mOldResultList: List<SatelliteItem>
    private val mNewResultList: List<SatelliteItem>

    init {
        mOldResultList = oldEmployeeList
        mNewResultList = newEmployeeList
    }

    override fun getOldListSize(): Int {
        return mOldResultList.size
    }

    override fun getNewListSize(): Int {
        return mNewResultList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldResultList[oldItemPosition].id == mNewResultList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldResult: SatelliteItem = mOldResultList[oldItemPosition]
        val newResult: SatelliteItem = mNewResultList[newItemPosition]
        return oldResult.id == newResult.id
    }

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}
