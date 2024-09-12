// ListItemActivity.kt
package com.example.listview

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class ListItemActivity : Activity() {

    private var itemPosition: Int = -1 // To track which item is being edited

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_item)

        val editText = findViewById<EditText>(R.id.editText)
        val saveButton = findViewById<Button>(R.id.saveButton)

        // Retrieve the item text and position from the Intent
        val intent = intent
        val itemText = intent.getStringExtra("itemText") ?: ""
        itemPosition = intent.getIntExtra("itemPosition", -1)

        editText.setText(itemText)

        saveButton.setOnClickListener {
            val newText = editText.text.toString()
            if (newText.isNotBlank()) {
                // Pass data back to MainActivity
                val resultIntent = Intent().apply {
                    putExtra("editedText", newText)
                    putExtra("itemPosition", itemPosition)
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, "Text cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
