package fivestage

import chisel3._
import chisel3.util._

class control extends Module{
    val io = IO(new Bundle{
        val op_code = Input(UInt(7.W))
        val memwrite = Output(UInt(1.W))
        val branch = Output(UInt(1.W))
        val memread = Output(UInt(1.W))
        val regwrite = Output(UInt(1.W))
        val memtoreg = Output(UInt(1.W))
        val alu_op = Output(UInt(3.W))
        val operand_A = Output(UInt(2.W))
        val operand_B = Output(UInt(1.W))
        val extend_Sel = Output(UInt(2.W))
        val nextPc_Sel = Output(UInt(2.W))
    })
    when(io.op_code === "b0110011".U){//R-Type
        io.memwrite := "b0".U
        io.branch := "b0".U
        io.memread := "b0".U
        io.regwrite := "b1".U
        io.memtoreg := "b0".U
        io.alu_op := "b000".U
        io.operand_A := "b00".U
        io.operand_B := "b0".U
        io.extend_Sel := "b00".U
        io.nextPc_Sel := "b00".U
    }.elsewhen(io.op_code === "b0010011".U){//I-Type
        io.memwrite := "b0".U
        io.branch := "b0".U
        io.memread := "b0".U
        io.regwrite := "b1".U
        io.memtoreg := "b0".U
        io.alu_op := "b001".U
        io.operand_A := "b00".U
        io.operand_B := "b1".U
        io.extend_Sel := "b00".U
        io.nextPc_Sel := "b00".U
    }.elsewhen(io.op_code === "b0000011".U){//Load
        io.memwrite := "b0".U
        io.branch := "b0".U
        io.memread := "b1".U
        io.regwrite := "b1".U
        io.memtoreg := "b1".U
        io.alu_op := "b100".U
        io.operand_A := "b00".U
        io.operand_B := "b1".U
        io.extend_Sel := "b00".U
        io.nextPc_Sel := "b00".U
    }.elsewhen(io.op_code === "b0100011".U){//S-Type
        io.memwrite := "b1".U
        io.branch := "b0".U
        io.memread := "b0".U
        io.regwrite := "b0".U
        io.memtoreg := "b0".U
        io.alu_op := "b101".U
        io.operand_A := "b00".U
        io.operand_B := "b1".U
        io.extend_Sel := "b10".U
        io.nextPc_Sel := "b00".U
    }.elsewhen(io.op_code === "b1100011".U){//SB-Type
        io.memwrite := "b0".U
        io.branch := "b1".U
        io.memread := "b0".U
        io.regwrite := "b0".U
        io.memtoreg := "b0".U
        io.alu_op := "b010".U
        io.operand_A := "b00".U
        io.operand_B := "b0".U
        io.extend_Sel := "b00".U
        io.nextPc_Sel := "b01".U
    }.elsewhen(io.op_code === "b0110111".U){//LUI
        io.memwrite := "b0".U
        io.branch := "b0".U
        io.memread := "b0".U
        io.regwrite := "b1".U
        io.memtoreg := "b0".U
        io.alu_op := "b110".U
        io.operand_A := "b11".U
        io.operand_B := "b1".U
        io.extend_Sel := "b10".U
        io.nextPc_Sel := "b00".U
    }.elsewhen(io.op_code === "b1101111".U){//UJ-Type
        io.memwrite := "b0".U
        io.branch := "b0".U
        io.memread := "b0".U
        io.regwrite := "b1".U
        io.memtoreg := "b0".U
        io.alu_op := "b011".U
        io.operand_A := "b10".U 
        io.operand_B := "b0".U
        io.extend_Sel := "b00".U
        io.nextPc_Sel := "b10".U
    }.elsewhen(io.op_code === "b0010111".U){//AUIPC
        io.memwrite := "b0".U
        io.branch := "b0".U
        io.memread := "b0".U
        io.regwrite := "b1".U
        io.memtoreg := "b0".U
        io.alu_op := "b111".U
        io.operand_A := "b10".U
        io.operand_B := "b1".U
        io.extend_Sel := "b10".U
        io.nextPc_Sel := "b00".U
    }.elsewhen(io.op_code === "b1100111".U){//JALR
        io.memwrite := "b0".U
        io.branch := "b0".U
        io.memread := "b0".U
        io.regwrite := "b1".U
        io.memtoreg := "b0".U
        io.alu_op := "b011".U
        io.operand_A := "b10".U
        io.operand_B := "b1".U
        io.extend_Sel := "b00".U
        io.nextPc_Sel := "b11".U
    }.otherwise{
        io.memwrite := DontCare
        io.branch := DontCare
        io.memread := DontCare
        io.regwrite := DontCare
        io.memtoreg := DontCare
        io.alu_op := DontCare
        io.operand_A := DontCare
        io.operand_B := DontCare
        io.extend_Sel := DontCare
        io.nextPc_Sel := DontCare
    }
}