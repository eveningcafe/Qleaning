/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectai;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 *
 */
public class Main extends JFrame{

    private static int ReadConfigFilefindTypePac() {
        boolean isLessStatePac= false;
        try {
            String input = "src\\Experiment\\config.txt";
            FileInputStream fis = new FileInputStream(new File(input));
            try (BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line != null && !line.isEmpty()) {
                        System.out.println(line);
                        String []strArray=line.split(" ");
                        System.out.println(Integer.parseInt(strArray[11]));
                        if(Integer.parseInt(strArray[11])==1) isLessStatePac=true; 
                    }
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
       
        if(isLessStatePac==true) return 1;
        else       return 0;
        
    }

   
    
    public void initUI(GraphicDisplay display) {

        add(display);      
        setBackground(Color.red);
        setTitle("Points");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) throws InterruptedException {
        
         
        
   
        
        PrintStream out= null;
        try {
            out = new PrintStream(new FileOutputStream("src\\Experiment\\result.txt"));
            if(ReadConfigFilefindTypePac()==0){
            PacmanClassicQleaning pacman= new PacmanClassicQleaning(0,(float)0.9,(float)0.5,1);
            ReadConfigFileToPac( pacman);
            GhostAlphaBetaPrunningAgent ghost1 = new GhostAlphaBetaPrunningAgent(1, 2, 1);
            GhostAlphaBetaPrunningAgent ghost2 = new GhostAlphaBetaPrunningAgent(2, 2, 1);
            ClassicGameRules newGame = new ClassicGameRules(150, false);
            ArrayList<Agent> ghostAgents = new ArrayList();
            ghostAgents.add(ghost1);
            ghostAgents.add(ghost2);
            GameState initState = new GameState(null);
            GraphicDisplay display = new GraphicDisplay(initState);
            Main m = new Main();
            m.initUI(display);
            m.setVisible(true);
            int run = 0;
            pacman.soVan=0;
            while (run++ < pacman.numOfRun) {   
                System.out.println("run " + run);
                out.print("run " + run+": ");
                Game game = newGame.newGame(initState, display, ghostAgents,
                        pacman, true);
                pacman.soBuocDi=0;
                pacman.soVan++;
                game.run(out);
                //for(int o=0;o<pacman.pacState.size();o++)
                //    System.out.println(""+pacman.pacState.get(o).Qvalue);
                
            }
            }else{
            PacmanClassicQleaningLessState pacman = new PacmanClassicQleaningLessState(0,(float)0.9,(float)0.5,1);
            ReadConfigFileToPac( pacman); 
            GhostAlphaBetaPrunningAgent ghost1 = new GhostAlphaBetaPrunningAgent(1, 2, 1);
            GhostAlphaBetaPrunningAgent ghost2 = new GhostAlphaBetaPrunningAgent(2, 2, 1);
            ClassicGameRules newGame = new ClassicGameRules(150, false);
            ArrayList<Agent> ghostAgents = new ArrayList();
            ghostAgents.add(ghost1);
            ghostAgents.add(ghost2);
            GameState initState = new GameState(null);
            GraphicDisplay display = new GraphicDisplay(initState);
            Main m = new Main();
            m.initUI(display);
            m.setVisible(true);
            int run = 0;
            pacman.soVan=0;
            while (run++ < pacman.numOfRun) {   
                System.out.println("run " + run);
                out.print("run " + run+": ");
                Game game = newGame.newGame(initState, display, ghostAgents,
                        pacman, true);
                pacman.soBuocDi=0;
                pacman.soVan++;
                game.run(out);
                //for(int o=0;o<pacman.pacState.size();o++)
                //    System.out.println(""+pacman.pacState.get(o).Qvalue);
                
            }
            };
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();
        }
    }
    public static void ReadConfigFileToPac(PacmanClassicQleaningLessState pacman) {
        boolean isLessStatePac= false;
        try {
            String input = "src\\Experiment\\config.txt";
            FileInputStream fis = new FileInputStream(new File(input));
            try (BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line != null && !line.isEmpty()) {
                        System.out.println(line);
                        String []strArray=line.split(" ");
                        pacman.alpha=Float.parseFloat(strArray[2]); pacman.gamma= Float.parseFloat(strArray[5]); pacman.numOfRun=Integer.parseInt(strArray[9]);                  
                    }
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    public static void ReadConfigFileToPac(PacmanClassicQleaning pacman){
        boolean isLessStatePac= false;
        try {
            String input = "src\\Experiment\\config.txt";
            FileInputStream fis = new FileInputStream(new File(input));
            try (BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line != null && !line.isEmpty()) {
                        System.out.println(line);
                        String []strArray=line.split(" ");
                        pacman.alpha=Float.parseFloat(strArray[2]); pacman.gamma= Float.parseFloat(strArray[5]); pacman.numOfRun=Integer.parseInt(strArray[9]);                  
                        
                    }
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
     
        }
}

/*public class Main extends JFrame {

    public void initUI(GraphicDisplay display) {
        add(display);
        setBackground(Color.red);
        setTitle("Points");
        setSize(670, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) throws InterruptedException {
        int typePac = 0;
        int depth = 1;
        int numGhost = 3;
        int evalFunc = 0;
        int iter = 0;
        CommandLine commandLine;
        Option option_a = Option.builder("albe").argName("depth").hasArg()
                .desc("Pacman cat tia alpha - beta").build();
        Option option_b = Option.builder("expect").argName("depth").hasArg()
                .desc("Pacman expectiminimax").build();
        Option option_c = Option.builder("repeat").argName("number of iteration").hasArg()
                .desc("So lan lap lai").build();
        Option option_d = Option.builder("eval").argName("evaluate function 0 or 1").hasArg()
                .desc("Ham danh gia cua Ghost").build();
        Option option_e = Option.builder("numGhost").argName("number of ghost").hasArg()
                .desc("So luong ghost").build();
        Options options = new Options();
        CommandLineParser parser = new DefaultParser();

        options.addOption(option_a);
        options.addOption(option_b);
        options.addOption(option_c);
        options.addOption(option_d);
        options.addOption(option_e);

        String header = " Lua chon ";
        String footer = " AI project: Game Pacman";
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("Pacman", header, options, footer, true);

        String[] _args = {"-albe", "3",
            "-repeat", "10", "-eval", "0", "-numGhost", "3",};

        try {
            commandLine = parser.parse(options, args);

            if (commandLine.hasOption("albe")) {
                typePac = 1;
                if (commandLine.getOptionValue("albe") != null) {
                    depth = Math.max(1, Integer.parseInt(commandLine.getOptionValue("albe")));
                }
            }

            if (commandLine.hasOption("expect")) {
                typePac = 2;
                if (commandLine.getOptionValue("expect") != null) {
                    depth = Math.max(1, Integer.parseInt(commandLine.getOptionValue("expect")));
                }
            }

            if (commandLine.hasOption("repeat")) {
                if (commandLine.getOptionValue("repeat") != null) {
                    iter = Integer.parseInt(commandLine.getOptionValue("repeat"));
                }
            }

            if (commandLine.hasOption("eval")) {
                if (commandLine.getOptionValue("eval") != null) {
                    evalFunc = Integer.parseInt(commandLine.getOptionValue("eval"));
                }
            }

            if (commandLine.hasOption("numGhost")) {
                if (commandLine.getOptionValue("numGhost") != null) {
                    numGhost = Integer.parseInt(commandLine.getOptionValue("numGhost"));
                }
            }

        } catch (ParseException exception) {
            System.out.print("Parse error: ");
            System.out.println(exception.getMessage());
        }
        
        ClassicGameRules newGame = new ClassicGameRules(180, false);
        ArrayList<Agent> ghostAgents = new ArrayList();
        GameState initState = new GameState(null);
        GraphicDisplay display = new GraphicDisplay(initState);
        Main m = new Main();
        

        if (evalFunc > 1 || evalFunc < 0) {
            evalFunc = 0;
        }
        if (numGhost < 1 || numGhost > 3) {
            GhostAlphaBetaPrunningAgent ghost2 = new GhostAlphaBetaPrunningAgent(2, 1, evalFunc); // index 2, độ sâu tìm kiếm 1
            GhostAlphaBetaPrunningAgent ghost1 = new GhostAlphaBetaPrunningAgent(3, 2, evalFunc);
            RandomAgent ghost3 = new RandomAgent(3);
            ghostAgents.add(ghost1);
            ghostAgents.add(ghost2);
            ghostAgents.add(ghost3);
        } else {
            for (int j = 1; j < numGhost; j++) {
                GhostAlphaBetaPrunningAgent ghostT = new GhostAlphaBetaPrunningAgent(j, j, evalFunc);
                ghostAgents.add(ghostT);
            }
            RandomAgent ghostR = new RandomAgent(numGhost);
            ghostAgents.add(ghostR);
        }
        iter = Math.max(1, iter);
        int run = 0;
        switch (typePac) {
            case 1: {
                PacmanAlphaBetaPrunningAgent pacman = new PacmanAlphaBetaPrunningAgent(0, depth);
                m.initUI(display);
                m.setVisible(true);
                while (run++ < iter) {
                    Game game = newGame.newGame(initState, display, ghostAgents,
                            pacman, true);
                    game.run();

                    pacman.score += game.state.gameStateData.score;
                    pacman.maxTime += game.maxTime;
                    pacman.avgTime += (game.mTime / game.run);
                }
                System.out.println("Max Time " + pacman.maxTime / (run - 1));
                System.out.println("AVG Time " + pacman.avgTime / (run - 1));
                System.out.println("AVG score = " + (pacman.score / (run - 1)));

                break;
            }

            case 2: {
                PacmanExpectiMiniMaxAgent pacman = new PacmanExpectiMiniMaxAgent(0, depth);
                m.initUI(display);
                m.setVisible(true);
                while (run++ < iter) {
                    Game game = newGame.newGame(initState, display, ghostAgents,
                            pacman, true);
                    game.run();
                    pacman.score += game.state.gameStateData.score;
                    pacman.maxTime += game.maxTime;
                    pacman.avgTime += (game.mTime / game.run);
                }
                System.out.println("Max Time " + pacman.maxTime / (run - 1));
                System.out.println("AVG Time " + pacman.avgTime / (run - 1));
                System.out.println("AVG score = " + (pacman.score / (run - 1)));

                break;
            }
            default: {
                KeyboardAgent pacman = new KeyboardAgent(0);
                display.addKeyListener(pacman);
                display.setFocusable(true);
                m.initUI(display);
                m.setVisible(true);
                while (run++ < iter) {
                    Game game = newGame.newGame(initState, display, ghostAgents,
                            pacman, true);
                    game.run();
                }
                break;
            }
        }
    Thread.sleep(3000);
    System.exit(0);
    }
}
*/