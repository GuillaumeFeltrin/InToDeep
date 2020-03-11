package fr.isen.android.project.intodeep

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import fr.isen.android.project.intodeep.classes.AdvicesClass
import fr.isen.android.project.intodeep.classes.LocationClass
import kotlinx.android.synthetic.main.activity_add_advice.*
import kotlinx.android.synthetic.main.activity_add_spot.*
import kotlinx.android.synthetic.main.activity_memo.*
import kotlinx.android.synthetic.main.activity_memo.myBackgroundLayout

class AddAdviceActivity : AppCompatActivity() {

    lateinit var database: DatabaseReference
    lateinit var storage: FirebaseStorage
    lateinit var toolbar: ActionBar
    lateinit var frameAnimation: AnimationDrawable

    var image_uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_advice)

        toolbar = supportActionBar!!
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_nav_bar)
        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        frameAnimation = myBackgroundLayout.getBackground() as AnimationDrawable
        frameAnimation.setEnterFadeDuration(4500)
        frameAnimation.setExitFadeDuration(4500)
        frameAnimation.start()

        database = FirebaseDatabase.getInstance().reference
        storage = FirebaseStorage.getInstance()
        addManuallySpot()
        buttonSubmitAdvice.setOnClickListener{
            addAdviceToDatabase(database)
            intent= Intent(this, MemoActivity::class.java)
            startActivity(intent)
        }
    }

    fun addAdviceToDatabase(firebaseData: DatabaseReference) {

        var name: String? = null
        var category: String? = null
        var description: String? = null
        name = nameAdvice.text.toString()
        category = categoryAdvice.text.toString()
        description = descriptionAdvice.text.toString()
        val newAdvice = AdvicesClass("1", name, category, description)
        val key = firebaseData.child("advices").push().key ?: ""
        newAdvice.id = key
        var id_advice = newAdvice.id.toString()
        firebaseData.child("advices").child(key).setValue(newAdvice)

        val storage_ref = storage.reference
        var path : String? = null
        path = id_advice + ".jpg"


        val img_ref = storage_ref.child(path)
        image_uri?.let{
            img_ref.child(path).putFile(it)
        }

    }

    fun addManuallySpot(){
        writeNewSite("1", "L'apéro", "Pratiques", "L'apéro c'est rigolo")


        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.child("advices").children.forEach {

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

    private fun writeNewSite(id: String, name: String, category: String, description: String) {
        val location = AdvicesClass(id, name, category, description)
        database.child("advices").child(id).setValue(location)
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.memo_item -> {
                intent= Intent(this, MemoActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.perso_item -> {
                intent= Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.feed_item -> {
                intent= Intent(this, GoogleMapInfoWindowActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.add_item -> {
                intent= Intent(this, AddSpotActivity::class.java)
                startActivity(intent)
                true
            }
        }
        false
    }
}
