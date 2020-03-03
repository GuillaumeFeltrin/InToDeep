package fr.isen.android.project.intodeep

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        map_button.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }

        buttonLogin.setOnClickListener {
            val intent = Intent(this, NewLocationActivity::class.java)
            startActivity(intent)
        }
    }
}
