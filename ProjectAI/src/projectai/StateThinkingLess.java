/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectai;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author ngohoa
 */
public class StateThinkingLess {
    public float Qvalue;
    public ArrayList<Action> move;
    
    public Position pacPosition;
    public Position nearestBean;
    public int numberBeans;
    public boolean isDangerousIn5;
    public Position ghostAngry1;
    public Position ghostAngry2;
    public Position ghostScare1;
    public Position ghostScare2;
    public StateThinkingLess() {
        Qvalue=0;
        move=new ArrayList();
    }
    public void takeInfo(GameState state) {
        this.pacPosition=state.getPacmanPosition();   
        this.numberBeans=state.getNumFood();
        this.nearestBean=null;
        float nearestFoodDistance=9999;
        for (Position foodPos : state.gameStateData.food) {
                float kc = AstarSearch.AstarFind(state.getPacmanPosition(),foodPos,state);
                if(nearestFoodDistance > kc){                  
                    nearestFoodDistance= kc; 
                    this.nearestBean=foodPos;
                }
            }
        if(state.getGhostState(1).scaredTimer>0) {ghostAngry1=new Position(-1, -1);ghostScare1=state.getGhostPosition(1);}
        else {ghostAngry1=state.getGhostPosition(1);ghostScare1=new Position(-1, -1);}
        if(state.getGhostState(2).scaredTimer>0) {ghostAngry2=new Position(-1, -1);ghostScare2=state.getGhostPosition(2);}
        else {ghostAngry2=state.getGhostPosition(2);ghostScare2=new Position(-1, -1);}
        if(ghostAngry1.x==-1&&ghostAngry1.y==-1&&ghostAngry2.x==-1&&ghostAngry2.y==-1) isDangerousIn5=false;
        else {
            float ghost1Distance =9999;float ghost2Distance=9999;
            if(ghostAngry1.x!=-1)ghost1Distance = AstarSearch.AstarFind(pacPosition,ghostAngry1, state);
            if(ghostAngry2.y!=-1)ghost2Distance = AstarSearch.AstarFind(pacPosition,ghostAngry2, state);
            if(ghost1Distance>5&&ghost2Distance>5) this.isDangerousIn5= false;
            else this.isDangerousIn5=true;
        }
    }

    boolean isSame(StateThinkingLess currentState) {
        if(pacPosition.x==currentState.pacPosition.x&&pacPosition.y==currentState.pacPosition.y){
            if(numberBeans==currentState.numberBeans){
                if(nearestBean.x==currentState.nearestBean.x&&nearestBean.y==currentState.nearestBean.y){
                     if(isDangerousIn5==currentState.isDangerousIn5){
                            if(ghostAngry1.x==currentState.ghostAngry1.x&&currentState.ghostAngry1.y==ghostAngry1.y){
                                if(ghostAngry2.x==currentState.ghostAngry2.x&&currentState.ghostAngry2.y==ghostAngry2.y){
                                    if(ghostScare1.x==currentState.ghostScare1.x&&currentState.ghostScare1.y==ghostScare1.y){
                                        if(ghostScare2.x==currentState.ghostScare2.x&&currentState.ghostScare2.y==ghostScare2.y)
                                            return true;
                                    }
                                }
                            }     
                        }
                }
            }
        }
        return false;
            
    }


    
    public class Action{
        int[] detail;
        StateThinkingLess nextState;    
        public Action(int[] direction,StateThinkingLess nextState){
            this.detail=direction;
            this.nextState=nextState;
        }
    }
    public void addMoveFromDirect_No_check(int[] direction,StateThinkingLess nextState){
        Action a = new Action(direction,nextState );
        move.add(a);
    }
    public void fixNextStateofDirectMove(int[] dirrectneedfix,StateThinkingLess trueNextState){
        for(Action t: this.move){
            if(dirrectneedfix[0]==t.detail[0]&&dirrectneedfix[1]==t.detail[1]) 
                t.nextState=trueNextState;
            continue;
        }
    }
    int[] finMaxAC(GameState state, ArrayList<StateThinkingLess> pacState) {
        Action max;   
        Random rn = new Random();
        if(state.getLegalAction(0).size()>move.size()){
            int size= move.size();
            for(Object a: state.getLegalAction(0) ){
                int[]ac=(int[])a;
                //System.out.println("hanh dong dinh them la"+ac[0]+" "+ac[1]);
                //System.out.println("hanh dong dang co trong move"+ move.get(0).detail[0]+" "+ move.get(0).detail[1]);
                for(int i=0;i<size;i++){
                    Action m=move.get(i);
                    if(ac[0]==m.detail[0]&&ac[1]==m.detail[1]) continue;
                    else {
                        takeAction(this, ac, pacState);                       
                        //System.out.println("them");
                    }
                }
                
            }
        }
        int m= rn.nextInt(move.size())+1;
        //System.out.println("co la"+move.size());
        max=move.get(m-1);
        for(Action a : move){
        if(a.nextState.Qvalue>max.nextState.Qvalue) max=a;
        }
        return max.detail;
    }
    
    float finMaxQ() {
        if(move.size()==0)return 0;
        float maxQ=move.get(0).nextState.Qvalue;
        for(Action a : move){
            float QifDoA= a.nextState.Qvalue;
            if(QifDoA>maxQ) maxQ=QifDoA;
        }
        return maxQ;
    }
    // thuc hien hanh dong. tra ve trang thai tiep theo khi thuc hien. can kiem tra xem trang thai moi tao ra da co trong pacstate chua. neu co thi tro den no
    public StateThinkingLess takeAction(StateThinkingLess current,int[] direction, ArrayList<StateThinkingLess> pacState){
        int had=0;
        Action hadAction=null;
        if(current.move==null){
        StateThinkingLess newstate= new StateThinkingLess();
        Action newAction = new Action(direction, newstate);
        current.move.add(newAction);
        return newstate;
        }else{
        for(Action a : move){
        if(direction[0]==a.detail[0]&&direction[1]==a.detail[1]) {
            had=1; hadAction=a;
        }
        }
        if (had==0){
            StateThinkingLess newstate= new StateThinkingLess();
            Action newAction = new Action(direction, newstate);
            newAction.nextState=newstate;
            current.move.add(newAction);
            return newstate;
        }
        else  return hadAction.nextState;
        }
    }
    
}

