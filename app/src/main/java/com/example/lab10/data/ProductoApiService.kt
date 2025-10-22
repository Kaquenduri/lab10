package com.example.lab10.data
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductoApiService {
    @GET("tienda/producto")
    suspend fun selectProducto():ArrayList<ProductoModel>

    @GET("tienda/producto/{id}")
    suspend fun selectProducto(@Path("id") id:String): Response<ProductoModel>

    @Headers("Content-Type: application/json")
    @POST("tienda/producto")
    suspend fun insertProducto(@Body producto: ProductoModel): Response<ProductoModel>

    @PUT("tienda/producto/{id}")
    suspend fun updateProducto(@Path("id") id:String, @Body producto: ProductoModel): Response<ProductoModel>

    @DELETE("tienda/producto/{id}")
    suspend fun deleteProducto(@Path("id") id:String): Response<ProductoModel>
}