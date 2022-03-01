package com.blackkara.challenge.scorp.data.repositories

import com.blackkara.challenge.scorp.data.FetchCompletionHandler

interface PeopleListRepository {
    fun loadPeopleList(next: String?, handler: FetchCompletionHandler)
}