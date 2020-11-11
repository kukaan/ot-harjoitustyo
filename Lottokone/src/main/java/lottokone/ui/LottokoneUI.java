
package lottokone.ui;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import lottokone.domain.LottokoneService;

public class LottokoneUI {
    private Scanner reader;
    private LottokoneService service;
    private Map<String, String> commands;

    public LottokoneUI(Scanner reader, LottokoneService service) {
        this.reader = reader;
        this.service = service;
        
        commands = new TreeMap<>();
        
        commands.put("quit", "exit the program");
        commands.put("draw", "draw the lucky numbers");
        
    }
    
    public void start() {
        System.out.println("Welcome to Lottokone!");
        printHelp();
        
        while (true) {
            System.out.print("\n> ");
            String input = reader.nextLine();
            if (!commands.containsKey(input)) {
                System.out.println("unknown command");
                printHelp();
            }
            
            if (input.equals("quit")) {
                break;
            } else if (input.equals("draw")) {
                draw();
            } else if (input.equals("")) {
                
            }
        }
    }

    private void printHelp() {
        StringBuilder help = new StringBuilder("\n");
        for (String s : commands.keySet()) {
            help.append(s).append("\t").append(commands.get(s)).append("\n");
        }
        System.out.println(help);
    }
    
    private void draw() {
        System.out.println(Arrays.toString(service.draw()));
    }
}
