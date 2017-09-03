/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectai;


import java.util.*;
import static projectai.Util.manhattanDistance;
import projectai.GameState;
import java.util.Random;
import projectai.StateThinking;
import projectai.Main;
/**
 *
 * @author ngohoa
 */
public class PacmanClassicQleaning extends Agent{
    int soBuocDi;
    int soVan;
    float alpha;        
    float gamma;  
    int numOfRun;
    float reward;
    float valueOfOldState;
    ArrayList<StateThinking> pacState;
    StateThinking currentState;
    StateThinking oldState;
    public PacmanClassicQleaning(int index, float leaningRate,float discountFactor,int numofrun) {
        super(index);
        this.numOfRun=numofrun;
        soBuocDi=0;soVan=0;
        pacState =new ArrayList<>();
        currentState=new StateThinking();
        oldState=null;
        this.alpha=leaningRate;
        this.gamma=discountFactor;
    }
    
    @Override   
    int[] getAction(GameState state) {
        System.out.println("nuoc di thu: "+soBuocDi+"so van"+soVan);
        soBuocDi++;
        boolean haveNewState=true;
        int[] curentAction = null;
        if((soBuocDi==1)&&(soVan!=1)){currentState=pacState.get(0);}
        if(currentState.move.size()==0){
             //System.out.println("chon null");
        ArrayList legalAction =state.getLegalAction(0);
        Random rn = new Random();
        int m= rn.nextInt(legalAction.size());
        curentAction=(int[]) legalAction.get(m);
        }
        else{
        curentAction=currentState.finMaxAC(state,pacState);       
        haveNewState=false;
        }
        if(oldState==null){
            StateThinking nexState= currentState.takeAction(currentState, curentAction, pacState);
            if(haveNewState==true)pacState.add(currentState);
            oldState=currentState;
            currentState=nexState;  
            return curentAction;
        }
        
        reward=getreward(evaluation(state),valueOfOldState);
        valueOfOldState=evaluation(state);
        StateThinking nexState= currentState.takeAction(currentState, curentAction, pacState);
        //System.out.println(" so buoc kham pha " +currentState.move.size() );
        //System.out.println("reward"+ reward+"alpha "+alpha+" gamma "+gamma +" curQvalue"+ currentState.Qvalue);
        //System.out.println("reward"+ reward);
        oldState.Qvalue=oldState.Qvalue + alpha*(reward+gamma*currentState.finMaxQ()-oldState.Qvalue);
        //System.out.println("tinh ra"+currentState.Qvalue);
        if(haveNewState==true)pacState.add(currentState);
        
        //System.out.println("trang thai dau tien:"+pacState.get(0).Qvalue+"");
        oldState=currentState;
        currentState=nexState;        
        return curentAction;
    }
    public float evaluation(GameState state){
        return state.getScore();
    }
    private float getreward(float evaluation, float valueOfOldState) {
        return evaluation-valueOfOldState;
    }  
}
