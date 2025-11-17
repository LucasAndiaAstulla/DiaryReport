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
            val texto = gerarMensagem()

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


    private fun gerarMensagem(): String {
        val prefs = getSharedPreferences("dadosChecklist", MODE_PRIVATE)
        val nome = nomeInput.text.toString()
        val data = dataInput.text.toString()
        val assumir = prefs.getString("intercorrenciasAssumir", "")
        val entregar = prefs.getString("intercorrenciasEntregar", "")

        val personal = prefs.getString("vendasPersonal", "")
        val diaria = prefs.getString("vendasDiaria", "")
        val plus = prefs.getString("vendasPlus", "")
        val bio = prefs.getString("vendasBio", "")
        val mensal = prefs.getString("vendasMensal", "")
        val start = prefs.getString("vendasStart", "")
        val visita = prefs.getString("apenasVisitas", "")


        return """
    ✅ CHECK LIST DE PLANTÃO entrada $data

    📍 Intercorrências ao assumir o plantão:
    $assumir

    ☑ Faltas na Equipe:
    ▸ *faltas

    ☑ Ar Condicionado:
    
    ☑ Equipamentos com problema:
    *equipamentos
    
    ☑ Equipamentos com problema:
    *equipamentos
    
    ☑ Operacional
    TOTEM *equipamentos
    EVO *equipamentos
    TEF *equipamentos
    BIOIMPENDACIA *equipamentos
    
    ☑ Computadores (Pcs):
    *equipamentos
    
    
    📍 Intercorrências ao entregar o plantão:
    $entregar
    
    Att, $nome
    Consultor(a) Comercial – Academia Gaviões Pimentas🏆
    
    📌 PROJEÇÃO DE VENDAS $data
    
    Consultor: $nome
    
    PERSONAL: $personal
    AVULSO: $diaria
    MENSAL: $mensal
    START: $start
    PLUS: $plus
    BIOIMPEDÂNCIA: $bio
    VISITAS: $visita
    """.trimIndent()
    }


}