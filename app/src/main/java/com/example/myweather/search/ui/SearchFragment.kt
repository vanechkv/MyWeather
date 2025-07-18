package com.example.myweather.search.ui

import android.content.Context.INPUT_METHOD_SERVICE
import android.inputmethodservice.InputMethodService
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myweather.R
import com.example.myweather.databinding.FragmentSearchBinding
import com.example.myweather.search.domain.models.City
import com.example.myweather.search.domain.models.CityState
import com.example.myweather.search.ui.adapter.CityAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModel()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var searchEditText: EditText
    private lateinit var cancelBtn: TextView
    private lateinit var title: TextView
    private lateinit var overlay: View
    private lateinit var clearBtn: ImageView
    private lateinit var rcCity: RecyclerView
    private val cityAdapter = CityAdapter(onCityClick = {
        onCityClick(it)
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchEditText = binding.searchEditText
        cancelBtn = binding.cancelButton
        title = binding.title
        overlay = binding.overlay
        clearBtn = binding.icClear
        rcCity = binding.rcCity

        cancelBtn.setOnClickListener {
            searchEditText.setText("")
            searchEditText.clearFocus()
            val inputMethodManager =
                requireActivity().getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(binding.searchEditText.windowToken, 0)
            cityAdapter.updateItems(emptyList())
        }

        clearBtn.setOnClickListener {
            searchEditText.setText("")
            cityAdapter.updateItems(emptyList())
        }

        searchEditText.setOnFocusChangeListener { view, b ->
            when (b) {
                true -> setFocusVisible(true)
                false -> setFocusVisible(false)
            }
        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchDebounce(s?.toString().orEmpty())
                clearButtonVisible(s)
            }

            override fun afterTextChanged(s: Editable?) = Unit

        }

        searchEditText.addTextChangedListener(textWatcher)

        rcCity.layoutManager = LinearLayoutManager(requireContext())
        rcCity.adapter = cityAdapter

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun clearButtonVisible(s: CharSequence?) {
        clearBtn.isVisible = !s.isNullOrEmpty()
    }

    private fun setFocusVisible(isVisible: Boolean) {
        title.isVisible = !isVisible
        cancelBtn.isVisible = isVisible
        overlay.isVisible = isVisible
        rcCity.isVisible = isVisible
    }

    private fun render(state: CityState) {
        when(state) {
            is CityState.Content -> {
                cityAdapter.updateItems(state.city)
            }
            is CityState.Empty -> Unit
            is CityState.Error -> Unit
            CityState.Loading -> Unit
        }
    }

    private fun onCityClick(city: City) {

    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}