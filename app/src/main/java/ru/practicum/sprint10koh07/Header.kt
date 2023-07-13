package ru.practicum.sprint10koh07

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Header(
    val title: String
)


class HeaderViewHolder(parentView: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parentView.context)
        .inflate(R.layout.v_header, parentView, false)
) {

    private val title: TextView = itemView.findViewById(R.id.title)

    fun bind(header: Header) {
        title.text = header.title
    }
}