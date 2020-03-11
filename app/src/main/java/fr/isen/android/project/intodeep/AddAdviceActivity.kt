package fr.isen.android.project.intodeep

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DatabaseReference
import fr.isen.android.project.intodeep.classes.LocationClass
import kotlinx.android.synthetic.main.activity_add_advice.*
import kotlinx.android.synthetic.main.activity_add_spot.*
import kotlinx.android.synthetic.main.activity_memo.*
import kotlinx.android.synthetic.main.activity_memo.myBackgroundLayout

class AddAdviceActivity : AppCompatActivity() {

    lateinit var toolbar: ActionBar
    lateinit var frameAnimation: AnimationDrawable

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
    }

    /*fun addAdviceToDatabase(firebaseData: DatabaseReference) {

        var name: String? = null
        var category: String? = null
        var description: String? = null
        name = nameAdvice.text.toString()

        description = descriptionSpot.text.toString()
        val newSpot = LocationClass("1", title, latitude, longitude, deep, description)
        val key = firebaseData.child("diving_site").push().key ?: ""
        newSpot.id = key
        var id_spot = newSpot.id.toString()
        firebaseData.child("diving_site").child(key).setValue(newSpot)

        val storage_ref = storage.reference
        var path : String? = null
        path = id_spot + ".jpg"


        val img_ref = storage_ref.child(path)
        image_uri?.let{
            img_ref.child(path).putFile(it)
        }

    }

    fun addListenerOnCheckButtons(){

    }*/

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
                intent= Intent(this, MapsActivity::class.java)
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
