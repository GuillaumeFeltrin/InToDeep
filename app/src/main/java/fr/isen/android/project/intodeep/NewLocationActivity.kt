package fr.isen.android.project.intodeep

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import fr.isen.android.project.intodeep.classes.LastLocations
import fr.isen.android.project.intodeep.classes.LocationClass
import kotlinx.android.synthetic.main.activity_new_location.*

class NewLocationActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_location)
        database = FirebaseDatabase.getInstance().reference
        writeNewSite("1", "Le Togo", "43.168611", "6.60222")
        writeNewSite("2", "Le Donator", "42.998889", "6.277778")
        writeNewSite("3", "Le Grec", "42.993611", "6.278333")
        writeNewSite("4", "Le Spahis", "43.111667", "6.407778")

        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.child("diving_site").children.forEach {
                    val post = it.getValue(LocationClass::class.java)
                    var nameSite: String? = post?.name
                    var latitudeSite: String? = post?.latitude
                    var longitudeSite: String? = post?.longitude
                }

                /*textViewName.text = "${}"
                textViewLatitude.text = "${latitudeISte}"
                textViewLongitude.text = "${longitudeSite}"*/
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("errorReading", "loadPost:onCancelled", databaseError.toException())
            }
        })
    }

    private fun writeNewSite(id: String, name: String, latittude: String, longitude: String) {
        val location = LocationClass(id, name, latittude, longitude)
        database.child("diving_site").child(id).setValue(location)
    }
}
