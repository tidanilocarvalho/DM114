package br.com.danilo.projectdm114

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    /**
     * A function to handle the creation of the MainActivity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        userLogin()
    }

    /**
     * A function used to allow users to authenticate themselves using an existing Google account
     */
    private fun userLogin () {
        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            userLogged(user)
        } else {
            provideAuthForUserNotLogged()
        }
    }

    /**
     * A function to handle a user already logged during the creation of the MainActivity
     */
    private fun userLogged(user: FirebaseUser) {
        val name = user.displayName
        val email = user.email
        Log.i(TAG, "User logged $name with email account $email")
        setContentView(R.layout.activity_main)
    }

    /**
     * A function to handle a user not logged before during the creation of the MainActivity
     */
    private fun provideAuthForUserNotLogged() {
        val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())

        Log.i(TAG, "User not logged")

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(), 1)
    }

    /**
     * A function to inflate Menus for MainActivity
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    /**
     * A function to verify the result of the authentication
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                val user = FirebaseAuth.getInstance().currentUser
                userLogged(user!!)
            } else {
                Toast.makeText(this, "Sign in failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * A function to handle the menu events
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_sign_out -> {
                AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener {
                        this.recreate()
                    }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
