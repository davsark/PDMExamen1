package com.example.examen

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.examen.databinding.CardProductoBinding

class ProductoAdapter(
    private val listaProductos: List<Producto>,
    private val onAñadirClick: (Producto) -> Unit
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    // ViewHolder usando ViewBinding
    class ProductoViewHolder(val binding: CardProductoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val binding = CardProductoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = listaProductos[position]

        // Acceso directo a las vistas mediante binding
        holder.binding.apply {
            imagenCard.setImageResource(producto.imagenResId)
            tituloCard.text = producto.titulo
            descripcionCard.text = producto.descripcion
            precioCard.text = "${producto.precio} €"

            // Listener para el botón de carrito
            carritoCard.setOnClickListener {
                onAñadirClick(producto)
                Toast.makeText(
                    root.context,
                    "Añadido: ${producto.titulo}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun getItemCount(): Int = listaProductos.size
}
