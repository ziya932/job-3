package com.example.locationshareddipitl404.model_dipti_amad_ict_l4_04

import com.google.firebase.database.PropertyName



data class UserDipti_amad_ict_L4_04(
    val userId: String,

    @get:PropertyName("displayName")
    @set:PropertyName("displayName")
    var displayName: String = "",

    @get:PropertyName("email")
    @set:PropertyName("email")
    var email: String = "",

    @get:PropertyName("location")
    @set:PropertyName("location")
    var location: String = ""
) {
    // No-argument constructor
    constructor() : this("", "", "")
}