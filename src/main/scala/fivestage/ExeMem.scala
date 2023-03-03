package fivestage

import chisel3._

class Exe_mem extends Module{
    val io = IO(new Bundle{
        val memwrite_in = Input(UInt(1.W))
        val memwrite_out = Output(UInt(1.W))
        val memread_in = Input(UInt(1.W))
        val memread_out = Output(UInt(1.W))
        val memtoreg_in = Input(UInt(1.W))
        val memtoreg_out = Output(UInt(1.W))
        val regwrite_in = Input(UInt(1.W))
        val regwrite_out = Output(UInt(1.W))
        val rd_in = Input(UInt(5.W))
        val rd_out = Output(UInt(5.W))
        val outalu_in  = Input(SInt(32.W))
        val outalu_out = Output(SInt(32.W))
        val rs2_in = Input(SInt(32.W))
        val rs2_out = Output(SInt(32.W))
        val sel_rs2_in = Input(UInt(1.W))
        val sel_rs2_out = Output(UInt(1.W))

    })
    
    val reg_memwrite = RegInit(0.U(1.W))
    val reg_memread = RegInit(0.U(1.W))
    val reg_memtoreg = RegInit(0.U(1.W))
    val reg_regwrite = RegInit(0.U(1.W))
    val reg_rd = RegInit(0.U(5.W))
    val reg_outalu = RegInit(0.S(32.W))
    val reg_rs2 = RegInit(0.S(32.W))


                //wiring
    
    reg_memwrite := io.memwrite_in
    io.memwrite_out := reg_memwrite

    reg_memread := io.memread_in
    io.memread_out := reg_memread

    reg_memtoreg := io.memtoreg_in
    io.memtoreg_out := reg_memtoreg

    reg_regwrite := io.regwrite_in
    io.regwrite_out := reg_regwrite

    reg_rd := io.rd_in
    io.rd_out := reg_rd

    reg_outalu := io.outalu_in
    io.outalu_out := reg_outalu
}