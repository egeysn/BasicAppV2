package com.egeysn.basicappv2.presentation.satellites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.egeysn.basicappv2.R
import com.egeysn.basicappv2.common.extension.addSimpleVerticalDecoration
import com.egeysn.basicappv2.common.extension.show
import com.egeysn.basicappv2.common.utils.UiText
import com.egeysn.basicappv2.databinding.FragmentLocationsBinding
import com.egeysn.basicappv2.domain.models.SatelliteItem
import com.egeysn.basicappv2.presentation.base.BaseMVVMFragment
import com.egeysn.basicappv2.presentation.satelliteDetail.SatelliteDetailFragment
import com.egeysn.basicappv2.presentation.satellites.adapter.LocationsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationsFragment :
    BaseMVVMFragment(), LocationsAdapter.LocationsItemListener {

    private var searchAdapter: LocationsAdapter = LocationsAdapter(this)

    private lateinit var binding: FragmentLocationsBinding
    private val viewModel: LocationsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLocationsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etSearch.clearFocus()
        setUpList()
        listeners()
        setupObservers()
        viewModel.searchUser()
    }
    private fun setupObservers() {
        viewModel.getViewState().observe {
            handleStateChange(it)
        }
    }

    private fun handleStateChange(state: LocationsViewModel.SearchViewState) {
        when (state) {
            is LocationsViewModel.SearchViewState.Error -> handleError(state.error)
            LocationsViewModel.SearchViewState.Init -> Unit
            is LocationsViewModel.SearchViewState.Loading -> handleLoading(state.isLoading)
            is LocationsViewModel.SearchViewState.Success -> handleSuccess(state.data)
        }
    }

    private fun handleSuccess(data: List<SatelliteItem>) {
        binding.viewError.tvError.visibility = View.GONE
        searchAdapter.setItems(data)
    }

    private fun handleLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

    private fun handleError(error: UiText) {
        binding.viewError.tvError.show()
        searchAdapter.setItems(arrayListOf())
        binding.viewError.tvError.text = error.asString(requireContext())
    }

    private fun listeners() {
        binding.rvUsers.setOnClickListener { binding.etSearch.clearFocus() }
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideSoftKeyboard()
            }
            true
        }
        binding.etSearch.addTextChangedListener { text ->
            viewModel.searchUser(text.toString())
        }
    }

    private fun setUpList() {
        binding.rvUsers.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            addSimpleVerticalDecoration(
                16,
                includeFirstItem = true,
                includeLastItem = true,
            )
            adapter = searchAdapter
        }
    }

    private fun hideSoftKeyboard() {
        val inputMethodManager: InputMethodManager = requireActivity().getSystemService(
            AppCompatActivity.INPUT_METHOD_SERVICE,
        ) as InputMethodManager
        if (inputMethodManager.isAcceptingText) {
            inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus!!.windowToken, 0)
        }
    }

    override fun onLocationsItemClicked(id: Int, name: String) {
        findNavController().navigate(R.id.action_locationsFragment_to_satelliteDetailFragment, SatelliteDetailFragment.toBundle(name, id))
    }
}
