package com.diogojayme.quicksells.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.diogojayme.quicksells.R
import com.diogojayme.quicksells.sells_list.SellsListFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment: Fragment() {
    var loginViewModel: LoginViewModel = LoginViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createAccount.setOnClickListener {
            val directions = LoginFragmentDirections.toRegister()
            Navigation.findNavController(it).navigate(directions)
        }

        login.setOnClickListener {
            loginViewModel.login(username.text.toString(), password.text.toString())
        }

        registerObservers()
    }

    private fun registerObservers() {
        loginViewModel.loadingLiveData.observe(viewLifecycleOwner, Observer {
            loading.visibility = if (it) View.VISIBLE else View.GONE
            login.isEnabled = !it
        })

        loginViewModel.loginLiveData.observe(viewLifecycleOwner, Observer {
            login.isEnabled = !it

            val directions = LoginFragmentDirections.toSellsFragment()

            if(view != null)
                Navigation.findNavController(requireView()).navigate(directions)
        })
    }
}