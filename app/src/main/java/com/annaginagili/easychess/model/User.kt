package com.annaginagili.easychess.model

import android.net.Uri
import java.io.Serializable

data class User(val image: Uri?, val username: String, val uid: String):Serializable