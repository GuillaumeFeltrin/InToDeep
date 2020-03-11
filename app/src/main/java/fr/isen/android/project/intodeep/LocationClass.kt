package fr.isen.android.project.intodeep

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class LocationClass (
    var id: String = "",
    var name: String = "",
    var latitude: String = "",
    var longitude: String ="",
    var description: String="",
    var deep: String=""
)