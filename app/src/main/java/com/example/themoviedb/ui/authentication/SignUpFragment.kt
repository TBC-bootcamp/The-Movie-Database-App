package com.example.themoviedb.ui.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.themoviedb.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.view.*

/**
 * A simple [Fragment] subclass.
 */
class SignUpFragment : Fragment() {

    private val mAuth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance().reference

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
        init(view)
        return view
    }

    private fun init(view: View) {
        view.signUpBtn.setOnClickListener {
            val name = registerName.text.toString()
            val email = registerEmail.text.toString()
            val password = registerPassword.text.toString()
            val registerPasswordRepeat = registerPasswordRepeat.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()){
                if(password == registerPasswordRepeat){
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(getAuthActivity()) {
                                if (it.isSuccessful) {
                                    Toast.makeText(getAuthActivity(), "Account has been Registered", Toast.LENGTH_LONG).show()
                                    val user = AccountUserModel()
                                    user.name = name
                                    user.email = email
                                    db.child(mAuth.currentUser!!.uid).setValue(user)
                                    getAuthActivity().inflateFragment(LoginFragment(), "Login Fragment")
                                } else {
                                    Toast.makeText(getAuthActivity(), "Failure", Toast.LENGTH_LONG).show()
                                }
                            }
                } else {
                    Toast.makeText(getAuthActivity(), "Passwords does not match", Toast.LENGTH_LONG).show()
                }
            }

        }

        view.backToLoginBtn.setOnClickListener {
            getAuthActivity().inflateFragment(LoginFragment(), "Login Fragment")
        }

    }

    private fun getAuthActivity() = activity as AuthActivity
}
