package fivestage

import chisel3._
import chisel3.util._

class top extends Module{
    val io = IO(new Bundle{
        
        val result_out = Output(SInt(32.W))
        val addr = Output(UInt(10.W))
    })
    val pc_top = Module(new pC())
    val dataMem_top = Module(new data_mem())
    val instrMem_top = Module(new instrMem())
    val controlUnit_top = Module(new control())
    val immdGen_top = Module(new immdValGen())
    val aluControl_top = Module(new aluControl())
    val alu_top = Module(new ALU())
    val regFile_top = Module(new regFile())
    val jalr_top = Module(new jalr())



    val iFD_top = Module(new Instr_fetch_decode())
    val iDEX_top = Module(new Instr_decode_exe())
    val eXMEM_top = Module(new Exe_mem())
    val memWrite_top = Module(new MEM_WRITE())
    val hazard_top = Module(new Hazards())
    val forwarding_top = Module(new Forward_Unit())
    val blu_top = Module(new Branch_logic())
    val bfu_top = Module(new Branch_Forward())
    val strHAZ_top = Module(new structural_hazards())


    forwarding_top.io.exMEM_RDREG := eXMEM_top.io.rd_out
    forwarding_top.io.wbMEM_RDREG := memWrite_top.io.rd_out
    forwarding_top.io.idEX_REGRS1 := iDEX_top.io.rs1Instr_out
    forwarding_top.io.idEX_REGRS2 := iDEX_top.io.rs2Instr_out
    forwarding_top.io.exMEM_REGWR := eXMEM_top.io.regwrite_out
    forwarding_top.io.wbMEM_REGWR := memWrite_top.io.regwrite_out
    
    io.result_out := 0.S

    pc_top.io.in := pc_top.io.pc4

    instrMem_top.io.instmemaddr := pc_top.io.out(11,2)
    val inst = instrMem_top.io.instr
    io.addr := instrMem_top.io.instmemaddr

    iFD_top.io.pc_in := pc_top.io.out
    iFD_top.io.pc4_in := pc_top.io.pc4
    iFD_top.io.ins_in := inst

    controlUnit_top.io.op_code := iFD_top.io.ins_out(6,0)  

    regFile_top.io.rs1_addr := iFD_top.io.ins_out(19,15)
    regFile_top.io.rs2_addr := iFD_top.io.ins_out(24,20)


    immdGen_top.io.instr := iFD_top.io.ins_out
    immdGen_top.io.pc_imm := iFD_top.io.pc_out

    aluControl_top.io.alu_Operation := iDEX_top.io.aluCtrl_out
    aluControl_top.io.func3 := iDEX_top.io.func3_out
    aluControl_top.io.func7 := iDEX_top.io.func7_out

    strHAZ_top.io.rs1_sel := iFD_top.io.ins_out(19,15)
    strHAZ_top.io.rs2_sel := iFD_top.io.ins_out(24,20)
    strHAZ_top.io.wbMEM_RDREG := memWrite_top.io.rd_out
    strHAZ_top.io.wbMEM_REGWR := memWrite_top.io.regwrite_out

    when(strHAZ_top.io.rs1_strfwd === 1.U){

        iDEX_top.io.operand_A_in := regFile_top.io.writeData
    
    }.otherwise{

        iDEX_top.io.operand_A_in := regFile_top.io.rs1
    }

    when(strHAZ_top.io.rs2_strfwd === 1.U){

        iDEX_top.io.operand_B_in := regFile_top.io.writeData
    
    }.otherwise{

        iDEX_top.io.operand_B_in := regFile_top.io.rs2
    }

    when(controlUnit_top.io.extend_Sel === "b00".U){

        iDEX_top.io.imm_in := immdGen_top.io.i_imm
    
    }.elsewhen(controlUnit_top.io.extend_Sel === "b01".U){

        iDEX_top.io.imm_in := immdGen_top.io.s_imm
        
    }.elsewhen(controlUnit_top.io.extend_Sel === "b10".U){

        iDEX_top.io.imm_in := immdGen_top.io.u_imm

    }.otherwise{

        iDEX_top.io.imm_in := 0.S(32.W)
    }

    when(hazard_top.io.cntrl_frwd === "b1".U){

        iDEX_top.io.memwrite_in := 0.U
        iDEX_top.io.memread_in := 0.U
        iDEX_top.io.brnch_en_in:= 0.U
        iDEX_top.io.regwrite_in := 0.U
        iDEX_top.io.memtoreg_in := 0.U
        iDEX_top.io.aluCtrl_in := 0.U
        iDEX_top.io.operand_A_sel_in := 0.U
        iDEX_top.io.operand_B_sel_in := 0.U
        iDEX_top.io.pc_next_in := 0.U

	}.otherwise {

        iDEX_top.io.memwrite_in := controlUnit_top.io.memwrite
        iDEX_top.io.memread_in := controlUnit_top.io.memread
        iDEX_top.io.brnch_en_in := controlUnit_top.io.branch
        iDEX_top.io.regwrite_in := controlUnit_top.io.regwrite 
        iDEX_top.io.memtoreg_in := controlUnit_top.io.memtoreg
        iDEX_top.io.aluCtrl_in := controlUnit_top.io.alu_op
        iDEX_top.io.operand_A_sel_in := controlUnit_top.io.operand_A
        iDEX_top.io.operand_B_sel_in := controlUnit_top.io.operand_B
        iDEX_top.io.pc_next_in := controlUnit_top.io.nextPc_Sel

    }

    bfu_top.io.idEX_REGRD    := iDEX_top.io.rd_out
    bfu_top.io.iDEX_memread  := iDEX_top.io.memread_out
    bfu_top.io.exMEM_RDREG   := eXMEM_top.io.rd_out
    bfu_top.io.wbMEM_RDREG   := memWrite_top.io.rd_out
    bfu_top.io.exMEM_memread := eXMEM_top.io.memread_out
    bfu_top.io.wbMEM_memread := memWrite_top.io.readMem_out
    bfu_top.io.rs1_sel       := iFD_top.io.ins_out(19,15)
    bfu_top.io.rs2_sel       := iFD_top.io.ins_out(24,20)
    bfu_top.io.cntrl_branch  := controlUnit_top.io.branch


    blu_top.io.in_rs1   := regFile_top.io.rs1
    blu_top.io.in_rs2   := regFile_top.io.rs2
    blu_top.io.in_func3 := iFD_top.io.ins_out(14,12)

    jalr_top.io.rs1 := regFile_top.io.rs1
    jalr_top.io.imm := immdGen_top.io.i_imm


    when(bfu_top.io.forward_rs1 === "b0000".U)
    {
        blu_top.io.in_rs1 := regFile_top.io.rs1
        jalr_top.io.rs1   := regFile_top.io.rs1
    
    }.elsewhen(bfu_top.io.forward_rs1 === "b0001".U)
    {
        blu_top.io.in_rs1 := aluControl_top.io.out
        jalr_top.io.rs1   := regFile_top.io.rs1
    
    }.elsewhen(bfu_top.io.forward_rs1 === "b0010".U){

        blu_top.io.in_rs1 := eXMEM_top.io.outalu_out
        jalr_top.io.rs1   := regFile_top.io.rs1
    
    }.elsewhen(bfu_top.io.forward_rs1 === "b0011".U){

        blu_top.io.in_rs1 := regFile_top.io.writeData
        jalr_top.io.rs1   := regFile_top.io.rs1
    
    }.elsewhen(bfu_top.io.forward_rs1 === "b0100".U){

        blu_top.io.in_rs1 := dataMem_top.io.dataout
        jalr_top.io.rs1   := regFile_top.io.rs1
    
    }.elsewhen(bfu_top.io.forward_rs1 === "b0101".U){

        blu_top.io.in_rs1 := regFile_top.io.writeData
        jalr_top.io.rs1   := regFile_top.io.rs1
    
    }.elsewhen(bfu_top.io.forward_rs1 === "b0110".U){

        jalr_top.io.rs1   := alu_top.io.out
        blu_top.io.in_rs1 := regFile_top.io.rs1
    
    }.elsewhen(bfu_top.io.forward_rs1 === "b0111".U){

        jalr_top.io.rs1   := eXMEM_top.io.outalu_out
        blu_top.io.in_rs1 := regFile_top.io.rs1
    
    }.elsewhen(bfu_top.io.forward_rs1 === "b1000".U){

        jalr_top.io.rs1   := regFile_top.io.writeData
        blu_top.io.in_rs1 := regFile_top.io.rs1
    
    }.elsewhen(bfu_top.io.forward_rs1 === "b1001".U){

        jalr_top.io.rs1   := dataMem_top.io.dataout
        blu_top.io.in_rs1 := regFile_top.io.rs1
    
    }.elsewhen(bfu_top.io.forward_rs1 === "b1010".U){

        jalr_top.io.rs1   := regFile_top.io.writeData
        blu_top.io.in_rs1 := regFile_top.io.rs1

    }.otherwise{

        blu_top.io.in_rs1 := regFile_top.io.rs1
        jalr_top.io.rs1   := regFile_top.io.rs1
    
    }

    when(bfu_top.io.forward_rs2 === "b0000".U){

        blu_top.io.in_rs2 := regFile_top.io.rs2

    }.elsewhen(bfu_top.io.forward_rs2 === "b0001".U){

        blu_top.io.in_rs2 := alu_top.io.out

    }.elsewhen(bfu_top.io.forward_rs2 === "b0010".U){

        blu_top.io.in_rs2 := eXMEM_top.io.outalu_out
    
    }.elsewhen(bfu_top.io.forward_rs2 === "b0011".U){

        blu_top.io.in_rs2 := regFile_top.io.writeData
    
    }.elsewhen(bfu_top.io.forward_rs2 === "b0100".U){

        blu_top.io.in_rs2 := dataMem_top.io.dataout
    
    }.elsewhen(bfu_top.io.forward_rs2 === "b0101".U){

        blu_top.io.in_rs2 :=regFile_top.io.writeData
    
    }.otherwise{

        blu_top.io.in_rs2 := regFile_top.io.rs2
    }
    
    iDEX_top.io.pc_in            := iFD_top.io.pc_out
    iDEX_top.io.pc4_in           := iFD_top.io.pc4_out
    iDEX_top.io.memwrite_in      := controlUnit_top.io.memwrite
    iDEX_top.io.memread_in       := controlUnit_top.io.memread
    iDEX_top.io.regwrite_in      := controlUnit_top.io.regwrite
    iDEX_top.io.memtoreg_in      := controlUnit_top.io.memtoreg
    iDEX_top.io.aluCtrl_in       := controlUnit_top.io.alu_op
    iDEX_top.io.operand_A_sel_in := controlUnit_top.io.operand_A
    iDEX_top.io.operand_B_sel_in := controlUnit_top.io.operand_B
    iDEX_top.io.brnch_en_in      := controlUnit_top.io.branch
    iDEX_top.io.pc_next_in       := controlUnit_top.io.nextPc_Sel
    iDEX_top.io.func3_in         := iFD_top.io.ins_out(14,12)
    iDEX_top.io.func7_in         := iFD_top.io.ins_out(30)
    iDEX_top.io.rs1Instr_in      := iFD_top.io.ins_out(19,15)
    iDEX_top.io.rs2Instr_in      := iFD_top.io.ins_out(24,20)
    iDEX_top.io.rd_in            := iFD_top.io.ins_out(11,7)
    iDEX_top.io.operand_A_in     := regFile_top.io.rs1
    iDEX_top.io.operand_B_in     := regFile_top.io.rs2


    hazard_top.io.inst_FD       := iFD_top.io.ins_out
    hazard_top.io.iDEX_memread  := iDEX_top.io.memread_out
    hazard_top.io.idEX_REGRD   := iDEX_top.io.rd_out
    hazard_top.io.pc_in         := iFD_top.io.pc4_out
    hazard_top.io.current_pc_in := iFD_top.io.pc_out

    when(hazard_top.io.inst_frwd === "b1".U){

        iFD_top.io.ins_in := hazard_top.io.inst_out
        iFD_top.io.pc_in  := hazard_top.io.current_pc_in

    }.otherwise{

        iFD_top.io.ins_in := instrMem_top.io.instr
    }

    when(hazard_top.io.pc_frwd === "b1".U){

        pc_top.io.in := hazard_top.io.pc_out
    }.otherwise{

        when(controlUnit_top.io.nextPc_Sel === "b01".U){

            when(blu_top.io.output === 1.U && controlUnit_top.io.branch === 1.U){

                pc_top.io.in := immdGen_top.io.sb_imm.asUInt
                iFD_top.io.pc_in := 0.U
                iFD_top.io.pc4_in := 0.U
                iFD_top.io.ins_in := 0.U
            }.otherwise{

                pc_top.io.in := pc_top.io.pc4
            }
        
        }.elsewhen(controlUnit_top.io.nextPc_Sel === "b11".U){

            pc_top.io.in := jalr_top.io.pcVal.asUInt
            iFD_top.io.pc_in := 0.U
            iFD_top.io.pc4_in := 0.U
            iFD_top.io.ins_in := 0.U
        
        }.otherwise{

            pc_top.io.in := pc_top.io.pc4
        }

    }

    eXMEM_top.io.memwrite_in := iDEX_top.io.memwrite_out
    eXMEM_top.io.memread_in  := iDEX_top.io.memread_out
    eXMEM_top.io.memtoreg_in := iDEX_top.io.memtoreg_out
    eXMEM_top.io.regwrite_in := iDEX_top.io.regwrite_out
    eXMEM_top.io.rd_in       := iDEX_top.io.rd_out
    eXMEM_top.io.outalu_in   := alu_top.io.out

    alu_top.io.alu_Operation := aluControl_top.io.alu_Operation

    when(iDEX_top.io.operand_A_sel_out === "b010".U){

        alu_top.io.in_A := iDEX_top.io.pc4_out.asUInt
    
    }.otherwise{

        alu_top.io.in_A := MuxCase(0.S,Array(

            (forwarding_top.io.fwd_a === 0.U) -> iDEX_top.io.operand_A_out,
            (forwarding_top.io.fwd_a === 1.U) -> eXMEM_top.io.outalu_out,
            (forwarding_top.io.fwd_a === 2.U) -> regFile_top.io.writeData,
            (forwarding_top.io.fwd_a === 3.U) -> iDEX_top.io.operand_A_out

        ))
    }

    when(iDEX_top.io.operand_B_sel_out === 1.U){

        alu_top.io.in_B := iDEX_top.io.imm_out

        when(forwarding_top.io.fwd_b === 0.U){

            eXMEM_top.io.rs2_in := iDEX_top.io.operand_B_out
        
        }.elsewhen(forwarding_top.io.fwd_b === 1.U){

            eXMEM_top.io.rs2_in := eXMEM_top.io.outalu_out
        
        }.elsewhen(forwarding_top.io.fwd_b === 2.U){

            eXMEM_top.io.rs2_in := regFile_top.io.writeData
        
        }.otherwise{

            eXMEM_top.io.rs2_in := regFile_top.io.writeData
        }
    
    }.otherwise{

        when(forwarding_top.io.fwd_b === 0.U){

            alu_top.io.in_B     := iDEX_top.io.operand_B_out
            eXMEM_top.io.rs2_in := iDEX_top.io.operand_B_out
        
        }.elsewhen(forwarding_top.io.fwd_b === "b01".U){

            alu_top.io.in_B := eXMEM_top.io.outalu_out
            eXMEM_top.io.rs2_in := eXMEM_top.io.outalu_out
        
        }.elsewhen(forwarding_top.io.fwd_b === "b10".U){

            alu_top.io.in_B := regFile_top.io.writeData
            eXMEM_top.io.rs2_in := regFile_top.io.writeData
        
        }.otherwise{

            alu_top.io.in_B := iDEX_top.io.operand_B_out
            eXMEM_top.io.rs2_in := iDEX_top.io.operand_B_out
        }
    }

    dataMem_top.io.dataaddr := (eXMEM_top.io.outalu_out).asUInt
    dataMem_top.io.datain   := eXMEM_top.io.rs2_out
    dataMem_top.io.memwr    := eXMEM_top.io.memwrite_out
    dataMem_top.io.memrd    := eXMEM_top.io.memread_out


    memWrite_top.io.memtoreg_in     := eXMEM_top.io.memtoreg_out
    memWrite_top.io.regwrite_in  := eXMEM_top.io.regwrite_out
    memWrite_top.io.readMem_in   := eXMEM_top.io.memread_out
    memWrite_top.io.rd_in        := eXMEM_top.io.rd_out
    memWrite_top.io.outdata_in   := dataMem_top.io.dataout
    memWrite_top.io.outalu_in    := eXMEM_top.io.outalu_out
    memWrite_top.io.memwrite_in  := eXMEM_top.io.memwrite_out


    regFile_top.io.writeData := MuxCase(0.S,Array(

        (memWrite_top.io.memtoreg_out === 0.U) -> memWrite_top.io.outalu_out,
        
        (memWrite_top.io.memwrite_out === 1.U) -> memWrite_top.io.outdata_out

    ))

    regFile_top.io.regwrite := memWrite_top.io.regwrite_out
    regFile_top.io.rd_addr  := memWrite_top.io.rd_out

}