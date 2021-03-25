package chapter3.exercises

import chapter3.List
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

import chapter3.Listing_3_11.foldRight

// tag::init[]
typealias Identity<B> = (B) -> B

fun <A, B> foldLeftR(xs: List<A>, z: B, f: (B, A) -> B) =
    foldRight(
        xs,
        { b: B -> b },
        { a, g -> { b -> g(f(b, a)) } }
    )(z)

fun <A, B> foldLeftRDemystified(
    xs: List<A>,
    zero: B,
    combiner: (B, A) -> B
): B {
    val identity: Identity<B> = { b: B -> b }

    val combinerDelayer: (A, Identity<B>) -> Identity<B> =
        { a: A, delayedExec: Identity<B> ->
            { b: B -> delayedExec(combiner(b, a)) }
        }

    val chain: Identity<B> = foldRight(xs, identity, combinerDelayer)

    return chain(zero)
}

fun <A, B> foldRightL(xs: List<A>, z: B, f: (A, B) -> B): B =
    foldLeft(
        xs,
        { b: B -> b },
        { g, a -> { b -> g(f(a, b)) } }
    )(z)

fun <A, B> foldRightLDemystified(
    xs: List<A>,
    zero: B,
    combiner: (A, B) -> B
): B {
    val identity: Identity<B> = { b: B -> b }

    val combinerDelayer: (Identity<B>, A) -> Identity<B> =
        { delayedExec: Identity<B>, a: A ->
            { b: B -> delayedExec(combiner(a, b)) }
        }

    val chain: Identity<B> = foldLeft(xs, identity, combinerDelayer)

    return chain(zero)
}
// end::init[]

class Exercise_3_12 : WordSpec({
    "list foldLeftR" should {
        "implement foldLeft functionality using foldRight" {
            foldLeftR(
                List.of(1, 2, 3, 4, 5),
                0,
                { x, y -> x + y }) shouldBe 15
        }
    }

    "list foldRightL" should {
        "implement foldRight functionality using foldLeft" {
            foldRightL(
                List.of(1, 2, 3, 4, 5),
                0,
                { x, y -> x + y }) shouldBe 15
        }
    }
})
