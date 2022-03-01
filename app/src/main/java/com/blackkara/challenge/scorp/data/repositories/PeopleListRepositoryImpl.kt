package com.blackkara.challenge.scorp.data.repositories

import DataSource
import com.blackkara.challenge.scorp.data.FetchCompletionHandler

class PeopleListRepositoryImpl : PeopleListRepository {
    private val dataSource = DataSource()
    override fun loadPeopleList(next: String?, handler: FetchCompletionHandler) {
        return dataSource.fetch(next, handler)
    }
}