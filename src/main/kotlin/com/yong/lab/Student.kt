package com.yong.lab

fun main(args: Array<String>) {
    val student=Student(StudentStatus.Active("Math"))
    println(student.checkStatus())
}

class Student(private val status:StudentStatus) {
    fun checkStatus()=when(status){
        is StudentStatus.NotEnrolled->"You should enroll"
        is StudentStatus.Active->"You enrolled ${status.courseId}"
        is StudentStatus.Graduated->"You have graduated"
        else ->"Unknown"
    }
}

/* Sealed class */
sealed class StudentStatus{ // declares subclasses
    object NotEnrolled:StudentStatus() // singleton instance just like enum item
    class Active(val courseId:String):StudentStatus() // allows multiple instances; don't forget `val`
    object Graduated:StudentStatus()
}