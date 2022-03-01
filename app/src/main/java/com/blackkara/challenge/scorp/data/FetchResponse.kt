package com.blackkara.challenge.scorp.data

import com.blackkara.challenge.scorp.data.model.Person

data class FetchResponse(val people: List<Person>, val next: String?)