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
import projectai.StateThinkingLess;
/**
 *
 * @author ngohoa
 */
public class PacmanClassicQleaningLessState extends Agent{
    int soBuocDi;
    int soVan;
    float alpha;        
    float gamma;  
    float reward;
    int numOfRun;
    float valueOfOldState;
    ArrayList<StateThinkingLess> pacState;
    StateThinkingLess currentState;
    StateThinkingLess oldState;
    int[] oldDirect;
    public PacmanClassicQleaningLessState(int index, float leaningRate,float discountFactor,int numofrun) {
        super(index);
        soBuocDi=0;soVan=0;
        this.numOfRun=numofrun;
        pacState =new ArrayList<>();
        currentState=new StateThinkingLess();
        oldState=null;
        oldDirect=null;
        this.alpha=leaningRate;
        this.gamma=discountFactor;
    }
    @Override   
    int[] getAction(GameState state) {
        System.out.println("nuoc di thu: "+soBuocDi+"so van"+soVan);
        soBuocDi++;
        boolean haveNewState=true;
        int[] curentAction = null;
        
        currentState.takeInfo(state);
        // kiem tra xem currentState da co trong pacState hay chua
        for(StateThinkingLess s : pacState){
            if(s.isSame(currentState)==true){
                oldState.fixNextStateofDirectMove(oldDirect, s);
                currentState=s;
                haveNewState=false;
                //System.out.println("hit");
                continue;
                }
        }
        if(haveNewState==true){
            //System.out.println("nothit");
            StateThinkingLess nextState = new StateThinkingLess();
            ArrayList legalAction =state.getLegalAction(0);
            Random rn = new Random();
            int m= rn.nextInt(legalAction.size());
            curentAction=(int[]) legalAction.get(m);
            nextState= currentState.takeAction(currentState, curentAction, pacState);
            reward=getreward(evaluation(state),valueOfOldState);
            valueOfOldState=evaluation(state);
            if(oldState==null){
            pacState.add(currentState);
            oldDirect=curentAction;
            oldState=currentState;
            currentState=nextState;
            return curentAction;
            }
            currentState.Qvalue=currentState.Qvalue + alpha*(reward+gamma*nextState.finMaxQ()-currentState.Qvalue);
            pacState.add(currentState);
            oldDirect=curentAction;
            oldState=currentState;
            currentState=nextState;
            return curentAction;
        }else{
            //System.out.println("la "+pacState.indexOf(currentState));
            curentAction=currentState.finMaxAC(state,pacState);
            StateThinkingLess nextState= currentState.takeAction(currentState, curentAction, pacState);
            reward=getreward(evaluation(state),valueOfOldState);
            valueOfOldState=evaluation(state);
            oldState.Qvalue=oldState.Qvalue + alpha*(reward+gamma*currentState.finMaxQ()-oldState.Qvalue);
            oldDirect=curentAction;
            oldState=currentState;
            currentState=nextState;
            return curentAction;
        }
        
        
    }
    public float evaluation(GameState state){
        return state.getScore();
    }
    private float getreward(float evaluation, float valueOfOldState) {
        return evaluation-valueOfOldState;
    }
    private double approximationevalua(GameState state) {
        double evalScore = 0;
        AgentState pacmanState = state.getPacmanState();
        evalScore += state.getGhostStates().stream().map((ghostState)
                -> Util.nearGhostEvaluate(pacmanState, ghostState))
                .reduce(evalScore, (accumulator, _item) -> accumulator + _item);
        evalScore += Util.foodRemainEvaluate(state);
        evalScore += Util.capsuleRemainPunish(state);
        evalScore += Util.nearestCapuleEvaluate(state);
        evalScore += Util.nearestFoodEvaluate(state);
        evalScore += 2 * state.gameStateData.score;
        return 0;
    }
}