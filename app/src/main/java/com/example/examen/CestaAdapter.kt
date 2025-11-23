package com.example.examen

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.examen.databinding.CardProductoCestaBinding

class CestaAdapter(
    private val listaProductos: List<Producto>  // ← Usa Producto, no ProductoCesta
) : RecyclerView.Adapter<CestaAdapter.CestaViewHolder>() {

    class CestaViewHolder(val binding: CardProductoCestaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CestaViewHolder {
        val binding = CardProductoCestaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CestaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CestaViewHolder, position: Int) {
        val producto = listaProductos[position]

        holder.binding.apply {
            // Asignar datos usando propiedades de Producto
            tvNombreCesta.text = producto.titulo  // ← Usa 'titulo' de Producto
            tvCantidadCesta.text = producto.cantidad.toString()

            // Calcular precio total
            tvPrecioCesta.text = String.format("%.2f €", producto.precio)

            // Listeners de los botones (sin funcionalidad por ahora)
            btnDisminuirCesta.setOnClickListener {
                Toast.makeText(root.context, "Disminuir: ${producto.titulo}", Toast.LENGTH_SHORT).show()
            }

            btnAumentarCesta.setOnClickListener {
                Toast.makeText(root.context, "Aumentar: ${producto.titulo}", Toast.LENGTH_SHORT).show()
            }

            btnEliminarCesta.setOnClickListener {
                Toast.makeText(root.context, "Eliminar: ${producto.titulo}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int = listaProductos.size
}