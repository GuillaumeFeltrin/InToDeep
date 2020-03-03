package fr.isen.android.project.intodeep

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import androidx.core.os.HandlerCompat.postDelayed
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Handler


class loading : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        var handler = Handler()
        handler.postDelayed(Runnable {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}
