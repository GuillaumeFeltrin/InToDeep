package fr.isen.android.project.intodeep.classes

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class AdvicesClass (
    var id: String = "",
    var name: String = "",
    var category: String = "",
    var description: String = ""
)