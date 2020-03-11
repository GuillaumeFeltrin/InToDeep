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
import kotlinx.android.synthetic.main.activity_goods.*
import kotlinx.android.synthetic.main.activity_memo.myBackgroundLayout

class GoodsActivity : AppCompatActivity() {

    lateinit var toolbar: ActionBar
    lateinit var frameAnimation: AnimationDrawable

    val goods: ArrayList<String> = ArrayList()
    val descriptionGoods: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods)

        toolbar = supportActionBar!!
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_nav_bar)
        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        frameAnimation = myBackgroundLayout.getBackground() as AnimationDrawable
        frameAnimation.setEnterFadeDuration(4500)
        frameAnimation.setExitFadeDuration(4500)
        frameAnimation.start()

        addAdvices()
        addAdvicesDescription()
        rvGoods.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvGoods.layoutManager = GridLayoutManager(this, 1)
        rvGoods.adapter = GoodsAdapter(goods, descriptionGoods, this)
    }

    private fun addAdvices(){
        goods.add("Ne pas jeter de détritus")
        goods.add("Ne pas nourrir les poissons")
        goods.add("Ne pas toucher les poissons")
        goods.add("Rester à bonne distance du récif")
        goods.add("Attention à tes palmes")
        goods.add("Ajuster et attacher son équipement")
        goods.add("Ne rien ramasser au fond")
        goods.add("Ne rien jeter du bateau")
    }

    private fun addAdvicesDescription(){
        descriptionGoods.add("La pollution des eaux c'est pas top")
        descriptionGoods.add("Les poissons mangent pas la même nourriture que toi")
        descriptionGoods.add("Les poissons peuvent stresser alors lâche-les")
        descriptionGoods.add("Faut pas toucher le bord si tu veux pas tout abimer")
        descriptionGoods.add("Tu peux endommager le récif et faire mal aux autres")
        descriptionGoods.add("Le mano qui se prends dans le corail c'est pas top")
        descriptionGoods.add("Si c'est sous l'eau alors ça y reste (sauf les déchets)")
        descriptionGoods.add("La mer c'est pas une poubelle")
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
