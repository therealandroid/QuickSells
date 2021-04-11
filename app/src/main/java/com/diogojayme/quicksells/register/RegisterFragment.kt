package com.diogojayme.quicksells.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.diogojayme.quicksells.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.createAccount
import kotlinx.android.synthetic.main.fragment_login.loading
import kotlinx.android.synthetic.main.fragment_login.password
import kotlinx.android.synthetic.main.fragment_login.username
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment() {
    var registerViewModel: RegisterViewModel = RegisterViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        register.setOnClickListener {
            registerViewModel.register(
                name = name.text.toString(),
                email = username.text.toString(),
                password = password.text.toString()
            )
        }

        registerObservers()
    }

    private fun registerObservers() {
        registerViewModel.loadingLiveData.observe(viewLifecycleOwner, Observer {
            loading.visibility = if (it) View.VISIBLE else View.GONE
            register.isEnabled = !it
        })

        registerViewModel.registerLiveData.observe(viewLifecycleOwner, Observer {
            register.isEnabled = it

            val text: String = if (it) {
                "New account created with success"
            } else {
                "Register failed failed"
            }
            Snackbar.make(username, text, Snackbar.LENGTH_SHORT).show()
        })
    }
}