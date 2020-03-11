package fr.isen.android.project.intodeep

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.isen.android.project.intodeep.adapters.GoodsAdapter
import fr.isen.android.project.intodeep.adapters.MaterialAdapter
import kotlinx.android.synthetic.main.activity_goods.*
import kotlinx.android.synthetic.main.activity_material.*
import kotlinx.android.synthetic.main.activity_memo.*
import kotlinx.android.synthetic.main.activity_memo.myBackgroundLayout

class MaterialActivity : AppCompatActivity() {

    lateinit var toolbar: ActionBar
    lateinit var frameAnimation: AnimationDrawable

    val materials: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material)

        toolbar = supportActionBar!!
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_nav_bar)
        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        frameAnimation = myBackgroundLayout.getBackground() as AnimationDrawable
        frameAnimation.setEnterFadeDuration(4500)
        frameAnimation.setExitFadeDuration(4500)
        frameAnimation.start()

        addMaterials()
        rvMaterials.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvMaterials.layoutManager = GridLayoutManager(this, 1)
        rvMaterials.adapter = MaterialAdapter(materials, this)

    }

    private fun addMaterials(){
        materials.add("Bouteille(s) (gonflée(s) de préférence)")
        materials.add("Détendeur (incluant manomètre et Direct System adapté)")
        materials.add("Octopus (ou tout autre détendeur de secours) ")
        materials.add("Combinaison de plongée")
        materials.add("Palmes")
        materials.add("Ceinture de lest et/ou poches à plombs (avec le poids suffisant)")
        materials.add("Masque")
        materials.add("Gilet stabilisateur")
        materials.add("Chaussons")
        materials.add("Gants")
        materials.add("Cagoule")
        materials.add("Lampe (chargée)")
        materials.add("Ordinateur")
        materials.add("Couteau")
        materials.add("Boussole")
        materials.add("Souris (sous-veste)")
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
