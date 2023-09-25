package com.egeysn.basicappv2.presentation.newDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.egeysn.basicappv2.common.extension.hide
import com.egeysn.basicappv2.common.extension.safeGet
import com.egeysn.basicappv2.common.extension.show
import com.egeysn.basicappv2.common.utils.UiText
import com.egeysn.basicappv2.data.remote.models.satellitedetail.SatelliteDetailDto
import com.egeysn.basicappv2.databinding.FragmentSatelliteDetailBinding
import com.egeysn.basicappv2.presentation.base.BaseMVVMFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SatelliteDetailFragment : BaseMVVMFragment() {
    companion object {
        const val ID = "id"
        const val NAME = "name"

        fun toBundle(name: String, id: Int): Bundle {
            return bundleOf(ID to id, NAME to name)
        }
    }

    private lateinit var binding: FragmentSatelliteDetailBinding
    private val viewModel: SatelliteDetailViewModel by activityViewModels()

    private val name get() = arguments?.getString(NAME).safeGet()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSatelliteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        val id = arguments?.getInt(ID)
        viewModel.getNewDetail(id ?: 0)
    }

    private fun setupObservers() {
        viewModel.newDetailState.observe { handleSatelliteDetailStateChange(it) }
    }
    private fun handleSatelliteDetailStateChange(state: SatelliteDetailViewModel.NewDetailViewState) {
        when (state) {
            is SatelliteDetailViewModel.NewDetailViewState.Error -> handleError(state.error)
            SatelliteDetailViewModel.NewDetailViewState.Init -> Unit
            is SatelliteDetailViewModel.NewDetailViewState.Loading -> handleLoading(state.isLoading)
            is SatelliteDetailViewModel.NewDetailViewState.Success -> handleSuccess(state.data)
        }
    }

    private fun handleError(error: UiText) {
        Toast.makeText(requireContext(), error.asString(requireContext()), Toast.LENGTH_SHORT)
            .show()
        binding.bodySatelliteDetail.hide()
        binding.viewError.tvError.apply {
            show()
            text = error.asString(requireContext())
        }
    }

    private fun handleSuccess(data: SatelliteDetailDto) {
        binding.apply {
            bodySatelliteDetail.show()
            viewError.tvError.hide()
            tvName.text = name
            tvDate.text = data.firstFlight
            tvHeightMass.text = "${data.height}/${data.mass}"
            tvCost.text = data.costPerLaunch.toString()
        }
    }

    private fun handleLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }
}
