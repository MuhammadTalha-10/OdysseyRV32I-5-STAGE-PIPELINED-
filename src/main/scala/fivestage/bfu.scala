package fivestage

import chisel3._

class Branch_Forward extends Module{
    
    val io = IO(new Bundle{

        val idEX_REGRD = Input(UInt(5.W))
        val exMEM_RDREG = Input(UInt(5.W))
        val iDEX_memread = Input(UInt(1.W))
        val exMEM_memread = Input(UInt(1.W))
        val wbMEM_RDREG = Input(UInt(5.W))
        val wbMEM_memread = Input(UInt(1.W))
        val rs1_sel = Input(UInt(5.W))
        val rs2_sel = Input(UInt(5.W))
        val cntrl_branch = Input(UInt(1.W))
        val forward_rs1 = Output(UInt(4.W))
        val forward_rs2 = Output(UInt(4.W))
    })

    io.forward_rs1 := "b0000".U
    io.forward_rs2 := "b0000".U

    //ALU HAZARD

    when(io.idEX_REGRD =/= "b00000".U && io.iDEX_memread =/= 1.U && (io.idEX_REGRD === io.rs1_sel) && (io.idEX_REGRD === io.rs2_sel)){
        io.forward_rs1 := "b0001".U
        io.forward_rs2 := "b0001".U
    
    }.elsewhen(io.idEX_REGRD =/= "b00000".U && io.iDEX_memread =/= 1.U && (io.idEX_REGRD === io.rs1_sel)){

        io.forward_rs1 := "b0001".U

    }.elsewhen(io.idEX_REGRD =/= "b00000".U && io.iDEX_memread =/= 1.U && (io.idEX_REGRD === io.rs2_sel)){

        io.forward_rs2 := "b0001".U
    }

    //EX/MEM HAZARD

    when(io.exMEM_RDREG =/= "b00000".U && io.exMEM_memread =/= 1.U && 
        ((io.idEX_REGRD =/= "b00000".U) && (io.idEX_REGRD === io.rs1_sel) && (io.idEX_REGRD === io.rs2_sel)) &&
        (io.exMEM_RDREG === io.rs1_sel) && (io.exMEM_RDREG === io.rs2_sel))
        {

            io.forward_rs1 := "b0010".U
            io.forward_rs2 := "b0010".U
        
        }.elsewhen(io.exMEM_RDREG =/= "b00000".U && io.exMEM_memread =/= 1.U &&
        ((io.idEX_REGRD =/= "b00000".U) && (io.idEX_REGRD === io.rs2_sel)) &&
        (io.exMEM_RDREG === io.rs2_sel))
        {
            io.forward_rs2 := "b0010".U
        
        }.elsewhen(io.exMEM_RDREG =/= "b00000".U && io.exMEM_memread === 1.U && 
        ((io.idEX_REGRD =/= "b00000".U) && (io.idEX_REGRD === io.rs1_sel) && (io.idEX_REGRD === io.rs2_sel)) &&
        (io.exMEM_RDREG === io.rs1_sel))
        {
            io.forward_rs1 := "b0010".U

        
        }.elsewhen(io.exMEM_RDREG =/= "b00000".U && io.exMEM_memread === 1.U && 
        ((io.idEX_REGRD =/= "b00000".U) && (io.idEX_REGRD === io.rs1_sel) && (io.idEX_REGRD === io.rs2_sel)) &&
        (io.exMEM_RDREG === io.rs1_sel) && (io.exMEM_RDREG === io.rs2_sel))
        {

            io.forward_rs1 := "b0100".U
            io.forward_rs2 := "b0100".U

        }.elsewhen(io.exMEM_RDREG =/= "b00000".U && io.exMEM_memread === 1.U &&
        ((io.idEX_REGRD =/= "b00000".U) && (io.idEX_REGRD === io.rs2_sel)) &&
        (io.exMEM_RDREG === io.rs2_sel))
        {

            io.forward_rs2 := "b0100".U
        
        }.elsewhen(io.cntrl_branch === 1.U && io.exMEM_RDREG =/= "b00000".U && io.exMEM_memread === 1.U &&
        ((io.idEX_REGRD =/= "b00000".U) && (io.idEX_REGRD === io.rs1_sel)) && 
        (io.exMEM_RDREG === io.rs1_sel))
        {

            io.forward_rs1 := "b0100".U

        }

    // MEM/WB HAZARD

    when(io.wbMEM_RDREG =/= "b00000".U && io.wbMEM_memread =/= 1.U &&
    
    ((io. idEX_REGRD =/= "b00000".U) && (io.idEX_REGRD === io.rs1_sel) && (io.idEX_REGRD === io.rs2_sel)) &&
    
    ((io.exMEM_RDREG =/= "b00000".U) && (io.exMEM_RDREG === io.rs1_sel) && (io.exMEM_RDREG === io.rs2_sel)) &&
    (io.wbMEM_RDREG === io.rs1_sel) && (io.wbMEM_RDREG === io.rs2_sel))
    {

        io.forward_rs1 := "b0011".U
        io.forward_rs2 := "b0011".U

    }.elsewhen(io.wbMEM_RDREG =/= "b00000".U && io.wbMEM_memread =/= 1.U &&
    
    ((io.idEX_REGRD =/= "b00000".U) && (io.idEX_REGRD === io.rs2_sel)) &&
    
    ((io.exMEM_RDREG =/= "b00000".U) && (io.exMEM_RDREG === io.rs2_sel)) &&
    (io.wbMEM_RDREG === io.rs2_sel))
    {

        io.forward_rs2 := "b0011".U

    }.elsewhen(io.wbMEM_RDREG =/= "b00000".U && io.wbMEM_memread =/= 1.U &&
    
    ((io.idEX_REGRD =/= "b00000".U) && (io.idEX_REGRD === io.rs1_sel)) &&
    
    ((io.exMEM_RDREG =/= "b00000".U) && (io.exMEM_RDREG === io.rs1_sel)) && 
    (io.wbMEM_RDREG === io.rs1_sel))
    {

        io.forward_rs1 := "b0011".U

    }.elsewhen(io.wbMEM_RDREG =/= "b00000".U && io.wbMEM_memread =/= 1.U &&
    
    ((io. idEX_REGRD =/= "b00000".U) && (io.idEX_REGRD === io.rs1_sel) && (io.idEX_REGRD === io.rs2_sel)) &&
    
    ((io.exMEM_RDREG =/= "b00000".U) && (io.exMEM_RDREG === io.rs1_sel) && (io.exMEM_RDREG === io.rs2_sel)) &&
    (io.wbMEM_RDREG === io.rs1_sel) && (io.wbMEM_RDREG === io.rs2_sel))
    {


        io.forward_rs1 := "b0101".U
        io.forward_rs2 := "b0101".U

    }.elsewhen(io.wbMEM_RDREG =/= "b00000".U && io.wbMEM_memread =/= 1.U &&
    
    ((io.idEX_REGRD =/= "b00000".U) && (io.idEX_REGRD === io.rs1_sel)) &&
    
    ((io.exMEM_RDREG =/= "b00000".U) && (io.exMEM_RDREG === io.rs1_sel)) && 
    (io.wbMEM_RDREG === io.rs2_sel))
    {

        io.forward_rs1 := "b0101".U

    }.elsewhen(io.wbMEM_RDREG =/= "b00000".U && io.wbMEM_memread =/= 1.U &&
    
    ((io.idEX_REGRD =/= "b00000".U) && (io.idEX_REGRD === io.rs2_sel)) &&
    
    ((io.exMEM_RDREG =/= "b00000".U) && (io.exMEM_RDREG === io.rs2_sel)) &&
    (io.wbMEM_RDREG === io.rs2_sel))
    {

        io.forward_rs2 := "b0101".U

        // forwarding jalR
    
    }.elsewhen(io.cntrl_branch === 0.U){

        // ALU HAZARD
        when(io.idEX_REGRD =/= "b00000".U && io.iDEX_memread =/= 1.U && (io.idEX_REGRD === io.rs1_sel)){
            
            io.forward_rs1 := "b0110".U
        }
        
        // EX/MEM HAZARD
        when(io.exMEM_RDREG =/= "b00000".U && io.exMEM_memread =/= 1.U && 
        
        ((io.idEX_REGRD =/= "b00000".U) && (io.idEX_REGRD === io.rs1_sel )) &&
        
        (io.exMEM_RDREG === io.rs1_sel)){

            io.forward_rs1 := "b0111".U
        
        }.elsewhen(io.exMEM_RDREG =/= "b00000".U && io.exMEM_memread =/= 1.U && 
        
        ((io.idEX_REGRD =/= "b00000".U) && (io.idEX_REGRD === io.rs1_sel )) &&
        
        (io.exMEM_RDREG === io.rs1_sel)){

            io.forward_rs1 := "b1001".U
        
        }
    }

    // MEM/WB HAZARD cont...

    when(io.wbMEM_RDREG =/= "b00000".U && io.wbMEM_memread =/= 1.U &&
    
    ((io.idEX_REGRD =/= "b00000".U) && (io.idEX_REGRD === io.rs1_sel)) &&
    
    ((io.exMEM_RDREG =/= "b00000".U) && (io.exMEM_RDREG === io.rs1_sel)) && 
    (io.wbMEM_RDREG === io.rs1_sel))
    {

        io.forward_rs1 := "b1000".U 
    
    }.elsewhen(io.wbMEM_RDREG =/= "b00000".U && io.wbMEM_memread =/= 1.U &&
    
    ((io.idEX_REGRD =/= "b00000".U) && (io.idEX_REGRD === io.rs1_sel)) &&
    
    ((io.exMEM_RDREG =/= "b00000".U) && (io.exMEM_RDREG === io.rs1_sel)) && 
    (io.wbMEM_RDREG === io.rs1_sel))
    {

        io.forward_rs1 := "b1010".U

    }

}