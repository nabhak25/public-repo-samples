package com.example.knotesapp.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.knotesapp.R
import com.example.knotesapp.network.model.Note
import java.text.ParseException
import java.text.SimpleDateFormat

class NotesAdapter(private val context: Context, private val notes: List<Note>) : RecyclerView.Adapter<NotesAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val itemView = LayoutInflater.from(p0.context).inflate(R.layout.note_list_row, p0, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        val note = notes[p1]

        p0.note.text = note.note

        p0.dot.text = Html.fromHtml("&#8226")

        p0.dot.setTextColor(getRandomMaterialColor("400"))

        p0.timestamp.text = formatDate(note.timeStamp)
    }


    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val note: TextView = view.findViewById(R.id.note)
            val dot: TextView = view.findViewById(R.id.dot)
            val timestamp: TextView = view.findViewById(R.id.timestamp)
    }

    private fun getRandomMaterialColor(colorString: String): Int {
        var returnColor = Color.GRAY
        val arrayId = context.resources.getIdentifier("mdcolor_$colorString", "array", context.packageName)

        if (arrayId != 0) {
            val colors = context.resources.obtainTypedArray(arrayId)
            val index = Math.random() * colors.length()
            returnColor = colors.getColor(index.toInt(), Color.GRAY)
            colors.recycle()
        }
        return returnColor
    }

    @SuppressLint("SimpleDateFormat")
    private fun formatDate(dateStr: String): String {
        try {
            val fmt = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val date = fmt.parse(dateStr)
            val formatOut = SimpleDateFormat("MMM d")
            return formatOut.format(date)

        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }
}