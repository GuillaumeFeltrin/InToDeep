package fr.isen.android.project.intodeep.classes

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class LocationClass (
    var id: String,
    var locationId: String?,
    var name: String?,
    var latitude: String?,
    var longitude: String?
)