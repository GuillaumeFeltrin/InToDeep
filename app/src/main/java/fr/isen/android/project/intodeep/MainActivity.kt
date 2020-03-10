package fr.isen.android.project.intodeep

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        myRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnap: DataSnapshot) {
                /*val post = dataSnap.child("diving_site").getValue()*/

                for (child in dataSnap.child("diving_site").children) {
                    //Log.v("TAG", "appel de base : $child")

                }
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }
}
