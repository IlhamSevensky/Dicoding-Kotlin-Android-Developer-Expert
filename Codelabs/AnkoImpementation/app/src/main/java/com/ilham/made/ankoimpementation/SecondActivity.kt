package com.ilham.made.ankoimpementation

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.dip
import org.jetbrains.anko.padding
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalLayout

class SecondActivity : AppCompatActivity() {

    private var name: String? = ""
    lateinit var nameTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verticalLayout{
            padding = dip(16)
            nameTextView = textView()
        }

        val intent = intent
        name = intent.getStringExtra("name")
        nameTextView.text = name
    }

}