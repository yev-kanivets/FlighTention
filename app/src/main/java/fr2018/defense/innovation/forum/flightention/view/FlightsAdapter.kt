package fr2018.defense.innovation.forum.flightention.view

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView

class FlightsAdapter : RecyclerView.Adapter<FlightsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TextView(parent.context))
    }

    override fun getItemCount(): Int = 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

}
