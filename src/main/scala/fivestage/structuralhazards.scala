package fivestage
import chisel3._

class structural_hazards extends Module{

    val io = IO(new Bundle{

        val rs1_sel = Input(UInt(5.W))
        val rs2_sel = Input(UInt(5.W))
        val wbMEM_REGWR = Input(UInt(1.W))
        val wbMEM_RDREG = Input(UInt(5.W))
        val rs1_strfwd = Output(UInt(1.W))
        val rs2_strfwd = Output(UInt(1.W))
    })

    when(io.wbMEM_REGWR === 1.U && io.wbMEM_RDREG === io.rs1_sel){

        io.rs1_strfwd := 1.U
    
    }.otherwise{

        io.rs1_strfwd := 0.U
    }

    when(io.wbMEM_REGWR === 1.U && io.wbMEM_RDREG === io.rs2_sel){

        io.rs2_strfwd := 1.U
    
    }.otherwise{

        io.rs2_strfwd := 0.U
    }
}