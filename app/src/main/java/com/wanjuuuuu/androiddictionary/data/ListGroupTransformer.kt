package com.wanjuuuuu.androiddictionary.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

object ListGroupTransformer {

    private val dispatcher = Dispatchers.Default

    fun transform(
        categorizingFlow: Flow<Map<String, List<TermListItem>>>,
        collapsed: Set<String>
    ): Flow<List<ListGroup>> {
        return categorizingFlow.map { toGroup(it, collapsed) }.flowOn(dispatcher)
    }

    private fun toGroup(
        categorized: Map<String, List<TermListItem>>,
        collapsed: Set<String>
    ): List<ListGroup> {
        return categorized.map { ListGroup(it.key, it.value, collapsed.contains(it.key)) }
    }
}