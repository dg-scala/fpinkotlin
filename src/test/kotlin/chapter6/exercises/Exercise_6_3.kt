package chapter6.exercises

import chapter6.RNG
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

/**
 * TODO: Re-enable tests by removing `!` prefix!
 */
class Exercise_6_3 : WordSpec({

    //tag::init[]
    fun intDouble(rng: RNG): Pair<Pair<Int, Double>, RNG> {
        val (i, r1) = nonNegativeInt(rng)
        val (d, r2) = double(r1)
        return Pair(Pair(i, d), r2)
    }

    fun doubleInt(rng: RNG): Pair<Pair<Double, Int>, RNG> {
        val (d, r1) = double(rng)
        val (i, r2) = nonNegativeInt(r1)
        return Pair(Pair(d, i), r2)
    }

    fun double3(rng: RNG): Pair<Triple<Double, Double, Double>, RNG> {
        val (d1, r1) = double(rng)
        val (d2, r2) = double(r1)
        val (d3, r3) = double(r2)
        return Pair(Triple(d1, d2, d3), r3)
    }
    //end::init[]

    "intDouble" should {

        val doubleBelowOne =
            Int.MAX_VALUE.toDouble() / (Int.MAX_VALUE.toDouble() + 1)

        val unusedRng = object : RNG {
            override fun nextInt(): Pair<Int, RNG> = TODO()
        }

        val rng3 = object : RNG {
            override fun nextInt(): Pair<Int, RNG> =
                Pair(Int.MAX_VALUE, unusedRng)
        }

        val rng2 = object : RNG {
            override fun nextInt(): Pair<Int, RNG> =
                Pair(Int.MAX_VALUE, rng3)
        }

        val rng = object : RNG {
            override fun nextInt(): Pair<Int, RNG> =
                Pair(Int.MAX_VALUE, rng2)
        }

        "generate a pair of int and double" {
            val (id, _) = intDouble(rng)
            val (i, d) = id
            i shouldBe Int.MAX_VALUE
            d shouldBe doubleBelowOne
        }

        "generate a pair of double and int" {
            val (di, _) = doubleInt(rng)
            val (d, i) = di
            d shouldBe doubleBelowOne
            i shouldBe Int.MAX_VALUE
        }

        "generate a triple of double, double, double" {
            val (ddd, _) = double3(rng)
            val (d1, d2, d3) = ddd
            d1 shouldBe doubleBelowOne
            d2 shouldBe doubleBelowOne
            d3 shouldBe doubleBelowOne
        }
    }
})
