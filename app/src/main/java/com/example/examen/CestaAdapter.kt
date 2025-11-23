package com.example.examen

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.examen.databinding.CardProductoCestaBinding

class CestaAdapter(
    private val listaProductos: List<Producto>,  // ← Usa Producto, no ProductoCesta
    private val onAumentar: (Int) -> Unit,   // ← Recibe la posición
    private val onDisminuir: (Int) -> Unit,  // ← Recibe la posición
    private val onEliminar: (Int) -> Unit    // ← Recibe la posición
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
                val posReal = holder.bindingAdapterPosition
                if (posReal != RecyclerView.NO_POSITION) {
                    onDisminuir(posReal)
                }
            }

            btnAumentarCesta.setOnClickListener {
                val posReal = holder.bindingAdapterPosition
                if (posReal != RecyclerView.NO_POSITION) {
                    onAumentar(posReal)
                }
            }

            btnEliminarCesta.setOnClickListener {
                val posReal = holder.bindingAdapterPosition
                if (posReal != RecyclerView.NO_POSITION) {
                    onEliminar(posReal)
                }
            }
        }
    }
    override fun getItemCount(): Int = listaProductos.size
}


