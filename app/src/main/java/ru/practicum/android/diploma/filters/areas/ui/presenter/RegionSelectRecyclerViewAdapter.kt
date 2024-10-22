package ru.practicum.android.diploma.filters.areas.ui.presenter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.AreaCardBinding
import ru.practicum.android.diploma.filters.areas.domain.models.Area

class RegionSelectRecyclerViewAdapter(
    private val clickListener: RegionSelectClickListener
) : RecyclerView.Adapter<AreaViewHolder>() {

    var list = mutableListOf<Area>()
    private var previousList = mutableListOf<Area>()
    private var previousRequest: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreaViewHolder {
        val view = LayoutInflater.from(parent.context)
        return AreaViewHolder(AreaCardBinding.inflate(view, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AreaViewHolder, position: Int) {
        val itemView = list[position]
        holder.bind(itemView)

        holder.itemView.setOnClickListener {
            clickListener.onClick(itemView)
        }
    }

    fun filterResults(request: String) {
        if (request.isNotEmpty()
            && previousRequest.isNotEmpty()
            && previousRequest.contains(request)
        ) {
            list.clear()
            list.addAll(previousList)
        }

        val filteredList = list.filter { area ->
            area.name
                .lowercase()
                .contains(request)
        }

        if (filteredList.isNotEmpty()) {
            previousList.clear()
            previousList.addAll(list)
        }
        previousRequest = request

        list.clear()
        list.addAll(filteredList)
        notifyDataSetChanged()
    }

    fun interface RegionSelectClickListener {
        fun onClick(area: Area)
    }
}
