package com.example.themoviedb.ui.authentication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.themoviedb.ui.home_page.MainActivity

import com.example.themoviedb.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.view.*


class LoginFragment : Fragment() {

    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        init(view)
        return view
    }

    private fun init(view: View) {

//        if (mAuth.currentUser != null) {
//            getAuthActivity().startActivity(Intent(getAuthActivity(), MainActivity::class.java))
//            getAuthActivity().finish()
//        }

        view.registrationBtn.setOnClickListener {
            getAuthActivity().inflateFragment(SignUpFragment(), "Sign Up Fragment")
        }

        view.loginBtn.setOnClickListener {
            val email = view.loginEmail.text.toString()
            val password = view.loginPassword.text.toString()
            if(email.isNotEmpty() && password.isNotEmpty()){
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(getAuthActivity()) {
                    if (it.isSuccessful) {
                        getAuthActivity().startActivity(Intent(getAuthActivity(), MainActivity::class.java))
                        getAuthActivity().finish()
                    } else {
                        Toast.makeText(context, "შეცდომაა !", Toast.LENGTH_LONG).show()
                    }
                }
            }

        }
    }

    private fun getAuthActivity() = activity as AuthActivity

}
