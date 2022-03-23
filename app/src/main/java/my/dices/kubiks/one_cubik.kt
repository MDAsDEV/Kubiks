package my.dices.kubiks
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.media.SoundPool
import android.media.SoundPool.OnLoadCompleteListener
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_one_cubik.*
import kotlinx.android.synthetic.main.activity_two_kubiks.*
import kotlin.random.Random


class one_cubik : AppCompatActivity(), OnLoadCompleteListener {
    var was = false
    var loaded: Boolean = false
    lateinit var soundpool: SoundPool
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
    private var is_english: Boolean = false
    var shake_data: Boolean = true
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
        setContentView(R.layout.activity_one_cubik)
        Log.i("test create == ", "creating one dice")
        val OneKubiksLayout = findViewById<RelativeLayout>(R.id.one_kubik_relative_layout_id)
        val OneKubiksPlayButton = findViewById<ImageButton>(R.id.pbutton)
        val OneKubiksSettings = findViewById<ImageButton>(R.id.settings_button)
        val OneKubiksBackButton = findViewById<ImageButton>(R.id.bbutton)
        val ImageViewObject = findViewById<ImageView>(R.id.imageView)
        prefs_sound = getSharedPreferences("sound_settings", MODE_PRIVATE)
        prefs_background = getSharedPreferences("background_settings", MODE_PRIVATE)
        prefs_language = getSharedPreferences("language_settings", MODE_PRIVATE)
        prefs_shake = getSharedPreferences("shake_settings", MODE_PRIVATE)
        Log.i("test shake data contains == ", prefs_shake.contains(SHAKE_PREFERENCE_MODE).toString())
        if (prefs_shake.contains(SHAKE_PREFERENCE_MODE)){
            shake_data = prefs_shake.getBoolean(SHAKE_PREFERENCE_MODE, false)
            if (shake_data){
                initSensor()
            }
        }

        if (prefs_background.contains(BACKGROUND_PREFERENCE_MODE)){
            if (prefs_language.contains(LANGUAGE_PREFERENCE_MODE)){
                language_data = prefs_language.getString(LANGUAGE_PREFERENCE_MODE, "None").toString()
                is_english = ("eng" in language_data)
            }
            Log.i("language data now == ", language_data)
            val color_background = prefs_background.getString(BACKGROUND_PREFERENCE_MODE, "None").toString()
            Log.i("test color background found ==  ", color_background)
            if (color_background == "Зеленый"){
                OneKubiksLayout.setBackgroundResource(R.drawable.background_salad)
                OneKubiksSettings.setImageResource(R.drawable.settings_green_transparent)
                if (!is_english){
                    OneKubiksPlayButton.setImageResource(R.drawable.play_rus_green_transparent)
                }
                else {
                    OneKubiksPlayButton.setImageResource(R.drawable.play_green_transparent)
                }
                OneKubiksBackButton.setImageResource(R.drawable.back_green_transparent)
                ImageViewObject.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.diceth_green_transparent
                    )
                )
            }
            if (color_background == "Розовый"){
                OneKubiksLayout.setBackgroundResource(R.drawable.background_pink)
                if (!is_english){
                    OneKubiksPlayButton.setImageResource(R.drawable.play_rus_pink_transparent)
                }
                else {
                    OneKubiksPlayButton.setImageResource(R.drawable.play_pink)
                }
                OneKubiksSettings.setImageResource(R.drawable.settings_pink_transparent)
                OneKubiksBackButton.setImageResource(R.drawable.back_pink_transparent)
                ImageViewObject.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.diceth_pink_transparent
                    )
                )
            }
            if (color_background == "Голубой"){
                if (!is_english){
                    OneKubiksPlayButton.setImageResource(R.drawable.play_rus_blue_transparent)
                }
                else {
                    OneKubiksPlayButton.setImageResource(R.drawable.playbutton_blue)
                }
                OneKubiksLayout.setBackgroundResource(R.drawable.backgroundsecw_blue)
            }
            if (color_background == "Фиолетовый"){
                OneKubiksLayout.setBackgroundResource(R.drawable.background_purple)
                OneKubiksSettings.setImageResource(R.drawable.settings_purple_transparent)
                OneKubiksBackButton.setImageResource(R.drawable.back_purple_transparent)
                if (!is_english){
                    OneKubiksPlayButton.setImageResource(R.drawable.play_rus_purple_transparent)
                }
                else {
                    OneKubiksPlayButton.setImageResource(R.drawable.play_purple_transparent)
                }
                ImageViewObject.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.diceth_purple_transparent
                    )
                )
            }
        }
        else{
            Log.i("test color background not found", "error")
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
    fun change_img_one(CurrentColor: String){
        Log.i("test colors == ", CurrentColor.toString())
        if (CurrentColor == "Зеленый") {
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.diceo_green_transparent
                )
            )
        }
        else if (CurrentColor == "Голубой"){
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.diceo_blue
                )
            )
        }
        else if (CurrentColor == "Розовый") {
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.diceo_pink_transparent
                )
            )
        }
        else if (CurrentColor == "Фиолетовый"){
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.diceo_purple_transparent
                )
            )
        }
    }
    fun change_img_two(CurrentColor: String){

        if (CurrentColor == "Зеленый") {
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.dicet_green_transparent
                )
            )
        }
        else if (CurrentColor == "Голубой"){
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.dicet_blue
                )
            )
        }
        else if (CurrentColor == "Розовый") {
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.dicet_pink_transparent
                )
            )
        }
        else if (CurrentColor == "Фиолетовый"){
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.dicet_purple_transparent
                )
            )
        }
    }
    fun change_img_three(CurrentColor: String){
        if (CurrentColor == "Зеленый") {
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.diceth_green_transparent
                )
            )
        }
        else if (CurrentColor == "Голубой"){
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.diceth_blue
                )
            )
        }
        else if (CurrentColor == "Розовый") {
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.diceth_pink_transparent
                )
            )
        }
        else if (CurrentColor == "Фиолетовый"){
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.diceth_purple_transparent
                )
            )
        }
    }
    fun change_img_four(CurrentColor: String){
        if (CurrentColor == "Зеленый") {
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.dicef_green_transparent
                )
            )
        }
        else if (CurrentColor == "Голубой"){
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.dicef_blue
                )
            )
        }
        else if (CurrentColor == "Розовый") {
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.dicef_pink_transparent
                )
            )
        }
        else if (CurrentColor == "Фиолетовый"){
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.dicef_purple_transparent
                )
            )
        }
    }
    fun change_img_five(CurrentColor: String){
        if (CurrentColor == "Зеленый") {
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.dicefi_green_transparent
                )
            )
        }
        else if (CurrentColor == "Голубой"){
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.dicefi_blue
                )
            )
        }
        else if (CurrentColor == "Розовый") {
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.dicefi_pink_transparent
                )
            )
        }
        else if (CurrentColor == "Фиолетовый"){
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.dicefi_purple_transparent
                )
            )
        }
    }
    fun change_img_six(CurrentColor: String){
        if (CurrentColor == "Зеленый") {
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.dices_green_transparent_center
                )
            )
        }
        else if (CurrentColor == "Голубой"){
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.dices_blue
                )
            )
        }
        else if (CurrentColor == "Розовый") {
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.dices_pink_transparent_center
                )
            )
        }
        else if (CurrentColor == "Фиолетовый"){
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.dices_purple_transparent_center
                )
            )
        }
    }
    fun get_color(): String {
        prefs_background = getSharedPreferences("background_settings", MODE_PRIVATE)
        if (prefs_background.contains(BACKGROUND_PREFERENCE_MODE)) {
            val color_background = prefs_background.getString(BACKGROUND_PREFERENCE_MODE, "None")
            return color_background.toString()
        }
        else
        {
            Log.i("test background prefs", "Background settings not found")
        }
        return ""
    }
    fun lets_play(){
        val nom: Int = rand_one()
        Log.i("test dices == ", nom.toString())
        val CurrentColor: String = get_color()
        Log.i("test colors == ", CurrentColor.toString())
        when (rand_one()){
            1 -> change_img_one(CurrentColor)
            2 -> change_img_two(CurrentColor)
            3 -> change_img_three(CurrentColor)
            4 -> change_img_four(CurrentColor)
            5 -> change_img_five(CurrentColor)
            6 -> change_img_six(CurrentColor)
        }
    }
    fun play_sound(){
        mp.isLooping = false
        val speed: Float = 1.3.toFloat()
        mp.setVolume(leftVolume.toFloat(), rightVolume.toFloat())
        //mp.setPlaybackParams(mp.getPlaybackParams().setSpeed(speed));
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
    override fun onPause() { // Add the following line to unregister the Sensor Manager onPause
        if (prefs_shake.contains(SHAKE_PREFERENCE_MODE)) {
            shake_data = prefs_shake.getBoolean(SHAKE_PREFERENCE_MODE, false)
            if (shake_data) {
                mSensorManager!!.unregisterListener(mShakeDetector)
            }
        }
        super.onPause()
    }
    override fun onStart() {
        super.onStart()
        Log.i("Volume Level == ", leftVolume.toString() + ' ' + rightVolume.toString())
    }
    override fun onResume() { // Функция, запускающаяся при включении приложения
        super.onResume()
        if (prefs_shake.contains(SHAKE_PREFERENCE_MODE)) {
            shake_data = prefs_shake.getBoolean(SHAKE_PREFERENCE_MODE, false)
            if (shake_data){
                mSensorManager!!.registerListener(
                    mShakeDetector,
                    mAccelerometer,
                    SensorManager.SENSOR_DELAY_UI
                )
            }
        }
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
    override fun onLoadComplete(soundPool: SoundPool?, sampleId: Int, status: Int) {
        TODO("Not yet implemented")
    }
}





