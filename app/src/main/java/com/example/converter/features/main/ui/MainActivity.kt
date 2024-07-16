package com.example.converter.features.main.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.converter.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view, MainFragment()).commit()
    }
}