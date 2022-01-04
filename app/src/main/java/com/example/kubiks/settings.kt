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
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_settings.*
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener

import android.widget.Toast




class settings : AppCompatActivity() {
    private val SOUND_PREFERENCES_MODE = "sound"
    private val BACKGROUND_PREFERENCE_MODE = "background"
    private val LANGUAGE_PREFERENCE_MODE = "language"
    var language_data: String = "rus"
    var is_mute_sound: Boolean = false
    var was = false
    private lateinit var prefs_sound: SharedPreferences
    private lateinit var prefs_background: SharedPreferences
    private lateinit var prefs_language: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("check error creating activity", "Successfull")
        super.onCreate(savedInstanceState)
        Log.i("check error creating activity2", "Successfull")
        setContentView(R.layout.activity_settings)
        Log.i("check error creating activity3", "Successfull")
        prefs_sound = getSharedPreferences("sound_settings", Context.MODE_PRIVATE)
        Log.i("check error creating activity4", "Successfull")
        prefs_background = getSharedPreferences("background_settings" ,Context.MODE_PRIVATE)
        Log.i("check error creating activity5", "Successfull")
        prefs_language = getSharedPreferences("language_settings", Context.MODE_PRIVATE)
        Log.i("check error creating activity6", "Successfull")
        val AllColors = resources.getStringArray(R.array.colors)
        if (prefs_sound.contains(SOUND_PREFERENCES_MODE)) {
            is_mute_sound = prefs_sound.getBoolean(SOUND_PREFERENCES_MODE, false)
            Log.i("test music preferences", is_mute_sound.toString())
        }
        if (prefs_background.contains(BACKGROUND_PREFERENCE_MODE)){
            val color_background = prefs_background.getString(BACKGROUND_PREFERENCE_MODE, "None")
            Log.i("test color background ", color_background)
        }
        if (prefs_language.contains(LANGUAGE_PREFERENCE_MODE)){
            language_data = prefs_language.getString(LANGUAGE_PREFERENCE_MODE, "None").toString()
            Log.i("test language data = =", language_data)
        }
        else{
            Log.i("language data", "not found")
            var prefs_language_edit = prefs_language.edit()
            prefs_language_edit.putString(LANGUAGE_PREFERENCE_MODE, "rus").apply()
            language_data = "rus"
        }
        val SwitchLanguage: SwitchCompat = findViewById<SwitchCompat>(R.id.switch_language)
        SwitchLanguage.isChecked = "eng" in language_data
        Log.i("start language data == ", ("eng" in language_data).toString())
        SwitchLanguage.setOnCheckedChangeListener {
            buttonView, isChecked -> change_language_mode()
        }
        // Необходимо добавить метод setSelection!!!
        val SwitchSound: SwitchCompat = findViewById<SwitchCompat>(R.id.switch_sound)
        SwitchSound.isChecked = !is_mute_sound
        SwitchSound.setOnCheckedChangeListener { buttonView, isChecked ->
            change_sound_mode()
        }

        val button_back_id = findViewById<ImageButton>(R.id.homeBackButton) as ImageButton
        if (was == false) {
            button_back_id.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    when (event?.action) {
                        MotionEvent.ACTION_DOWN -> scale_back()
                    }
                    return v?.onTouchEvent(event) ?: true
                }
            })
        }
        Log.i("check error creating activity7", "Succesfull")
        val spinner_object = findViewById<Spinner>(R.id.spinner_choose_fone)
        Log.i("language data check error == ", language_data)
        if ("eng" in language_data){
            var Data = resources.getStringArray(R.array.colors_eng)
            Log.i("data DATA eng == ", Data.toString())
            var DataArrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, Data)
            spinner_object.adapter = DataArrayAdapter
        }
        else
        {
            var Data = resources.getStringArray(R.array.colors)
            Log.i("data DATA rus == ", Data.toString())
            var DataArrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, Data)
            spinner_object.adapter = DataArrayAdapter
        }
        if (prefs_background.contains(BACKGROUND_PREFERENCE_MODE)){
            val color_background = prefs_background.getString(BACKGROUND_PREFERENCE_MODE, "None")
            val color_background_id = AllColors.indexOf(color_background)
            spinner_object.setSelection(color_background_id)
        }
        spinner_object.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View, selectedItemPosition: Int, selectedId: Long
            ) {
                Log.i("Selected Item Poion == ", selectedItemPosition.toString())
                val CurrentBackgroundColor = AllColors.get(selectedItemPosition)
                changeBackgroundMode(CurrentBackgroundColor)

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
    }

    fun change_language_mode(){
        Log.i("lang", "check")
        if (prefs_language.contains(LANGUAGE_PREFERENCE_MODE)){
            language_data = prefs_language.getString(LANGUAGE_PREFERENCE_MODE, "None").toString()
            Log.i("lang found == ", language_data.toString())
        }
        Log.i("lang == ", language_data)
        val edit_language_prefs = prefs_language.edit()
        when (language_data){
            "rus" -> edit_language_prefs.putString(LANGUAGE_PREFERENCE_MODE, "eng".toString()).apply()
            "eng" -> edit_language_prefs.putString(LANGUAGE_PREFERENCE_MODE, "rus".toString()).apply()
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
    }
    fun go_home(){
        val back_activity = Intent(this, MainActivity::class.java)
        startActivity(back_activity)
    }

    fun changeBackgroundMode(NewColor: String){
        if (prefs_background.contains(BACKGROUND_PREFERENCE_MODE)){
            val color_background = prefs_background.getString(BACKGROUND_PREFERENCE_MODE, "None")
        }
        val edit_background_settings = prefs_background.edit()
        edit_background_settings.putString(BACKGROUND_PREFERENCE_MODE, NewColor).apply()
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

    fun go_back(){
        val back_activity = Intent(this, MainActivity::class.java)
        startActivity(back_activity)
    }
}