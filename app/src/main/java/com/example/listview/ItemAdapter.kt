// ItemAdapter.kt
package com.example.listview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import android.widget.BaseAdapter

class ItemAdapter(
    private val context: Context,
    private val items: MutableList<Item>
) : BaseAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: inflater.inflate(R.layout.list_item, parent, false)
        val item = getItem(position) as Item

        val textView: TextView = view.findViewById(R.id.textView)
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val checkBox: CheckBox = view.findViewById(R.id.checkBox)

        textView.text = item.text
        imageView.setImageResource(item.imageResId)
        checkBox.isChecked = item.isChecked

        view.setOnClickListener {
            // Show edit/delete dialog
            AlertDialog.Builder(context)
                .setTitle("Options")
                .setItems(arrayOf("Edit", "Delete")) { _, which ->
                    when (which) {
                        0 -> showEditDialog(position)
                        1 -> deleteItem(position)
                    }
                }
                .show()
        }

        return view
    }

    private fun showEditDialog(position: Int) {
        // Implement edit dialog
        val item = items[position]
        val editText = android.widget.EditText(context)
        editText.setText(item.text)

        AlertDialog.Builder(context)
            .setTitle("Edit Item")
            .setView(editText)
            .setPositiveButton("Save") { _, _ ->
                item.text = editText.text.toString()
                notifyDataSetChanged()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteItem(position: Int) {
        // Implement delete logic
        items.removeAt(position)
        notifyDataSetChanged()
    }
}
