package fr.isen.android.project.intodeep.classes

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class AdvicesClass (
    var idAdvice: String = "",
    var nameAdvice: String = "",
    var category: String = "",
    var descriptionAdvice: String = ""
)