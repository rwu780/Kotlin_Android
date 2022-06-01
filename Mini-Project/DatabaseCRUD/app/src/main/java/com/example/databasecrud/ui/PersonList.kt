package com.example.databasecrud.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.databasecrud.databinding.FragmentPersonListBinding
import com.example.databasecrud.ui.adapter.PersonAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PersonList : Fragment() {

    private lateinit var binding: FragmentPersonListBinding

    private val viewModel: PersonViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PersonAdapter(
            onClick = {
                val action = PersonListDirections.actionPersonListToPersonDetail(email = it.email)
                findNavController().navigate(action)

            },
            onLongClick = {
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage("Do you want to delete person ${it.firstName}, ${it.lastName}")
                    .setPositiveButton("Yes"){ _, _ ->
                        viewModel.deleteUser(it.email)
                    }
                    .setNegativeButton("No"){ dialog, _ ->
                        dialog.cancel()
                    }
                builder.create().show()
                return@PersonAdapter true
            }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        lifecycle.coroutineScope.launch {
            viewModel.retrieveAllPerson().collect() {
                adapter.submitList(it)
            }
        }


        binding.fab.setOnClickListener {
            val action = PersonListDirections.actionPersonListToPersonDetail(email = "")
            findNavController().navigate(action)
        }
    }
}