package com.example.kubiks

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_settings.*

class settings : AppCompatActivity() {
    private val SOUND_PREFERENCES_MODE = "sound"
    var is_mute_sound: Boolean = false
    var was = false
    private lateinit var prefs_sound: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        prefs_sound = getSharedPreferences("sound_settings", Context.MODE_PRIVATE)

        if (prefs_sound.contains(SOUND_PREFERENCES_MODE)){
            is_mute_sound = prefs_sound.getBoolean(SOUND_PREFERENCES_MODE, false)
            Log.i("test music preferences", is_mute_sound.toString())
        }
        Log.i("Sound check == ", "SOUND IS " + is_mute_sound.toString())
        val buttonSound = findViewById<ImageButton>(R.id.button_beautiful_sound)
        val imageSoundText = findViewById<ImageView>(R.id.onbutton)
        if (is_mute_sound){
            val OffSoundDrawable = ContextCompat.getDrawable(applicationContext, R.drawable.clearedvoiceoffbutton_edited)
            val OffSoundText = ContextCompat.getDrawable(applicationContext, R.drawable.off_button)
            val OffSoundText_obj = findViewById<ImageView>(R.id.onbutton)
            /*OffSoundText_obj.scaleX = 1.2.toFloat()
            OffSoundText_obj.scaleY = 2.toFloat()
            */
            buttonSound.setImageDrawable(OffSoundDrawable)
            imageSoundText.setImageDrawable(OffSoundText)
            Log.i("SOUND BUTTON OPEN == ", "OFF")
        }
        else {
            val OnSoundDrawable = ContextCompat.getDrawable(applicationContext, R.drawable.clearedvoicebutton)
            val OnSoundText = ContextCompat.getDrawable(applicationContext, R.drawable.testtransparenton)
            imageSoundText.setImageDrawable(OnSoundText)
            buttonSound.setImageDrawable(OnSoundDrawable)
            Log.i("SOUND BUTTON2 == ", "ON")
        }


        val button_id = findViewById<ImageButton>(R.id.button_beautiful_sound) as ImageButton
        if (was == false) {
            button_id.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    when (event?.action){
                        MotionEvent.ACTION_DOWN -> scale_sound()//Do Something
                    }

                    return v?.onTouchEvent(event) ?: true
                }
            })
        }
        val button_back_id = findViewById<ImageButton>(R.id.homeBackButton) as ImageButton
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
    }
    fun change_sound_mode(){
        if (prefs_sound.contains(SOUND_PREFERENCES_MODE)){
            is_mute_sound = prefs_sound.getBoolean(SOUND_PREFERENCES_MODE, false)
            Log.i("test music preferences", is_mute_sound.toString())
        }
        is_mute_sound = (1 - is_mute_sound.compareTo(false)) == 1
        Log.i("boolean tag == ", is_mute_sound.toString())
        val edit_sound_settings = prefs_sound.edit()
        edit_sound_settings.putBoolean(SOUND_PREFERENCES_MODE, is_mute_sound).apply()
        val buttonSound = findViewById<ImageButton>(R.id.button_beautiful_sound)
        val imageSoundText = findViewById<ImageView>(R.id.onbutton)
        if (is_mute_sound){
            val OffSoundDrawable = ContextCompat.getDrawable(applicationContext, R.drawable.clearedvoiceoffbutton_edited)
            val OffSoundText = ContextCompat.getDrawable(applicationContext, R.drawable.off_button)
            val OffSoundText_obj = findViewById<ImageView>(R.id.onbutton)
            OffSoundText_obj.scaleX = 1.2.toFloat()
            OffSoundText_obj.scaleY = 1.4.toFloat()

            buttonSound.setImageDrawable(OffSoundDrawable)
            imageSoundText.setImageDrawable(OffSoundText)
            Log.i("SOUND BUTTON == ", "OFF")
        }
        else {
            val OnSoundDrawable = ContextCompat.getDrawable(applicationContext, R.drawable.clearedvoicebutton)
            val OnSoundText = ContextCompat.getDrawable(applicationContext, R.drawable.testtransparenton)
            imageSoundText.setImageDrawable(OnSoundText)
            buttonSound.setImageDrawable(OnSoundDrawable)
            Log.i("SOUND BUTTON2 == ", "ON")
        }

    }
    fun go_home(){
        val back_activity = Intent(this, MainActivity::class.java)
        startActivity(back_activity)
    }

    override fun onPause() {
        super.onPause()
        val edit_sound_settings = prefs_sound.edit()
        edit_sound_settings.putBoolean(SOUND_PREFERENCES_MODE, is_mute_sound).apply()
    }

    override fun onResume() {
        super.onResume()
        if (prefs_sound.contains(SOUND_PREFERENCES_MODE)){
            is_mute_sound = prefs_sound.getBoolean(SOUND_PREFERENCES_MODE, false)
            Log.i("test music preferences", is_mute_sound.toString())
        }
    }

    fun scale_back(){
        was = true
        val image: ImageView = findViewById(R.id.homeBackButton)
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
    fun scale_sound(){
        was = true
        val image: ImageView = findViewById(R.id.button_beautiful_sound)
        val animation =
            AnimationUtils.loadAnimation(this, R.anim.scale_back)
        image.startAnimation(animation)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
            }
            override fun onAnimationEnd(animation: Animation) {
                Log.i("START", "here started")
                change_sound_mode()
            }
            override fun onAnimationRepeat(animation: Animation) {}
        })

    }
    fun go_back(){
        val back_activity = Intent(this, MainActivity::class.java)
        startActivity(back_activity)
    }
}