package com.example.examen

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.examen.databinding.ActivityLoginBinding
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicializar ViewBinding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ajuste de bordes para EdgeToEdge
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        configurarBotones()
    }

    private fun configurarBotones() {
        //Boton iniciar sesión con validación
        binding.btnIniciarSesionLogin.setOnClickListener {
            // Obtener el texto de los campos
            val usuario = binding.campoUsuarioLogin.text.toString()
            val contrasena = binding.campoContraLogin.text.toString()
            // Validar si están vacíos
                if (usuario.isEmpty() || contrasena.isEmpty()) {
                    // Mostrar Toast de error
                    Toast.makeText(
                        this,
                        "Por favor, rellena todos los campos",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    // Campos llenos → Navegar a HomeActivity
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish() // Cierra LoginActivity para que no vuelva con el botón atrás
                }
        }
            //Botón registrase
            binding.btnRegistrarseLogin.setOnClickListener {
                Toast.makeText(
                    this,
                    "Función no implementada",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }