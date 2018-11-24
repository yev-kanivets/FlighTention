package fr2018.defense.innovation.forum.flightention.view

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import fr2018.defense.innovation.forum.flightention.R
import fr2018.defense.innovation.forum.flightention.model.Flight
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection

class ExpandableFligtsSection(
    private val sectionAdapter: SectionedRecyclerViewAdapter,
    private val title: String,
    private var flights: List<Flight>
) : StatelessSection(
    SectionParameters.builder()
        .itemResourceId(R.layout.section_flights_item)
        .headerResourceId(R.layout.section_flights_header)
        .build()
) {

    private var expanded: Boolean = true

    override fun getContentItemsTotal(): Int = if (expanded) flights.size else 0

    override fun getHeaderViewHolder(view: View): ViewHolder {
        val headerViewHolder = HeaderViewHolder(view)

        headerViewHolder.itemView.setOnClickListener {
            expanded = !expanded
            headerViewHolder.ivArrow.setImageResource(
                if (expanded) R.drawable.ic_chevron_up else R.drawable.ic_chevron_down
            )
            sectionAdapter.notifyDataSetChanged()
        }

        return headerViewHolder
    }

    override fun onBindHeaderViewHolder(holder: ViewHolder) {
        val headerViewHolder = holder as HeaderViewHolder
        headerViewHolder.tvTitle.text = title
    }

    override fun onBindItemViewHolder(holder: ViewHolder, position: Int) {
        val itemViewHolder = holder as ItemViewHolder
        itemViewHolder.textView.text = flights[position].callSign
    }

    override fun getItemViewHolder(view: View): ViewHolder = ItemViewHolder(view)

    fun updateFlights(flights: List<Flight>) {
        this.flights = flights
        sectionAdapter.notifyDataSetChanged()
    }

    private class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val ivArrow: ImageView = itemView.findViewById(R.id.ivArrow)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)

    }

    private class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textView: TextView = itemView.findViewById(R.id.tvTitle)

    }

}
