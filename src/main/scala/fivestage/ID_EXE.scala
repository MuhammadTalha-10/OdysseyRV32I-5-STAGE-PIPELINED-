package fivestage
import chisel3._

class Instr_decode_exe extends Module{
    val io = IO(new Bundle{

        val memwrite_in = Input(UInt(1.W))
        val memwrite_out = Output(UInt(1.W))
        val memread_in = Input(UInt(1.W))
        val memread_out = Output(UInt(1.W))
        val memtoreg_in = Input(UInt(1.W))
        val memtoreg_out = Output(UInt(1.W))
        val operand_A_in = Input(SInt(1.W))
        val operand_A_out = Output(SInt(32.W))
        val operand_B_in = Input(SInt(1.W))
        val operand_B_out = Output(SInt(32.W))
        val rd_in = Input(UInt(5.W))
        val rd_out = Output(UInt(5.W))
        val aluCtrl_in = Input(UInt(3.W))
        val aluCtrl_out = Output(UInt(3.W))
        val regwrite_in = Input(UInt(1.W))
        val regwrite_out = Output(UInt(1.W))
        val rs1Instr_in = Input(UInt(5.W))
        val rs1Instr_out = Output(UInt(5.W))
        val rs2Instr_in = Input(UInt(5.W))
        val rs2Instr_out = Output(UInt(5.W))
        val operand_A_sel_in = Input(UInt(2.W))
        val operand_A_sel_out = Output(UInt(2.W))
        val operand_B_sel_in = Input(UInt(1.W))
        val operand_B_sel_out = Output(UInt(1.W))
        val pc_in = Input(UInt(32.W))
        val pc_out = Input(UInt(32.W))
        val pc4_in = Input(UInt(32.W))
        val pc4_out = Output(UInt(32.W))
        val brnch_en_in = Input(UInt(1.W))
        val brnch_en_out = Output(UInt(1.W))
        val func3_in = Input(UInt(3.W))
        val func3_out = Output(UInt(3.W))
        val func7_in = Input(UInt(1.W))
        val func7_out = Output(UInt(1.W))
        val pc_next_in = Input(UInt(2.W))
        val pc_next_out = Output(UInt(2.W))
        val imm_in = Input(SInt(32.W))
        val imm_out = Output(SInt(32.W)) 
    })

        val reg_memwrite = RegInit(0.U(1.W))
        val reg_memread = RegInit(0.U(1.W))
        val reg_memtoreg = RegInit(0.U(1.W))
        val reg_operand_A = RegInit(0.S(32.W))
	val reg_operand_B = RegInit(0.S(32.W))
	val reg_rd = RegInit(0.U(5.W))
	val reg_aluCtrl = RegInit(0.U(5.W))
	val reg_regwrite = RegInit(0.U(1.W))
	val reg_rs1Instr = RegInit(0.U(5.W))
	val reg_rs2Instr = RegInit(0.U(5.W))
	val reg_operand_A_sel = RegInit(0.U(2.W))
	val reg_operand_B_sel = RegInit(0.U(1.W))
	val reg_pc = RegInit(0.U(32.W))
	val reg_pc4 = RegInit(0.U(32.W))
        val reg_pcNext = RegInit(0.U(2.W))
        val reg_imm = RegInit(0.S(32.W))
        val reg_brnch_en = RegInit(0.U(1.W))
        val reg_func3 = RegInit(0.U(3.W))
        val reg_func7 = RegInit(0.U(1.W))

        

            //mem write
    reg_memwrite := io.memwrite_in
    io.memwrite_out := reg_memwrite
            //mem read
    reg_memread := io.memread_in
    io.memread_out := reg_memread
            // mem to reg
    reg_memtoreg := io.memtoreg_in
    io.memtoreg_out := reg_memtoreg
            // operand A
    reg_operand_A := io.operand_A_in
    io.operand_A_out := reg_operand_A
            // operand B
    reg_operand_B := io.operand_B_in
    io.operand_B_out := reg_operand_B
            // destination reg
    reg_rd := io.rd_in
    io.rd_out := reg_rd
            // Alu control
    reg_aluCtrl := io.aluCtrl_in
    io.aluCtrl_out := reg_aluCtrl
            
    reg_regwrite := io.regwrite_in
    io.regwrite_out := reg_regwrite
    reg_rs1Instr := io.rs1Instr_in
    io.rs1Instr_out := reg_rs1Instr
    reg_rs2Instr := io.rs2Instr_in
    io.rs2Instr_out := reg_rs2Instr
    reg_operand_A_sel := io.operand_A_sel_in
    io.operand_A_sel_out := reg_operand_A_sel
    reg_operand_B_sel := io.operand_B_sel_in
    io.operand_B_sel_out := reg_operand_B_sel
    reg_brnch_en := io.brnch_en_in
    io.brnch_en_out := reg_brnch_en
    reg_pc := io.pc_in
    io.pc_out := reg_pc
    reg_pc4 := io.pc4_in
    io.pc4_out := reg_pc4
    reg_pcNext := io.pc_next_in
    io.pc_next_out := reg_pcNext
    reg_func3 := io.func3_in
    io.func3_out := reg_func3
    reg_func7 := io.func7_in
    io.func7_out := reg_func7
    reg_imm := io.imm_in
    io.imm_out := reg_imm

}
