package com.dsc.unprg.memohalloween

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView

/**
 * @author mmaisano
 *
 * Juego de la memoria desarrollado en el contexto del DSC UNPRG - Octubre 2020
 * Te gustaria agregar mas funcionalidad? Algunas ideas:
 *
 * Cambios en la interfaz grafica, nuevas imagenes? fichas con bordes redondeados?
 * Posibilidad de elegir entre distintas temáticas: frutas, animales, etc.
 * Un boton de comenzar de nuevo: Oculta nuevamente todas las fichas y vuelve a mezclar
 * Un contador de movimientos
 * Un mensaje de felicitación al finalizar la partida
 *
 * Gracias por sumarse! maribel.maisano@gmail.com
 *
 */
class MainActivity : AppCompatActivity() {

    private lateinit var fichas: List<ImageView>
    private lateinit var imagenes: List<Int>
    private var imagenesPorFicha = mutableMapOf<ImageView, Int>()
    private var ultimaFichaDescubierta :ImageView? = null
    // Previene que se pueda descubrir una ficha mientras hay otra jugada en curso
    private var jugadaEnCurso = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fichas = listOf(findViewById(R.id.ficha1), findViewById(R.id.ficha2), findViewById(R.id.ficha3),
            findViewById(R.id.ficha4), findViewById(R.id.ficha5), findViewById(R.id.ficha6),
            findViewById(R.id.ficha7), findViewById(R.id.ficha8), findViewById(R.id.ficha9),
            findViewById(R.id.ficha10), findViewById(R.id.ficha11), findViewById(R.id.ficha12))

        imagenes = listOf(R.drawable.ficha_bruja,R.drawable.ficha_calabaza,R.drawable.ficha_dulce,
            R.drawable.ficha_fantasma,R.drawable.ficha_pocion,R.drawable.ficha_vela)

        inicializarFichas()
    }

    fun inicializarFichas() {
        val imagenesMezcladas = mezclarImagenes()
        this.fichas.forEachIndexed { index, imageView ->
            ocultarFicha(imageView)
            imagenesPorFicha[imageView] = imagenesMezcladas[index]
            imageView.setOnClickListener { if(!jugadaEnCurso) revelarFicha(imageView) }
        }
    }

    private fun revelarFicha(imageView: ImageView) {

        imagenesPorFicha[imageView]?.let { imageView.setImageResource(it) }

        if (ultimaFichaDescubierta == null) {
            ultimaFichaDescubierta = imageView
        } else {
            // No hay coincidencia
            if (imagenesPorFicha[ultimaFichaDescubierta] != imagenesPorFicha[imageView]) {
                jugadaEnCurso = true
                Handler().postDelayed({
                    ocultarFicha(imageView)
                    ocultarFicha(ultimaFichaDescubierta!!)
                    ultimaFichaDescubierta = null
                    jugadaEnCurso = false
                }, 1000)
            // Hay coincidencia
            } else {
                ultimaFichaDescubierta = null
            }
        }
    }

    private fun mezclarImagenes(): List<Int> {
        val imagenesMezcladas = this.imagenes.shuffled().toMutableList()
        imagenesMezcladas.addAll(this.imagenes.shuffled())
        return imagenesMezcladas
    }

    fun ocultarFicha(imageView: ImageView) {
        imageView.setImageResource(R.drawable.ficha_halloween)
    }
}