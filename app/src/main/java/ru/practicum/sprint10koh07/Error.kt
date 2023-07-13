package ru.practicum.sprint10koh07

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Error(
    val title: String
)


class ErrorViewHolder(parentView: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parentView.context)
        .inflate(R.layout.v_error, parentView, false)
) {

    private val title: TextView = itemView.findViewById(R.id.title)
    val retry: Button = itemView.findViewById(R.id.b_retry)

    fun bind(error: Error) {
        title.text = error.title
    }
}