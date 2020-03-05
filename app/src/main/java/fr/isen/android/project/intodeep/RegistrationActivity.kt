package fr.isen.android.project.intodeep

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {

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
                    // Sign in success, update UI with the signed-in user'sq information
                    Log.d("subscription", "createUserWithEmail:success")
                    intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("subscription", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Registration failed" , Toast.LENGTH_SHORT).show()
                }
            }
    }
}
