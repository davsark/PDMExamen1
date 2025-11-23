
package com.example.examen


import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examen.databinding.ActivityHomeBinding
import android.widget.ArrayAdapter

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    // AÑADIDAS: Propiedades que faltaban
    private lateinit var listaProductosCompleta: List<Producto>
    private lateinit var productoAdapter: ProductoAdapter

    private val listaCesta = mutableListOf<Producto>()
    private lateinit var cestaAdapter: CestaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        cargarProductos()
        configurarCesta()
        configurarSpinner()  // ← AÑADIDA
    }

    private fun cargarProductos() {
        // CORREGIDO: Asignar a la propiedad (sin "val")
        listaProductosCompleta = listOf(
            Producto(1, "Chanchito Clásico", "Alcancía tradicional de cerámica para ahorros", 15.99, R.drawable.chanchito, "Categoría 1"),
            Producto(2, "Chanchito Premium", "Modelo premium con acabado dorado", 25.50, R.drawable.chanchito, "Categoría 1"),
            Producto(3, "Chanchito Mini", "Versión compacta perfecta para niños", 9.99, R.drawable.chanchito, "Categoría 1"),
            Producto(4, "Chanchito Gigante", "Alcancía de gran capacidad para metas grandes", 45.00, R.drawable.chanchito, "Categoría 2"),
            Producto(5, "Chanchito Vintage", "Diseño retro inspirado en los años 60", 32.99, R.drawable.chanchito, "Categoría 2"),
            Producto(6, "Chanchito Digital", "Con contador digital de ahorros", 55.00, R.drawable.chanchito, "Categoría 2")
        )

        productoAdapter = ProductoAdapter(listaProductosCompleta) { producto ->
            añadirProductoACesta(producto)
        }

        binding.recyclerViewProductosHome.apply {
            layoutManager = LinearLayoutManager(
                this@HomeActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = productoAdapter
        }
    }
    private fun configurarCesta() {
        // Crear el adapter con los callbacks
        cestaAdapter = CestaAdapter(
            listaCesta,
            onAumentar = { posicion -> aumentarCantidad(posicion) },
            onDisminuir = { posicion -> disminuirCantidad(posicion) },
            onEliminar = { posicion -> eliminarProducto(posicion) }
        )

        // Configurar el RecyclerView
        binding.recyclerViewCestaHome.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = cestaAdapter
        }
    }

    private fun añadirProductoACesta(producto: Producto) {
        // Buscar si el producto ya está en la cesta
        val indice = listaCesta.indexOfFirst { it.id == producto.id }

        if (indice != -1) {
            // Ya existe → Aumentar cantidad
            val productoExistente = listaCesta[indice]
            listaCesta[indice] = productoExistente.copy(cantidad = productoExistente.cantidad + 1)
            cestaAdapter.notifyItemChanged(indice)
        } else {
            // No existe → Añadir nuevo
            listaCesta.add(producto.copy(cantidad = 1))
            cestaAdapter.notifyItemInserted(listaCesta.size - 1)
        }

        actualizarTotal()
    }

    private fun aumentarCantidad(posicion: Int) {
        val producto = listaCesta[posicion]
        listaCesta[posicion] = producto.copy(cantidad = producto.cantidad + 1)
        cestaAdapter.notifyItemChanged(posicion)
        actualizarTotal()
    }

    private fun disminuirCantidad(posicion: Int) {
        val producto = listaCesta[posicion]

        // Solo disminuir si la cantidad es mayor que 1
        if (producto.cantidad > 1) {
            listaCesta[posicion] = producto.copy(cantidad = producto.cantidad - 1)
            cestaAdapter.notifyItemChanged(posicion)
            actualizarTotal()
        }
    }

    private fun eliminarProducto(posicion: Int) {
        listaCesta.removeAt(posicion)
        cestaAdapter.notifyItemRemoved(posicion)
        actualizarTotal()
    }

    private fun actualizarTotal() {
        // Calcular el total sumando precio * cantidad de cada producto
        val total = listaCesta.sumOf { it.precio * it.cantidad }

        // Actualizar el TextView
        binding.tvTotalHome.text = String.format("%.2f €", total)
    }
    // AÑADIR AL FINAL DE LA CLASE (antes de la llave de cierre)
    private fun configurarSpinner() {
        // 1. Crear lista de opciones (Todas + las categorías únicas)
        val categorias = mutableListOf("Todas")
        categorias.addAll(listaProductosCompleta.map { it.categoria }.distinct())

        // 2. Crear ArrayAdapter para el Spinner
        val spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            categorias
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // 3. Asignar adapter al Spinner
        binding.spinnerCategoriaHome.adapter = spinnerAdapter

        // 4. Configurar listener para detectar cambios
        binding.spinnerCategoriaHome.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val categoriaSeleccionada = categorias[position]
                filtrarProductosPorCategoria(categoriaSeleccionada)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No hacer nada
            }
        }
    }

    private fun filtrarProductosPorCategoria(categoria: String) {
        // Filtrar productos según la categoría seleccionada
        val productosFiltrados = if (categoria == "Todas") {
            listaProductosCompleta  // Mostrar todos
        } else {
            listaProductosCompleta.filter { it.categoria == categoria }
        }

        // Actualizar el adapter con la lista filtrada
        productoAdapter = ProductoAdapter(productosFiltrados) { producto ->
            añadirProductoACesta(producto)
        }

        binding.recyclerViewProductosHome.adapter = productoAdapter
    }
}

