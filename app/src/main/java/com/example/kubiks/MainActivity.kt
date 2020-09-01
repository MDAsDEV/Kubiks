package com.example.kubiks
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences
    private val APP_PREFERENCES_COUNTER = "counter"
    private var was_first_screen: Int = 0
    var was = false
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prefs =
            getSharedPreferences("settings", Context.MODE_PRIVATE)
        val start_phone = findViewById(R.id.imageView3) as ImageView;
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
                        MotionEvent.ACTION_DOWN -> animation1_1()//Do Something
                        MotionEvent.ACTION_UP -> animation1_2()
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
                        MotionEvent.ACTION_DOWN -> animation2_1()//Do Something
                        MotionEvent.ACTION_UP -> animation2_2()
                    }

                    return v?.onTouchEvent(event) ?: true
                }
            })
        }
    }
    fun counter_plus(){
        was_first_screen += 1 // Изменяем число при нажатии на экран
        if (was_first_screen > 0) {
            val start_phone = findViewById(R.id.imageView3) as ImageView;
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

    fun animation1_1() {
        counter_plus()
        if (was_first_screen > 1) {
            was = true
            val imageView = findViewById(R.id.one_dice) as ImageView
            imageView.setImageDrawable(null)
            imageView.setBackgroundResource(R.drawable.anim)
            val animationDrawable = imageView.background as AnimationDrawable
            if (!animationDrawable.isRunning) {

                var totalFrameDuration = 0
                for (i in 0 until animationDrawable.numberOfFrames) {
                    totalFrameDuration += animationDrawable.getDuration(i)
                }

                animationDrawable.start()
                Handler().postDelayed(
                    Runnable { animationDrawable.stop() },
                    totalFrameDuration.toLong()
                )
            }
            was = false
        }
    }


    fun animation1_2() {
        if (was_first_screen > 1) {
            was = true
            val imageView = findViewById(R.id.one_dice) as ImageView
            imageView.setImageDrawable(null)
            imageView.setBackgroundResource(R.drawable.anim2)
            val animationDrawable = imageView.background as AnimationDrawable
            if (!animationDrawable.isRunning) {

                var totalFrameDuration = 0
                for (i in 0 until animationDrawable.numberOfFrames) {
                    totalFrameDuration += animationDrawable.getDuration(i)
                }

                animationDrawable.start()
                Handler().postDelayed(
                    Runnable { animationDrawable.stop() },
                    totalFrameDuration.toLong()
                )
            }
            was = false
            go_to_one()
        }
    }

    fun animation2_1() {
        counter_plus()
        if (was_first_screen > 1) {
            was = true
            val imageView2 = findViewById(R.id.two_dices) as ImageView
            imageView2.setImageDrawable(null)
            imageView2.setBackgroundResource(R.drawable.anim2)
            val animationDrawable2 = imageView2.background as AnimationDrawable
            if (!animationDrawable2.isRunning) {

                var totalFrameDuration2 = 0
                for (i in 0 until animationDrawable2.numberOfFrames) {
                    totalFrameDuration2 += animationDrawable2.getDuration(i)
                }

                animationDrawable2.start()
                Handler().postDelayed(
                    Runnable { animationDrawable2.stop() },
                    totalFrameDuration2.toLong()
                )
            }
            was = false
        }
    }

    fun animation2_2() {
        if (was_first_screen > 1) {
            was = true
            val imageView2 = findViewById(R.id.two_dices) as ImageView
            imageView2.setImageDrawable(null)
            imageView2.setBackgroundResource(R.drawable.anim)
            val animationDrawable2 = imageView2.background as AnimationDrawable
            if (!animationDrawable2.isRunning) {

                var totalFrameDuration2 = 0
                for (i in 0 until animationDrawable2.numberOfFrames) {
                    totalFrameDuration2 += animationDrawable2.getDuration(i)
                }

                animationDrawable2.start()
                Handler().postDelayed(
                    Runnable { animationDrawable2.stop() },
                    totalFrameDuration2.toLong()
                )
            }
            was = false
            go_to_two()
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
            val start_phone = findViewById(R.id.imageView3) as ImageView;
            start_phone.isVisible = false // Делаем нашу ImageButton (которая является подсказкой куда тыкать на экране) невидимой
        }

    }

}