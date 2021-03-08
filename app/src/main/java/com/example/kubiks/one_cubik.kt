package com.example.kubiks
import android.annotation.SuppressLint
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import android.media.SoundPool.OnLoadCompleteListener
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_one_cubik.*
import kotlin.random.Random
import kotlin.system.exitProcess


class one_cubik : AppCompatActivity(), OnLoadCompleteListener {
    var was = false
    var loaded: Boolean = false
    lateinit var soundpool: SoundPool
    private lateinit var mp: MediaPlayer
    private var totalTime: Int = 0

    //private val soundId = 1
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_cubik)
        val button_id = findViewById<ImageButton>(R.id.pbutton) as ImageButton
        if (!was) {
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
        if (!was){
            button_back_id.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    when (event?.action){
                        MotionEvent.ACTION_DOWN -> scale_back()//Do Something
                    }
                    return v?.onTouchEvent(event) ?: true
                }
            })
        }

        val button_setings = findViewById<ImageButton>(R.id.settings_button) as ImageButton
        if (!was) {
            button_setings.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    when (event?.action) {
                        MotionEvent.ACTION_DOWN -> scale_settings()//Do Something
                    }
                    return v?.onTouchEvent(event) ?: true
                }
            })
        }



        mp = MediaPlayer.create(this, R.raw.dice)
        mp.isLooping = false
        mp.setVolume(0.5f, 0.5f)
        totalTime = mp.duration
        Log.i("total time == ", totalTime.toString())
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
    fun play_sound(){
        mp.start()

    }
    fun scale_play(){
        if (!mp.isPlaying) {
            play_sound()
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
            Log.i("test", "start function")
        }
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
    fun scale_settings(){
        was = true
        val image: ImageView = findViewById(R.id.settings_button)
        val animation = AnimationUtils.loadAnimation(this, R.anim.scale_settings)
        image.startAnimation(animation)
        animation.setAnimationListener(object : AnimationListener {
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

    override fun onLoadComplete(soundPool: SoundPool?, sampleId: Int, status: Int) {
        TODO("Not yet implemented")
    }
}





