package com.yong.lab

import java.io.File

const val MAX_EXP = 5000

fun main(args: Array<String>) {
    println("***** Lambda *****")
    /* simpler lambda call */
    funSimpleLambda { println("Simple") }

    /* function reference: `::function_name` */
    funWithFuncRef("yong", ::printGreeting);

    /* returned lambda */
    funUsingReturnedLambda("yong")

    println("***** Extension functions: apply *****")
    /* extension function: apply (member methods and return it) */
    val file = File("data/file.txt").apply {
        setReadable(true) // extend the object (= file.setReadable(true))
        setWritable(true) // modify the object
        setExecutable(false) // return the object
    }

    println("***** Extension functions: run *****")
    /* extension function: run (member methods and run a lambda) */
    val s = "Hello, world!"
    val has = s.run { contains("world") } // extend the object and return the last expression
    println(has)
    fun strLen(s: String) = s.length
    println(s.run { length }) // s.length()
    println(s.run(::strLen)) // strLen(s)
    s.run(::strLen).run(::println)

    println("***** Extension functions: let *****")
    /* extension function: let (use it and run a lambda) */
    listOf(1, 2, 3).last().let { it * it }.run(::println) // `it` = the object as argument that becomes immutable
    val nullable: String? = null;
    nullable?.let { "You are not null" } ?: "You are null".run(::println) // return the last expression

    println("***** Extension functions: also *****")
    /* extension function: also (use it and return it) */
    listOf(1, 2, 3).last().also { it * it }.run(::println) // return the object
    var i: Int
    listOf(1, 2, 3).last().also { i = it * it }.also { println(i) }

    println("***** Extension functions: takeIf *****")
    /* extension function: takeIf (use it and return it */
    s.takeIf { it.contains("world") }?.run(::println)
    s.takeIf { it.contains("world") } ?: "No world".run(::println) // ("No world".run())
    (s.takeIf { it.contains("world") } ?: "No world").run(::println)

    println("***** Collections: List *****")
    /* List */
    val list = listOf("a", "b", "c", "a") // A list is always read-only
    list.getOrElse(3) { "unknown" }.run(::println) // get
    list.getOrNull(3) ?: "unknown".run(::println)
    list.containsAll(listOf("c", "a")).takeIf { it }.run(::println)
    val mList = mutableListOf("a", "b", "c") // mutable list
    mList[2] = "e"
    mList.add("a").takeIf { it }.run { println(mList) } // modify
    mList.remove("a").takeIf { it }.run { println(mList) }
    list.forEach { print("$it ") }.run { println() } // loop
    list.forEachIndexed { index, s -> print("[$index]$s ") }.run { println() } // loop with index
    val productList = File("data/file.txt").readText().split("\r\n")
    productList.forEachIndexed { i, s ->
        s.split(",").forEachIndexed { j, s -> print("$s" + if (j == 0) ":" else ", ") }
        i.takeIf { i == productList.size - 1 }?.let { println() }
    }
    val (first, _, third) = productList // destructure
    println("$first | ?,? | $third")
    val ages = intArrayOf(32, 10, 88, 43) // evaluated to int[] for compatibility with Java; Don't use if possible
    println(ages)
    (list as MutableList)[0] = "z" // read-only list is not strictly immutable
    println(list)

    println("***** Collections: Set *****")
    /* Set */
    val planets = setOf("Mercury", "Venus", "Earth")
    println(planets.elementAt(2)) // immutable sets suppor tindexed search but is slow
    val numbers = mutableSetOf<Int>() // need a type without initialization
    (0..100).forEach { numbers.add((0..10).shuffled().first()) }
    println(numbers)
    println(list.toSet()) // toSet() removes duplicates
    println(list.distinct()) // distinct()=toSet()+toList()

    println("***** Collections: Map *****")
    /* Map */
    val map = mapOf("a" to 1, "b" to 2, "c" to 3) // "a" to 1 = "a".to(1) = Pair("a", 1)
    println(map)
    val mmap = mutableMapOf("a" to 1, "b" to 2, "c" to 3) // "a" to 1 = "a".to(1) = Pair("a", 1)
    mmap += "b" to 12 // upsert for mutalble map
    mmap.forEach { (t, u) -> print("$t:$u ") }.also { println() }
    println("${map["b"]} = ${map.getValue("b")}")
    println(map.getOrDefault("d", 4))
    println(map.getOrElse("d") { "No such key" })

    println("***** Classes *****")
    /* Classes */
    val player = Player("yong")
    println("${player.name} (${player.healthPoints}/100)")
    val player2 = Player(_name = "geon", healthPoints = 90, isImmortal = false) // named arguments
    println("${player2.name} (${player2.healthPoints}/100)")
//    val player3=Player("kim",0) // java.lang.IllegalArgumentException

    println("***** Inheritance *****")
    val room = Room("Foyer")
    println("${room.describe()} (${room.load()})")
    val townSquare:Room = TownSquare() // :Room : poloymorphism
    println("${townSquare.describe()} (${townSquare.load()})")

    println("***** Singleton: object declaration *****")
    println(Game.play())

    println("***** Singleton: object expression *****")
    val abandonedTownSquare=object :TownSquare(){ // create a anonymous subclass of TownSquare; naturally a singleton
        override fun load()="Nobody here"
    }
    println(abandonedTownSquare.load())

    println("***** Singleton: companion object *****")
    println("#players: ${Player.count()}") // only one companion object per a class
    Player.leave() // singleton part of a normal class
    println("#players: ${Player.count()}")

    println("***** Nested classes *****")

    println("***** Data classes *****")
    val (x, y)=Coordinate(10,20) // destructuing declaration of data class
    println("Coordinate: x=$x, y=$y")
    val (name, dangerLevel)=TownSquare()  // destructuing declaration of normal class
    println("TownSquare: name=$name, dangerLevel=$dangerLevel")

    println("***** Enum classes *****")
    println(Direction.EAST.peek(player.position))
    println(Game.play())
    println(Game.play())

    println("*** equals() ***")
    println(Weapon("Excalibur")==Weapon("Excalibur"))

    println("*** Interfaces ***")
}

fun funSimpleLambda(simpleFun: () -> Unit) {
    simpleFun()
}

inline fun funWithFuncRef(name: String, printGreeting: (String) -> Unit) {
    printGreeting(name)
}

fun printGreeting(name: String) {
    println("Hi, $name")
}

fun funUsingReturnedLambda(name: String) {
    val greetingFunction = getGreetingFunction()
    greetingFunction(name) // Hello, yong (x1)
    greetingFunction(name) // Hello, yong (x2)
}

fun getGreetingFunction(): (String) -> Unit {
    val greeting = "Hello"
    var times = 1
    // Lambda is a closure(close over) so that it can use outer variables.
    // Outer val name is replaced by its value in the inner lambda.
    // Inner lambda refereces outer var that the lambda can modify.
    return { name: String -> println("$greeting, $name (x${times++})") }
}
