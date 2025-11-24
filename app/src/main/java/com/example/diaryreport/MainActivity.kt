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

            if (dataNome.isEmpty() || dataDia.isEmpty()) {
                Toast.makeText(this, "Preencha o seu Nome e a Data de hoje!", Toast.LENGTH_SHORT).show()
            } else{
                intent.putExtra("nome_enviado", dataNome)
                intent.putExtra("dia_enviado", dataDia)
                startActivity(intent)
            }
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

        var personal = ""
        personal = if (prefs.getString("vendasPersonal", "") == "") {
            "0"
        } else {
            prefs.getString("vendasPersonal", "").toString()
        }

        var diaria = prefs.getString("vendasDiaria", "")
        diaria = if (prefs.getString("vendasDiaria", "") == "") {
            "0"
        } else {
            prefs.getString("vendasDiaria", "").toString()
        }

        var plus = prefs.getString("vendasPlus", "")
        plus = if (prefs.getString("vendasPlus", "") == "") {
            "0"
        } else {
            prefs.getString("vendasPlus", "").toString()
        }

        var bio = prefs.getString("vendasBio", "")
        bio = if (prefs.getString("vendasBio", "") == "") {
            "0"
        } else {
            prefs.getString("vendasBio", "").toString()
        }

        var mensal = prefs.getString("vendasMensal", "")
        mensal = if (prefs.getString("vendasMensal", "") == "") {
            "0"
        } else {
            prefs.getString("vendasMensal", "").toString()
        }

        var start = prefs.getString("vendasStart", "")
        start = if (prefs.getString("vendasStart", "") == "") {
            "0"
        } else {
            prefs.getString("vendasStart", "").toString()
        }

        var visita = prefs.getString("apenasVisitas", "")
        visita = if (prefs.getString("apenasVisitas", "") == "") {
            "0"
        } else {
            prefs.getString("apenasVisitas", "").toString()
        }


        var faltas = ""
        if (prefs.getBoolean("semfalta", true)){
            faltas = "Sem faltas"
        } else if (prefs.getBoolean("falta", true)) {
            faltas = "Faltas presentes, verificar na intercorrências ao entregar o plantão "
        }


        var ar = ""
        if (prefs.getBoolean("arbom", true)){
            ar = "Ar condicionado OK"
        } else if (prefs.getBoolean("erroar", true)) {
            ar = "Problemas no ar condicionado, verificar na intercorrências ao entregar o plantão "
        }

        var equipamentos = ""
        if (prefs.getBoolean("sistemanormal", true)){
            equipamentos = "OK"
        } else if (prefs.getBoolean("errosistema", true)) {
            equipamentos = "Erro, verificar na intercorrências ao entregar"
        }

        var catracas = ""
        if (prefs.getBoolean("catracanormal", true)){
            catracas = "Catracas OK"
        } else if (prefs.getBoolean("errocatraca", true)) {
            catracas = "Problemas nas catracas, verificar na intercorrências ao entregar o plantão "
        }

        var computadores = ""
        if (prefs.getBoolean("compnormal", true)){
            computadores = "Computadores funcionando corretamente"
        } else if (prefs.getBoolean("errocomp", true)) {
            computadores = "Problemas nos computadores, verificar na intercorrências ao entregar o plantão "
        }

        var maquinas = ""
        if (prefs.getBoolean("semmanu", true)){
            maquinas = "Nenhum equipamento em manutenção"
        } else if (prefs.getBoolean("erromanu", true)) {
            maquinas = "Alguns equipamentos em manutenção"
        }


        //Mensagem que vai ser enviada

        return """
    ✅ CHECK LIST DE PLANTÃO entrada $data

    📍 Intercorrências ao assumir o plantão:
    $assumir

    ☑ Faltas na Equipe:
    ▸ $faltas

    ☑ Ar Condicionado:
    ▸ $ar
    ☑ Equipamentos com problema:
    ▸ $maquinas
    
    ☑ Catracas
    ▸ $catracas
    
    ☑ Operacional
    TOTEM $equipamentos
    EVO $equipamentos
    TEF $equipamentos
    BIOIMPENDACIA $equipamentos
    
    ☑ Computadores (Pcs):
    ▸ $computadores
    
    
    📍 Intercorrências ao entregar o plantão:
    ▸ $entregar
    
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