package ru.practicum.android.diploma.filters.ui.presenter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.AreaCardBinding
import ru.practicum.android.diploma.filters.areas.domain.models.Area

class AreasRecyclerViewAdapter(
    private val clickListener: AreaClickListener
) : RecyclerView.Adapter<AreasViewHolder>() {

    var list = mutableListOf<Area>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreasViewHolder {
        val view = LayoutInflater.from(parent.context)
        return AreasViewHolder(AreaCardBinding.inflate(view, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AreasViewHolder, position: Int) {
        val itemView = list[position]
        holder.bind(itemView)

        holder.itemView.setOnClickListener {
            clickListener.onClick(itemView)
        }
    }

    fun interface AreaClickListener {
        fun onClick(area: Area)
    }
}
