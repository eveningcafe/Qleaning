/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectai;
import java.util.*;
import projectai.GameState;
/**
 *
 * @author ngohoa
 */
class StateThinking {
    public float Qvalue;
    public ArrayList<Action> move;
    
    public StateThinking() {
        Qvalue=0;
        move=new ArrayList();
    }
    public class Action{
        int[] detail;
        StateThinking nextState;    
        public Action(int[] direction,StateThinking nextState){
            this.detail=direction;
            this.nextState=nextState;
        }
    }
    int[] finMaxAC(GameState state, ArrayList<StateThinking> pacState) {
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
    public StateThinking takeAction(StateThinking current,int[] direction, ArrayList<StateThinking> pacState){
        int had=0;
        Action hadAction=null;
        if(current.move==null){
        StateThinking newstate= new StateThinking();
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
            StateThinking newstate= new StateThinking();
            Action newAction = new Action(direction, newstate);
            newAction.nextState=newstate;
            current.move.add(newAction);
            return newstate;
        }
        else  return hadAction.nextState;
        }
    }
    
}
