package chapter6.exercises

import chapter3.Cons
import chapter3.List
import chapter3.Nil
import chapter4.Boilerplate.foldRight
import chapter6.RNG
import chapter6.Rand
import chapter6.rng1
import chapter6.unit
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

/**
 * TODO: Re-enable tests by removing `!` prefix!
 */
class Exercise_6_7 : WordSpec({

    //tag::init[]
    fun <A> sequence(fs: List<Rand<A>>): Rand<List<A>> =
        fs.foldRight(unit(Nil)) { ra: Rand<A>, ras: Rand<List<A>> ->
            map2(ra, ras) { a, xs -> Cons(a, xs) }
        }
    //end::init[]

    //tag::init2[]
    fun <A> sequence2(fs: List<Rand<A>>): Rand<List<A>> = when (fs) {
        is Nil -> unit(Nil)
        is Cons -> { r ->
            val (a, r1) = fs.head(r)
            val (xa, r2) = sequence2(fs.tail)(r1)
            Pair(Cons(a, xa), r2)
        }
    }
    //end::init2[]

    fun ints2(count: Int, rng: RNG): Pair<List<Int>, RNG> {
        fun loop(n: Int): List<Rand<Int>> =
            if (n == 0) Nil
            else Cons({ r -> unit(1)(r)}, loop(n - 1))
        return sequence(loop(count))(rng)
    }

    "sequence" should {

        "combine the results of many actions using recursion" {

            val combined: Rand<List<Int>> =
                sequence(
                    List.of(
                        unit(1),
                        unit(2),
                        unit(3),
                        unit(4)
                    )
                )

            combined(rng1).first shouldBe
                    List.of(1, 2, 3, 4)
        }

        """combine the results of many actions using
            foldRight and map2""" {

            val combined2: Rand<List<Int>> =
                sequence2(
                    List.of(
                        unit(1),
                        unit(2),
                        unit(3),
                        unit(4)
                    )
                )

            combined2(rng1).first shouldBe
                    List.of(1, 2, 3, 4)
        }
    }

    "ints" should {
        "generate a list of ints of a specified length" {
            ints2(4, rng1).first shouldBe
                    List.of(1, 1, 1, 1)
        }
    }
})
