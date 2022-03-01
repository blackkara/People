package com.blackkara.challenge.scorp.utils

import com.blackkara.challenge.scorp.data.repositories.PeopleListRepository
import com.blackkara.challenge.scorp.data.repositories.PeopleListRepositoryImpl

class DependencyHelperImpl : DependencyHelper{
    override fun peopleListRepository(): PeopleListRepository {
        return PeopleListRepositoryImpl()
    }
}