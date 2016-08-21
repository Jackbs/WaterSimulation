package Simulation;


import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 * Created by Jack on 21-07-16.
 */
public class FluidBlock extends Block {
    private static final double kpaPerBlock = 9.807;
    private static final double Density = 1000.0;
    private static final double Gravity = 9.807;
    private static final double AtmosP = 0.0;

    private int id;
    private Block[] sideBlocks;
    public double[] sideFluidFlow = {0.0,0.0,0.0,0.0,0.0,0.0}; //Negitive for ourflow, positive for inflow

    private double MaxPressure;
    private double pressure;
    private double FillLevel;
    private double depth;

    private double TotalEvalue,GravEvalue,VelosEvalue;
    private Vector3D inflowvector;


    public FluidBlock(int id, Level currentLevel) {
        super(id,currentLevel);
        pressure = 0.0;
        sideBlocks = new Block[6];
        FillLevel = 1.0;
        TotalEvalue = 0.0;
        depth = 0.0;
    }

    public double getMaxPressure(){return MaxPressure;}

    public double getFillLevel(){
        return FillLevel;
    }

    public double getDepth(){
        return depth;
    }

    public double getTotalEvalue(){
        return TotalEvalue;
    }

    @Override
    public double getPressure(){
        return -1*pressure/1000;
    }

    public void setPressure(double p){
        pressure = p;
    }

    public void setFillLevel(double fl){
        FillLevel = fl;
    }


    @Override
    public boolean isSolid() {
        return false;
    }

    public double calcVelosity(double ExternalEval, int side){
        double value;
        value = Math.sqrt(2*((ExternalEval - pressure)/Density));
        //System.out.println("Calculating Velosity V(m/s),ExternalEval,EvalGrav,pressure ["+value+","+ExternalEval+","+GravEvalue+","+pressure+"]");
        return value;
    }

    public void calcPressureFromH(double ExternalEval, int side){
        pressure = (ExternalEval - calcEvalueGrav() - calcEvalueVelos(side));
    }

    public void calcPressure(double ExternalEval, int side){
        pressure = (ExternalEval - calcEvalueGrav() - calcEvalueVelos(side));
    }

    public double calcEvalueGrav(){
        if(FillLevel != 0){
            GravEvalue = Density*Gravity*(depth+FillLevel);
        }else{
            GravEvalue = 0.0;
        }
        return GravEvalue;
    }

    public double calcEvalueVelos(int side){
        if(sideFluidFlow[side] != 0.0){
            VelosEvalue = 0.5*Density*Math.pow(sideFluidFlow[side], 2);
        }else{
            VelosEvalue = 0.0;
        }
        return VelosEvalue;
    }

    public double calcEvalue(int side){ //side 0,1,2,3,4,5 = Above,Bellow,Up,Down,Left,Right
        TotalEvalue = pressure + calcEvalueGrav() + calcEvalueVelos(side);
        //System.out.println("Calculating Total E Value Total,Pressure,EvalGrav,EvalVel ["+TotalEvalue+","+pressure+","+GravEvalue+","+VelosEvalue+"]");
        return TotalEvalue;
    }

    //Set out flow velosity
    public void setOutflowVelosity(){
        updateSideBlocks();
        for(int i = 0;i<sideBlocks.length;i++){
            if(sideBlocks[i] != null) {
                if(!sideBlocks[i].isSolid()) {
                    calcEvalue(i);
                    if (sideBlocks[i].isFluid()){
                        sideFluidFlow[i] = calcVelosity(((FluidBlock) sideBlocks[i]).calcEvalue(getOppisateside(i)), i);
                    } else {
                        if(i == 0){
                            sideFluidFlow[i] = calcVelosity(AtmosP-Density*Gravity, i);
                        }else if(i == 1){
                            sideFluidFlow[i] = calcVelosity(AtmosP+Density*Gravity, i);
                        }else{
                            sideFluidFlow[i] = calcVelosity(AtmosP, i);
                        }
                        currentLevel.setBlock(sideBlocks[i].getBlkLoc(),new FluidBlock(5,currentLevel));
                        ((FluidBlock)(currentLevel.getBlock(sideBlocks[i].getBlkLoc()))).setInflowVelosity(sideFluidFlow[i]*-1,getOppisateside(i));
                        //System.out.println("Outflow,Side[" + sideFluidFlow[i] + "," + i + "]");
                    }


                    if (sideFluidFlow[i] != 0.0) {
                        System.out.println("Outflow,Side[" + sideFluidFlow[i] + "," + i + "]");
                    }
                }

            }

        }

    }

    public void setInflowVelosity(double Inflow, int side) {
        sideFluidFlow[side] = Inflow;
    }

    private void updateSideBlocks(){
        sideBlocks[0] = this.getBlockAbove();
        sideBlocks[1] = this.getBlockBellow();
        sideBlocks[2] = this.getBlockUp();
        sideBlocks[3] = this.getBlockDown();
        sideBlocks[4] = this.getBlockLeft();
        sideBlocks[5] = this.getBlockRight();
    }


    public double calcMaxPressure(){
        updateSideBlocks();

        double maxDeltaP = 0.0;
        for(int i = 0;i<sideBlocks.length;i++){
            if(sideBlocks[i] != null) {
                if (!sideBlocks[i].isSolid()) {
                    double currentBlkP = (getPressure() - (sideBlocks[i].getPressure()));
                    if(i == 0){ //block above
                        currentBlkP = currentBlkP-kpaPerBlock;
                        if(currentBlkP<0.01){
                            currentBlkP = 0.0;
                        }
                    }
                    if(i == 1){ //block bellow
                        currentBlkP = currentBlkP+kpaPerBlock;
                    }
                    if (maxDeltaP < currentBlkP) {
                        maxDeltaP = currentBlkP;
                    }
                }
            }
        }

        //System.out.println("Above,bellow,up,down,left,right["+Above+","+Bellow+","+Up+","+Down+","+Left+","+Right+",");

        MaxPressure = maxDeltaP;
        return maxDeltaP;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id;
        return result;
    }

    @Override
    public boolean isFluid() {
        return true;
    }

    public void setDepth(double depth) {
        this.depth = depth;
        calcEvalue(0);
    }

    public void FluidInfo(){
        System.out.format("Velosity[top,bottom,up,down,left,right] = [%.1f,%.1f,%.1f,%.1f,%.1f,%.1f] Eval:%.1f  Fill Level:%.1f Depth:%.1f ]",sideFluidFlow[0],sideFluidFlow[1],sideFluidFlow[2],sideFluidFlow[3],sideFluidFlow[4],sideFluidFlow[5],TotalEvalue,FillLevel,depth);
        System.out.println();
    }

    public int getOppisateside(int side){
        if(side == 0){
            return 1;
        }
        if(side == 1){
            return 0;
        }
        if(side == 2){
            return 3;
        }
        if(side == 3){
            return 2;
        }
        if(side == 4){
            return 5;
        }
        if(side == 5){
            return 4;
        }
        return -1;
    }


}
