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
import androidx.navigation.findNavController
import br.com.danilo.projectdm114.messaging.ORDER_DETAIL_MESSAGING_KEY
import br.com.danilo.projectdm114.orderfcm.OrderDetailInfoFragmentDirections
import br.com.danilo.projectdm114.orderlist.OrderListFragmentDirections
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        userLogin()
    }

    private fun userLogin () {
        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            userLogged(user)
        } else {
            provideAuthForUserNotLogged()
        }
    }

    private fun userLogged(user: FirebaseUser) {
        val name = user.displayName
        val email = user.email

        Log.i(TAG, "User logged $name with email account $email")

        setContentView(R.layout.activity_main)

        if (this.intent.hasExtra(ORDER_DETAIL_MESSAGING_KEY)) {
            showOrderDetailInfo(intent.getStringExtra(ORDER_DETAIL_MESSAGING_KEY))
        }
    }

    private fun provideAuthForUserNotLogged() {
        val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())

        Log.i(TAG, "User not logged")

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(), 1)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

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
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_order_detail_list -> {
                this.findNavController(R.id.nav_host_fragment).navigate(OrderListFragmentDirections.actionShowOrderList())
                return true
            }

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

    override fun onNewIntent(intent: Intent) {
        if (intent.hasExtra("orderDetail")) {
            showOrderDetailInfo(intent.getStringExtra(ORDER_DETAIL_MESSAGING_KEY)!!)
        }
        super.onNewIntent(intent)
    }

    private fun showOrderDetailInfo(orderDetailInfo: String) {
        this.findNavController(R.id.nav_host_fragment)
            .navigate(OrderDetailInfoFragmentDirections.actionShowOrderDetailInfo(orderDetailInfo))
    }
}
