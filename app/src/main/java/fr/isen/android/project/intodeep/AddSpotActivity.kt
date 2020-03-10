package fr.isen.android.project.intodeep

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import fr.isen.android.project.intodeep.classes.LocationClass
import kotlinx.android.synthetic.main.activity_add_spot.*
import kotlinx.android.synthetic.main.activity_add_spot.bottom_nav_bar
import kotlinx.android.synthetic.main.activity_memo.*

class AddSpotActivity : AppCompatActivity() {

    lateinit var database: DatabaseReference
    lateinit var auth: FirebaseAuth
    lateinit var storage: FirebaseStorage
    lateinit var toolbar: ActionBar

    val GALLERY = 1
    val CAMERA = 2
    val PERMISSION_CODE = 1000
    var image_uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_spot)

        toolbar = supportActionBar!!
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_nav_bar)
        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        storage = FirebaseStorage.getInstance()
        database = FirebaseDatabase.getInstance().reference

        addManuallySpot()

        pictureButton.setOnClickListener{
            showPictureDialog()
        }

        buttonSubmit.setOnClickListener{
            addToDatabase(database)
            intent= Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
    }

    fun addToDatabase(firebaseData: DatabaseReference) {

        var title : String? =  null
        var latitude: String? = null
        var longitude: String? = null
        var deep: String? = null
        var description : String? =  null
        title = nameSpot.text.toString()
        latitude = lattitudeSpot.text.toString()
        longitude = longitudeSpot.text.toString()
        deep = deepSpot.text.toString()
        description = descriptionSpot.text.toString()
        val newSpot = LocationClass("1", title, latitude, longitude, deep, description)
        val key = firebaseData.child("diving_site").push().key ?: ""
        newSpot.id = key
        var id_spot = newSpot.id.toString()
        firebaseData.child("diving_site").child(key).setValue(newSpot)

        val storage_ref = storage.reference
        var path : String? = null
        path = id_spot + ".jpg"


        val img_ref = storage_ref.child(path)
        image_uri?.let{
            img_ref.child(path).putFile(it)
        }

    }

    fun addManuallySpot(){
        writeNewSite("1", "Le Togo", "43.168611", "6.60222")
        writeNewSite("2", "Le Donator", "42.998889", "6.277778")
        writeNewSite("3", "Le Grec", "42.993611", "6.278333")
        writeNewSite("4", "Le Spahis", "43.111667", "6.407778")

        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.child("diving_site").children.forEach {
                    val post = it.getValue(LocationClass::class.java)
                    Log.d("lol", post?.toString())
                    var nameSite: String? = post?.name
                    var latitudeSite: String? = post?.latitude
                    var longitudeSite: String? = post?.longitude
                }

                /*textViewName.text = "${}"
                textViewLatitude.text = "${latitudeISte}"
                textViewLongitude.text = "${longitudeSite}"*/
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("errorReading", "loadPost:onCancelled", databaseError.toException())
            }
        })
    }

    private fun writeNewSite(id: String, name: String, latittude: String, longitude: String) {
        val location = LocationClass(id, name, latittude, longitude)
        database.child("diving_site").child(id).setValue(location)
    }

    fun showPictureDialog() {

        val pictureDialogBuilder = AlertDialog.Builder(this)

        pictureDialogBuilder.setNegativeButton(
            "Fermer",
            DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })

        val pictureDialogItems = arrayOf(
            "Selectionner une photo depuis la gallerie",
            "Prendre une photo depuis la camera"
        )
        pictureDialogBuilder.setItems(
            pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> choosePhotoFromGallary()
                1 -> takePhotoFromCamera()
            }

        }
        val alert = pictureDialogBuilder.create()
        alert.setTitle("Veuillez faire un choix")
        alert.show()
    }

    fun choosePhotoFromGallary(){
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(galleryIntent, GALLERY)
    }
    fun takePhotoFromCamera(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED ||
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED){
                //permission was not enabled
                val permission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                //show popup to request permission
                requestPermissions(permission, PERMISSION_CODE)
            }
            else{
                //permission already granted
                openCamera()
            }
        }
        else{
            //system os is < marshmallow
            openCamera()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if(resultCode == Activity.RESULT_OK)
        {
            if(requestCode == GALLERY)
            {
                pictureButton.setImageURI(data?.data)
                image_uri =  data?.data
            }
            if(requestCode == CAMERA)
            {
                pictureButton.setImageURI(image_uri)

            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        //called when user presses ALLOW or DENY from Permission Request Popup
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup was granted
                    openCamera()
                }
                else{
                    //permission from popup was denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, CAMERA)

    }

    fun requestPermission(permission: String, requestCode: Int, handler: () -> Unit)
    {

        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                Toast.makeText(
                    this,
                    "Merci d'accepter les permissions dans vos parametres",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
            }
        } else {
            handler()
        }
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
                intent= Intent(this, MapsActivity::class.java)
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
