package com.yong.lab

class Weapon(_name:String){
    var name=_name // (constructor) this.name=_name
        get()="The Legendary $field" // $field = _name by above construction
        set(value){
            field=value.toLowerCase().reversed().capitalize()
        }
    init {
        name = _name // call setter: this.set(_name)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Weapon

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}