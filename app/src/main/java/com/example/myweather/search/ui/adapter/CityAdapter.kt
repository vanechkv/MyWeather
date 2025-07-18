package com.example.myweather.search.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myweather.search.domain.models.City
import com.example.myweather.search.ui.holder.CityViewHolder

class CityAdapter(
    private val onCityClick: (City) -> Unit
) : RecyclerView.Adapter<CityViewHolder>() {

    private var city: List<City> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityViewHolder(parent, onCityClick)
    }

    override fun getItemCount(): Int {
        return city.size
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(city[position])
    }

    fun updateItems(newItems: List<City>) {
        val oldItems = city
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = oldItems.size

            override fun getNewListSize(): Int = newItems.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldItems[oldItemPosition].uri == newItems[newItemPosition].uri
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldItems[oldItemPosition] == newItems[newItemPosition]
            }

        }).dispatchUpdatesTo(this)

        city = newItems
    }
}