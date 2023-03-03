// package fivestage
// import chisel3._
// import chiseltest._

// class datamemtest extends FreeSpec with ChiselScalatestTester {

//     "datamem test" in {
//         test (new data_mem){
//             c =>
//             c.io.memwr.poke(1.B)
//             c.io.memrd.poke(0.B)
//             c.io.dataaddr.poke(3.U)
//             c.clock.step(50)
//         }
//     }
// }