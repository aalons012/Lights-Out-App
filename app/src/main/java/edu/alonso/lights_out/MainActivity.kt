package edu.alonso.lights_out

import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.children
import android.content.Intent

const val GAME_STATE = "gameState"

// Main Activity is the Main Method(Screen) of the app and handles the User Interface and the Logic
class MainActivity : AppCompatActivity() {

    private lateinit var game: LightsOutGame            // This is the Model of the game
    private lateinit var lightGridLayout: GridLayout    // This is the View of the game
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
        if (savedInstanceState == null) {
            startGame()
        } else {
            game.state = savedInstanceState.getString(GAME_STATE)!!
            setButtonColors()
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(GAME_STATE, game.state)
    }


    // This method starts the game
    private fun startGame(){
        game.newGame()        // tells the logic to start a new game
        setButtonColors()     // tells the view to update the colors of the buttons
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

    fun onHelpClick(view: View) {
        // Start the HelpActivity
        startActivity(Intent(this, HelpActivity::class.java))
        startActivity(intent)
    }

    fun onChangeColorClick(view: View) {
        // Start the ColorActivity
        startActivity(Intent(this, ColorActivity::class.java))
        startActivity(intent)
    }
}