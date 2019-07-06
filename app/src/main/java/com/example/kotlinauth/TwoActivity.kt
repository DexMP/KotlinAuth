package com.example.kotlinauth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_two.*
import okhttp3.FormBody
import okhttp3.Request

class TwoActivity : AppCompatActivity() {

    val USER_DATA = "USER_DATA"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two)

        val sharedPreferences = getSharedPreferences(USER_DATA, Context.MODE_PRIVATE)
        result.text = "token: " + sharedPreferences.getString("token", "lost") + "\nusername: " +  sharedPreferences.getString("username", "lost")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.exit, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.exit -> {
                exit()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun exit(){
        val formBody = FormBody.Builder()
            .add("username", username.text.toString())
            .build()
        val request = Request.Builder()
            .url("https://apiscud.areas.su/logout")
            .post(formBody)
            .build()
        if (request.isHttps){
            val dialogBuilder = AlertDialog.Builder(this)
            val inflater = this.layoutInflater
            val dialogView = inflater.inflate(R.layout.custom_dialog, null)
            dialogBuilder.setView(dialogView)

            dialogBuilder.setTitle("Выход")
            dialogBuilder.setMessage("Вы уверены что хотите выйти?")
            dialogBuilder.setNeutralButton("Нет") { dialog, whichButton ->

            }
            dialogBuilder.setPositiveButton("Да") { dialog, whichButton ->
                val intent = Intent(this, TwoActivity::class.java)
                startActivity(intent)
                finish()
            }

            val b = dialogBuilder.create()
            b.show()
        }
    }
}
