package chapter3.exercises

import chapter3.List
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

import chapter3.Listing_3_11.foldRight

// tag::init[]
fun <A, B> foldLeftR(xs: List<A>, z: B, f: (B, A) -> B): B =
    foldRight(xs, z, TODO()) // unsolved - look at Solution_3_12.kt demystified

fun <A, B> foldRightL(xs: List<A>, z: B, f: (A, B) -> B): B =
    foldLeft(xs, z, TODO()) // unsolved - look at Solution_3_12.kt demystified
// end::init[]

class Exercise_3_12 : WordSpec({
    "list foldLeftR" should {
        "!implement foldLeft functionality using foldRight" {
            foldLeftR(
                List.of(1, 2, 3, 4, 5),
                0,
                { x, y -> x + y }) shouldBe 15
        }
    }

    "list foldRightL" should {
        "!implement foldRight functionality using foldLeft" {
            foldRightL(
                List.of(1, 2, 3, 4, 5),
                0,
                { x, y -> x + y }) shouldBe 15
        }
    }
})
