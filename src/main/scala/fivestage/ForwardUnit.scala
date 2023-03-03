package fivestage
import chisel3._

class Forward_Unit extends Module{
    val io = IO(new Bundle{
        val exMEM_RDREG = Input(UInt(5.W))
        val wbMEM_RDREG = Input(UInt(5.W))
        val idEX_REGRS1 = Input(UInt(5.W))
        val idEX_REGRS2 = Input(UInt(5.W))
        val exMEM_REGWR = Input(UInt(1.W))
        val wbMEM_REGWR = Input(UInt(1.W))
        val fwd_a = Output(UInt(2.W))
        val fwd_b = Output(UInt(2.W))

    })

    io.fwd_a := "b00".U
    io.fwd_b := "b00".U


    // EXECUTION HAZARD
    when(io.exMEM_REGWR === "b1".U && io.exMEM_RDREG =/= "b00000".U && (io.exMEM_RDREG === io.idEX_REGRS1) && (io.exMEM_RDREG === io.idEX_REGRS2)) {
               io.fwd_a := "b01".U
		io.fwd_b := "b01".U
    } .elsewhen(io.exMEM_REGWR === "b1".U && io.exMEM_RDREG =/= "b00000".U && (io.exMEM_RDREG === io.idEX_REGRS2)) {
		io.fwd_b := "b01".U
    } .elsewhen(io.exMEM_REGWR === "b1".U && io.exMEM_RDREG =/= "b00000".U && (io.exMEM_RDREG === io.idEX_REGRS1)) {
        
		io.fwd_a := "b01".U
    
    }
    

    // MEMORY HAZARD

    when(io.wbMEM_REGWR === "b1".U && io.wbMEM_RDREG =/= "b00000".U && ~((io.exMEM_REGWR === "b1".U) && (io.exMEM_RDREG =/= "b00000".U) && (io.exMEM_RDREG === io.idEX_REGRS1) && (io.exMEM_RDREG === io.idEX_REGRS2)) && (io.wbMEM_RDREG === io.idEX_REGRS1) && (io.wbMEM_RDREG === io.idEX_REGRS2)) {

    	io.fwd_a := "b10".U
    	io.fwd_b := "b10".U

    } .elsewhen(io.wbMEM_REGWR === "b1".U && io.wbMEM_RDREG =/= "b00000".U && ~((io.exMEM_REGWR === "b1".U) && (io.exMEM_RDREG =/= "b00000".U) && (io.exMEM_RDREG === io.idEX_REGRS2)) && (io.wbMEM_RDREG === io.idEX_REGRS2)) {
    
	io.fwd_b := "b10".U
    
    } .elsewhen(io.wbMEM_REGWR === "b1".U && io.wbMEM_RDREG =/= "b00000".U && ~((io.exMEM_REGWR === "b1".U) && (io.exMEM_RDREG =/= "b00000".U) && (io.exMEM_RDREG === io.idEX_REGRS2))  && (io.wbMEM_RDREG === io.idEX_REGRS1)) {
    io.fwd_a := "b10".U
	}


    
    
}
