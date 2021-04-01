package chapter5.exercises

import chapter3.List
import chapter4.None
import chapter4.Option
import chapter4.Some
import chapter4.exercises.getOrElse
import chapter4.exercises.map
import chapter5.Stream
import chapter5.Stream.Companion.cons
import chapter5.Stream.Companion.empty
import chapter5.solutions.take
import chapter5.solutions.toList
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

//tag::init[]
fun <A, S> unfoldC(z: S, f: (S) -> Option<Pair<A, S>>): Stream<A> =
    when (val oas = f(z)) {
        is None -> empty()
        is Some -> cons({ oas.get.first }, { unfoldC(oas.get.second, f) })
    }

fun <A, S> unfold(z: S, f: (S) -> Option<Pair<A, S>>): Stream<A> =
    f(z).map { pair ->
        cons({ pair.first }, { unfold(pair.second, f)} )
    }.getOrElse { empty() }
//end::init[]

/**
 * Re-enable the tests by removing the `!` prefix!
 */
class Exercise_5_11 : WordSpec({
    "unfoldC" should {
        """return a stream based on an initial state and a function
            applied to each subsequent element""" {
            unfoldC(0, { s: Int ->
                Some(Pair(s, s + 1))
            }).take(5).toList() shouldBe
                    List.of(0, 1, 2, 3, 4)
        }
    }

    "unfold" should {
        """return a stream based on an initial state and a function
            applied to each subsequent element""" {
            unfold(0, { s: Int ->
                Some(Pair(s, s + 1))
            }).take(5).toList() shouldBe
                    List.of(0, 1, 2, 3, 4)
        }
    }
})
