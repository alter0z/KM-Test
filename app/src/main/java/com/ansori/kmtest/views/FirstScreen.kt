package com.ansori.kmtest.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.ansori.kmtest.databinding.ActivityFirstScreenBinding

class FirstScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFirstScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        binding.check.setOnClickListener {
            val input = binding.palindrome.text.toString()
            when {
                input.isEmpty() -> binding.palindrome.error = "Please fill some words"
                else -> {
                    if (isPalindrome(input)) Toast.makeText(this, "isPalindrome", Toast.LENGTH_SHORT).show()
                    else Toast.makeText(this, "not palindrome", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.next.setOnClickListener {
            val name = binding.name.text.toString()
            when {
                name.isEmpty() -> binding.name.error = "Please fill your name"
                else -> {
                    val intent = Intent(this, SecondScreen::class.java)
                    intent.putExtra(SecondScreen.NAME, name)
                    startActivity(intent)
                }
            }
        }
    }

    // fun to check input is palindrome or not
    private fun isPalindrome(input: String): Boolean {
        val stringBuilder = StringBuilder(input)
        val reverseString = stringBuilder.reverse().toString()
        return input.equals(reverseString, true)
    }
}