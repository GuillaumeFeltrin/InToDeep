package fr.isen.android.project.intodeep

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.isen.android.project.intodeep.adapters.BasicsAdapter
import fr.isen.android.project.intodeep.adapters.GoodsAdapter
import kotlinx.android.synthetic.main.activity_basics.*
import kotlinx.android.synthetic.main.activity_goods.*
import kotlinx.android.synthetic.main.activity_memo.*
import kotlinx.android.synthetic.main.activity_memo.myBackgroundLayout

class BasicsActivity : AppCompatActivity() {

    lateinit var toolbar: ActionBar
    lateinit var frameAnimation: AnimationDrawable

    val basics: ArrayList<String> = ArrayList()
    val descriptionBasics: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basics)

        toolbar = supportActionBar!!
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_nav_bar)
        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        frameAnimation = myBackgroundLayout.getBackground() as AnimationDrawable
        frameAnimation.setEnterFadeDuration(4500)
        frameAnimation.setExitFadeDuration(4500)
        frameAnimation.start()

        addBasics()
        addBasicsDescription()
        rvBasics.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvBasics.layoutManager = GridLayoutManager(this, 1)
        rvBasics.adapter = BasicsAdapter(basics, descriptionBasics, this)
    }

    private fun addBasics(){
        basics.add("Il est important de bien manger")
        basics.add("Une bonne hydratation avant l'effort")
        basics.add("Savoir se faire confiance dans le sport")
        basics.add("Ne jamais plonger seul(e)")
        basics.add("Bien contrôler sa remontée à la surface")
        basics.add("Bien comprendre le pallier de sécurité")
        basics.add("Respectez l’écart entre deux plongées")
        basics.add("Ne plongez pas avant de prendre l’avion")
        basics.add("Pas de sport violent après la plongée")
        basics.add("Si vous ne vous sentez pas bien en remontant, dites-le")
    }

    private fun addBasicsDescription(){
        descriptionBasics.add("La plongée n’en a pas l’air au premier abord, mais c’est un sport qui demande énormément d’énergie à... : porter du matériel qui hors de l’eau pèse plusieurs kilos, lutter contre l’effet de la saturation de l’azote, se réchauffer dans l’eau, le palmage ... Veiller donc à ne pas plonger le ventre vide .")
        descriptionBasics.add("De même, vous vous déshydratez énormément en plongée. « La plongée modifie l’activité physiologique de notre corps. Dans l’eau, le sang se concentre autour des organes vitaux. Pour faire baisser la pression, le corps se dégage de toute l’eau dans le sang. D’où cette envie irrépressible de faire pipi après chaque plongée !\n" +
                "Et de poursuivre sa démonstration : « A cela, s’ajoute le fait que l’air de la bouteille est très sec donc il faut l’humidifier pour favoriser les échanges gazeux. » En clair: buvez beaucoup d’eau, de thé, de jus de fruit… Tout, sauf de l’alcool évidemment.")
        descriptionBasics.add("Cette règle s’applique à tous les sports et toutes les activités de plein-air mais il est parfois important de répéter les bases : faites-vous confiance. S’il y a trop de mer, que vous vous sentez patraque ou que tout simplement vous ne vous sentez pas à l’aise, ne vous forcez pas.\n" +
                "« Il est nécessaire de connaître ses limites et de ne pas aller au-delà. En plongée, une crise de panique sous l’eau ou un essoufflement peuvent être dangereux. Vous n’êtes plus concentré sur les règles de base et vous pouvez faire des bêtises», assure Julien Kenee.\n" +
                "Si votre niveau vous indique de ne pas dépasser 20 mètres, c’est qu’il y a une raison. Lorsqu’on n’a jamais plongé profond, on ne sait pas comment réagira son corps, notamment face à l’ivresse des profondeurs. ")
        descriptionBasics.add("« En plongée, on n’est jamais seul sous l’eau, assure Julien Kenee. C’est un sport qui s’effectue en binôme.\n" +
                "On se surveille mutuellement et puis, c’est quand même plus sympa de découvrir les fonds sous-marins ensemble et de pouvoir parler des poissons qu’on a croisé en remontant sur le bateau ! »." +
                "La règle est simple : ne jamais s’éloigner à plus de deux mètres de son comparse pour pouvoir réagir rapidement en cas de problème. Si votre binôme fait un malaise, a un problème avec son détendeur ou a besoin d’air rapidement, vous devez pouvoir être là en trois coups de palmes.")
        descriptionBasics.add("C’est la règle de base : en plongée, il est indispensable de remonter lentement afin de laisser le temps à l’organisme d’éliminer l’azote, sans quoi on risque un accident de décompression.\n" +
                "On estime qu’une remontée idéale n’excède pas 10 à 15 mètres par minute. Si vous avez un ordinateur de plongée, vous n’aurez pas à vous soucier de cette question car il est paramétré pour sonner si vous vous prenez pour une fusée.")
        descriptionBasics.add("Pour être sûr d’éliminer au maximum l’azote qui se trouve dans l’organisme, l’immense majorité des plongeurs font un « palier de sécurité ». Le principe est simple :\n" +
                "une « pause » de trois minutes entre 3 et 5 mètres de profondeur\n" +
                "Si vous êtes dans un jardin corallien, vous ne verrez pas le temps filer, sinon prenez votre mal en patience. « Ce palier n’est pas obligatoire contrairement à ceux que l’on fait lors des plongées profondes et/ou longues, assure Julien Kenee.")
        descriptionBasics.add("Plonger plusieurs fois par jour, c’est possible, mais à condition de respecter un « intervalle de surface ». En général, on considère qu’il faut rester au minimum deux ou trois heures à la surface avant de replonger. De même, si vous comptez explorer les fonds marins plusieurs fois dans la journée, prévoyez de faire la plongée la plus profonde en premier.")
        descriptionBasics.add("Vous avez beau avoir été particulièrement prudent pendant votre remontée et respecté à la lettre les paliers de sécurité, votre organisme n’a pas eu le temps d’éliminer tout l’azote qu’il contient. Pas de panique, il poursuit son travail à la surface et toutes ces petites « bulles » disparaîtront sans dommage. A condition de ne pas prendre l’avion immédiatement après votre plongée.\n" +
                "La pressurisation des cabines pourrait en effet entraîner un accident de décompression. Pour une plongée simple, il faut compter en moyenne 12 heures avant de prendre l’avion et 24 heures si vous avez plongé plusieurs fois.")
        descriptionBasics.add("Toujours à cause de la saturation en azote des tissus, il est recommandé de ne pas faire d’apnée ni de sport après une plongée. Un effort violent favorise en effet les accidents de décompression. Ce serait quand même dommage de se blesser après avoir plongé.")
        descriptionBasics.add("Même s’il y a de grandes chances pour que ce ne soit pas grave, ne restez surtout pas avec votre malaise : ces signes peuvent être les symptômes d’un accident de décompression.\n"+"\n"+"\n"+"\n"+"\n")
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
