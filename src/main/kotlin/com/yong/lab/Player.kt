package com.yong.lab

import java.util.*

/* primary constructor */
class Player(
    _name: String, // temporary arg has no val or var
    override var healthPoints: Int,
    private val isImmortal: Boolean = false
):Fightable { // Args of primary constructor become class properties
    /* custom getter/setter for properties */
    var name = _name // every property has a getter; var property has a setter also
        get() = "${field.capitalize()} of $race" // custom getter; backing field is not changed
        set(value) { // custom setter
            print("(1)") // not called at instanciation; called by explicit assignemnt
            field = value.trim()
        }

    /* manual class property */
    var weapon: Weapon = Weapon("knife")
    /* lateinit */
    lateinit var weapon2: Weapon // no init value but have to init before use; cannot custom getter/setter
    /* lazy init */
    val race by lazy { selectRace() } // inited when used first and cached
    var position=Coordinate(0,0)

    /* initializer block */
    init { // check prerequisites; every property should be initd before this block
        print("(3)new player")
        require(healthPoints>0,{"healthPoints need to greater than 0"})
        require(name.isNotBlank(),{"player should have a name"})
        join()
    }

    /* secondary constructor */
    constructor(name: String) : this(name, healthPoints=100) { // uses primary constructor `this()`
        print("(4)")
    }

    fun speak(statement: String) = println(statement) // public function by default

    fun printWeapon() {
        weapon?.also { println(it.name) }
    }

    fun printWeapon2(){
        if(::weapon2.isInitialized) println(weapon2) // `::` stands for not value but reference
    }

    private fun selectRace():String {
        print("(2)")
        return setOf("Human","Elf","Dwarf","Orc","Goblin","Undead").shuffled().first()
    }

    /* companion object*/
    companion object {
        private var numPlayers=0
        fun join()= numPlayers++
        fun leave()= numPlayers--
        fun count()= numPlayers
    }

    override val diceCount=3
    override val diceSides=6
    override fun attack(opponent: Fightable): Int {
        val damageDealt=damageRoll+damageRoll*Random().nextInt(2)
        opponent.healthPoints-=damageDealt
        return damageDealt
    }
}