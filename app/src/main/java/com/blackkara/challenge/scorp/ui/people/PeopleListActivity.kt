package com.blackkara.challenge.scorp.ui.people

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blackkara.challenge.scorp.R
import com.blackkara.challenge.scorp.data.model.Person
import com.blackkara.challenge.scorp.databinding.ActivityPeopleListBinding
import com.blackkara.challenge.scorp.utils.DependencyHelperImpl

class PeopleListActivity : AppCompatActivity(), PeopleListContract.View {

    private lateinit var binding: ActivityPeopleListBinding
    private lateinit var personListAdapter: PersonListAdapter
    private lateinit var presenter: PeopleListContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPeopleListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = PeopleListPresenter(this, DependencyHelperImpl())

        initView()

        presenter.onViewCreated()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun showData(data: List<Person>) {
        binding.progressBarInitial.visibility = View.GONE
        binding.recyclerViewPersonList.visibility = View.VISIBLE
        binding.textViewNoData.visibility = View.GONE
        binding.textViewTap.visibility = View.GONE
        personListAdapter.addPersons(data.toMutableList())
    }

    override fun showDataEmpty() {
        binding.progressBarInitial.visibility = View.GONE
        binding.recyclerViewPersonList.visibility = View.GONE
        binding.textViewNoData.visibility = View.VISIBLE
        binding.textViewTap.visibility = View.VISIBLE
    }

    override fun showInitialLoading(state: Boolean) {
        if (state) {
            binding.progressBarInitial.visibility = View.VISIBLE
        } else {
            binding.progressBarInitial.visibility = View.GONE
        }
    }

    override fun showMoreLoading(state: Boolean) {
        if (state) {
            personListAdapter.addLoadingFooter()
        } else {
            personListAdapter.removeLoadingFooter()
        }
    }

    override fun showLoadError() {
        Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show()
    }

    override fun clearScreen() {
        binding.progressBarInitial.visibility = View.GONE
        binding.recyclerViewPersonList.visibility = View.GONE
        binding.textViewNoData.visibility = View.GONE
        binding.textViewTap.visibility = View.GONE
        personListAdapter.clear()
    }

    private fun initView(){
        personListAdapter = PersonListAdapter(mutableListOf())
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerViewPersonList.layoutManager = layoutManager
        binding.recyclerViewPersonList.adapter = personListAdapter
        binding.recyclerViewPersonList.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                layoutManager.apply {
                    presenter.onScrolled(childCount, itemCount, findFirstVisibleItemPosition())
                }
            }
        })

        binding.textViewTap.setOnClickListener {
            presenter.onRefreshTapped()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_refresh -> {
            presenter.onRefreshTapped()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}