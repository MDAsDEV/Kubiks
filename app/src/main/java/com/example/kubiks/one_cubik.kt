package com.example.kubiks
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_one_cubik.*
import kotlin.random.Random


class one_cubik : AppCompatActivity() {
    var was = false
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_cubik)
        val button_id = findViewById<ImageButton>(R.id.pbutton) as ImageButton
        if (was == false) {
            button_id.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    when (event?.action){
                        MotionEvent.ACTION_DOWN -> scale_play()//Do Something
                    }
                    return v?.onTouchEvent(event) ?: true
                }
            })
        }
        val button_back_id = findViewById<ImageButton>(R.id.bbutton) as ImageButton
        if (was == false){
            button_back_id.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    when (event?.action){
                        MotionEvent.ACTION_DOWN -> scale_back()//Do Something
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
    fun change_img_one(){
        imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.diceo ))
    }
    fun change_img_two(){
        imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.dicet ))
    }
    fun change_img_three(){
        imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.diceth ))
    }
    fun change_img_four(){
        imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.dicef ))
    }
    fun change_img_five(){
        imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.dicefi ))
    }
    fun change_img_six(){
        imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.dices ))
    }
    fun lets_play(){
        val nom: Int = rand_one()
        when (rand_one()){
            1 -> change_img_one()
            2 -> change_img_two()
            3 -> change_img_three()
            4 -> change_img_four()
            5 -> change_img_five()
            6 -> change_img_six()
        }
    }
    fun scale_play(){
        was = true
        val image: ImageView = findViewById(R.id.pbutton)
        val animation =
            AnimationUtils.loadAnimation(this, R.anim.scale_anim)
        image.startAnimation(animation)
        val animation_empty = AnimationUtils.loadAnimation(this, R.anim.scale_empty)

        animation.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation) {
            }
            override fun onAnimationEnd(animation: Animation) {
                Log.i("START NEW", "START")
                image.startAnimation(animation_empty)
            }
            override fun onAnimationRepeat(animation: Animation) {}
        })

        animation_empty.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation) {
            }
            override fun onAnimationEnd(animation: Animation) {
                Log.i("START", "here started")
                lets_play()
            }
            override fun onAnimationRepeat(animation: Animation) {}
        })
        was = false
    }
    fun scale_back(){
        was = true
        val image: ImageView = findViewById(R.id.bbutton)
        val animation =
            AnimationUtils.loadAnimation(this, R.anim.scale_back)
        image.startAnimation(animation)
        animation.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation) {
            }
            override fun onAnimationEnd(animation: Animation) {
                Log.i("START", "here started")
                go_back()
            }
            override fun onAnimationRepeat(animation: Animation) {}
        })
    }

}


