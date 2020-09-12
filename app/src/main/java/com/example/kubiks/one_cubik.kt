package com.example.kubiks
import android.widget.ImageView
import androidx.core.content.ContextCompat
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.widget.ImageButton
import kotlinx.android.synthetic.main.activity_one_cubik.*
import kotlin.random.Random

class one_cubik : AppCompatActivity() {
    var was = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_cubik)
        val button_id = findViewById<ImageButton>(R.id.pbutton) as ImageButton
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
        }

    fun go_back(view: View){
        val back_activity = Intent(this, MainActivity::class.java)
        startActivity(back_activity)
    }
    fun rand_one(): Int {
        val el: Int= Random.nextInt(1, 7)
        return (el)
    }
    fun change_img_one(view: View){
        imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.diceo ))
    }
    fun change_img_two(view: View){
        imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.dicet ))
    }
    fun change_img_three(view: View){
        imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.diceth ))
    }
    fun change_img_four(view: View){
        imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.dicef ))
    }
    fun change_img_five(view: View){
        imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.dicefi ))
    }
    fun change_img_six(view: View){
        imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.dices ))
    }
    fun lets_play(view: View){
        val nom: Int = rand_one()
        when (rand_one()){
            1 -> change_img_one(view)
            2 -> change_img_two(view)
            3 -> change_img_three(view)
            4 -> change_img_four(view)
            5 -> change_img_five(view)
            6 -> change_img_six(view)
        }
    }
    fun animation1_1() {
            was = true
            val imageView = findViewById(R.id.pbutton) as ImageView
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
    fun animation1_2() {
            was = true
            val imageView = findViewById(R.id.pbutton) as ImageView
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
        }

}
