package com.x5bart.translatenordic.model



import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table
import com.activeandroid.query.Select

class Lang: Model {

    var langId:String = ""
        internal set
    var title:String =""
        internal set
    constructor() : super() {}
    constructor(name:String, value:String) : super() {
        this.langId = name
        this.title = value
        if (!isHere(name))
        {
            this.save()
        }
    }
    companion object {
        fun isHere(name:String):Boolean {
            return Select().from(Lang::class.java).where("langId = ?", name).exists()
        }
        fun getTitle(langId:String):String {
            val lang = Select().from(Lang::class.java).where("langId = ?", langId).executeSingle()
            if (lang != null)
            {
                return lang.title
            }
            else
            {
                return ""
            }
        }
        val langs:List<Lang>
            get() {
                val langs = Select().from(Lang::class.java).execute()
                if (langs != null)
                {
                    return langs
                }
                else
                {
                    return emptyList<Lang>()
                }
            }
        fun getLangById(langId:String):Lang {
            return Select().from(Lang::class.java).where("langId = ?", langId).executeSingle()
        }
    }
}
