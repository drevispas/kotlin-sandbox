package com.yong.lab

import java.util.*

interface Fightable {
    var healthPoints:Int
    val diceCount:Int
    val diceSides:Int
    val damageRoll:Int
    get()=(0 until diceCount).map { Random().nextInt(diceSides) }.sum()

    fun attack(opponent:Fightable):Int
}

abstract class Monster(val name:String, override var healthPoints:Int):Fightable{
    override fun attack(opponent: Fightable): Int {
        val damageDealt=damageRoll
        opponent.healthPoints-=damageDealt
        return damageDealt
    }
}

class Goblin(name:String="Goblin", healthPoints: Int=30):Monster(name,healthPoints){
    override val diceCount=2
    override val diceSides=8
}