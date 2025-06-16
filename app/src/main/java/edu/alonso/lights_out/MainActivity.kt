package edu.alonso.lights_out

import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.children

class MainActivity : AppCompatActivity() {

    private lateinit var game: LightsOutGame
    private lateinit var lightGridLayout: GridLayout
    private var lightOnColor = 0
    private var lightOffColor = 0

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lightGridLayout = findViewById(R.id.light_grid)

        for (gridButton in lightGridLayout.children){
            gridButton.setOnClickListener(this::onLightButtonClick)
        }

        lightOnColor = ContextCompat.getColor(this, R.color.yellow)
        lightOffColor = ContextCompat.getColor(this, R.color.black)

        game = LightsOutGame()
        startGame()
    }

    private fun startGame(){
        game.newGame()
        setButtonColors()
    }

    private fun onLightButtonClick(view: View){
        val buttonIndex = lightGridLayout.indexOfChild(view)
        val row = buttonIndex / GRID_SIZE
        val col = buttonIndex % GRID_SIZE

        game.selectLight(row, col)
        setButtonColors()

        if (game.isGameOver){
            Toast.makeText(this, R.string.congrats, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setButtonColors(){
        // This sets ALL buttons to have color
        for (buttonIndex in 0 until lightGridLayout.childCount){
            val gridButton = lightGridLayout.getChildAt(buttonIndex)

            val row = buttonIndex / GRID_SIZE
            val col = buttonIndex % GRID_SIZE

            if(game.isLightOn(row, col)) {
                gridButton.setBackgroundColor(lightOnColor)
            } else{
                gridButton.setBackgroundColor(lightOffColor)
            }
        }
    }

    fun onNewGameClick(view: View) {
        startGame()
    }
}