package com.ej.culturalfestival

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.ej.culturalfestival.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val mainActivityBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val toolbar = mainActivityBinding.toolbar
        toolbar.title=""
        toolbar.subtitle=""
        setSupportActionBar(toolbar)
        setContentView(mainActivityBinding.root)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }
}