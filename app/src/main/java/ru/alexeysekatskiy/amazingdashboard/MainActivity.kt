package ru.alexeysekatskiy.amazingdashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_content.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.open, R.string.close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.id_my_ads -> {
            }
            R.id.id_car -> {
            }
            R.id.id_pc -> {
            }
            R.id.id_smartphone -> {
            }
            R.id.id_domestic -> {
            }

            R.id.id_sign_up -> {
            }
            R.id.id_sign_in -> {
            }
            R.id.id_sign_out -> {
            }
        }

        Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show() //TODO delete
        drawer_layout.closeDrawer(GravityCompat.START)

        return true
    }
}