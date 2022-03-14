package de.joeybag.joeybag
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.SignInButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import de.joeybag.joeybag.ui.home.RecyclerAdapter
import de.joeybag.joeybag.ui.login.ForgotPasswordPage
import de.joeybag.joeybag.ui.login.RegisterPage
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.regex.Pattern


class MainActivity : AppCompatActivity() {

    private lateinit var inputEmail: EditText
    private lateinit var inputPassword: EditText
    private lateinit var login: Button
    private lateinit var register: Button
    private lateinit var forgotPassword: Button
    private lateinit var googleLogIn: SignInButton

    var email: String? = ""
    var password: String? = ""

    private lateinit var jAuth: FirebaseAuth
    private lateinit var jUser: FirebaseUser
    private lateinit var db: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputEmail = findViewById(R.id.email_login)
        inputPassword = findViewById(R.id.password)
        login = findViewById(R.id.login_btn)
        register = findViewById(R.id.register_btn)
        forgotPassword = findViewById(R.id.forgot_pwd_btn)
        googleLogIn = findViewById(R.id.google_icon)

        jAuth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance()

        login.setOnClickListener(View.OnClickListener {
            checkCredentials()
        })

        register.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    RegisterPage::class.java
                )
            )
        })

        forgotPassword.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    ForgotPasswordPage::class.java
                )
            )
        })

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
                    Toast.makeText(applicationContext,"Authentication failed",Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(applicationContext,"Authentication failed",Toast.LENGTH_SHORT).show()
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

//    private var layoutManager: RecyclerView.LayoutManager? = null
//    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null

//    lateinit var googleLogIn: SignInButton
//
//    private lateinit var mGoogleSignInClient: GoogleSignInClient
//    private lateinit var jAuth: FirebaseAuth

//    override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = jAuth.currentUser
//        //updateUI(currentUser)
//
//        if (currentUser!=null) {
//            val intent = Intent(this,ProfileFragment::class.java)
//            startActivity(intent)
//        }
//    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//       setContentView(R.layout.activity_main)
//
//        layoutManager = LinearLayoutManager(this)
//
//        recyclerView.layoutManager = layoutManager
//
//        adapter = RecyclerAdapter()
//        recyclerView.adapter = adapter

//        jAuth = Firebase.auth
//        googleLogIn = findViewById(R.id.google_icon)

    }



//    private fun createRequest() {
//        // Configure Google Sign In
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()
//
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
//    }

//    private fun signIn() {
//        val signInIntent = mGoogleSignInClient.signInIntent
//        startActivityForResult(signInIntent, RC_SIGN_IN)
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                val account = task.getResult(ApiException::class.java)!!
//                //Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
//                firebaseAuthWithGoogle(account.idToken!!)
//            } catch (e: ApiException) {
//                // Google Sign In failed, update UI appropriately //Log.w(TAG, "Google sign in failed", e)
//
//                Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
//
//            }
//        }
//    }

//    private fun firebaseAuthWithGoogle(idToken: String) {
//        val credential = GoogleAuthProvider.getCredential(idToken, null)
//        jAuth.signInWithCredential(credential)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information // Log.d(TAG, "signInWithCredential:success")
//
//                    val user = jAuth.currentUser
//                    val intent = Intent(this,ProfileFragment::class.java)
//                    startActivity(intent)
//
//                    } else {
//                    // If sign in fails, display a message to the user. //  Log.w(TAG, "signInWithCredential:failure", task.exception)
//                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
//
//                }
//            }
//    }

//}