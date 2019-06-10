package com.example.paintapp

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var activeColor: ActiveColor = ActiveColor.Red

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)

        paintView.init(metrics)

        colorChangerManagement()
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

    private fun colorChangerManagement() {
        redColor_Button.text = "V"

        blueColor_Button.setBackgroundColor(Color.BLUE)
        greenColor_Button.setBackgroundColor(Color.GREEN)
        yellowColor_Button.setBackgroundColor(Color.YELLOW)
        redColor_Button.setBackgroundColor(Color.RED)

        blueColor_Button.setOnClickListener {
            paintView.changeColor(Color.BLUE)
            activeColor = ActiveColor.Blue
            approveActiveColor(activeColor)
        }

        greenColor_Button.setOnClickListener {
            paintView.changeColor(Color.GREEN)
            activeColor = ActiveColor.Green
            approveActiveColor(activeColor)
        }

        yellowColor_Button.setOnClickListener {
            paintView.changeColor(Color.YELLOW)
            activeColor = ActiveColor.Yellow
            approveActiveColor(activeColor)
        }

        redColor_Button.setOnClickListener {
            paintView.changeColor(Color.RED)
            activeColor = ActiveColor.Red
            approveActiveColor(activeColor)
        }
    }

    private fun approveActiveColor(activeColor: ActiveColor) {
        when(activeColor) {
            ActiveColor.Blue -> {
                blueColor_Button.text = "V"
                greenColor_Button.text = ""
                yellowColor_Button.text = ""
                redColor_Button.text = ""
            }
            ActiveColor.Green -> {
                blueColor_Button.text = ""
                greenColor_Button.text = "V"
                yellowColor_Button.text = ""
                redColor_Button.text = ""
            }
            ActiveColor.Yellow -> {
                blueColor_Button.text = ""
                greenColor_Button.text = ""
                yellowColor_Button.text = "V"
                redColor_Button.text = ""
            }
            ActiveColor.Red -> {
                blueColor_Button.text = ""
                greenColor_Button.text = ""
                yellowColor_Button.text = ""
                redColor_Button.text = "V"
            }
        }
    }
}
