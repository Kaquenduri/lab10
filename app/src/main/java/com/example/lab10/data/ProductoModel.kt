package com.example.lab10.data
import com.google.gson.annotations.SerializedName
data class ProductoModel(
    @SerializedName("id")
    var id:Int,
    @SerializedName("nombre")
    var nombre:String,
    @SerializedName("precio")
    var precio:Float,
    @SerializedName("stock")
    var stock:Int,
    @SerializedName("pub_date")
    var fecha:String,
    @SerializedName("categoria")
    var categoria:String
)
