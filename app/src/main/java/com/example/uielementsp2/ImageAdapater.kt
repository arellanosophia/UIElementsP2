package com.example.uielementsp2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class CustomAdapter(private var itemModel :ArrayList<Modal>, private var context: Context) : BaseAdapter() {
     var layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return itemModel.size
    }
    override fun getItem(position: Int): Any? {
        return itemModel[position]
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var view = convertView

        if (view == null) {
            view = layoutInflater.inflate(R.layout.row_item, parent,false)
        }
        var textView = view?.findViewById<TextView>(R.id.textView)
        var imageView = view?.findViewById<ImageView>(R.id.imageView)

        textView?.text = itemModel[position].name
        imageView?.setImageResource(itemModel[position].image!!)

        return view!!
    }
}