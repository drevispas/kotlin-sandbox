package com.yong.lab

open class Room(val name:String) { // classes are final by default; `open` allows inheritance
    protected open val dangerLevel=5
    var monster:Monster?=Goblin()

    fun describe()="Room: $name, danger level: $dangerLevel, Creature: ${monster?.name?:"none"}"
    open fun load() = "Nobody here" // members are final by default; `open` allows overriding
    /* destructuring */
    operator fun component1()=name
    operator fun component2()=dangerLevel
}

open class Square():Room("Square") { // : super-constructor()
    override val dangerLevel = super.dangerLevel - 3
    private var bellSound = "댕댕"
    override fun load() = "Welcome to Square! ${ringBell()}" // overrided members are open by default
    protected fun ringBell() = "종탑에서 $bellSound" // normal members are final by default
}

open class TownSquare:Square() {
    override fun load() = "Welcome to Town Square! ${ringBell()}"
}
