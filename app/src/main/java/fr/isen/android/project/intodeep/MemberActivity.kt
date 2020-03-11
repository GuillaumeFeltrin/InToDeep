package fr.isen.android.project.intodeep

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.*

class MemberActivity : AppCompatActivity() {

    private val databse: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val myRef: DatabaseReference = databse.getReference()

    private val table_name = "profile"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member)
    }

    override fun onStart(){
        super.onStart()
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (oui in dataSnapshot.child(table_name).children) {
                    for (child in oui.children) {
                        Log.v("_profile", child.toString())
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("_profile", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
    }
}
