package fr.isen.android.project.intodeep

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.isen.android.project.intodeep.adapters.AdminAdapter
import kotlinx.android.synthetic.main.activity_admin.*
import kotlinx.android.synthetic.main.activity_material.*
import kotlinx.android.synthetic.main.activity_memo.myBackgroundLayout

class AdminActivity : AppCompatActivity() {

    lateinit var toolbar: ActionBar
    lateinit var frameAnimation: AnimationDrawable

    val admin: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        toolbar = supportActionBar!!
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_nav_bar)
        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        frameAnimation = myBackgroundLayout.getBackground() as AnimationDrawable
        frameAnimation.setEnterFadeDuration(4500)
        frameAnimation.setExitFadeDuration(4500)
        frameAnimation.start()

        addAdmin()
        rvAdmin.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvAdmin.layoutManager = GridLayoutManager(this, 1)
        rvAdmin.adapter = AdminAdapter(admin, this)
    }

    private fun addAdmin(){
        admin.add("Carnet de plongée")
        admin.add("Preuve d’assurance")
        admin.add("Certificat médical (datant de moins d’une année)")
        admin.add("Carte de plongeur (brevet)")
        admin.add("Maillot de bain")
        admin.add("Essuie")
        admin.add("Joints de rechange (pour bouteille, détendeurs)")
        admin.add("Collation")
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.memo_item -> {
                intent= Intent(this, MemoActivity::class.java)
                startActivity(intent)
                //true
            }
            R.id.perso_item -> {
                intent= Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                //true
            }
            R.id.feed_item -> {
                intent= Intent(this, GoogleMapInfoWindowActivity::class.java)
                startActivity(intent)
                //true
            }
            R.id.add_item -> {
                intent= Intent(this, AddSpotActivity::class.java)
                startActivity(intent)
                //true
            }
        }
        false
    }
}
