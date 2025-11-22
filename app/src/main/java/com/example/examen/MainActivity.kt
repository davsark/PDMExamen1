package com.example.examen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.examen.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //1. Declaramos la variable para el Binding
    private lateinit var binding: ActivityMainBinding

    //2. Variable para controlar la duración de la Splash
    private var isSplashVisible = true

    override fun onCreate(savedInstanceState: Bundle?) {
        // 3. INSTALAR SPLASH SCREEN (Siempre debe ser la primera línea)
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 4. INICIALIZAR VIEW BINDING
        // Inflamos la vista y la establecemos como contenido
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 5. LÓGICA DE LOS 3 SEGUNDOS
        // Mantenemos la splash visible mientras la variable sea true
        splashScreen.setKeepOnScreenCondition {
            isSplashVisible
        }
        // Usamos un Handler para cambiar la variable a false a los 3 segundos
        Handler(Looper.getMainLooper()).postDelayed({
            isSplashVisible = false
        }, 3000)

        // 6. AJUSTE DE BORDES (EdgeToEdge) USANDO BINDING
        // Tu plantilla usaba findViewById(R.id.main), ahora usamos binding.main
        // (Asegúrate de que en tu activity_main.xml el ID del layout raíz sea "@+id/main")
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}