package com.yong.lab

data class Barrel<out T>(val item:T) {
}

fun main(args:Array<String>) {
    var armorBarrel: Barrel<Armor> = Barrel(Armor("wood", 10))
    var lootBarrel: Barrel<Loot> = Barrel(Coin(100))

    lootBarrel = armorBarrel // covariance makes Barrael<Loot> be also super of Barrel<Armor>
    val myArmor: Armor = lootBarrel.item

    println(armorBarrel)
    println(lootBarrel)
}