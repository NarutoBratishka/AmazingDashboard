package ru.alexeysekatskiy.amazingdashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import ru.alexeysekatskiy.amazingdashboard.databinding.ActivityMainBinding
import ru.alexeysekatskiy.amazingdashboard.dialogHelper.DialogConst
import ru.alexeysekatskiy.amazingdashboard.dialogHelper.SignDialogHelper

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var rootElement: ActivityMainBinding
    private val dialogHelper = SignDialogHelper(this)
    val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootElement = ActivityMainBinding.inflate(layoutInflater)
        val view = rootElement.root
        setContentView(view)
        init()
    }

    private fun init() {
        val toggle = ActionBarDrawerToggle(this, rootElement.drawerLayout, rootElement.mainContent.toolbar, R.string.open, R.string.close)
        rootElement.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        rootElement.navView.setNavigationItemSelectedListener(this)
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
                dialogHelper.createSignDialog(DialogConst.SIGN_UP_STATE)
            }
            R.id.id_sign_in -> {
                dialogHelper.createSignDialog(DialogConst.SIGN_IN_STATE)
            }
            R.id.id_sign_out -> {
            }
        }

        Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show() //TODO delete
        rootElement.drawerLayout.closeDrawer(GravityCompat.START)

        return true
    }
}