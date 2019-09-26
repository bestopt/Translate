package com.x5bart.translatenordic.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.annotation.NonNull
import com.x5bart.translatenordic.R
import com.x5bart.translatenordic.model.Lang

class LanguageAdapter(private val context: Context, @param:NonNull private val listener: OnLanguageSelected) :
    BaseAdapter() {
    private val langs: List<Lang>

    internal class ViewHolder {
        var title: TextView? = null
    }

    init {
        langs = Lang.getLangs
    }

    override fun getCount(): Int {
        return langs.size
    }

    override fun getItem(i: Int): Lang {
        return langs[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var view = view
        val viewHolder: ViewHolder
        if (view == null) {
            val layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = layoutInflater.inflate(R.layout.list_item, viewGroup, false)
            viewHolder = ViewHolder()
            viewHolder.title = view!!.findViewById(R.id.text) as TextView
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }
        viewHolder.title!!.setText(getItem(i).getTitle())
        viewHolder.title!!.setOnClickListener { listener.onSelect(getItem(i)) }
        return view
    }
}
