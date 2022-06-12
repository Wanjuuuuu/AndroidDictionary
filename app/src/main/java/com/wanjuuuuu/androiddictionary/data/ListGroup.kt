package com.wanjuuuuu.androiddictionary.data

data class ListGroup(
    val category: String,
    val terms: List<TermListItem>,
    val collapsed: Boolean = false
) {
    fun toggleCollapsed(): ListGroup {
        return copy(collapsed = !collapsed)
    }
}