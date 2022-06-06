package com.wanjuuuuu.androiddictionary.data

sealed class ListItem {

    abstract val id: Long

    data class Category(val category: String) : ListItem() {
        override val id: Long = -1
    }

    data class Term(val term: TermListItem) : ListItem() {
        override val id: Long = term.id
    }
}