package com.example.paintapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)

        paintView.init(metrics)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.normal -> {
                paintView.normal()
                return true
            }
            R.id.emboss -> {
                paintView.emboss()
                return true
            }
            R.id.blur -> {
                paintView.blur()
                return true
            }
            R.id.clear -> {
                paintView.clear()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
