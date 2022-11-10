package com.ej.culturalfestival

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.ej.culturalfestival.databinding.ActivityMainBinding
import com.ej.culturalfestival.fragment.CalendarFagment
import com.ej.culturalfestival.fragment.SearchFragment
import com.ej.culturalfestival.fragment.WebviewFragment
import com.ej.culturalfestival.viewmodel.FestivalViewModel

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    val festivalViewModel : FestivalViewModel by lazy { ViewModelProvider(this).get(FestivalViewModel::class.java) }
    val calendarFagment  = CalendarFagment.newInstance()
    val searchFragment = SearchFragment.newInstance()
    val webviewFragment = WebviewFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        uiDraw()
        setFragment(CalendarFagment.TAG)
    }

    private fun uiDraw() {
        val toolbar = binding.toolbar
        toolbar.title = "축제 검색"
        val color = ContextCompat.getColor(this, R.color.main_green)
        toolbar.setTitleTextColor(color)
        setSupportActionBar(toolbar)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_menu,menu)
        val item1 = menu?.findItem(R.id.menu_search)
        val search = item1?.actionView as SearchView
        search.queryHint = "검색어 입력!!"

        val menuListener = createMenuListener()
        item1.setOnActionExpandListener(menuListener)

        val searchListener = createTitleQueryListener(search)
        search.setOnQueryTextListener(searchListener)

        return true
    }

    private fun createMenuListener() = object : MenuItem.OnActionExpandListener {
        override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
    //                search.setQuery("abcd",false)
            return true
        }

        override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
            if (supportFragmentManager.backStackEntryCount >= 2) {
                supportFragmentManager.popBackStack()
            }
            return true
        }
    }

    private fun createTitleQueryListener(search: SearchView) =
        object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("search", "검색")
                festivalViewModel.festivalSearchResult.observe(this@MainActivity) {
                    setFragment(SearchFragment.TAG)
                }
                festivalViewModel.getFestivalByTitle(query!!)
                search.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
//            R.id.menu_search ->{
//                setFragment("search")
//            }
            android.R.id.home ->{
                supportFragmentManager.popBackStack()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun setFragment(name : String){
        var tran = supportFragmentManager.beginTransaction()

        when(name){
            CalendarFagment.TAG ->{
//                supportFragmentManager.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE)
//                tran.addToBackStack(name)
                tran.replace(R.id.container, calendarFagment)
            }

            SearchFragment.TAG ->{
                tran.addToBackStack(name)
                tran.replace(R.id.container,searchFragment)
            }

            WebviewFragment.TAG ->{
                tran.addToBackStack(name)
                tran.replace(R.id.container,webviewFragment)
            }
        }
        tran.commit()
    }
}