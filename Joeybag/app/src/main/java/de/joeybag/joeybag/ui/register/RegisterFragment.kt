package de.joeybag.joeybag.ui.register

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.installations.local.PersistedInstallation
import com.google.firebase.ktx.Firebase
import de.joeybag.joeybag.R

class RegisterFragment : Fragment() {

    private lateinit var firstNameCustomer: EditText
    private lateinit var lastNameCustomer: EditText
    private lateinit var emailCustomer: EditText
    private lateinit var passwordCustomer: EditText
    private lateinit var confirmPasswordCustomer: EditText
    private lateinit var mobileNoCustomer: EditText
    private lateinit var cityCustomer: EditText
    private lateinit var addressCustomer: EditText
    private lateinit var btnRegister: Button
    private lateinit var database: FirebaseDatabase
    private lateinit var jAuth: FirebaseAuth
    var jUser: FirebaseUser? = null

    var firstName: String? = ""
    var lastName: String? = ""
    var email: String? = ""
    var password: String? = ""
    var confirmPassword: String? = ""
    private var mobileNo: String? = ""
    var city: String? = ""
    var address: String? = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_regsiter, container, false)
        firstNameCustomer = view.findViewById(R.id.firstname)
        lastNameCustomer = view.findViewById(R.id.lastname)
        emailCustomer = view.findViewById(R.id.emailaddress)
        passwordCustomer = view.findViewById(R.id.password_register)
        confirmPasswordCustomer = view.findViewById(R.id.password_confirm_register)
        mobileNoCustomer = view.findViewById(R.id.phone)
        cityCustomer = view.findViewById(R.id.city)
        addressCustomer = view.findViewById(R.id.address)
        btnRegister = view.findViewById(R.id.button_register)
        database = FirebaseDatabase.getInstance()

        btnRegister.setOnClickListener {
            firstName = firstNameCustomer.text.toString()
            lastName = lastNameCustomer.text.toString()
            email = emailCustomer.text.toString()
            password = passwordCustomer.text.toString()
            confirmPassword = confirmPasswordCustomer.text.toString()
            mobileNo = mobileNoCustomer.text.toString()
            city = cityCustomer.text.toString()
            address = addressCustomer.text.toString()

            when (true) {
                firstName.isNullOrEmpty() -> {
                    firstNameCustomer.requestFocus()
                    firstNameCustomer.error = "Empty"
                }
                lastName.isNullOrEmpty() -> {
                    lastNameCustomer.requestFocus()
                    lastNameCustomer.error = "Empty"
                }
                (!Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.isNullOrEmpty()) -> {
                    emailCustomer.requestFocus()
                    emailCustomer.error = "Empty"
                }
                password.isNullOrEmpty() -> {
                    passwordCustomer.requestFocus()
                    passwordCustomer.error = "Empty"
                }
                confirmPassword.isNullOrEmpty() -> {
                    confirmPasswordCustomer.requestFocus()
                    confirmPasswordCustomer.error = "Empty"
                }
                mobileNo.isNullOrEmpty() -> {
                    mobileNoCustomer.requestFocus()
                    mobileNoCustomer.error = "Empty"
                }
                city.isNullOrEmpty() -> {
                    cityCustomer.requestFocus()
                    cityCustomer.error = "Empty"
                }
                address.isNullOrEmpty() -> {
                    addressCustomer.requestFocus()
                    addressCustomer.error = "Empty"
                }
                else -> {
                    jAuth = FirebaseAuth.getInstance()
                    val fireStore = Firebase.firestore
                    jAuth.createUserWithEmailAndPassword(email!!, password!!)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {

                                val currUser = jAuth.currentUser
                                currUser!!.sendEmailVerification().addOnCompleteListener {
                                        Toast.makeText(
                                            view.context,
                                            "Verification Link is send to the email. Please Verify",
                                            Toast.LENGTH_LONG
                                        ).show()

                                 val registrationIntent = Intent(view.context, RegistrationSuccessfulActivity::class.java)
                                 startActivity(registrationIntent)
                                 requireActivity().finish()
                                }
                            }
                        }.addOnFailureListener {
                            Toast.makeText(
                                view.context,
                                "An Error occurred please retry, Error: ${it.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        return view
    }
}