package fivestage

import chisel3._
import chisel3.util._
import chisel3.util.experimental.loadMemoryFromFile

class instrMem extends Module{
    val io = IO(new Bundle{
        val instr = Output(UInt(32.W))
        val instmemaddr = Input(UInt(10.W))
    })
    val mem = Mem(1024, UInt(32.W))
    loadMemoryFromFile(mem, "/")
    io.instr := mem.read(io.instmemaddr)
}