package com.x5bart.translatenordic.model

import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table
import com.activeandroid.query.Delete
import com.activeandroid.query.Select

@Table(name = "Favorite")
class Favorite : Model {
    @Column
    var fromId: String=""
        internal set
    @Column
    var toId: String =""
        internal set

    constructor() : super() {}

    constructor(from: String, to: String) : super() {
        this.fromId = from
        this.toId = to
        if (Favorite.getByIds(from, to) == null) {
            this.save()
        }
    }

    companion object {

        fun getByIds(from: String, to: String): Favorite? {
            return Select().from(Favorite::class.java).where("fromId = ? AND toId = ?", from, to)
                .executeSingle()
        }

        val getFavorites: List<Favorite>
            get() {
                val items = Select().from(Favorite::class.java).execute<Favorite>()
                return items ?: emptyList()
            }

        fun delete(from: String, to: String) {
            Delete().from(Favorite::class.java).where("fromId = ? AND toId = ?", from, to)
                .executeSingle<Model>()
        }

    }
}