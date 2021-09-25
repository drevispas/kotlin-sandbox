package com.yong.lab

import kotlin.system.exitProcess

object Game {
    private val player = Player("yong")
    private var room: Room = TownSquare()
    private var worldMap = listOf(
        listOf(room, Room("Tavern"), Room("Back Room")),
        listOf(Room("Long Corridor"), Room("Generic Room"))
    )

    init {
        println("Welcome to the Game!")
        player.speak("I am a newbie.")
    }

    private class GameInput(arg: String?) {
        private val input = arg ?: ""
        val command = input.split(" ")[0]
        val argument = input.split(" ").getOrElse(1, { "" })
        fun commandNotFound() = "Command not found!"
        fun processCommand() = when (command.toLowerCase()) {
            "move" -> move(argument)
            "fight" -> fight()
            else -> commandNotFound()
        }
    }

    fun play(): String {
        println("${room.describe()} (${room.load()})")
        printPlayerStatus(player)
//        println(GameInput(readLine()).processCommand())
        GameInput("move EAST").processCommand()
        return GameInput("fight").processCommand()
    }

    private fun printPlayerStatus(player: Player) {
        println("${player.name}: ${player.healthPoints}/100 at ${player.position}")
    }

    private fun move(directionInput: String) =
        try {
            val direction = Direction.valueOf(directionInput.toUpperCase())
            val newPosition = direction.peek(player.position)
            if (!newPosition.isInBounds) {
                throw IllegalStateException("To $direction is out of boundary.")
            }
            player.position = newPosition
            room = worldMap[newPosition.y][newPosition.x]
            "OK, moved to ${room.name} toward $direction"
        } catch (e: Exception) {
            "Wrong direction: $directionInput"
        }

    private fun fight()=room.monster?.let {
        while (player.healthPoints>0&&it.healthPoints>0){
            slay(it)
        }
        "Combat is over"
    }?:"No monster here"

    private fun slay(monster: Monster) {
        println("${monster.name} -- dealt damage ${monster.attack(player)}")
        println("${player.name} -- dealt damage ${player.attack(monster)}")
        if(player.healthPoints<=0){
            println("You lose! Exit the game...")
            exitProcess(0)
        }
        if(monster.healthPoints<=0){
            println("You defeat ${monster.name}!")
            room.monster=null
        }
    }
}