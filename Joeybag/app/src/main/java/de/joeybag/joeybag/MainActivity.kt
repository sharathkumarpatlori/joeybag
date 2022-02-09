package de.joeybag.joeybag
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import de.joeybag.joeybag.ui.about.AboutFragment
import de.joeybag.joeybag.ui.home.HomeFragment
import de.joeybag.joeybag.ui.login.LoginFragment
import de.joeybag.joeybag.ui.partners.PartnersFragment
import de.joeybag.joeybag.ui.profile.ProfileFragment
import de.joeybag.joeybag.ui.register.RegisterFragment


class MainActivity : AppCompatActivity() {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_main)

//        jAuth = Firebase.auth
//        googleLogIn = findViewById(R.id.google_icon)

        val bottomNav =
            findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(navListener)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                HomeFragment()).commit()

            //createRequest()

//            googleLogIn.setOnClickListener(View.OnClickListener {
//                signIn()
//            })
        }
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

    private val navListener: BottomNavigationView.OnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.getItemId()) {
                R.id.nav_home -> selectedFragment = HomeFragment()
                R.id.nav_partners -> selectedFragment = PartnersFragment()
                R.id.nav_profile -> selectedFragment = ProfileFragment()
                R.id.nav_about_us -> selectedFragment = AboutFragment()
                R.id.nav_login -> selectedFragment = LoginFragment()
            }
            if (selectedFragment != null) {
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    selectedFragment
                ).commit()
            }
            true
        }

    companion object {
        private const val RC_SIGN_IN = 9001
    }

}