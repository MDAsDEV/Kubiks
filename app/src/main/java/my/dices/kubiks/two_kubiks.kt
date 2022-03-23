package my.dices.kubiks

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import android.content.Intent
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    private val LANGUAGE_PREFERENCE_MODE = "language"
    private val SHAKE_PREFERENCE_MODE = "shake"
    lateinit var language_data: String
    var is_mute_sound: Boolean = false
    var shake_data: Boolean = true
    private var is_english: Boolean = false
    private lateinit var prefs_sound: SharedPreferences
    private lateinit var prefs_background: SharedPreferences
    private lateinit var prefs_language: SharedPreferences
    private lateinit var prefs_shake: SharedPreferences
    private var mSensorManager: SensorManager? = null
    private var mAccelerometer: Sensor? = null
    private var mShakeDetector: ShakeDetector? = null
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two_kubiks)
        val button_id = findViewById<ImageButton>(R.id.pbutton) as ImageButton
        val TwoKubiksLayout = findViewById<RelativeLayout>(R.id.layout_test)
        val TwoKubiksPlayButton = findViewById<ImageButton>(R.id.pbutton)
        val TwoKubiksSettingsButton = findViewById<ImageButton>(R.id.settings_button_2_kubiks)
        val TwoKubiksBackButton = findViewById<ImageButton>(R.id.bbutton)
        val TwoKubiksImageViewDown = findViewById<ImageView>(R.id.imageViewdown)
        val TwoKubiksImageViewUp = findViewById<ImageView>(R.id.imageViewup)
        //val TwoKubiksBackButton = findViewById<ImageButton>(R.id.)
        prefs_sound = getSharedPreferences("sound_settings", Context.MODE_PRIVATE)
        prefs_background = getSharedPreferences("background_settings" ,Context.MODE_PRIVATE)
        prefs_language = getSharedPreferences("language_settings", Context.MODE_PRIVATE)
        prefs_shake = getSharedPreferences("shake_settings", MODE_PRIVATE)
        if (prefs_shake.contains(SHAKE_PREFERENCE_MODE)){
            shake_data = prefs_shake.getBoolean("shake", false)
            if (shake_data){
                initSensor()
            }
        }
        if (prefs_background.contains(BACKGROUND_PREFERENCE_MODE)){
            if (prefs_language.contains(LANGUAGE_PREFERENCE_MODE)){
                language_data = prefs_language.getString(LANGUAGE_PREFERENCE_MODE, "None").toString()
                is_english = ("eng" in language_data)
            }
            Log.i("language data == ", language_data)
            Log.i("language data is english == ", is_english.toString())
            val color_background = prefs_background.getString(BACKGROUND_PREFERENCE_MODE, "None")
            if (color_background == "Зеленый"){
                TwoKubiksLayout.setBackgroundResource(R.drawable.background_salad)
                TwoKubiksSettingsButton.setImageResource(R.drawable.settings_green_transparent)
                if (!is_english){
                    TwoKubiksPlayButton.setImageResource(R.drawable.play_rus_green_transparent)
                }
                else {
                    TwoKubiksPlayButton.setImageResource(R.drawable.play_green_transparent)
                }
                TwoKubiksImageViewUp.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.dicedth_green_transparent
                    )
                )
                TwoKubiksImageViewDown.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.diceuth_green_transparent
                    )
                )
                TwoKubiksBackButton.setImageResource(R.drawable.back_green_transparent)
            }
            if (color_background == "Розовый"){
                TwoKubiksLayout.setBackgroundResource(R.drawable.background_pink)
                if (!is_english){
                    TwoKubiksPlayButton.setImageResource(R.drawable.play_rus_pink_transparent)
                }
                else {
                    TwoKubiksPlayButton.setImageResource(R.drawable.play_pink)
                }
                TwoKubiksSettingsButton.setImageResource(R.drawable.settings_pink_transparent)
                TwoKubiksBackButton.setImageResource(R.drawable.back_pink_transparent)
                TwoKubiksImageViewUp.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.dicedth_pink_transparent
                    )
                )
                TwoKubiksImageViewDown.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.diceuth_pink_transparent
                    )
                )
            }
            if (color_background == "Голубой"){
                if (!is_english){
                    TwoKubiksPlayButton.setImageResource(R.drawable.play_rus_blue_transparent)
                }
                else {
                    TwoKubiksPlayButton.setImageResource(R.drawable.playbutton_blue)
                }
                TwoKubiksLayout.setBackgroundResource(R.drawable.backgroundsecw_blue)
            }
            if (color_background == "Фиолетовый"){
                TwoKubiksLayout.setBackgroundResource(R.drawable.background_purple)
                Log.i("background purple == ", "found purple")
                TwoKubiksBackButton.setImageResource(R.drawable.back_purple_transparent)
                TwoKubiksSettingsButton.setImageResource(R.drawable.settings_purple_transparent)
                if (!is_english){
                    TwoKubiksPlayButton.setImageResource(R.drawable.play_rus_purple_transparent)
                }
                else {
                    TwoKubiksPlayButton.setImageResource(R.drawable.play_purple_transparent)
                }
                TwoKubiksImageViewUp.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.dicedth_purple_transparent
                    )
                )
                TwoKubiksImageViewDown.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.diceuth_purple_transparent
                    )
                )
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
    //1 кубик низ
    fun change_img_one_do(CurrentColor: String){
        if (CurrentColor == "Зеленый") {
            imageViewup.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.dicedo_green_transparent
                )
            )
        }
        else if (CurrentColor == "Голубой"){
            imageViewup.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.dicedo
                )
            )
        }
        else if (CurrentColor == "Розовый") {
            imageViewup.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.dicedo_pink_transparent
                )
            )
        }
        else if (CurrentColor == "Фиолетовый"){
            imageViewup.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.dicedo_purple_transparent
                )
            )
        }
    }
    //2 кубика
    fun change_img_two_do(CurrentColor: String){
        if (CurrentColor == "Зеленый") {
            imageViewup.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.dicedt_green_transparent
                )
            )
        }
        else if (CurrentColor == "Голубой"){
            imageViewup.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.dicedt
                )
            )
        }
        else if (CurrentColor == "Розовый") {
            imageViewup.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.dicedt_pink_transparent
                )
            )
        }
        else if (CurrentColor == "Фиолетовый"){
            imageViewup.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.dicedt_purple_transparent
                )
            )
        }
    }

    //три кубика
    fun change_img_three_do(CurrentColor: String){
        if (CurrentColor == "Зеленый") {
            imageViewup.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.dicedth_green_transparent
                )
            )
        }
        else if (CurrentColor == "Голубой"){
            imageViewup.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.dicedth
                )
            )
        }
        else if (CurrentColor == "Розовый") {
            imageViewup.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.dicedth_pink_transparent
                )
            )
        }
        else if (CurrentColor == "Фиолетовый"){
            imageViewup.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.dicedt_purple_transparent
                )
            )
        }
    }
    //4 кубика!!
    fun change_img_four_do(CurrentColor: String){
        if (CurrentColor == "Зеленый") {
            imageViewup.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.dicedf_green_transparent
                )
            )
        }
        else if (CurrentColor == "Голубой"){
            imageViewup.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.dicedf
                )
            )
        }
        else if (CurrentColor == "Розовый") {
            imageViewup.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.dicedf_pink_transparent
                )
            )
        }
        else if (CurrentColor == "Фиолетовый"){
            imageViewup.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.dicedf_purple_transparent
                )
            )
        }
    }
    //5 кубиков
    fun change_img_five_do(CurrentColor: String){
        if (CurrentColor == "Зеленый") {
            imageViewup.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.dicedfi_green_transparent
                )
            )
        }
        else if (CurrentColor == "Голубой"){
            imageViewup.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.dicedfi
                )
            )
        }
        else if (CurrentColor == "Розовый") {
            imageViewup.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.dicedfi_pink_transparent
                )
            )
        }
        else if (CurrentColor == "Фиолетовый"){
            imageViewup.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.dicedfi_purple_transparent
                )
            )
        }
    }
    //6 кубиков!!
    fun change_img_six_do(CurrentColor:String){
        if (CurrentColor == "Зеленый") {
            imageViewup.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.dices_green_transparent
                )
            )
        }
        else if (CurrentColor == "Голубой"){
            imageViewup.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.diceds
                )
            )
        }
        else if (CurrentColor == "Розовый") {
            imageViewup.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.dices_pink_transparent
                )
            )
        }
        else if (CurrentColor == "Фиолетовый"){
            imageViewup.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.dices_purple_transparent
                )
            )
        }
    }
    //один кубик!!
    fun change_img_one_dt(CurrentColor: String){
        if (CurrentColor == "Зеленый") {
            imageViewdown.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.diceuo_green_transparent
                )
            )
        }
        else if (CurrentColor == "Голубой"){
            imageViewdown.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.diceuo
                )
            )
        }
        else if (CurrentColor == "Розовый") {
            imageViewdown.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.diceuo_pink_transparent
                )
            )
        }
        else if (CurrentColor == "Фиолетовый"){
            imageViewdown.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.diceuo_purple_transparent
                )
            )
        }
    }
    fun change_img_two_dt(CurrentColor: String){
        if (CurrentColor == "Зеленый") {
            imageViewdown.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.diceut_green_transparent
                )
            )
        }
        else if (CurrentColor == "Голубой"){
            imageViewdown.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.diceut
                )
            )
        }
        else if (CurrentColor == "Розовый") {
            imageViewdown.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.diceut_pink_transparent
                )
            )
        }
        else if (CurrentColor == "Фиолетовый"){
            imageViewdown.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.diceut_purple_transparent
                )
            )
        }
    }
    fun change_img_three_dt(CurrentColor: String){
        if (CurrentColor == "Зеленый") {
            imageViewdown.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.diceuth_green_transparent
                )
            )
        }
        else if (CurrentColor == "Голубой"){
            imageViewdown.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.diceuth
                )
            )
        }
        else if (CurrentColor == "Розовый") {
            imageViewdown.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.diceuth_pink_transparent
                )
            )
        }
        else if (CurrentColor == "Фиолетовый"){
            imageViewdown.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.diceuth_purple_transparent
                )
            )
        }
    }
    fun change_img_four_dt(CurrentColor: String){
        if (CurrentColor == "Зеленый") {
            imageViewdown.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.diceuf_green_transparent
                )
            )
        }
        else if (CurrentColor == "Голубой"){
            imageViewdown.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.diceuf
                )
            )
        }
        else if (CurrentColor == "Розовый") {
            imageViewdown.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.diceuf_pink_transparent
                )
            )
        }
        else if (CurrentColor == "Фиолетовый"){
            imageViewdown.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.diceuf_purple_transparent
                )
            )
        }
    }
    fun change_img_five_dt(CurrentColor: String){
        if (CurrentColor == "Зеленый") {
            imageViewdown.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.diceufi_green_transparent
                )
            )
        }
        else if (CurrentColor == "Голубой"){
            imageViewdown.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.diceufi
                )
            )
        }
        else if (CurrentColor == "Розовый") {
            imageViewdown.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.diceufi_pink_transparent
                )
            )
        }
        else if (CurrentColor == "Фиолетовый"){
            imageViewdown.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.diceufi_purple_transparent
                )
            )
        }
    }
    fun change_img_six_dt(CurrentColor: String){
        if (CurrentColor == "Зеленый") {
            imageViewdown.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.diceus_green_transparent
                )
            )
        }
        else if (CurrentColor == "Голубой"){
            imageViewdown.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.diceus
                )
            )
        }
        else if (CurrentColor == "Розовый") {
            imageViewdown.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.diceus_pink_transparent
                )
            )
        }
        else if (CurrentColor == "Фиолетовый"){
            imageViewdown.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.diceus_purple_transparent
                )
            )
        }
    }
    fun get_color(): String {
        prefs_background = getSharedPreferences("background_settings", Context.MODE_PRIVATE)
        if (prefs_background.contains(BACKGROUND_PREFERENCE_MODE)) {
            val color_background = prefs_background.getString(BACKGROUND_PREFERENCE_MODE, "None")
            return color_background.toString()
        }
        return ""
    }
    fun lets_play_do(){
        val nom: Int = rand_one()
        val CurrentColor: String = get_color()
        when (rand_one()){
            1 -> change_img_one_do(CurrentColor)
            2 -> change_img_two_do(CurrentColor)
            3 -> change_img_three_do(CurrentColor)
            4 -> change_img_four_do(CurrentColor)
            5 -> change_img_five_do(CurrentColor)
            6 -> change_img_six_do(CurrentColor)
        }
    }
    fun lets_play_dt(){
        val nom: Int = rand_one()
        val CurrentColor: String = get_color()
        when (rand_one()){
            1 -> change_img_one_dt(CurrentColor)
            2 -> change_img_two_dt(CurrentColor)
            3 -> change_img_three_dt(CurrentColor)
            4 -> change_img_four_dt(CurrentColor)
            5 -> change_img_five_dt(CurrentColor)
            6 -> change_img_six_dt(CurrentColor)
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
    override fun onPause() { // Add the following line to unregister the Sensor Manager onPause
        if (prefs_shake.contains(SHAKE_PREFERENCE_MODE)) {
            shake_data = prefs_shake.getBoolean(SHAKE_PREFERENCE_MODE, false)
            if (shake_data) {
                mSensorManager!!.unregisterListener(mShakeDetector)
            }
        }
        super.onPause()
    }
    override fun onResume() { // Функция, запускающаяся при включении приложения
        super.onResume()
        Log.i("check event", "on resume event")
        if (prefs_shake.contains(SHAKE_PREFERENCE_MODE)) {
            shake_data = prefs_shake.getBoolean(SHAKE_PREFERENCE_MODE, false)
            if (shake_data) {
                mSensorManager!!.registerListener(
                    mShakeDetector,
                    mAccelerometer,
                    SensorManager.SENSOR_DELAY_UI
                )
            }
        }
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

    private fun initSensor() {
        // ShakeDetector initialization
        // ShakeDetector initialization
        mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mShakeDetector = ShakeDetector()
        mShakeDetector!!.setOnShakeListener(object : ShakeDetector.OnShakeListener {
            override fun onShake(count: Int) { /*
                 * The following method, "handleShakeEvent(count):" is a stub //
                 * method you would use to setup whatever you want done once the
                 * device has been shook.
                 */
                //Toast.makeText(this@one_cubik, count.toString(), Toast.LENGTH_SHORT).show()
                scale_play()
            }
        })
    }
}

