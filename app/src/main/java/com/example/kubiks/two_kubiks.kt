package com.example.kubiks

import android.widget.ImageView
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_one_cubik.*
import kotlinx.android.synthetic.main.activity_two_kubiks.*
import kotlin.random.Random

class two_kubiks : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two_kubiks)
    }

    fun go_back(view: View){
        val back_activity = Intent(this, MainActivity::class.java)
        startActivity(back_activity)
    }

    fun rand_one(): Int {
        val el: Int= Random.nextInt(1, 7)
        return (el)
    }
    fun change_img_one_do(view: View){
        imageViewup.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.dicedo ))
    }
    fun change_img_two_do(view: View){
        imageViewup.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.dicedt ))
    }
    fun change_img_three_do(view: View){
        imageViewup.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.dicedth ))
    }
    fun change_img_four_do(view: View){
        imageViewup.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.dicedf ))
    }
    fun change_img_five_do(view: View){
        imageViewup.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.dicedfi ))
    }
    fun change_img_six_do(view: View){
        imageViewup.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.diceds ))
    }
    fun change_img_one_dt(view: View){
        imageViewdown.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.diceuo ))
    }
    fun change_img_two_dt(view: View){
        imageViewdown.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.diceut ))
    }
    fun change_img_three_dt(view: View){
        imageViewdown.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.diceuth ))
    }
    fun change_img_four_dt(view: View){
        imageViewdown.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.diceuf ))
    }
    fun change_img_five_dt(view: View){
        imageViewdown.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.diceufi ))
    }
    fun change_img_six_dt(view: View){
        imageViewdown.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.diceus ))
    }
    fun lets_play_do(view: View){
        val nom: Int = rand_one()
        when (rand_one()){
            1 -> change_img_one_do(view)
            2 -> change_img_two_do(view)
            3 -> change_img_three_do(view)
            4 -> change_img_four_do(view)
            5 -> change_img_five_do(view)
            6 -> change_img_six_do(view)
        }
    }
    fun lets_play_dt(view: View){
        val nom: Int = rand_one()
        when (rand_one()){
            1 -> change_img_one_dt(view)
            2 -> change_img_two_dt(view)
            3 -> change_img_three_dt(view)
            4 -> change_img_four_dt(view)
            5 -> change_img_five_dt(view)
            6 -> change_img_six_dt(view)
        }
    }
    fun lets_play_two_cubes(view: View){
        lets_play_dt(view)
        lets_play_do(view)

    }
}

