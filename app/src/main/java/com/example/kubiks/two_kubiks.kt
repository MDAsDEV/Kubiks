package com.example.kubiks

import android.widget.ImageView
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_one_cubik.*
import kotlinx.android.synthetic.main.activity_two_kubiks.*
import kotlin.random.Random

class two_kubiks : AppCompatActivity() {
    var was = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two_kubiks)
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
    fun change_img_one_do(view: View){
        imageViewup.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.dicedo ))
    }
    fun change_img_two_do(view: View){
        imageViewup.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.dicedt ))
    }
    fun change_img_three_do(view: View){
        imageViewup.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.dicedth ))
    }
    fun change_img_four_do(view: View){
        imageViewup.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.dicedf ))
    }
    fun change_img_five_do(view: View){
        imageViewup.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.dicedfi ))
    }
    fun change_img_six_do(view: View){
        imageViewup.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.diceds ))
    }
    fun change_img_one_dt(view: View){
        imageViewdown.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.diceuo ))
    }
    fun change_img_two_dt(view: View){
        imageViewdown.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.diceut ))
    }
    fun change_img_three_dt(view: View){
        imageViewdown.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.diceuth ))
    }
    fun change_img_four_dt(view: View){
        imageViewdown.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.diceuf ))
    }
    fun change_img_five_dt(view: View){
        imageViewdown.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.diceufi ))
    }
    fun change_img_six_dt(view: View){
        imageViewdown.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.diceus ))
    }
    fun lets_play_do(view: View){
        val nom: Int = rand_one()
        when (rand_one()){
            1 -> change_img_one_do(view)
            2 -> change_img_two_do(view)
            3 -> change_img_three_do(view)
            4 -> change_img_four_do(view)
            5 -> change_img_five_do(view)
            6 -> change_img_six_do(view)
        }
    }
    fun lets_play_dt(view: View){
        val nom: Int = rand_one()
        when (rand_one()){
            1 -> change_img_one_dt(view)
            2 -> change_img_two_dt(view)
            3 -> change_img_three_dt(view)
            4 -> change_img_four_dt(view)
            5 -> change_img_five_dt(view)
            6 -> change_img_six_dt(view)
        }
    }
    fun lets_play_two_cubes(view: View){
        lets_play_dt(view)
        lets_play_do(view)
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

