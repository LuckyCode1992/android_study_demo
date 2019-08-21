package com.justcode.hxl.androidstudydemo.kotlin代理

import android.util.Log

interface Player {
    fun playGame()
}

interface GameMaker {
    fun developGame()
}

class WitcherPlayer(val enemy: String) : Player {
    override fun playGame() {
        Log.d("kkkt", "killin $enemy")
    }
}

class WitcherCreator(val gameName: String) : GameMaker {
    override fun developGame() {
        Log.d("kkkt", "Makin $gameName")
    }
}
class WitcherPassionate:Player by WitcherPlayer("monsters"),
        GameMaker by WitcherCreator("withcer 3"){
    fun fulfillYourDestiny(){
        playGame()
        developGame()
    }
}