package com.example.examen

import android.app.Activity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examen.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicializar ViewBinding
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ajuste de bordes para EdgeToEdge
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Configurar RecyclerView
        cargarProductos()
    }
    private fun cargarProductos() {
        // 1. Crear datos de ejemplo
        val listaProductos = listOf(
            Producto(1, "Chanchito Clásico", "Alcancía tradicional de cerámica para ahorros", 15.99, R.drawable.chanchito),
            Producto(2, "Chanchito Premium", "Modelo premium con acabado dorado", 25.50, R.drawable.chanchito),
            Producto(3, "Chanchito Mini", "Versión compacta perfecta para niños", 9.99, R.drawable.chanchito),
            Producto(4, "Chanchito Gigante", "Alcancía de gran capacidad para metas grandes", 45.00, R.drawable.chanchito),
            Producto(5, "Chanchito Vintage", "Diseño retro inspirado en los años 60", 32.99, R.drawable.chanchito),
            Producto(6, "Chanchito Digital", "Con contador digital de ahorros", 55.00, R.drawable.chanchito)
        )
        // 2. Crear el adaptador pasándole la lista
        val adapter = ProductoAdapter(listaProductos)

        // 3. Configurar el RecyclerView
        binding.recyclerViewProductos.apply {
            // LinearLayoutManager HORIZONTAL: scroll de izquierda a derecha
            layoutManager = LinearLayoutManager(
                this@HomeActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            // Asignar el adaptador
            this.adapter = adapter
        }
    }

}