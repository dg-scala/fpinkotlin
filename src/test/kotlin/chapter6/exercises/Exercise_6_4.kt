package chapter6.exercises

import chapter3.Cons
import chapter3.List
import chapter3.Nil
import chapter6.RNG
import chapter6.rng1
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

/**
 * TODO: Re-enable tests by removing `!` prefix!
 */
class Exercise_6_4 : WordSpec({

    //tag::init[]
    fun ints(count: Int, rng: RNG): Pair<List<Int>, RNG> {
        return if (count > 0) {
            val (i, r1) = nonNegativeInt(rng)
            val (xs, r2) = ints(count - 1, r1)
            Pair(Cons(i, xs), r2)
        } else Pair(Nil, rng)
    }
    //end::init[]

    "ints" should {
        "generate a list of ints of a specified length" {

            ints(5, rng1) shouldBe
                Pair(List.of(1, 1, 1, 1, 1), rng1)
        }
    }
})
