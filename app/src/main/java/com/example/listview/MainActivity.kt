// MainActivity.kt
package com.example.listview

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var adapter: ItemAdapter
    private val items = mutableListOf<Item>() // Initialize with some data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        adapter = ItemAdapter(this, items)
        listView.adapter = adapter

        val editText = findViewById<EditText>(R.id.editText)
        val addButton = findViewById<Button>(R.id.addButton)

        addButton.setOnClickListener {
            val newText = editText.text.toString()
            if (newText.isNotBlank()) {
                items.add(Item(newText, R.drawable.ic_launcher_foreground, false)) // Pass Boolean value
                adapter.notifyDataSetChanged()
                editText.text.clear()
            }
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            // Handle item click if needed
        }
    }
}
