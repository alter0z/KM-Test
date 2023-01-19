package com.ansori.kmtest.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ansori.kmtest.databinding.ActivitySecondScreenBinding

class SecondScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySecondScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        secondScreen = this

        val name = intent.getStringExtra(NAME)
        val username = intent.getStringExtra(USERNAME)

        binding.name.text = name ?: "name is null"
        binding.username.text = username ?: "Selected User Name"

        binding.back.setOnClickListener { finish() }
        binding.choose?.setOnClickListener {
            val intent = Intent(this, ThirdScreen::class.java)
            intent.putExtra(ThirdScreen.NAME, name)
            startActivity(intent)
        }
    }

    companion object {
        const val NAME = "name"
        const val USERNAME = "username"
        var secondScreen: SecondScreen? = null
    }
}