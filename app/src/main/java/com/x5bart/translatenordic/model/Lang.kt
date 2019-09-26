package com.x5bart.translatenordic.model


import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table
import com.activeandroid.query.Select

@Table(name = "Lang")
class Lang : Model {
    @Column
     private var langId: String = ""

    @Column
   private var title: String = ""

    fun getLangId(): String {
        return langId
    }

    fun getTitle(): String {
        return title
    }

    constructor() : super() {}

    constructor(name: String, value: String) : super() {
        this.langId = name
        this.title = value
        if (!isHere(name)) {
            this.save()
        }
    }

    companion object {

        fun isHere(name: String): Boolean {
            return Select().from(Lang::class.java).where("langId = ?", name).exists()
        }

         public fun getTitle(langId: String): String {
            val lang =
                Select().from(Lang::class.java).where("langId = ?", langId).executeSingle<Lang>()
            return lang?.title ?: ""
        }


        val getLangs: List<Lang>
            get() {
                val langs = Select().from(Lang::class.java).execute<Lang>()
                return langs ?: emptyList()
            }

        fun getLangById(langId: String): Lang {
            return Select().from(Lang::class.java).where("langId = ?", langId).executeSingle()
        }
    }
}