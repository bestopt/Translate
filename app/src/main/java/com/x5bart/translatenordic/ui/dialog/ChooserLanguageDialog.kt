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
import com.x5bart.translatenordic.model.Lang

class ChooserLanguageDialog : DialogFragment() {

    private var rootView: View? = null
    private var adapter: LanguageAdapter? = null
    private var listener: OnLanguageSelected? = null


     override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val adb = AlertDialog.Builder(getActivity())
        rootView = getActivity()!!.getLayoutInflater().inflate(R.layout.dialog_container, null)
        adapter = LanguageAdapter(context!!, object : OnLanguageSelected {
             override fun onSelect(lang: Lang) {
                listener!!.onSelect(lang)
                this@ChooserLanguageDialog.dismiss()
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

        fun getInstance(@NonNull listener: OnLanguageSelected): ChooserLanguageDialog {
            val dialog = ChooserLanguageDialog()
            dialog.listener = listener
            return dialog
        }
    }
}
