package fr.isen.android.project.intodeep

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import fr.isen.android.project.intodeep.classes.LastLocations
import fr.isen.android.project.intodeep.classes.LocationClass
import kotlinx.android.synthetic.main.activity_new_location.*

class NewLocationActivity : AppCompatActivity() {
    lateinit var database: DatabaseReference
    lateinit var storage: FirebaseStorage

    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_location)
        database = FirebaseDatabase.getInstance().reference
        storage = FirebaseStorage.getInstance()
        buttonTest.setOnClickListener {
            downloadLocations()
        }
    }

    fun downloadLocations(){
        val locations = database.child("locations")

        locations.addValueEventListener(object : ValueEventListener
        {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                val locations = ArrayList<LocationClass>()

                for(postSnapshot in p0.children )
                {
                    val p = postSnapshot.value as HashMap<String, String>
                    val locationId = p["locationId"]
                    val name = p["name"]
                    val latitude = p["latitude"]
                    val longitude = p["longitude"]
                    val id = p["id"]
                    id?.let{
                        val publi = LocationClass(id, locationId, name, latitude, longitude)
                        locations.add(publi)
                    }
                }
                locations.reverse()
                recyclerAndDatabaseHandler(locations)
            }
        })
    }

    fun recyclerAndDatabaseHandler(locations : ArrayList<LocationClass>){
        val tabLocations = LastLocations()
        val publi = LocationClass(
            id = "123", locationId = "lol", name = "Le Donator",
            latitude = "42.998889", longitude = "6.277778"
         )
        val publi2 = LocationClass(
            id = "1234", locationId = "lol", name = "Le Togo",
            latitude = "43.168611", longitude = "6.60222"
         )
        //tabLocations.results = locations
        locations.add(publi)
        locations.add(publi2)
    }
}
