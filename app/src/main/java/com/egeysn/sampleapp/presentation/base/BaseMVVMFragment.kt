package com.egeysn.sampleapp.presentation.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseMVVMFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.w("onCreate")
    }

    protected inline fun <T> Flow<T>.observe(crossinline function: (T) -> Unit) {
        with(viewLifecycleOwner) {
            lifecycleScope.launch {
                collect {
                    function(it)
                }
            }
        }
    }
}
