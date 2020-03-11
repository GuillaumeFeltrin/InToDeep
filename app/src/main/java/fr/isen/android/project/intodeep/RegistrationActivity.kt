package fr.isen.android.project.intodeep

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {

    private val databse: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val myRef: DatabaseReference = databse.getReference()

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        auth = FirebaseAuth.getInstance()

        registerButton.setOnClickListener{
            if(passwordTextEdit.text.toString() == confirmTextEdit.text.toString()){
                createUser(emailTextEdit.text.toString(),passwordTextEdit.text.toString())
            }
        }
    }
    fun createUser(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //
                    var droit:String="@gmail.com"
                    var user = User(email,password)
                    var tailleGauche:Int = email.length - droit.length
                    //var string:String = corrige_chaine(email)
                    var key:String= email.substring(0,tailleGauche)
                    key = corrige_chaine(key)
                    myRef.child("profile").child(key).setValue(user)
                    //
                    Log.d("subscription", "createUserWithEmail:success")
                    intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.w("subscription", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Registration failed" , Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun corrige_chaine(email: String): String {
        var string:String = email
        Log.v("_inscription","avant : $string")
        string = string.replace(".","")
        Log.v("_inscription","apres : $string")
        return string

    }
}
