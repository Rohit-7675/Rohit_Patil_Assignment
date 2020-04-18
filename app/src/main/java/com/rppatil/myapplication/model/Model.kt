package com.rppatil.myapplication.model

import java.io.Serializable

data class JsonData(val title: String, val description: String, val imageHref: String) :
    Serializable