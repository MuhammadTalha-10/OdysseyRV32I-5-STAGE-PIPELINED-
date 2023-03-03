package fivestage
import chisel3._

class Hazards extends Module{
    val io = IO(new Bundle{
        
        val inst_FD = Input(UInt(32.W))
        val iDEX_memread = Input(UInt(1.W))
        val idEX_REGRD = Input(UInt(5.W))
        val pc_in = Input(UInt(32.W))
        val pc_out = Output(UInt(32.W))
        val current_pc_in = Input(UInt(32.W))
        val current_pc_out = Output(UInt(32.W))
        val inst_frwd = Output(UInt(1.W))
        val pc_frwd = Output(UInt(1.W))
        val cntrl_frwd = Output(UInt(1.W))
        val inst_out = Output(UInt(32.W))
        
    })
    
    val rs1_sel = io.inst_FD(19,15)
    val rs2_sel = io.inst_FD(24,20)

    when(io.iDEX_memread === "b1".U && ((io.idEX_REGRD === rs1_sel) ||(io.idEX_REGRD === rs2_sel))){
        io.inst_frwd := 1.U
        io.pc_frwd := 1.U
        io.cntrl_frwd := 1.U
        io.pc_out := io.pc_in
        io.current_pc_out := io.current_pc_in
    
    }.otherwise {
        io.inst_frwd := 0.U
        io.pc_frwd := 0.U
        io.cntrl_frwd := 0.U
       // io.inst_out := inst_FD
        io.pc_out :=io.pc_in
        io.current_pc_out :=io.current_pc_in
    }

}