package fr.isen.android.project.intodeep

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.text.set
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_modifie_membre.*

class ModifieMembreActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    private val databse: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val myRef: DatabaseReference = databse.getReference()
    private val table_name:String ="profile"
    var id:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modifie_membre)
        auth = FirebaseAuth.getInstance()

        id = id_maker(auth.currentUser?.email.toString())

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (child in dataSnapshot.child(table_name).children) {
                    if(child.key.toString() == id){
                        for(child in child.children){
                            modifie_preset(child.key.toString(), child.value.toString())
                        }
                    }
                }
            }
            override fun onCancelled(p0: DatabaseError) {

            }
        })

        modification_confim.setOnClickListener{


            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (child in dataSnapshot.child(table_name).children) {
                        if(child.key.toString() == id){
                            for(child in child.children){
                                modifie_param(child.key.toString())
                            }
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
        var gauche:String = ""

        gauche = mail.substring(0,mail.length-droit.length)

        gauche = gauche.replace(".","")

        return gauche
    }
}
