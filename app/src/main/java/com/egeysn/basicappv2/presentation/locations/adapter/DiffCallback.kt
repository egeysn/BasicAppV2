package com.egeysn.basicappv2.presentation.locations.adapter

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.egeysn.basicappv2.domain.models.LocationItem

class DiffCallback(oldEmployeeList: List<LocationItem>, newEmployeeList: List<LocationItem>) :
    DiffUtil.Callback() {
    private val mOldResultList: List<LocationItem>
    private val mNewResultList: List<LocationItem>

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
        val oldResult: LocationItem = mOldResultList[oldItemPosition]
        val newResult: LocationItem = mNewResultList[newItemPosition]
        return oldResult.id == newResult.id
    }

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}
