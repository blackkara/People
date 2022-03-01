package com.blackkara.challenge.scorp.ui.people

import com.blackkara.challenge.scorp.data.model.Person


interface PeopleListContract {

    interface Presenter {
        fun onViewCreated()
        fun onDestroy()
        fun onScrolled(childCount: Int, itemCount: Int, visibleItemPosition: Int)
        fun onRefreshTapped()
    }

    interface View {
        fun showData(data: List<Person>)
        fun showDataEmpty()
        fun showInitialLoading(state: Boolean)
        fun showMoreLoading(state: Boolean)
        fun showLoadError()
        fun clearScreen()
    }
}