package com.android.example.copyandpastewidget

import android.content.ClipData
import android.content.ClipDescription.MIMETYPE_TEXT_PLAIN
import android.content.ClipboardManager
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var textToCopy: EditText
    lateinit var textToShow: TextView
    lateinit var copyBtn: ImageView
    lateinit var pasteBtn: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textToCopy = findViewById(R.id.textToCopyTxt)
        textToShow = findViewById(R.id.textTxt)
        copyBtn = findViewById(R.id.copyBtn)
        pasteBtn = findViewById(R.id.pasteBtn)

        copyBtn.setOnClickListener {
            var textToCopyStr = textToCopy.text.toString()
            if(!textToCopyStr.equals("")){
                // Obtiene un manejador para el servicio del portapapeles.
                val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                // Crea un nuevo clip de texto para poner en el portapapeles
                val clipData: ClipData = ClipData.newPlainText("text/plain", textToCopyStr)
                // Establece el clip principal del portapapeles.
                clipboardManager.setPrimaryClip(clipData)
                Toast.makeText(this, "Texto copiado", Toast.LENGTH_SHORT).show()
            }
        }

        pasteBtn.setOnClickListener {
            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            // Verificar que el portapapeles contenga un clip y que puedas procesar el tipo de datos que representa el clip
            if(!clipboardManager.hasPrimaryClip() || !clipboardManager.primaryClipDescription?.hasMimeType(MIMETYPE_TEXT_PLAIN)!!){
                Toast.makeText(this, "No hay nada que pegar", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val item = clipboardManager.primaryClip!!.getItemAt(0)
            // Obtiene el portapapeles como texto.
            var text = item.text
            textToShow.text = text
        }

    }
}