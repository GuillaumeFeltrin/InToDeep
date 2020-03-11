package fr.isen.android.project.intodeep

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val databse: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val myRef: DatabaseReference = databse.getReference()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        switchActivityLoadingButton.setOnClickListener {
            startActivity(Intent(this, loading::class.java))
        }

        map_button.setOnClickListener {
            startActivity(Intent(this, GoogleMapInfoWindowActivity::class.java))
        }

        buttonLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        buttonHome.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        /*button_profile.setOnClickListener{
            val intent = Intent(this, profile::class.java)
            startActivity(intent)
        }*/
    }
}
