package de.joeybag.joeybag.ui.login

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.persistableBundleOf
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import de.joeybag.joeybag.R
import de.joeybag.joeybag.ui.forgotpassword.ForgotPasswordFragment
import de.joeybag.joeybag.ui.login.GoogleSignInActivity
import de.joeybag.joeybag.ui.register.RegisterFragment
import de.joeybag.joeybag.ui.register.RegistrationSuccessfulActivity
import java.util.regex.Pattern

class LoginFragment : Fragment() {

    lateinit var inputEmail: EditText
    lateinit var inputPassword: EditText
    lateinit var login: Button
    lateinit var register: Button
    lateinit var forgotPassword: Button
    lateinit var googleLogIn: SignInButton

    var email: String? = ""
    var password: String? = ""

    private lateinit var jAuth: FirebaseAuth
    private lateinit var jUser: FirebaseUser
    private lateinit var db: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_login, container, false)
        inputEmail = view.findViewById(R.id.email_login)
        inputPassword = view.findViewById(R.id.password)
        login = view.findViewById(R.id.login_btn)
        register = view.findViewById(R.id.register_btn)
        forgotPassword = view.findViewById(R.id.forgot_pwd_btn)
        googleLogIn = view.findViewById(R.id.google_icon)

        jAuth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance()

        login.setOnClickListener(View.OnClickListener {
            checkCredentials()
        })

        register.setOnClickListener(View.OnClickListener { _: View? ->
            val registrationFormIntent = Intent(view.context, RegisterPage::class.java)
            startActivity(registrationFormIntent)
            requireActivity().finish()
       })

       forgotPassword.setOnClickListener(View.OnClickListener { _: View? ->
           val forgotPasswordWindowIntent = Intent(view.context, ForgotPasswordPage::class.java)
           startActivity(forgotPasswordWindowIntent)
           requireActivity().finish()
       })

        googleLogIn.setOnClickListener(View.OnClickListener {
                GoogleSignInActivity()
            })

        return view
    }

    private fun checkCredentials() {
        email = inputEmail.text.toString()
        password = inputPassword.text.toString()
        if (email!!.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email!!).matches()) {
            showError(inputEmail, "Email is not valid")
        } else if (password!!.isEmpty() || !PASSWORD_PATTERN.matcher(password!!).matches()) {
            showError(
                inputPassword, """
     Password must contain 
     7 character
     atleast 1 Uppercase
     atleast 1 Lowercase
     atleast 1 digit
     atleast 1 special character
     
     """.trimIndent()
            )
        } else {
            signIn(email!!, password!!)

        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = jAuth.currentUser
        updateUI(currentUser)
        if (currentUser != null) {
            reload();
        }
    }

    private fun createAccount(email: String, password: String) {
        // [START create_user_with_email]
        jAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { it ->
                if (it.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = jAuth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", it.exception)
                    Toast.makeText(
                        view?.context, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
        // [END create_user_with_email]
    }

    private fun signIn(email: String, password: String) {
        // [START sign_in_with_email]
        jAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { it ->
                if (it.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = jAuth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", it.exception)
                    Toast.makeText(
                        view?.context, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
        // [END sign_in_with_email]
    }

    private fun sendEmailVerification() {
        // [START send_email_verification]
        val user = jAuth.currentUser!!
        user.sendEmailVerification()
            .addOnCompleteListener { it ->
                // Email Verification sent
            }
        // [END send_email_verification]
    }

    private fun showError(input: EditText?, s: String) {
        input!!.error = s
        input.requestFocus()
    }

    private fun updateUI(user: FirebaseUser?) {

    }

    private fun reload() {

    }

    companion object {
        private const val TAG = "EmailPassword"
        private val PASSWORD_PATTERN = Pattern.compile(
            "^" +
                    "(?=.*[0-9])" +  //at least 1 digit
                    "(?=.*[a-z])" +  //at least 1 lower case letter
                    "(?=.*[A-Z])" +  //at least 1 upper case letter
                    // "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +  //at least 1 special character
                    //"(?=\\S+$)" +           //no white spaces
                    ".{8,}" +  //at least 8 characters
                    "$"
        )
    }
}






//      After 70 code of line add this code if needed
//      register.setOnClickListener(View.OnClickListener { _: View? ->
//           startActivity(
//               Intent(
//                   this@LoginFragment,
//                   RegistrationFormActivity::class.java
//               )
//           )
//       })
//
//       forgotPassword.setOnClickListener(View.OnClickListener { _: View? ->
//           startActivity(
//               Intent(
//                   this@LoginFragment,
//                   ForgotPassword::class.java
//               )
//           )
//       })



//    After 156 line of code add this code of lines if needed
//    private fun signInUser() {
//        jAuth.createUserWithEmailAndPassword(email!!,password!!).addOnCompleteListener { it ->
//            if (it.isSuccessful){
//                    // Sign in success, update UI with the signed-in user's information
//                Log.d(TAG, "createUserWithEmail:success")
//                    val user = jAuth.currentUser
//                    updateUI(user)
//                } else {
//                // If sign in fails, display a message to the user.
//                Log.w(TAG, "createUserWithEmail:failure", it.exception)
//                Toast.makeText(
//                    view?.context,
//                    "Verification Link is send to the email. Please Verify",
//                    Toast.LENGTH_SHORT).show()
//
//            }
//        }
//    }












