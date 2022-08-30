package com.ej.culturalfestival

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import com.ej.culturalfestival.databinding.ActivityMainBinding
import com.ej.culturalfestival.fragment.CalendarFagment
import com.ej.culturalfestival.fragment.SearchFragment

class MainActivity : AppCompatActivity() {

    val mainActivityBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    val calendarFagment  = CalendarFagment.newInstance()
    val searchFragment = SearchFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val toolbar = mainActivityBinding.toolbar
        toolbar.title="축제 검색"
        val color = ContextCompat.getColor(this, R.color.main_green)
        toolbar.setTitleTextColor(color)
        setSupportActionBar(toolbar)



        setContentView(mainActivityBinding.root)
        setFragment("calendar")

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_menu,menu)
        val item1 = menu?.findItem(R.id.menu_search)
        val search = item1?.actionView as SearchView
        search.queryHint = "검색어 입력!!"

        val listener = object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("search","검색")

                search.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        }

        search.setOnQueryTextListener(listener)




        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
//            R.id.menu_search ->{
//                setFragment("search")
//            }
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

            "search" ->{
                tran.replace(R.id.container,searchFragment)
            }

        }

        tran.commit()
    }
}