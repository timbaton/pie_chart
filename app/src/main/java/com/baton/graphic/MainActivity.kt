package com.baton.graphic

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val chart: List<Segment> = listOf(
            Segment( 40.0, Color.CYAN),
            Segment( 40.0, Color.LTGRAY),
            Segment( 20.0, Color.MAGENTA)
        )

        pc_money.values = chart
    }
}
