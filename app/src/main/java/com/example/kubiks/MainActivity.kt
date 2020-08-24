package com.example.kubiks
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Handler
import android.view.MotionEvent
import android.widget.Button
import android.app.Notification
import android.content.DialogInterface
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.util.Log.*
import android.widget.ImageButton
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_one_cubik.*
import android.util.Log.e as utilLogE

class MainActivity : AppCompatActivity() {
    var was = false
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button_id = findViewById<ImageButton>(R.id.one_dice) as ImageButton
        if (was == false) {
            button_id.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    when (event?.action){
                        MotionEvent.ACTION_DOWN -> change_img()//Do Something
                        MotionEvent.ACTION_UP -> go_to_one()
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
                        MotionEvent.ACTION_DOWN -> change_img2()//Do Something
                        MotionEvent.ACTION_UP -> go_to_two()
                    }

                    return v?.onTouchEvent(event) ?: true
                }
            })
        }
    }

    fun go_to_one(){
        val one_activity = Intent(this, one_cubik::class.java)
        startActivity(one_activity)
    }
    fun go_to_two(){
        val two_activity = Intent(this, two_kubiks::class.java)
        startActivity(two_activity)
    }
    fun change_img() {
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
            Handler().postDelayed(Runnable { animationDrawable.stop() },
                totalFrameDuration.toLong()
            )
        }
        was = false
    }
    fun change_img2() {
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
            Handler().postDelayed(Runnable { animationDrawable2.stop() },
                totalFrameDuration2.toLong()
            )
        }
        was = false
    }

}