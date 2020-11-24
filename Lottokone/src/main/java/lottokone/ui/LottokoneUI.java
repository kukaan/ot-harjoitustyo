
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
    private Map<String, String> unavailableCommands;

    public LottokoneUI(Scanner reader, LottokoneService service) {
        this.reader = reader;
        this.service = service;
        
        commands = new TreeMap<>();
        unavailableCommands = new TreeMap<>();
        
        commands.put("quit", "exit the program");
        commands.put("help", "show available commands");
        commands.put("draw", "draw the lucky numbers");
        commands.put("register", "create a new account");
        commands.put("login", "log in to an account");
        
        unavailableCommands.put("logout", "log out from your account");
    }
    
    public void start() {
        System.out.println("Welcome to Lottokone!");
        printHelp();
        
        while (true) {
            System.out.print("\n");
            String input = reader.nextLine();
            if (!commands.containsKey(input)) {
                System.out.println("Unknown or unavailable command");
                printHelp();
                continue;
            }
            
            if (input.equals("quit")) {
                break;
            } else if (input.equals("help")) {
                printHelp();
            } else if (input.equals("draw")) {
                draw();
            } else if (input.equals("register")) {
                register();
            } else if (input.equals("login")) {
                login();
            } else if (input.equals("logout")) {
                logout();
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
    
    private void register() {
        System.out.println("Enter a new username:");
        String username = reader.nextLine();
        if (service.create(username)) {
            System.out.println("Registration successful");
        } else {
            System.out.println("Registration failed");
        }
    }
    
    private void login() {
        System.out.println("Enter your username:");
        String username = reader.nextLine();
        if (service.login(username)) {
            System.out.println("Login successful");
            makeCommandUnavailable("register");
            makeCommandUnavailable("login");
            makeCommandAvailable("logout");
            printHelp();
        } else {
            System.out.println("Login failed");
        }
    }
    
    private void makeCommandUnavailable(String cmd) {
        unavailableCommands.put(cmd, commands.remove(cmd));
    }
    
    private void makeCommandAvailable(String cmd) {
        commands.put(cmd, unavailableCommands.remove(cmd));
    }
    
    private void logout() {
        service.logout();
        makeCommandAvailable("register");
        makeCommandAvailable("login");
        makeCommandUnavailable("logout");
        System.out.println("Logged out");
        printHelp();
    }
}
