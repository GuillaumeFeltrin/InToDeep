package fr.isen.android.project.intodeep

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_member.*
import kotlinx.android.synthetic.main.activity_member.myBackgroundLayout
import kotlinx.android.synthetic.main.activity_memo.*


class MemberActivity : AppCompatActivity() {

    private val databse: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val myRef: DatabaseReference = databse.getReference()

    private lateinit var auth : FirebaseAuth
    private val table_name = "profile"

    private var mail_user:String =""

    private var description:String =""
    private var nom:String=""
    private var prenom:String=""
    private var niveau:String=""
    private var mail:String=""
    private var id:String=""

    lateinit var toolbar: ActionBar
    lateinit var frameAnimation: AnimationDrawable

    fun signOut() {
        auth = FirebaseAuth.getInstance()
        button_voila_il_est_tard.setOnClickListener {
            auth.signOut()
            auth.addAuthStateListener {
                intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v("_profile", "on create")
        auth = FirebaseAuth.getInstance()
        Log.v("_profile","un utilisateur est connecté ${auth.currentUser?.email.toString()}")
        setContentView(R.layout.activity_member)

        toolbar = supportActionBar!!
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_nav_bar)
        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        frameAnimation = myBackgroundLayout.getBackground() as AnimationDrawable
        frameAnimation.setEnterFadeDuration(4500)
        frameAnimation.setExitFadeDuration(4500)
        frameAnimation.start()

        signOut()
        if(auth.currentUser != null){
            mail_user = auth.currentUser?.email.toString()

            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    id = id_maker(mail_user)
                    //Log.v("_membre","id : $id")
                    for(child in dataSnapshot.child(table_name).children) {
                        //Log.v("_membre","id dans la base : ${child.key.toString()}")
                        if(child.key.toString() == id){
                            Log.v("_profile","$child")
                            for(child in child.children){
                                recupere_donnees(child.key.toString(),child.value.toString())
                            }
                            set_layout()
                            return
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

        member_modify_button.setOnClickListener{
            startActivity(Intent(this, ModifieMembreActivity::class.java))
        }
    }



    private fun id_maker(mail: String): String {
        var droit:String = "@gmail.com"
        var gauche:String = ""

        gauche = mail.substring(0,mail.length-droit.length)

        gauche = gauche.replace(".","")

        return gauche
    }

    private fun set_layout() {
        membre_nom.text = nom
        membre_email.text = mail
        membre_prenom.text = prenom
        membre_description.text = description
        membre_niveau.text = niveau

    }

    private fun recupere_donnees(key: String, value: String) {
        when (key) {
            "nom"->{set_nom(value)}
            "prenom"->{set_prenom(value)}
            "mail"->{set_mail(value)}
            "descritpion"->{set_descr(value)}
            "niveau"->{set_niveau(value)}
            else->{Log.v("_profile", "key : $key"); return }
        }
    }
    private fun set_nom(value: String) {nom = value}
    private fun set_prenom(value: String) {prenom = value}
    private fun set_mail(value: String) {mail = value}
    private fun set_descr(value: String) {description = value}
    private fun set_niveau(value: String) {niveau = value}

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
