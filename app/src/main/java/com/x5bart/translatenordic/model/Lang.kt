package com.x5bart.translatenordic.model


import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table
import com.activeandroid.query.Select

@Table(name = "Lang")
class Lang : Model {
    @Column
    var langId: String = ""
        internal set
    @Column
    var title: String = ""
        internal set

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

        fun getTitle(langId: String): String {
            val lang =
                Select().from(Lang::class.java).where("langId = ?", langId).executeSingle<Lang>()
            return lang?.title ?: ""
        }

        val langs: List<Lang>
            get() {
                val langs = Select().from(Lang::class.java).execute<Lang>()
                return langs ?: emptyList()
            }

        fun getLangById(langId: String): Lang {
            return Select().from(Lang::class.java).where("langId = ?", langId).executeSingle()
        }
    }
}