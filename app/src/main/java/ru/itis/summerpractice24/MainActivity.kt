package ru.itis.summerpractice24

import android.os.Bundle
import ru.itis.summerpractice24.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged

class MainActivity : AppCompatActivity() {
    var result :String = ""
    var list :List<Magician> = ArrayList()
    var temp :List<Magician> = ArrayList()
    var cnt = 0
    var cur = 0
    var l = 0
    private var viewBinding: ActivityMainBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding?.root)

        print()
    }

    fun print() {
        viewBinding?.apply {
            textEt.doOnTextChanged { text, start, before, count ->
                if (!text.toString().equals("") && isNumeric(text.toString())) {
                    sendTxtButton.isEnabled = true
                    cnt = Integer.parseInt(text.toString())
                    cur = cnt

                    sendTxtButton.setOnClickListener {
                        result = ""
                        l = 0
                        textView.text = "let's start!"
                        imageView.isVisible = false
                        createListOfMagitians2()
                        textViewPlay.text = competition()
                    }
                }
                else {
                    sendTxtButton.isEnabled = false
                }
            }
        }
    }

    fun competition() :String {
        ++l
        result += "\nLEVEL ${l}\n"
        var new = mutableListOf<Magician>()
        for (i in 0..<cur/2) {
            new.add(competition(temp[i], temp[i+cur/2]))
            result += " battle between ${temp[i].name} and ${temp[i+cur/2].name} \n${new[i].name} win\n"
        }
        if (cur%2 != 0) {
            new.add(temp[(cur+1)/2])
            result += " ${temp[(cur+1)/2].name} no opponent, next circle \n"
        }

        temp = new
        cur = (cur+1)/2

        if (cur > 1) {
            competition()
        }
        else {
            result += "\n!!! winner - ${temp[0].name}"
        }
        return result
    }

    fun createListOfMagitians2() {
        var l = mutableListOf<Magician>()
        var n = 0
        if (cnt > 0) {
            for (i in 1..cnt) {
                ++ n
                val kind = (0..3).random()
                var m :Magician = Magician()
                when (kind) {
                    0 -> {
                        m = MagitianBuilder().name(n).age((0..1000).random()).area((0..3).random()).abilitiesLevel((0..100).random()).side((0..2).random()).wand((0..7).random()).yearsOfEducation((0..8).random()).buildMag()
                    }
                    1 -> {
                        m = MagitianBuilder().name(n).age((0..1000).random()).area((0..3).random()).abilitiesLevel((0..100).random()).side((0..2).random()).type((0..5).random()).domesticationLevel((0..100).random()).buildBeing()
                    }
                    2 -> {
                        m = MagitianBuilder().name(n).age((0..1000).random()).area((0..3).random()).abilitiesLevel((0..100).random()).side((0..2).random()).deadlinessLevel((0..100).random()).buildCreature()
                    }
                    3 -> {
                        m = MagitianBuilder().name(n).age((0..1000).random()).area((0..3).random()).abilitiesLevel((0..100).random()).side((0..2).random()).notBeingType((0..3).random()).buildSpirit()
                    }
                }
                l.add(m)
            }
            list = l
            temp = l
        }
    }

    fun isNumeric(str: String?) :Boolean {
        if (str != null) {
            return str.all { it in '0'..'9' }
        }
        return false
    }

    fun competition(m1 :Magician, m2 :Magician) :Magician {
        if (m1.levelOfPower() > m2.levelOfPower()) {
            return m1
        }
        return m2
    }


    open class Magician : Fightable {
        var name :Int = 0
        var area: Area = Area.EVERYWHERE
        var age :Int = 0
        var abilitiesLevel :Int = 0 // 0-100
        var side: Side = Side.NEUTRAL

        override fun levelOfPower():Int {
            return 0
        }

        fun commonInfo() :String {
            return "I am $age, live (in the) ${area.name}, level of abilities is $abilitiesLevel, on ${side.name} side!!!"
        }
    }

    class Mag : Magician () {
        var wand :Wand = Wand.PHOENIX
        var yearsOfEducation :Int = 0

        override fun levelOfPower():Int {
            return ((area.kft + age%100)*(abilitiesLevel+side.kft+yearsOfEducation)*100)/16390
        }

        fun getInfo() :String {
            return "Hi I'm mag, ${commonInfo()}, wand is made of ${wand.name}, educated for $yearsOfEducation years"
        }
    }

    class Being : Magician () {
        var type :Type = Type.ELF
        var domesticationLevel :Int = 0

        override fun levelOfPower():Int {
            return ((area.kft + age%100)*(abilitiesLevel+(side.kft))*100)/16390
        }
    }

    class Creature : Magician () {
        var deadlinessLevel :Int = 0

        override fun levelOfPower():Int {
            println(side.ordinal)
            return ((area.kft + age%100)*(abilitiesLevel+side.kft)*100)/16390
        }
    }

    class Spirit : Magician () {
        var notBeingType :NotBeingType = NotBeingType.DEMENTOR
    }

    enum class Area(val kft: Int) {
        EVERYWHERE(50), AIR(30), WATER(20), EARTH(10);
    }

    enum class Side(val kft: Int) {
        EVIL(10), GOOD(5), NEUTRAL(1)
    }

    enum class Wand(val kft: Int) {
        ELDER(100), SNAKE(90), YEW(80), PHOENIX(70), VINE(60), FIR(50), WALNUT(40), CYPRESS(30)
    }

    enum class Type(val kft: Int) {
        GIANT(85), GOBLIN(75), ELF(65), TROLL(55), VAMPIRE(45), WEREWOLF(95)
    }

    enum class NotBeingType(val kft: Int) {
        BOGGART(2), DEMENTOR(5), POLTERGEIST(3), GHOST(1)
    }

    interface Fightable {
        fun levelOfPower():Int
    }

    class MagitianBuilder {
        private var name :Int = 0
        private var area: Area = Area.EVERYWHERE
        private var age :Int = 0
        private var abilitiesLevel :Int = 0 // 0-100
        private var side: Side = Side.NEUTRAL

        // mag
        private var wand :Wand = Wand.PHOENIX
        private var yearsOfEducation :Int = 0

        // being
        private var type :Type = Type.ELF
        private var domesticationLevel :Int = 0

        // creature
        private var deadlinessLevel :Int = 0

        // spirit
        private var notBeingType :NotBeingType = NotBeingType.DEMENTOR

        fun name(a: Int) = apply {
            this.name = a
        }

        fun area(a: Int) = apply {
            this.area = Area.values()[a] // a 0-3
        }

        fun age(a: Int) = apply {
            this.age = a
        }

        fun abilitiesLevel(a: Int) = apply {
            this.abilitiesLevel = a
        }

        fun side(a: Int) = apply {
            this.side = Side.values()[a] // a 0-2
        }

        fun wand(a: Int) = apply {
            this.wand = Wand.values()[a] // a 0-7
        }

        fun yearsOfEducation(a: Int) = apply {
            this.yearsOfEducation = a
        }

        fun type(a: Int) = apply {
            this.type = Type.values()[a] // a 0-5
        }

        fun domesticationLevel(a: Int) = apply {
            this.domesticationLevel = a
        }

        fun deadlinessLevel(a: Int) = apply {
            this.deadlinessLevel = a
        }

        fun notBeingType(a: Int) = apply {
            this.notBeingType = NotBeingType.values()[a] // a 0-3
        }

        fun buildMag(): Mag {
            val m :Mag = Mag()
            m.name = this.name
            m.area = this.area
            m.age = this.age
            m.side = this.side
            m.abilitiesLevel = this.abilitiesLevel
            m.wand = this.wand
            m.yearsOfEducation = this.yearsOfEducation
            return m
        }

        fun buildBeing() :Being {
            val m :Being = Being()
            m.name = this.name
            m.area = this.area
            m.age = this.age
            m.side = this.side
            m.abilitiesLevel = this.abilitiesLevel
            m.type = this.type
            m.domesticationLevel = this.domesticationLevel
            return m
        }

        fun buildCreature() :Creature {
            val m :Creature = Creature()
            m.name = this.name
            m.area = this.area
            m.age = this.age
            m.side = this.side
            m.abilitiesLevel = this.abilitiesLevel
            m.deadlinessLevel = this.deadlinessLevel
            return m
        }

        fun buildSpirit() :Spirit {
            val m :Spirit = Spirit()
            m.name = this.name
            m.area = this.area
            m.age = this.age
            m.side = this.side
            m.abilitiesLevel = this.abilitiesLevel
            m.notBeingType = this.notBeingType
            return m
        }
    }
}
