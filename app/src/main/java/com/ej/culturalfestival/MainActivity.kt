package com.ej.culturalfestival

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.ej.culturalfestival.databinding.ActivityMainBinding
import com.ej.culturalfestival.fragment.CalendarFagment

class MainActivity : AppCompatActivity() {

    val mainActivityBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    val calendarFagment  = CalendarFagment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val toolbar = mainActivityBinding.toolbar
//        toolbar.title="문화 축제"
//        toolbar.subtitle="문화 출제2"
        setSupportActionBar(toolbar)


        setContentView(mainActivityBinding.root)
        setFragment("calendar")

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_search ->{
                Log.d("menu","click")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun setFragment(name : String){
        var tran = supportFragmentManager.beginTransaction()

        when(name){
            "calendar" ->{
//                tran.addToBackStack(name)
                tran.replace(R.id.container, calendarFagment)
            }

//            "open_member" ->{
//                tran.addToBackStack(name)
//                tran.replace(R.id.container,GroupMemberFragment())
//            }

        }

        tran.commit()
    }
}