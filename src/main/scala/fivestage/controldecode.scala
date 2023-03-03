package fivestage

import chisel3._

class cntrl_dec extends Module{
    val io = IO(new Bundle{
        val Rtype = Input(UInt(1.W))
		val Load = Input(UInt(1.W))
		val Store = Input(UInt(1.W))
		val SBtype = Input(UInt(1.W))
		val Itype = Input(UInt(1.W))
		val Jalr = Input(UInt(1.W))
		val Jal = Input(UInt(1.W))
		val Lui = Input(UInt(1.W))
		val MemWr = Output(UInt(1.W))
		val Branch = Output(UInt(1.W))
		val Memread = Output(UInt(1.W))
		val RegWrite = Output(UInt(1.W))
		val Memtoreg = Output(UInt(1.W))
		val AluOp = Output(UInt(3.W))
		val Operand_a_Sel = Output(UInt(2.W))
		val Operand_b_Sel = Output(UInt(1.W))
		val Extend_Sel = Output(UInt(2.W))
		val Next_Pc_Sel = Output(UInt(2.W))
	})
    io.MemWr := 0.U
    io.Branch := 0.U
    io.Memread := 0.U
    io.RegWrite := 0.U
    io.Memtoreg := 0.U
    io.AluOp := "b000".U
    io.Operand_a_Sel := 0.U
    io.Operand_b_Sel := 0.U
    io.Extend_Sel := 0.U
    io.Next_Pc_Sel := 0.U


    

    }
