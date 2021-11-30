package com.example.kubiks
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_one_cubik.*
import kotlin.random.Random
import kotlin.system.exitProcess


class one_cubik : AppCompatActivity(), OnLoadCompleteListener {
    var was = false
    var loaded: Boolean = false
    lateinit var soundpool: SoundPool
    private lateinit var mp: MediaPlayer
    private var totalTime: Int = 0
    public var leftVolume: Int = 1
    public var rightVolume: Int = 1
    private val SOUND_PREFERENCES_MODE = "sound"
    private val BACKGROUND_PREFERENCES_MODE = "background"
    var is_mute_sound: Boolean = false
    private lateinit var prefs_sound: SharedPreferences
    private lateinit var prefs_background: SharedPreferences
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_cubik)
        val OneKubikLayout = findViewById<RelativeLayout>(R.id.one_kubik_relative_layout_id)
        prefs_sound = getSharedPreferences("sound_settings", Context.MODE_PRIVATE)
        prefs_background = getSharedPreferences("background_settings", Context.MODE_PRIVATE)

        if (prefs_background.contains(BACKGROUND_PREFERENCES_MODE)){
            val color_background = prefs_background.getString(BACKGROUND_PREFERENCES_MODE, "None")
            Log.i("test colorba_one kubik ", color_background)
            if (color_background == "Желтый"){
                OneKubikLayout.setBackgroundResource(R.drawable.ic_launcher_background)
            }
        }

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
        mp.isLooping = false
        mp.setVolume(leftVolume.toFloat(), rightVolume.toFloat())
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

    override fun onStart() {
        super.onStart()
        Log.i("Volume Level == ", leftVolume.toString() + ' ' + rightVolume.toString())
    }
    override fun onResume() { // Функция, запускающаяся при включении приложения
        super.onResume()
        Log.i("check event", "on resume event")
        if (prefs_sound.contains(SOUND_PREFERENCES_MODE)){
            Log.i("check_event", "on resume event if 2")
            is_mute_sound = prefs_sound.getBoolean(SOUND_PREFERENCES_MODE, false)
            Log.i("test music preferences", is_mute_sound.toString())
            if (is_mute_sound){
                leftVolume = 0
                rightVolume = 0
            }
            else {
                leftVolume = 1
                rightVolume = 1
            }
            Log.i("Volume Level == ", leftVolume.toString() + ' ' + rightVolume.toString())
        }

    }
    override fun onLoadComplete(soundPool: SoundPool?, sampleId: Int, status: Int) {
        TODO("Not yet implemented")
    }
}





