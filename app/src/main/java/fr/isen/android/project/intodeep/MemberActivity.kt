package fr.isen.android.project.intodeep

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_member.*


class MemberActivity : AppCompatActivity() {

    private val databse: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val myRef: DatabaseReference = databse.getReference()

    private lateinit var auth : FirebaseAuth
    private val table_name = "profile"

    private var mail_user:String ="empty"

    private var description:String ="empty"
    private var nom:String="empty"
    private var prenom:String="empty"
    private var niveau:String="empty"
    private var mail:String="empty"
    private var id:String="empty"
    private var lock:Boolean=false
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

        signOut()
        if(auth.currentUser != null){
            mail_user = auth.currentUser?.email.toString()

            myRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    id = id_maker(mail_user)
                    //Log.v("_membre","id : $id")
                    for(child in dataSnapshot.child(table_name).children) {
                        if(lock)(break)
                        Log.v("_profile","lock : $lock")
                        Log.v("_membre","id dans la base : ${child.key.toString()}")
                        if(child.key.toString() == id && !lock){
                            Log.v("_profile","$child")
                            for(child in child.children){
                                recupere_donnees(child.key.toString(),child.value.toString())
                            }
                            set_layout()
                            lock=true
                            break
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
        membre_niveau.text = niveau.toString()

    }

    private fun recupere_donnees(key: String, value: String) {
        when (key) {
            "nom"->{set_nom(value)}
            "prenom"->{set_prenom(value)}
            "mail"->{set_mail(value)}
            "descritpion"->{set_descr(value)}
            "niveau"->{set_niveau(value.toString())}
            else->{Log.v("_profile", "key : $key")}
        }
    }
    private fun set_nom(value: String) {nom = value}
    private fun set_prenom(value: String) {prenom = value}
    private fun set_mail(value: String) {mail = value}
    private fun set_descr(value: String) {description = value}
    private fun set_niveau(value: String) {niveau = value.toString()}
}
