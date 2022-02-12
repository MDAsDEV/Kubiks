package com.example.kubiks

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible


class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences
    private lateinit var prefs_language: SharedPreferences
    private val APP_PREFERENCES_COUNTER = "counter"
    private val LANGUAGE_PREFERENCE_MODE = "language"
    private var was_first_screen: Int = 0
    var language_data: String = "rus"

    var was = false
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prefs =
            getSharedPreferences("settings", Context.MODE_PRIVATE)
        var ClickView = findViewById<ImageView>(R.id.click)
        prefs_language = getSharedPreferences("language_settings", MODE_PRIVATE)
        if (prefs_language.contains(LANGUAGE_PREFERENCE_MODE)){
            language_data = prefs_language.getString(LANGUAGE_PREFERENCE_MODE, "None").toString()
            Log.i("test language data = =", language_data)
        }
        else{
            Log.i("language data", "not found")
            var prefs_language_edit = prefs_language.edit()
            prefs_language_edit.putString(LANGUAGE_PREFERENCE_MODE, "rus").apply()
            language_data = "eng"
        }
        if ("eng" in language_data){
            ClickView.setImageResource(R.drawable.click_buttons_to_start)
        }
        else{
            ClickView.setImageResource(R.drawable.click_buttons_to_start_rus)
        }
        val start_phone = findViewById(R.id.click) as ImageView;
        start_phone.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action){
                    MotionEvent.ACTION_DOWN -> counter_plus()
                }

                return v?.onTouchEvent(event) ?: true
            }
        })
        val button_id = findViewById<ImageButton>(R.id.one_dice) as ImageButton
        if (was == false) {
            button_id.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    when (event?.action){
                        MotionEvent.ACTION_DOWN -> scale_one_dice()//Do Something
                    }

                    return v?.onTouchEvent(event) ?: true
                }
            })
        }
        val button_id2 = findViewById<ImageButton>(R.id.two_dices) as ImageButton
        if (was == false) {
            button_id2.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    when (event?.action){
                        MotionEvent.ACTION_DOWN -> scale_two_dices()
                    }

                    return v?.onTouchEvent(event) ?: true
                }
            })
        }

    }
    fun counter_plus(){
        was_first_screen += 1 // Изменяем число при нажатии на экран
        if (was_first_screen > 0) {
            val start_phone = findViewById(R.id.dark_phone) as ImageView;
            start_phone.isVisible = false // Делаем нашу ImageView (которая является подсказкой куда тыкать на экране) невидимой
        }
    }
    fun go_to_one(){
        if (was_first_screen != 1) {
            val one_activity = Intent(this, one_cubik::class.java)
            startActivity(one_activity)
        }
    }
    fun go_to_two(){
        if (was_first_screen != 1) {
            val two_activity = Intent(this, two_kubiks::class.java)
            startActivity(two_activity)
        }
    }

    fun scale_one_dice(){
        counter_plus()
        if (was_first_screen > 1) {
            was = true
            val image: ImageView = findViewById(R.id.one_dice)
            val animation_zoom =
                AnimationUtils.loadAnimation(this, R.anim.scale_zoom)
            image.startAnimation(animation_zoom)
            val animation_less = AnimationUtils.loadAnimation(this, R.anim.scale_less)
            image.startAnimation(animation_less)
            go_to_one()
            was = false
        }
    }
    fun scale_two_dices(){
        counter_plus()
        if (was_first_screen > 1) {
            was = true
            val image: ImageView = findViewById(R.id.two_dices)
            val animation_zoom =
                AnimationUtils.loadAnimation(this, R.anim.scale_zoom)
            image.startAnimation(animation_zoom)
            val animation_less = AnimationUtils.loadAnimation(this, R.anim.scale_less)
            image.startAnimation(animation_less)
            go_to_two()
            was = false
        }
    }
    override fun onPause() { //Сохранение в настройках
        super.onPause()

        val edit_settings = prefs.edit()
        edit_settings.putInt(APP_PREFERENCES_COUNTER, was_first_screen).apply()

    }
    override fun onResume() { // Функция, запускающаяся при включении приложения
        super.onResume()

        if(prefs.contains(APP_PREFERENCES_COUNTER)){ // Получаем число нажатий на экран из настроек
            was_first_screen = prefs.getInt(APP_PREFERENCES_COUNTER, 0) // Записываем это число в переменную was_first_screen

        }
        if (was_first_screen > 0) {
            val start_phone = findViewById(R.id.dark_phone) as ImageView;
            start_phone.isVisible = false // Делаем нашу ImageButton (которая является подсказкой куда тыкать на экране) невидимой
        }
        Log.i("test music preferences", "onresume")

    }

}