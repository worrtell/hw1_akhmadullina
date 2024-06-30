package ru.itis.summerpractice24

import android.os.Bundle
import ru.itis.summerpractice24.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity

class Main2 : AppCompatActivity() {
    private var viewBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding?.root)
        sendText()

    }
    fun sendText() {
        viewBinding?.let { vB ->
            val b = Being()
            var cnt = 0
            vB.sendTxtButton.setOnClickListener {
                cnt++
                val s: Side = Side.NEUTRAL
                vB.textView.text = "presed $cnt times hehe ${b.levelOfPower()}"
                if (cnt == 27) {
                    cnt = 0
                }
            }
        }
    }

    abstract class Magitian() : Fightable {
        val area: Area = Area.ALL
        val age :Int = 0
        val abilitiesLevel :Int = 0 // 0-100
        val side: Side = Side.NEUTRAL

        override fun levelOfPower():Int {
            return ((area.kft + age%100)*(abilitiesLevel+side.kft)*100)/16390
        }
    }

    class Mag () : Magitian () {
        val wand :String = ""
        val yearsOfEducation :Int = 0

        override fun levelOfPower():Int {
            return ((area.kft + age%100)*(abilitiesLevel+side.kft+yearsOfEducation)*100)/16390
        }
    }

    class Creature () : Magitian () {
        val type :String = ""
        val friendlinessLevel :Int = 0

        override fun levelOfPower():Int {
            return ((area.kft + age%100)*(abilitiesLevel+(side.kft/friendlinessLevel))*100)/16390
        }
    }

    class Being () : Magitian () {
        val domesticationLevel :Int = 0
        val deadlinessLevel :Int = 0

        override fun levelOfPower():Int {
            //return ((area.kft + age%100)*(abilitiesLevel*domesticationLevel/domesticationLevel+side.kft)*100)/16390
            return side.ordinal
        }
    }
    class Spirit () : Magitian () {
        val type :String = ""
        val deathPlace :String = ""
    }

    enum class Area(val kft: Int) {
        ALL(50), AIR(30), WATER(20), EARTH(10);
    }

    enum class Side(val kft: Int) {
        EVIL(10), GOOD(5), NEUTRAL(1)
    }

    interface Fightable {
        fun levelOfPower():Int
    }
}