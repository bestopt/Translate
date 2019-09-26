package com.x5bart.translatenordic.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.annotation.NonNull
import com.x5bart.translatenordic.R
import com.x5bart.translatenordic.model.Favorite
import com.x5bart.translatenordic.model.Lang

class FavoriteAdapter(private val context: Context, @param:NonNull private val listener: OnFavPressed) :
    BaseAdapter() {
    private val favorites: List<Favorite>

    init {
        favorites = Favorite.getFavorites
    }

    internal class ViewHolder {
        var title: TextView? = null
    }

    override fun getCount(): Int {
        return favorites.size
    }

    override fun getItem(i: Int): Favorite {
        return favorites[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var view = view
        val viewHolder: LanguageAdapter.ViewHolder
        if (view == null) {
            val layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = layoutInflater.inflate(R.layout.list_item, viewGroup, false)
            viewHolder = LanguageAdapter.ViewHolder()
            viewHolder.title = view!!.findViewById(R.id.text) as TextView
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as LanguageAdapter.ViewHolder
        }
        val from = Lang.getLangById(getItem(i).fromId)
        val to = Lang.getLangById(getItem(i).toId)
        viewHolder.title!!.setText(String.format("%1\$s - %2\$s", from.getTitle(), to.getTitle()))
        viewHolder.title!!.setOnClickListener(View.OnClickListener { listener.onPressed(getItem(i)) })
        viewHolder.title!!.setOnLongClickListener(View.OnLongClickListener {
            listener.onLongPressed(getItem(i))
            false
        })
        return view
    }
}
