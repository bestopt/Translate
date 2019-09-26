package com.x5bart.translatenordic.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ListView
import androidx.annotation.NonNull
import androidx.fragment.app.DialogFragment
import com.x5bart.translatenordic.R
import com.x5bart.translatenordic.model.Favorite

class FavChooserDialog : DialogFragment() {
    private var rootView: View? = null
    private var adapter: FavoriteAdapter? = null
    private var listener: OnFavPressed? = null

    @NonNull
     override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val adb = AlertDialog.Builder(getActivity())
        rootView = getActivity()!!.getLayoutInflater().inflate(R.layout.dialog_container, null)
        adapter = FavoriteAdapter(context!!, object : OnFavPressed {
           override fun onPressed(favorite: Favorite) {
                listener!!.onPressed(favorite)
                this@FavChooserDialog.dismiss()
            }

            override fun onLongPressed(favorite: Favorite) {
                listener!!.onLongPressed(favorite)
                this@FavChooserDialog.dismiss()
            }
        })
        val items = rootView!!.findViewById(R.id.items) as ListView
        if (items != null) {
            items.adapter = adapter
        }
        adb.setView(rootView)

        val result = adb.create()
        result.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return result
    }

    companion object {

        fun getInstance(@NonNull listener: OnFavPressed): FavChooserDialog {
            val dialog = FavChooserDialog()
            dialog.listener = listener
            return dialog
        }
    }
}