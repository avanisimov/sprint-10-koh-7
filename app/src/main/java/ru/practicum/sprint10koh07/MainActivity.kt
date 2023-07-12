package ru.practicum.sprint10koh07

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    var itemsContainer: ViewGroup? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val itemsView: ScrollView = findViewById(R.id.items)

        itemsContainer = LayoutManager().generateItemsContainer()
        itemsView.addView(itemsContainer)

        val items: List<Item> = getItems()
        items.forEach { item ->
            val itemView = Adapter().adaptItem(item)
            itemsContainer?.addView(itemView)
        }

    }






}

class LayoutManager{
    fun generateItemsContainer(): ViewGroup {
        return LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }
    }
}

class Adapter(

){
    fun adaptItem(item: Item): View {
        val itemView: View = layoutInflater
            .inflate(R.layout.v_item, itemsContainer, false)

        val titleView = itemView.findViewById<TextView>(R.id.title)
        titleView.text = item.title
        return itemView
    }
}

data class Item(
    val id: String,
    val title: String,
)

fun getItems(): List<Item> {
    return (0..20).map {
        Item(
            id = "$it",
            title = "Item $it"
        )
    }
}