package com.example.diaryreport

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class infoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_information)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val localNome = findViewById<TextView>(R.id.namePull)
        val localDia = findViewById<TextView>(R.id.datePull)

        val nomeRecebido = intent.getStringExtra("nome_enviado")
        localNome.text = nomeRecebido

        val diaRecebido = intent.getStringExtra("dia_enviado")
        localDia.text = diaRecebido


    }
}