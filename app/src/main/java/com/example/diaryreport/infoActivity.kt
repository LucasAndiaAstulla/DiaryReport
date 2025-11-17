package com.example.diaryreport

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class infoActivity : AppCompatActivity() {

    lateinit var informacaoEntrar: EditText
    lateinit var informacaoSair: EditText
    lateinit var salvar: Button
    lateinit var inputPersonal: EditText
    lateinit var inputDiaria: EditText
    lateinit var inputPlus: EditText
    lateinit var inputBio: EditText
    lateinit var inputMensal: EditText
    lateinit var inputStart: EditText
    lateinit var inputVisitas: EditText

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_information)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        salvar = findViewById(R.id.buttonSalvar)
        informacaoEntrar = findViewById(R.id.inputEntrar)
        informacaoSair = findViewById(R.id.inputSair)

        inputPersonal = findViewById(R.id.inputPersonal)
        inputDiaria = findViewById(R.id.inputDiaria)
        inputPlus = findViewById(R.id.editPlus)
        inputBio = findViewById(R.id.editBiopedancia)
        inputMensal = findViewById(R.id.inputMensal)
        inputStart = findViewById(R.id.inputStart)
        inputVisitas = findViewById(R.id.inputVisitas)

        val localNome = findViewById<TextView>(R.id.namePull)
        val localDia = findViewById<TextView>(R.id.datePull)

        val nomeRecebido = intent.getStringExtra("nome_enviado")
        localNome.text = nomeRecebido

        val diaRecebido = intent.getStringExtra("dia_enviado")
        localDia.text = diaRecebido

        salvar.setOnClickListener {
            //informações sobre assumir e deixar cargo
            val prefs = getSharedPreferences("dadosChecklist", MODE_PRIVATE)
            val editor = prefs.edit()

            editor.putString("intercorrenciasAssumir", informacaoEntrar.text.toString())
            editor.putString("intercorrenciasEntregar", informacaoSair.text.toString())
            editor.putString("vendasPersonal", inputPersonal.text.toString())
            editor.putString("vendasDiaria", inputDiaria.text.toString())
            editor.putString("vendasPlus", inputPlus.text.toString())
            editor.putString("vendasBio", inputBio.text.toString())
            editor.putString("vendasMensal", inputMensal.text.toString())
            editor.putString("vendasStart", inputStart.text.toString())
            editor.putString("apenasVisitas", inputVisitas.text.toString())

            //Salvar nome e data
            val nomeRecebido = intent.getStringExtra("nome_enviado")
            localNome.text = nomeRecebido

            val diaRecebido = intent.getStringExtra("dia_enviado")
            localDia.text = diaRecebido

            editor.apply()

            finish()
        }

    }
}