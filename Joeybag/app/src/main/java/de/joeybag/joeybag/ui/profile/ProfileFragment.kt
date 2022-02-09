package de.joeybag.joeybag.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import de.joeybag.joeybag.R
import de.joeybag.joeybag.ui.login.LoginFragment

class ProfileFragment: Fragment() {

    lateinit var showName: TextView
    lateinit var showEmail: TextView
    lateinit var logout: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        showName = view.findViewById(R.id.name_customer)
        showEmail = view.findViewById(R.id.email_customer)
        logout = view.findViewById(R.id.logout_btn)

        val signInAccount = context?.let { GoogleSignIn.getLastSignedInAccount(it) }

        if(signInAccount != null){
            showName.text = signInAccount.displayName
            showEmail.text = signInAccount.email
        }

        logout.setOnClickListener(View.OnClickListener {
            Firebase.auth.signOut()
            val backToLoginIntent = Intent(view.context, LoginFragment::class.java)
            startActivity(backToLoginIntent)
            requireActivity().finish()
        })

        return view
    }
}