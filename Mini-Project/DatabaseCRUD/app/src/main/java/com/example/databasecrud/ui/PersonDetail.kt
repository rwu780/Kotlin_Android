package com.example.databasecrud.ui

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.databasecrud.data.model.Person
import com.example.databasecrud.databinding.FragmentPersonDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonDetail : Fragment() {

    private val navigationArg: PersonDetailArgs by navArgs()

    private lateinit var binding: FragmentPersonDetailBinding

    private val viewModel: PersonViewModel by activityViewModels()

    private var isNewPerson: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val email = navigationArg.email

        initViews(email)

        binding.btnSubmit.setOnClickListener {

            val firstName = binding.etFirstName.text.toString()
            val lastName = binding.etLastName.text.toString()

            val et_email = binding.etEmail.text.toString()

            if (!isValidEmail(et_email)){
                binding.etEmail.error = "Please enter a valid email"
            }
            else{
                viewModel.insertPerson(
                    firstName = firstName,
                    lastName = lastName,
                    email = et_email
                )
                findNavController().navigateUp()
            }
        }

        viewModel.retrievePerson(email).observe(this.viewLifecycleOwner){
            bindView(it)
        }

    }

    private fun initViews(email: String){
        isNewPerson = email.isBlank()

        if (isNewPerson){
            binding.btnSubmit.text = "Add New User"
            binding.etEmail.isEnabled = true

        } else {
            binding.btnSubmit.text = "Update User"
            binding.etEmail.isEnabled = false
        }

    }

    private fun bindView(person: Person){
        binding.etEmail.setText(person.email)
        binding.etFirstName.setText(person.firstName)
        binding.etLastName.setText(person.lastName)
    }

    private fun isValidEmail(email: String): Boolean{
        return email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}