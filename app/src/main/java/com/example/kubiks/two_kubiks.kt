package com.example.kubiks

import android.annotation.SuppressLint
import android.widget.ImageView
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_one_cubik.*
import kotlinx.android.synthetic.main.activity_two_kubiks.*
import kotlin.random.Random

class two_kubiks : AppCompatActivity() {
    var was = false
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two_kubiks)
        val button_id = findViewById<ImageButton>(R.id.pbutton) as ImageButton
        if (was == false) {
            button_id.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    when (event?.action){
                        MotionEvent.ACTION_DOWN -> scale_play()
                    }
                    return v?.onTouchEvent(event) ?: true
                }
            })
        }
        val button_back_id = findViewById<ImageButton>(R.id.bbutton) as ImageButton
        if (was == false) {
            button_back_id.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    when (event?.action){
                        MotionEvent.ACTION_DOWN -> scale_back()
                    }
                    return v?.onTouchEvent(event) ?: true
                }
            })
        }
        val button_setings = findViewById<ImageButton>(R.id.settings_button_2_kubiks) as ImageButton
        if (!was){
            button_setings.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    when (event?.action){
                        MotionEvent.ACTION_DOWN -> scale_settings()//Do Something
                    }
                    return v?.onTouchEvent(event) ?: true
                }
            })
        }

    }

    fun go_back(){
        val back_activity = Intent(this, MainActivity::class.java)
        startActivity(back_activity)
    }

    fun rand_one(): Int {
        val el: Int= Random.nextInt(1, 7)
        return (el)
    }
    fun change_img_one_do(){
        imageViewup.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.dicedo ))
    }
    fun change_img_two_do(){
        imageViewup.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.dicedt ))
    }
    fun change_img_three_do(){
        imageViewup.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.dicedth ))
    }
    fun change_img_four_do(){
        imageViewup.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.dicedf ))
    }
    fun change_img_five_do(){
        imageViewup.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.dicedfi ))
    }
    fun change_img_six_do(){
        imageViewup.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.diceds ))
    }
    fun change_img_one_dt(){
        imageViewdown.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.diceuo ))
    }
    fun change_img_two_dt(){
        imageViewdown.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.diceut ))
    }
    fun change_img_three_dt(){
        imageViewdown.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.diceuth ))
    }
    fun change_img_four_dt(){
        imageViewdown.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.diceuf ))
    }
    fun change_img_five_dt(){
        imageViewdown.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.diceufi ))
    }
    fun change_img_six_dt(){
        imageViewdown.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.diceus ))
    }
    fun lets_play_do(){
        val nom: Int = rand_one()
        when (rand_one()){
            1 -> change_img_one_do()
            2 -> change_img_two_do()
            3 -> change_img_three_do()
            4 -> change_img_four_do()
            5 -> change_img_five_do()
            6 -> change_img_six_do()
        }
    }
    fun lets_play_dt(){
        val nom: Int = rand_one()
        when (rand_one()){
            1 -> change_img_one_dt()
            2 -> change_img_two_dt()
            3 -> change_img_three_dt()
            4 -> change_img_four_dt()
            5 -> change_img_five_dt()
            6 -> change_img_six_dt()
        }
    }
    fun lets_play_two_cubes(){
        lets_play_dt()
        lets_play_do()
    }

    fun scale_back(){
        was = true
        val image: ImageView = findViewById(R.id.bbutton)
        val animation =
            AnimationUtils.loadAnimation(this, R.anim.scale_back)
        image.startAnimation(animation)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
            }
            override fun onAnimationEnd(animation: Animation) {
                Log.i("START", "here started")
                go_back()
            }
            override fun onAnimationRepeat(animation: Animation) {}
        })
    }
    fun scale_play(){
        was = true
        val image: ImageView = findViewById(R.id.pbutton)
        val animation =
            AnimationUtils.loadAnimation(this, R.anim.scale_anim)
        image.startAnimation(animation)
        val animation_empty = AnimationUtils.loadAnimation(this, R.anim.scale_empty)

        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
            }
            override fun onAnimationEnd(animation: Animation) {
                Log.i("START NEW", "START")
                image.startAnimation(animation_empty)
            }
            override fun onAnimationRepeat(animation: Animation) {}
        })

        animation_empty.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
            }
            override fun onAnimationEnd(animation: Animation) {
                Log.i("START", "here started")
                lets_play_two_cubes()
            }
            override fun onAnimationRepeat(animation: Animation) {}
        })
        was = false
    }
    fun scale_settings(){
        was = true
        val image: ImageView = findViewById(R.id.settings_button_2_kubiks)
        val animation = AnimationUtils.loadAnimation(this, R.anim.scale_settings)
        image.startAnimation(animation)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
            }
            override fun onAnimationEnd(animation: Animation) {
                Log.i("START", "here started")
                go_to_settings()
            }
            override fun onAnimationRepeat(animation: Animation) {}
        })
    }
    fun go_to_settings(){
        val intent_setting = Intent(this, settings::class.java)
        startActivity(intent_setting)
    }
}

