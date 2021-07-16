package com.example.cakeslist.presentation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.cakeslist.R

abstract class BaseActivity : AppCompatActivity() {

    fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}