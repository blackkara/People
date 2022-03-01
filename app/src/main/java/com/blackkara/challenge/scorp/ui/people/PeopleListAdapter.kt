package com.blackkara.challenge.scorp.ui.people

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blackkara.challenge.scorp.data.model.Person
import com.blackkara.challenge.scorp.databinding.LayoutPeopleListItemBinding
import com.blackkara.challenge.scorp.databinding.LayoutPeopleLoadingItemBinding


class PersonListAdapter(private val personList: MutableList<Person>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val ITEM = 0
        private const val LOADING = 1
    }

    private var isLoadingFooterShown = false

    override fun getItemViewType(position: Int): Int {
        return if (position == personList.size - 1 && isLoadingFooterShown) {
            LOADING
        } else {
            ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == LOADING) {
            val binding = LayoutPeopleLoadingItemBinding.inflate(inflater, parent, false)
            PersonListLoadingViewHolder(binding)
        } else {
            val binding = LayoutPeopleListItemBinding.inflate(inflater, parent, false)
            PersonListViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position) == LOADING){
            // Bu layout için bind gerekli değil
        } else {
            (holder as PersonListViewHolder).bind(personList[position])
        }
    }

    override fun getItemCount(): Int {
        return personList.size
    }

    private fun addPerson(person: Person){
        personList.add(person)
        notifyItemInserted(personList.size - 1)
    }

    fun addPersons(personList: MutableList<Person>) {
        personList.forEach {
            addPerson(it)
        }
    }

    fun addLoadingFooter(){
        isLoadingFooterShown = true
        addPerson(Person(-1,""))
    }

    fun removeLoadingFooter(){
        isLoadingFooterShown = false
        val position: Int = personList.size - 1
        personList.removeAt(position)
        notifyItemRemoved(position)
    }

    private fun removePerson(person: Person){
        val position = personList.indexOf(person)
        if(position > -1){
            personList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear(){
        isLoadingFooterShown = false
        personList.clear()
        notifyDataSetChanged()
    }
}

class PersonListViewHolder(private val binding: LayoutPeopleListItemBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(person: Person){
        binding.textViewId.text = "${person.id}"
        binding.textViewName.text = person.fullName
    }
}

class PersonListLoadingViewHolder(binding: LayoutPeopleLoadingItemBinding) : RecyclerView.ViewHolder(binding.root)