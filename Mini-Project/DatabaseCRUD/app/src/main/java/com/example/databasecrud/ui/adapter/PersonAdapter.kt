package com.example.databasecrud.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.databasecrud.data.model.Person
import com.example.databasecrud.databinding.ItemPersonBinding

class PersonAdapter(
    private val onClick : (Person) -> Unit,
    private val onLongClick: (Person) -> Boolean

) : ListAdapter<Person, PersonAdapter.ViewHolder>(DiffUtilCallback) {

    companion object {
        private val DiffUtilCallback = object : DiffUtil.ItemCallback<Person>() {
            override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
                return oldItem.email == newItem.email
            }

        }
    }

    class ViewHolder(
        private val binding: ItemPersonBinding
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(
            dataItem: Person,
            onClick: (Person) -> Unit,
            onLongClick: (Person) -> Boolean
        ){

            binding.tvFirstName.text = dataItem.firstName
            binding.tvLastName.text = dataItem.lastName
            binding.tvEmail.text = dataItem.email

            binding.root.setOnClickListener {
                onClick(dataItem)
            }

            binding.root.setOnLongClickListener {
                onLongClick.invoke(dataItem)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataItem = getItem(position)
        holder.bind(dataItem, onClick, onLongClick)
    }
}

