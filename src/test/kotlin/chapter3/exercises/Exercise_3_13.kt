package chapter3.exercises

import chapter3.Cons
import chapter3.List
import chapter3.Nil
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

// tag::init[]
fun <A> append(a1: List<A>, a2: List<A>): List<A> =
    foldRightL(a1, a2, { a, aa -> Cons(a, aa) })
// end::init[]

fun <A> appendL(a1: List<A>, a2: List<A>): List<A> =
    foldLeft(
        foldLeft(
            a1,
            Nil,
            { aa: List<A>, a: A -> Cons(a, aa) }), // <=> reverse(a1)
        a2,
        { aa, a -> Cons(a, aa) }
    )

class Exercise_3_13 : WordSpec({
    "list append" should {
        "append two lists to each other using foldRight" {
            append(
                List.of(1, 2, 3),
                List.of(4, 5, 6)
            ) shouldBe List.of(1, 2, 3, 4, 5, 6)
        }
    }

    "list appendL" should {
        "append two lists to each other using foldLeft" {
            appendL(
                List.of(1, 2, 3),
                List.of(4, 5, 6)
            ) shouldBe List.of(1, 2, 3, 4, 5, 6)
        }
    }
})
