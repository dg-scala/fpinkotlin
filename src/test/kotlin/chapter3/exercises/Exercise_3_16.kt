package chapter3.exercises

import chapter3.Cons
import chapter3.List
import chapter3.Listing_3_11.foldRight
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

// tag::init[]
fun doubleToString(xs: List<Double>): List<String> =
    foldRight(
        xs,
        List.empty<String>(),
        { d, ss -> Cons(d.toString(), ss) })
// end::init[]

class Exercise_3_16 : WordSpec({
    "list doubleToString" should {
        "convert every double element to a string" {
            doubleToString(List.of(1.1, 1.2, 1.3, 1.4)) shouldBe
                List.of("1.1", "1.2", "1.3", "1.4")
        }
    }
})
