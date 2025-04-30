package com.saikrishna.reachmobi.data

import android.content.Context
import androidx.core.content.edit

class SearchHistoryManager(context: Context) {
    private val prefs = context.getSharedPreferences("search_history", Context.MODE_PRIVATE)
    private val KEY = "searchQueries"

    fun getHistory(): List<String> {
        return prefs.getString(KEY, "")!!
            .takeIf { it.isNotEmpty() }
            ?.split("|")
            ?: emptyList()
    }


    fun addQuery(query: String) {
        if (query.trim().isNotEmpty()) {
            val history = getHistory().toMutableList()
            history.remove(query.trim())
            history.add(0, query.trim())
            if (history.size > 10) history.dropLast(history.size - 10)
            prefs.edit() {
                putString(KEY, history.joinToString("|"))
            }
        }
    }


    fun clear() {
        prefs.edit() { remove(KEY) }
    }
}