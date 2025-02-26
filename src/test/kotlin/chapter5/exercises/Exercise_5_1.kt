package chapter5.exercises

import chapter3.List
import chapter3.exercises.reverse
import chapter3.Nil as NilL
import chapter3.Cons as ConsL
import chapter5.Cons
import chapter5.Empty
import chapter5.Stream
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

/**
 * Re-enable the tests by removing the `!` prefix!
 */
class Exercise_5_1 : WordSpec({
    //tag::init[]
    fun <A> Stream<A>.toList(): List<A> {
        tailrec fun go(xs: Stream<A>, acc: List<A>): List<A> = when (xs) {
            is Empty -> acc
            is Cons -> go(xs.tail(), ConsL(xs.head(), acc))
        }
        return reverse(go(this, NilL))
    }
    //end::init[]

    "Stream.toList" should {
        "force the stream into an evaluated list" {
            val s = Stream.of(1, 2, 3, 4, 5)
            s.toList() shouldBe List.of(1, 2, 3, 4, 5)
        }
    }
})
