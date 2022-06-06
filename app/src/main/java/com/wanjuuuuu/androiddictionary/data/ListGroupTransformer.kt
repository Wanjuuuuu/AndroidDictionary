package com.wanjuuuuu.androiddictionary.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

object ListGroupTransformer {

    private val dispatcher = Dispatchers.Default

    fun transform(categorizingFlow: Flow<Map<String, List<TermListItem>>>): Flow<List<ListGroup>> {
        return categorizingFlow.map { toGroup(it) }.flowOn(dispatcher)
    }

    private fun toGroup(categorized: Map<String, List<TermListItem>>): List<ListGroup> {
        return categorized.map { ListGroup(it.key, it.value) }
    }
}