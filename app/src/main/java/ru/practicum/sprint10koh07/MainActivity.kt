package ru.practicum.sprint10koh07

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.IllegalStateException
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val data: List<Any> = listOf(
            listOf(Header(title = "Заголовок 1")),
            (1..3).map {
                ListElement.createRandomElement(id = it.toString())
            },
            listOf(Header(title = "Заголовок 2")),
            (1..1).map {
                ListElement.createRandomElement(id = it.toString())
            },
            listOf(Header(title = "Заголовок 3")),
            (1..5).map {
                ListElement.createRandomElement(id = it.toString())
            },
        ).flatten()
        val dataItems: List<Any> = if (Random.nextBoolean()) {
             data
        } else {
            listOf(
                Error(
                    title = "Проблема с сетью"
                )
            )
        }


        val adapter = MainActivityAdapter().apply {
            items = dataItems
            onListElementClickListener = OnListElementClickListener { item ->
                Toast.makeText(this@MainActivity, "Click on ${item.name}", Toast.LENGTH_SHORT)
                    .show()
                val intent = Intent(this@MainActivity, MainActivity::class.java)
                intent.putExtra("item_id", item.id)
                startActivity(intent)
            }
            onRetryClickListener = {
                items = data
                notifyDataSetChanged()
            }
        }

        val itemsRv: RecyclerView = findViewById(R.id.items_rv)
        itemsRv.layoutManager = LinearLayoutManager(this)
        itemsRv.adapter = adapter
    }

}

class MainActivityAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: List<Any> = emptyList()
    var onListElementClickListener: OnListElementClickListener? = null
    var onRetryClickListener: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_LIST_ELEMENT -> ListElementViewHolder(parent)
            VIEW_TYPE_ERROR -> ErrorViewHolder(parent)
            VIEW_TYPE_HEADER -> HeaderViewHolder(parent)
            else -> throw IllegalStateException("There is no ViewHolder for viewType $viewType")
        }

    }

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        return when (item) {
            is ListElement -> VIEW_TYPE_LIST_ELEMENT
            is Error -> VIEW_TYPE_ERROR
            is Header -> VIEW_TYPE_HEADER
            else -> 0
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ListElementViewHolder -> {
                val listElement = items[position] as ListElement
                holder.bind(listElement)
                holder.itemView.setOnClickListener {
                    onListElementClickListener?.onListElementClick(listElement)
                }
            }

            is ErrorViewHolder -> {
                holder.bind(items[position] as Error)
                holder.retry.setOnClickListener {
                    onRetryClickListener?.invoke()
                }
            }

            is HeaderViewHolder -> {
                holder.bind(items[position] as Header)

            }
        }

    }

    companion object {
        const val VIEW_TYPE_LIST_ELEMENT = 1
        const val VIEW_TYPE_ERROR = 2
        const val VIEW_TYPE_HEADER = 3

    }

}

fun interface OnListElementClickListener {
    fun onListElementClick(item: ListElement)

}

val list1 = listOf(
    ListElement(id = "1", name = "ListElement 1", Color.MAGENTA),
    ListElement(id = "2", name = "ListElement 2", Color.CYAN),
    ListElement(id = "3", name = "ListElement 3", Color.BLACK),
)

val list2 = listOf(
    ListElement(id = "1", name = "ListElement 1", Color.MAGENTA),
    ListElement(id = "3", name = "ListElement BLACK", Color.BLACK),
    ListElement(id = "2", name = "ListElement 2", Color.YELLOW),
)
