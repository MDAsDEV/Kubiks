package com.example.kubiks
import android.widget.ImageView
import androidx.core.content.ContextCompat
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_one_cubik.*
import kotlin.random.Random

class one_cubik : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_cubik)
    }

    fun go_back(view: View){
        val back_activity = Intent(this, MainActivity::class.java)
        startActivity(back_activity)
    }
    fun rand_one(): Int {
        val el: Int= Random.nextInt(1, 7)
        return (el)
    }
    fun change_img_one(view: View){
        imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.diceo ))
    }
    fun change_img_two(view: View){
        imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.dicet ))
    }
    fun change_img_three(view: View){
        imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.diceth ))
    }
    fun change_img_four(view: View){
        imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.dicef ))
    }
    fun change_img_five(view: View){
        imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.dicefi ))
    }
    fun change_img_six(view: View){
        imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.dices ))
    }
    fun lets_play(view: View){
        val nom: Int = rand_one()
        when (rand_one()){
            1 -> change_img_one(view)
            2 -> change_img_two(view)
            3 -> change_img_three(view)
            4 -> change_img_four(view)
            5 -> change_img_five(view)
            6 -> change_img_six(view)
        }
    }
}
