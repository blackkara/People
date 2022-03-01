package com.blackkara.challenge.scorp.ui.people

import com.blackkara.challenge.scorp.data.FetchCompletionHandler
import com.blackkara.challenge.scorp.data.FetchError
import com.blackkara.challenge.scorp.data.FetchResponse
import com.blackkara.challenge.scorp.utils.DependencyHelper

class PeopleListPresenter(view: PeopleListContract.View, injector: DependencyHelper) : PeopleListContract.Presenter {

    companion object {
        private const val serverRetryLimit = 1
    }

    private var repository = injector.peopleListRepository()
    private var view: PeopleListContract.View? = view


    private var isInitial = false
    private var isLoading = false
    private var isLoadingMore = false
    private var size: String? = null
    private var retryCount = 0

    override fun onViewCreated() {
        isInitial = true
        loadPersonList(size)
    }

    override fun onDestroy() {
        view = null
    }

    private fun loadPersonList(nextSize: String? = null) {
        isLoading = true
        if (isInitial) {
            view?.showInitialLoading(true)
        }
        repository.loadPeopleList(nextSize, object : FetchCompletionHandler {
            override fun invoke(response: FetchResponse?, error: FetchError?) {
                view?.let { view ->
                    isLoading = false

                    if (isLoadingMore) {
                        isLoadingMore = false
                        view.showMoreLoading(false)
                    }

                    if (isInitial) {
                        isInitial = false
                        view.showInitialLoading(false)
                    }

                    response?.apply { handleSuccess(this) }
                    error?.apply { handleError() }
                }
            }
        })
    }

    private fun handleSuccess(response: FetchResponse) {
        retryCount = 0
        response.people.distinctBy { x -> x.id }.let { people->
            when {
                people.isNotEmpty() -> {
                    size = response.next
                    view?.showData(people)
                }
                isInitial -> {
                    view?.showDataEmpty()
                }
                else -> {

                }
            }
        }
        isInitial = false
        view?.showInitialLoading(false)
    }

    private fun handleError() {
        view?.showLoadError()
        if (retryCount < serverRetryLimit) {
            retryCount++
            loadPersonList()
        }
    }

    override fun onScrolled(childCount: Int, itemCount: Int, visibleItemPosition: Int) {
        if (!isLoading && (size?.toIntOrNull()?.compareTo(0) ?: 0) > 0) {
            if (childCount + visibleItemPosition >= itemCount && visibleItemPosition >= 0) {
                isLoadingMore = true
                view?.showMoreLoading(true)
                loadPersonList(size)
            }
        }
    }

    override fun onRefreshTapped() {
        isInitial = true
        size = null
        view?.clearScreen()
        loadPersonList(size)
    }
}