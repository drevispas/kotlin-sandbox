package com.yong.lab

/*
 * Limitation:
 * - at least 1 constructor arg with var or val
 * - no abstract, open, sealed, inner
 */
data class Coordinate(val x: Int, val y: Int) {
    val isInBounds = x >= 0 && y >= 0

    /* operator overriding */
    operator fun plus(other: Coordinate) = Coordinate(x + other.x, y + other.y)
}

enum class Direction(val coordinate: Coordinate) {
    NORTH(Coordinate(0, -1)), // Direction.NORTH.name="NORTH, Direction.NORTH.ordinal=0
    EAST(Coordinate(1, 0)), // Each item is an instance of enum class
    SOUTH(Coordinate(0, 1)),
    WEST(Coordinate(-1, 0));

    fun peek(playerCoordinate: Coordinate) = playerCoordinate + coordinate // `+` = `Coordinate.plus()`
}