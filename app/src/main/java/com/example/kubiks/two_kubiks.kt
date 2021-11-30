package com.example.kubiks

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_one_cubik.*
import kotlinx.android.synthetic.main.activity_two_kubiks.*
import kotlin.random.Random

class two_kubiks : AppCompatActivity() {
    var was = false
    private lateinit var mp: MediaPlayer
    private var totalTime: Int = 0
    public var leftVolume: Int = 1
    public var rightVolume: Int = 1
    private val SOUND_PREFERENCES_MODE = "sound"
    private val BACKGROUND_PREFERENCE_MODE = "background"
    var is_mute_sound: Boolean = false
    private lateinit var prefs_sound: SharedPreferences
    private lateinit var prefs_background: SharedPreferences

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two_kubiks)
        val button_id = findViewById<ImageButton>(R.id.pbutton) as ImageButton
        val TwoKubiksLayout = findViewById<RelativeLayout>(R.id.layout_test)
        prefs_sound = getSharedPreferences("sound_settings", Context.MODE_PRIVATE)
        prefs_background = getSharedPreferences("background_settings" ,Context.MODE_PRIVATE)
        if (prefs_background.contains(BACKGROUND_PREFERENCE_MODE)){
            val color_background = prefs_background.getString(BACKGROUND_PREFERENCE_MODE, "None")
            Log.i("test color background ", color_background)
            if (color_background == "Желтый"){
                TwoKubiksLayout.setBackgroundResource(R.drawable.ic_launcher_background)
            }
        }
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

        mp = MediaPlayer.create(this, R.raw.dice)
        totalTime = mp.duration
        Log.i("total time == ", totalTime.toString())

    }
    fun play_sound(){
        mp.isLooping = false
        mp.setVolume(leftVolume.toFloat(), rightVolume.toFloat())
        mp.start()

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
        if (!mp.isPlaying) {
            play_sound()
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
    override fun onStart() {
        super.onStart()
        Log.i("Volume Level == ", leftVolume.toString() + ' ' + rightVolume.toString())
    }
    override fun onResume() { // Функция, запускающаяся при включении приложения
        super.onResume()
        Log.i("check event", "on resume event")
        if (prefs_sound.contains(SOUND_PREFERENCES_MODE)) {
            Log.i("check_event", "on resume event if 2")
            is_mute_sound = prefs_sound.getBoolean(SOUND_PREFERENCES_MODE, false)
            Log.i("test music preferences", is_mute_sound.toString())
            if (is_mute_sound) {
                leftVolume = 0
                rightVolume = 0
            } else {
                leftVolume = 1
                rightVolume = 1
            }
            Log.i("Volume Level == ", leftVolume.toString() + ' ' + rightVolume.toString())
        }
    }
}

