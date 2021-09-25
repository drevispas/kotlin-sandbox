package com.yong.lab

class LootBox<T : Loot>(vararg items: T) {
    private var loots: Array<out T> = items
    var open = false

    fun fetch(i: Int): T? = loots[i].takeIf { open }
    fun <R> fetch(i: Int, lootMod: (T) -> R): R? = lootMod(loots[i]).takeIf { open }
    override fun toString(): String {
        return "LootBox(loots=${loots.contentToString()}, open=$open)"
    }
}

open class Loot(val value: Int)

class Coin(value: Int) : Loot(value) {
    override fun toString(): String {
        return "Coin(value=$value)"
    }
}

class Armor(val name: String, value: Int) : Loot(value) {
    override fun toString(): String {
        return "Armor(name='$name', value=$value)"
    }
}

fun String.addEnthusiasm(amount: Int = 1) = this + "!".repeat(amount)
val String.numVowels
    get() = count { "aeiou".contains(it) }
infix fun String?.printWithDefault(default:String)=println(this?:default)
fun <T>Iterable<T>.random():T=this.shuffled().first()
fun <T> T.easyPrint(): T {
    println(this)
    return this
}

fun main(args: Array<String>) {
    val lootBox1: LootBox<Coin> = LootBox(Coin(50), Coin(100))
    val lootBox2: LootBox<Armor> = LootBox(Armor("wood", 10))
    lootBox1.open = true
    lootBox2.open = true
    println(lootBox1.fetch(1) { Coin(it.value * 3) } ?: "Nothing inside") // pass a lambda that multiplies anything by 3
    println(lootBox2.fetch(0)?.let { "$it is found" })
    println(lootBox2.fetch(0) { Coin(it.value * 10) } ?: "Not exchanged")
    lootBox2.fetch(0)?.name?.addEnthusiasm(3)?.easyPrint()
    "42".easyPrint().addEnthusiasm().easyPrint()
    "ace".numVowels.easyPrint()
    val nullableString:String?=null
    nullableString.printWithDefault("NULL")
    "Not null" printWithDefault "..."
    (0..10).random().easyPrint()
}