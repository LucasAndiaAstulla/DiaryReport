package com.example.diaryreport

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    lateinit var nomeInput: EditText
    lateinit var dataInput: EditText
    lateinit var buttonInfo: Button
    lateinit var buttonShared: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)


        nomeInput = findViewById(R.id.nomeInput)
        dataInput = findViewById(R.id.dataInput)
        buttonInfo = findViewById(R.id.informacoes)
        buttonShared = findViewById(R.id.compartilharButton)



        buttonInfo.setOnClickListener {
            val dataNome = nomeInput.text.toString()
            val dataDia = dataInput.text.toString()
            val intent = Intent(this, infoActivity::class.java)
            intent.putExtra("nome_enviado", dataNome)
            intent.putExtra("dia_enviado", dataDia)
            startActivity(intent)


        }

        buttonShared.setOnClickListener {
            val texto = gerarMensagem(nomeInput.toString(), dataInput.toString())

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.setPackage("com.whatsapp")
            intent.putExtra(Intent.EXTRA_TEXT, texto)

            try {
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "WhatsApp não encontrado", Toast.LENGTH_SHORT).show()
            }

        }


    }

    override fun onResume() {
        super.onResume()

        val prefs = getSharedPreferences("dadosChecklist", MODE_PRIVATE)

        val assumir = prefs.getString("intercorrenciasAssumir", "")
        val entregar = prefs.getString("intercorrenciasEntregar", "")

    }


    private fun gerarMensagem(nome:String, data:String): String {
        val prefs = getSharedPreferences("dadosChecklist", MODE_PRIVATE)

        val assumir = prefs.getString("intercorrenciasAssumir", "")
        val entregar = prefs.getString("intercorrenciasEntregar", "")

        return """
    CHECK LIST DE PLANTÃO entrada $data

    📍 Intercorrências ao assumir o plantão:
    $assumir

    ☑ Faltas na Equipe:
    ▸ faltas

    ☑ Ar Condicionado:
    arCond
    
    📍 Intercorrências ao entregar o plantão:
    $entregar
    
    Observações Importantes:
    
    Att, $nome
    Consultor(a) Comercial – Academia Gaviões Pimentas🏆
    
    📌 PROJEÇÃO DE VENDAS $data
    
    Consultor: $nome
    
    PERSONAL: 0
    AVULSO: 0
    MENSAL: 1
    START: 2
    PLUS: 0
    BIOIMPEDÂNCIA: 0
    VISITAS: 0
    """.trimIndent()
    }


}