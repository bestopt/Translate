package com.x5bart.translatenordic.ui


import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.x5bart.translatenordic.model.Lang


//import com.andrey.translator.R
//import com.andrey.translator.model.Favorite
//import com.andrey.translator.model.Lang
//import com.andrey.translator.model.TranslateEngine
//import com.andrey.translator.net.API
//import com.andrey.translator.net.Method
//import com.andrey.translator.net.ResponseError
//import com.andrey.translator.net.retrofit.ResponseItem
//import com.andrey.translator.net.retrofit.SingleItemResponse
//import com.andrey.translator.ui.dialog.ChooserLanguageDialog
//import com.andrey.translator.ui.dialog.FavChooserDialog
//import com.andrey.translator.ui.dialog.OnFavPressed
//import com.andrey.translator.ui.dialog.OnLanguageSelected
//
//import java.util.HashMap
//
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.Unbinder
import com.x5bart.translatenordic.R
import com.x5bart.translatenordic.model.Favorite
import com.x5bart.translatenordic.net.API
import com.x5bart.translatenordic.net.API.sendRequest
import com.x5bart.translatenordic.net.ResponseError
import com.x5bart.translatenordic.net.retrofit.ResponseItem
import com.x5bart.translatenordic.net.retrofit.SingleItemResponse
import com.x5bart.translatenordic.ui.dialog.ChooserLanguageDialog
import com.x5bart.translatenordic.ui.dialog.FavChooserDialog
import com.x5bart.translatenordic.ui.dialog.OnFavPressed
import com.x5bart.translatenordic.ui.dialog.OnLanguageSelected
import java.lang.reflect.Method
import com.x5bart.translatenordic.model.TranslateEngine as TranslateEngine1

/**
 * Created by Andrey Antonenko on 01.12.2016.
 */

class TranslateFragment : Fragment() {

    @BindView(R.id.from_lang)
    internal var fromLang: TextView? = null
    @BindView(R.id.to_lang)
    internal var toLang: TextView? = null
    @BindView(R.id.switch_lang)
    internal var switchLang: ImageView? = null
    @BindView(R.id.input_text)
    internal var inputText: EditText? = null
    @BindView(R.id.out_text)
    internal var outText: TextView? = null
    @BindView(R.id.translate)
    internal var translate: Button? = null
    @BindView(R.id.out_container)
    internal var outContainer: CardView? = null
    @BindView(R.id.yandex_service)
    internal var yandexService: TextView? = null
    @BindView(R.id.auto)
    internal var auto: TextView? = null
    @BindView(R.id.favorite)
    internal var favorite: ImageView? = null
    @BindView(R.id.content)
    internal var content: LinearLayout? = null
    @BindView(R.id.emptyView)
    internal var emptyView: RelativeLayout? = null

    private var fromLangSelected: Lang? = null
    private var toLangSelected: Lang? = null
    private var dlg: AlertDialog? = null
    private var isAuto: Boolean = false

    private var connectivityChangeFilter: IntentFilter? = null

    private var unbinder: Unbinder? = null

    private val connectionReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            updateInfo()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityChangeFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
    }


     override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.translator_layout, container, false)
        unbinder = ButterKnife.bind(this, rootView)

//        dlg = ProgressDialog(context)
        dlg!!.setTitle(R.string.dialog_title)
        dlg!!.setMessage(context?.getString(R.string.dialog_message))
        dlg!!.setCancelable(false)

        return rootView
    }

    override fun onStart() {
        super.onStart()
        activity?.registerReceiver(connectionReceiver, connectivityChangeFilter)
    }

    override fun onStop() {
        super.onStop()
        activity?.unregisterReceiver(connectionReceiver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder!!.unbind()
    }

    override fun onViewCreated(view: View,  savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateInfo()
    }

    private fun updateInfo() {
        dlg!!.show()
        val params = HashMap<String, String>()
        params["key"] = TranslateEngine1.getCurrentApiKey()
        params["ui"] = "ru"

        sendRequest(com.x5bart.translatenordic.net.Method.GET_LANGS, params, object : API.OnRequestComplete {
            override fun onSuccess(response: ResponseItem) {
                content!!.visibility = View.VISIBLE
                emptyView!!.visibility = View.GONE
                toLangSelected = Lang.getLangById("ru")
                fromLangSelected = Lang.getLangById("en")
                isAuto = false
                updateViews()
                dlg!!.dismiss()
            }

           override fun onError(error: ResponseError) {
                content!!.visibility = View.GONE
                emptyView!!.visibility = View.VISIBLE
                Toast.makeText(activity, error.getErrorMessage(), Toast.LENGTH_SHORT).show()
                dlg!!.dismiss()
            }
        })
    }

    @OnClick(R.id.yandex_service)
    fun openYandexService() {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://translate.yandex.ru"))
        startActivity(browserIntent)
    }

    @OnClick(R.id.from_lang)
    fun openFromLang() {
        val dialog = ChooserLanguageDialog.getInstance(object : OnLanguageSelected {
           override fun onSelect(lang: Lang) {
                fromLangSelected = lang
                updateViews()
            }
        })
        dialog.show(fragmentManager!!, "")
    }

    @OnClick(R.id.to_lang)
     fun openToLang() {
        val dialog = ChooserLanguageDialog.getInstance(object : OnLanguageSelected {
           override fun onSelect(lang: Lang) {
                toLangSelected = lang
                updateViews()
            }
        })
        dialog.show(fragmentManager!!, "")
    }

    @OnClick(R.id.switch_lang)
    fun switchLangs() {
        if (fromLangSelected != null) {
            val tmp = fromLangSelected
            fromLangSelected = toLangSelected
            toLangSelected = tmp
            updateViews()
        }
    }

    @OnClick(R.id.favorite)
    fun onFavClick() {

        Favorite(fromLangSelected!!.getLangId(), toLangSelected!!.getLangId())

        val dialog = FavChooserDialog.getInstance(object : OnFavPressed {
          override  fun onPressed(favorite: Favorite) {
                fromLangSelected = Lang.getLangById(favorite.fromId)
                toLangSelected = Lang.getLangById(favorite.toId)
                updateViews()
            }

            override fun onLongPressed(favorite: Favorite) {
                deleteFav(favorite)
            }
        })
        dialog.show(fragmentManager!!, "")
    }

    @OnClick(R.id.translate)
    fun translate() {
        val text = inputText!!.text.toString()
        if (!TextUtils.isEmpty(text) && toLangSelected != null) {
            dlg!!.show()
            val params = HashMap<String, String>()
            params["key"] = TranslateEngine1.getCurrentApiKey()
            params["text"] = text
            if (fromLangSelected != null && !isAuto) {
                params["lang"] = String.format(
                    "%1\$s-%2\$s",
                    fromLangSelected!!.getLangId(),
                    toLangSelected!!.getLangId()
                )
            } else {
                params["lang"] = toLangSelected!!.getLangId()
            }

            sendRequest(com.x5bart.translatenordic.net.Method.TRANSLATE, params, object : API.OnRequestComplete {
                 override fun onSuccess(response: ResponseItem) {
                    val out = (response as SingleItemResponse).text
                    outText!!.setText(out)
                    outContainer!!.setVisibility(View.VISIBLE)
                    dlg!!.dismiss()
                }

                override fun onError(error: ResponseError) {
                    Toast.makeText(getActivity(), error.getErrorMessage(), Toast.LENGTH_SHORT)
                        .show()
                    dlg!!.dismiss()
                }
            })
        }
    }

    @OnClick(R.id.auto)
    fun autoClick() {
        isAuto = !isAuto
        updateViews()
    }

    private fun updateViews() {
        if (isAuto) {
            auto!!.setTextColor(getContext()!!.getResources().getColor(R.color.colorAccent))
        } else {
            auto!!.setTextColor(getContext()!!.getResources().getColor(R.color.colorPrimary))
        }
        if (fromLangSelected != null) {
            fromLang!!.setText(fromLangSelected!!.getTitle())
        }
        if (toLangSelected != null) {
            toLang!!.setText(toLangSelected!!.getTitle())
        }
    }

    private fun deleteFav(favorite: Favorite) {
        AlertDialog.Builder(getContext())
            .setTitle(R.string.dialog_title)
            .setMessage(R.string.delete_message)
            .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                favorite.delete()
                dialog.dismiss()
            })
            .setNegativeButton("Отмена",
                DialogInterface.OnClickListener { dialog, i -> dialog.dismiss() })
            .create()
            .show()
    }
}
