package fr.isen.android.project.intodeep

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.core.text.set
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_member.*
import kotlinx.android.synthetic.main.activity_modifie_membre.*
import kotlinx.android.synthetic.main.activity_modifie_membre.myBackgroundLayout

class ModifieMembreActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    private val databse: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val myRef: DatabaseReference = databse.getReference()
    private val table_name:String ="profile"
    var id:String=""

    lateinit var toolbar: ActionBar
    lateinit var frameAnimation: AnimationDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modifie_membre)

        toolbar = supportActionBar!!
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_nav_bar)
        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        frameAnimation = myBackgroundLayout.getBackground() as AnimationDrawable
        frameAnimation.setEnterFadeDuration(4500)
        frameAnimation.setExitFadeDuration(4500)
        frameAnimation.start()

        auth = FirebaseAuth.getInstance()

        id = id_maker(auth.currentUser?.email.toString())

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (child in dataSnapshot.child(table_name).children) {
                    if(child.key.toString() == id){
                        for(childd in child.children){
                            modifie_preset(childd.key.toString(), childd.value.toString())
                        }
                    }
                }
            }
            override fun onCancelled(p0: DatabaseError) {

            }
        })

        modification_confim.setOnClickListener{


            myRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (child in dataSnapshot.child(table_name).children) {
                        if(child.key.toString() == id){
                            for(childD in child.children){
                                modifie_param(childD.key.toString())
                            }
                            break
                        }
                    }
                }
                override fun onCancelled(p0: DatabaseError) {

                }
            })
            startActivity(Intent(this, MemberActivity::class.java))
        }

    }

    private fun modifie_preset(key: String, value: String) {
        when(key){
            "nom"->{modifier_nom.setText(value)}
            "prenom"->{modifier_prenom.setText(value)}
            "descritpion"->{modifie_description.setText(value)}
            "niveau"->{modifier_niveau.setText(value)}
            else->{return}
        }
    }

    private fun modifie_param(key: String) {
        when(key){
            "nom"->{change_name(key)}
            "prenom"->{change_prenom(key)}
            "niveau"->{change_niveau(key)}
            "descritpion"->{change_description(key)}
            else->{return}
        }

    }

    private fun change_name(key: String) {
        myRef.child(table_name).child(id).child(key).setValue(modifier_nom.text.toString())
    }

    private fun change_prenom(key: String) {
        myRef.child(table_name).child(id).child(key).setValue(modifier_prenom.text.toString())
    }

    private fun change_description(key: String) {
        myRef.child(table_name).child(id).child(key).setValue(modifie_description.text.toString())
    }

    private fun change_niveau(key: String) {
        myRef.child(table_name).child(id).child(key).setValue(modifier_niveau.text.toString())
    }


    private fun id_maker(mail: String): String {
        var droit:String = "@gmail.com"
        //var gauche:String = ""

        var gauche = mail.substring(0,mail.length-droit.length)

        gauche = gauche.replace(".","")

        return gauche
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
