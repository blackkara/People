package com.blackkara.challenge.scorp.utils

import com.blackkara.challenge.scorp.data.repositories.PeopleListRepository

interface DependencyHelper {
    fun peopleListRepository(): PeopleListRepository
}