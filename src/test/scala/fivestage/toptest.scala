package fivestage

import chisel3._
import chiseltest._
import org.scalatest._

class fivestage_test extends FreeSpec with ChiselScalatestTester{
    "5 stage" in {
        test(new top){ 
        c =>
        c.clock.step(100)
        }
    }
}