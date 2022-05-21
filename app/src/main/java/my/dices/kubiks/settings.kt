package my.dices.kubiks
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import kotlinx.android.synthetic.main.activity_settings.*
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import com.yandex.mobile.ads.banner.AdSize
import com.yandex.mobile.ads.banner.BannerAdView
import com.yandex.mobile.ads.common.AdRequest


class settings : AppCompatActivity() {
    private val SOUND_PREFERENCES_MODE = "sound"
    private val BACKGROUND_PREFERENCE_MODE = "background"
    private val LANGUAGE_PREFERENCE_MODE = "language"
    private val SHAKE_PREFERENCES_MODE = "shake"
    var language_data: String = "rus"
    var is_mute_sound: Boolean = false
    var is_shake: Boolean = false
    var was = false
    private lateinit var prefs_sound: SharedPreferences
    private lateinit var prefs_background: SharedPreferences
    private lateinit var prefs_language: SharedPreferences
    private lateinit var prefs_shake: SharedPreferences
    lateinit var spinner_object: Spinner
    lateinit var SpinnerLanguage: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        prefs_sound = getSharedPreferences("sound_settings", MODE_PRIVATE)
        prefs_background = getSharedPreferences("background_settings" , MODE_PRIVATE)
        prefs_language = getSharedPreferences("language_settings", MODE_PRIVATE)
        prefs_shake = getSharedPreferences("shake_settings",  MODE_PRIVATE)
        val SwitchSound: SwitchCompat = findViewById<SwitchCompat>(R.id.switch_sound)
        val SwitchShake: SwitchCompat = findViewById<SwitchCompat>(R.id.switch_shake)
        val LayoutSettings = findViewById<RelativeLayout>(R.id.settings_layout)


        val AllColors = resources.getStringArray(R.array.colors)
        val AllLanguages = resources.getStringArray(R.array.languages)

        val banner = findViewById<BannerAdView>(R.id.banner_settings)
        banner.setAdUnitId("R-M-1611044-1")
        banner.setAdSize(AdSize.BANNER_320x50)
        Log.i("test advertisement == ", "test ad")
        val adRequest = AdRequest.Builder().build()
        banner.loadAd(adRequest)
        Log.i("test advertisement == ", "ad uploaded")


        if (prefs_sound.contains(SOUND_PREFERENCES_MODE)) {
            is_mute_sound = prefs_sound.getBoolean(SOUND_PREFERENCES_MODE, false)
            Log.i("test music preёferences", is_mute_sound.toString())
        }
        if (prefs_shake.contains(SHAKE_PREFERENCES_MODE)){
            is_shake = prefs_shake.getBoolean(SHAKE_PREFERENCES_MODE, false)
            Log.i("test shake preferences", is_shake.toString())
        }
        if (prefs_background.contains(BACKGROUND_PREFERENCE_MODE)){
            val color_background = prefs_background.getString(BACKGROUND_PREFERENCE_MODE, "None")
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
        SwitchSound.isChecked = !is_mute_sound
        SwitchSound.setOnCheckedChangeListener { buttonView, isChecked ->
            change_sound_mode()
        }
        SwitchShake.isChecked = is_shake
        SwitchShake.setOnCheckedChangeListener { buttonView, isChecked -> change_shake_mode()}

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
        Log.i("check error creating acti", "Succesfull")
        spinner_object = findViewById<Spinner>(R.id.spinner_choose_fone)
        SpinnerLanguage = findViewById<Spinner>(R.id.spinner_language)
        Log.i("language data check error ", language_data)
        if ("eng" in language_data){
            button_back_id.setImageResource(R.drawable.home_button_border_eng)
            SwitchSound.textOff = "OFF"
            SwitchSound.textOn = "ON"
            SwitchShake.textOff = "OFF"
            SwitchShake.textOn = "ON"
            var DataColors = resources.getStringArray(R.array.colors_eng)
            Log.i("data DATA eng == ", DataColors.toString())
            var DataArrayAdapter = ArrayAdapter.createFromResource(this, R.array.colors_eng,
            R.layout.myspinner)
            var TextViewSound = findViewById<TextView>(R.id.textview_sound)
            TextViewSound.setText(R.string.sound_textview_eng)
            spinner_object.adapter = DataArrayAdapter
            SpinnerLanguage.prompt = "Choose Language"
            SpinnerLanguage.setSelection(1)
            Log.i("test spinner language eng ", R.string.prompt_language_eng.toString())
            spinner_object.prompt = "Choose background color"
            Log.i("test spinner phone eng ", R.string.prompt_phone_eng.toString())
        }
        else
        {
            button_back_id.setImageResource(R.drawable.home_button_border_rus)
            SwitchSound.textOff = "ВЫКЛ"
            SwitchSound.textOn = "ВКЛ"
            SwitchShake.textOff = "ВЫКЛ"
            SwitchShake.textOn = "ВКЛ"
            var Data = resources.getStringArray(R.array.colors)
            Log.i("data DATA rus == ", Data.toString())
            var DataArrayAdapter = ArrayAdapter.createFromResource(this, R.array.colors,
                R.layout.myspinner)
            DataArrayAdapter.setDropDownViewResource(R.layout.myspinner)
            spinner_object.adapter = DataArrayAdapter
            SpinnerLanguage.setSelection(0)
            var TextViewSound = findViewById<TextView>(R.id.textview_sound)
            TextViewSound.setText(R.string.sound_textview_rus)
            var TextViewShake = findViewById<TextView>(R.id.textview_shake)
            TextViewShake.setText("ТРЯСКА")
            SpinnerLanguage.prompt = "Выберите язык"
            Log.i("test spinner language ", R.string.prompt_language_rus.toString())
            spinner_object.prompt = "Выберите цвет фона"
            Log.i("test phone language ", R.string.prompt_phone_rus.toString())
        }
        if (prefs_background.contains(BACKGROUND_PREFERENCE_MODE)){
            val color_background = prefs_background.getString(BACKGROUND_PREFERENCE_MODE, "None")
            val color_background_id = AllColors.indexOf(color_background)
            Log.i("color background id ", color_background_id.toString())
            spinner_object.setSelection(color_background_id)
        }
        var check: Int = 0
            spinner_object.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View, selectedItemPosition: Int, selectedId: Long
            ) {
                Log.i("Selected Item Poion", selectedItemPosition.toString())
                val CurrentBackgroundColor = AllColors.get(selectedItemPosition)

                changeBackgroundMode(CurrentBackgroundColor)


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
        SpinnerLanguage.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View, selectedItemPosition: Int, selectedId: Long
            )
            {
                    Log.i("Selected Item languag", selectedItemPosition.toString())
                        if (selectedItemPosition == 1) {
                        change_language_mode("eng")
                    }
                    else {
                        change_language_mode("rus")
                    }
                Log.i("check23 == ", check.toString())
                if (++check > 1){
                    Log.i("check23 > 1 == ", check.toString())
                    val handler = Handler()
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                    check += 1
                    }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
    }


    fun change_language_mode(NewLang: String){
        Log.i("language mode", "we are here")
        Log.i("language data == ", NewLang)
        val edit_language_prefs = prefs_language.edit()
        edit_language_prefs.putString(LANGUAGE_PREFERENCE_MODE, NewLang.toString()).apply()
    }

    fun change_shake_mode(){
        if (prefs_shake.contains(SHAKE_PREFERENCES_MODE)){
            is_shake = prefs_shake.getBoolean(SHAKE_PREFERENCES_MODE, false)
            Log.i ("test shake sound == ", is_shake.toString())
        }
        is_shake = (1 - is_shake.compareTo(false)) == 1
        Log.i ("test boolean shake tag ", is_shake.toString())
        val edit_shake_settings = prefs_shake.edit()
        edit_shake_settings.putBoolean(SHAKE_PREFERENCES_MODE, is_shake).apply()
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