package com.example.kotlinauth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.FormBody
import okhttp3.Request
import okhttp3.ResponseBody

class MainActivity : AppCompatActivity() {

    val USER_DATA = "USER_DATA"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sing.setOnClickListener{
            if(username.text.isNotEmpty() && password.text.isNotEmpty()){
                val formBody = FormBody.Builder()
                    .add("username", username.text.toString())
                    .add("password ", password.text.toString())
                    .build()
                val request = Request.Builder()
                    .url("https://apiscud.areas.su/login")
                    .post(formBody)
                    .build()
                val response = ResponseBody
                if(request.isHttps){
                    val sharedPreferences = getSharedPreferences(USER_DATA, Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("token", response.toString())
                    editor.putString("username", username.text.toString())
                    editor.apply()
                    val dialogBuilder = AlertDialog.Builder(this)
                    val inflater = this.layoutInflater
                    val dialogView = inflater.inflate(R.layout.custom_dialog, null)
                    dialogBuilder.setView(dialogView)

                    dialogBuilder.setTitle("Добро пожаловать!")
                    dialogBuilder.setMessage("Ваш токен: " + response)
                    dialogBuilder.setPositiveButton("Ок") { dialog, whichButton ->
                        val intent = Intent(this, TwoActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    val b = dialogBuilder.create()
                    b.show()
                } else {
                    val dialogBuilder = AlertDialog.Builder(this)
                    val inflater = this.layoutInflater
                    val dialogView = inflater.inflate(R.layout.custom_dialog, null)
                    dialogBuilder.setView(dialogView)

                    dialogBuilder.setTitle("Вход не удался")
                    dialogBuilder.setMessage("Что-то пошло не так (( \n Попробуйте снова!")
                    dialogBuilder.setPositiveButton("Ok") { dialog, whichButton ->

                    }
                    val b = dialogBuilder.create()
                    b.show()
                }
            } else {
                val dialogBuilder = AlertDialog.Builder(this)
                val inflater = this.layoutInflater
                val dialogView = inflater.inflate(R.layout.custom_dialog, null)
                dialogBuilder.setView(dialogView)

                dialogBuilder.setTitle("Вы не заполнили поля!")
                dialogBuilder.setMessage("Заполните все поля и попробуйте снова!")
                dialogBuilder.setPositiveButton("Ок") { dialog, whichButton ->

                }
                val b = dialogBuilder.create()
                b.show()
            }
        }
    }
}