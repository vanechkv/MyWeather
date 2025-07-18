package com.example.myweather.search.ui.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myweather.R
import com.example.myweather.databinding.ItemCityBinding
import com.example.myweather.search.domain.models.City

class CityViewHolder(
    parent: ViewGroup,
    private val onItemClick: (City) -> Unit,
    private val binding: ItemCityBinding = ItemCityBinding.bind(
        LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
    )
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(city: City) {
        binding.cityName.text = city.name
        binding.cityDescription.text = city.description
        binding.cityName.setOnClickListener {
            onItemClick(city)
        }
    }
}