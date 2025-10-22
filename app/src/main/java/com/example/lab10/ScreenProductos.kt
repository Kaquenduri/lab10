package com.example.lab10

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import com.example.lab10.data.ProductoApiService
import com.example.lab10.data.ProductoModel
import kotlinx.coroutines.delay

import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding

import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults

import androidx.compose.runtime.getValue

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.unit.dp

import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



@Composable
fun ContenidoProductosListado(navController: NavHostController, servicio: ProductoApiService) {
    var listaProductos: SnapshotStateList<ProductoModel> = remember { mutableStateListOf() }

    LaunchedEffect(Unit) {
        val listado = servicio.selectProducto()
        listado.forEach { listaProductos.add(it) }
    }

    LazyColumn {
        item {
            Row(
                modifier = Modifier.fillParentMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ID",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(0.1f)
                )
                Text(
                    text = "NOMBRE",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(0.5f)
                )
                Text(
                    text = "PRECIO",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(0.2f)
                )
                Text(
                    text = "ACCION",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(0.2f)
                )
            }
        }

        items(listaProductos) { item ->
            Row(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .fillParentMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${item.id}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(0.1f)
                )
                Text(
                    text = item.nombre,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(0.5f)
                )
                Text(
                    text = "${item.precio}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(0.2f)
                )
                IconButton(
                    onClick = {
                        navController.navigate("productoVer/${item.id}")
                        Log.e("PRODUCTO-VER", "ID = ${item.id}")
                    },
                    modifier = Modifier.weight(0.1f)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "Ver",
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
                IconButton(
                    onClick = {
                        navController.navigate("productoDel/${item.id}")
                        Log.e("PRODUCTO-DEL", "ID = ${item.id}")
                    },
                    modifier = Modifier.weight(0.1f)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "Eliminar",
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
        }
    }
}


@Composable
fun ContenidoProductoEditar(navController: NavHostController, servicio: ProductoApiService, pid: Int = 0) {
    var id by remember { mutableStateOf<Int>(pid) }
    var nombre by remember { mutableStateOf<String?>("") }
    var precio by remember { mutableStateOf<String?>("") }
    var stock by remember { mutableStateOf<String?>("") }
    var fecha by remember { mutableStateOf<String?>("") }
    var categoria by remember { mutableStateOf<String?>("") }
    var grabar by remember { mutableStateOf(false) }

    if (id != 0) {
        LaunchedEffect(Unit) {
            val objProducto = servicio.selectProducto(id.toString())
            delay(100)
            nombre = objProducto.body()?.nombre
            precio = objProducto.body()?.precio.toString()
            stock = objProducto.body()?.stock.toString()
            fecha = objProducto.body()?.fecha
            categoria = objProducto.body()?.categoria
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = id.toString(),
            onValueChange = { },
            label = { Text("ID (solo lectura)") },
            readOnly = true,
            singleLine = true
        )
        TextField(
            value = nombre!!,
            onValueChange = { nombre = it },
            label = { Text("Nombre:") },
            singleLine = true
        )
        TextField(
            value = precio!!,
            onValueChange = { precio = it },
            label = { Text("Precio:") },
            singleLine = true
        )
        TextField(
            value = stock!!,
            onValueChange = { stock = it },
            label = { Text("Stock:") },
            singleLine = true
        )
        TextField(
            value = fecha!!,
            onValueChange = { fecha = it },
            label = { Text("Fecha Publicacion:") },
            singleLine = true
        )
        TextField(
            value = categoria!!,
            onValueChange = { categoria = it },
            label = { Text("Categoria:") },
            singleLine = true
        )
        Button(
            onClick = {
                grabar = true
            }
        ) {
            Text("Grabar", fontSize = 16.sp)
        }
    }

    if (grabar) {
        val objProducto = ProductoModel(
            id,
            nombre!!,
            precio!!.toFloat(),
            stock!!.toInt(),
            fecha!!,
            categoria!!
        )
        LaunchedEffect(Unit) {
            if (id == 0)
                servicio.insertProducto(objProducto)
            else
                servicio.updateProducto(id.toString(), objProducto)
        }
        grabar = false
        navController.navigate("productos")
    }
}


@Composable
fun ContenidoProductoEliminar(navController: NavHostController, servicio: ProductoApiService, id: Int) {
    var showDialog by remember { mutableStateOf(true) }
    var borrar by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Confirmar Eliminacion") },
            text = { Text("¿Esta seguro de eliminar el Producto?") },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        borrar = true
                    }
                ) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                Button(onClick = {
                    showDialog = false
                    navController.navigate("productos")
                }) {
                    Text("Cancelar")
                }
            }
        )
    }

    if (borrar) {
        LaunchedEffect(Unit) {
            servicio.deleteProducto(id.toString())
            borrar = false
            navController.navigate("productos")
        }
    }
}




@Composable
fun ProductosApp(navController: NavHostController) {
    val urlBase = "http://10.0.2.2:8000/" // o tu IP si usarás un dispositivo externo
    val retrofit = Retrofit.Builder().baseUrl(urlBase)
        .addConverterFactory(GsonConverterFactory.create()).build()
    val servicio = retrofit.create(ProductoApiService::class.java)

    Scaffold(
        modifier = Modifier.padding(top = 40.dp),
        topBar = { BarraSuperiorProducto() },
        bottomBar = { BarraInferiorProducto(navController) },
        floatingActionButton = { BotonFABProducto(navController, servicio) },
        content = { paddingValues -> ContenidoProducto(paddingValues, navController, servicio) }
    )
}

@Composable
fun BotonFABProducto(navController: NavHostController, servicio: ProductoApiService) {
    val cbeState by navController.currentBackStackEntryAsState()
    val rutaActual = cbeState?.destination?.route
    if (rutaActual == "productos") {
        FloatingActionButton(
            containerColor = Color.Magenta,
            contentColor = Color.White,
            onClick = { navController.navigate("productoNuevo") }
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add"
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraSuperiorProducto() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "PRODUCTOS APP",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun BarraInferiorProducto(navController: NavHostController) {
    NavigationBar(
        containerColor = Color.LightGray
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") },
            selected = navController.currentDestination?.route == "inicio",
            onClick = { navController.navigate("inicio") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.Favorite, contentDescription = "Productos") },
            label = { Text("Productos") },
            selected = navController.currentDestination?.route == "productos",
            onClick = { navController.navigate("productos") }
        )
    }
}

@Composable
fun ContenidoProducto(
    pv: PaddingValues,
    navController: NavHostController,
    servicio: ProductoApiService
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(pv)
    ) {
        NavHost(
            navController = navController,
            startDestination = "productos" // Ruta de inicio
        ) {

            composable("productos") { ContenidoProductosListado(navController, servicio) }
            composable("productoNuevo") {
                ContenidoProductoEditar(navController, servicio, 0)
            }
            composable("productoVer/{id}", arguments = listOf(
                navArgument("id") { type = NavType.IntType })
            ) {
                ContenidoProductoEditar(navController, servicio, it.arguments!!.getInt("id"))
            }
            composable("productoDel/{id}", arguments = listOf(
                navArgument("id") { type = NavType.IntType })
            ) {
                ContenidoProductoEliminar(navController, servicio, it.arguments!!.getInt("id"))
            }
        }
    }
}






