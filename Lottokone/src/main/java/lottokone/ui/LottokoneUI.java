
package lottokone.ui;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import lottokone.domain.LottokoneService;
import lottokone.domain.Numbers;

public class LottokoneUI {
    private Scanner reader;
    private LottokoneService service;
    private Map<String, String> availableCommands;
//    private Map<String, String> unavailableCommands;
    private Map<String, String> loggedInCommands;
    private Map<String, String> loggedOutCommands;

    public LottokoneUI(Scanner reader, LottokoneService service) {
        this.reader = reader;
        this.service = service;
        
        availableCommands = new TreeMap<>();
//        unavailableCommands = new TreeMap<>();
        loggedInCommands = new TreeMap<>();
        loggedOutCommands = new TreeMap<>();
        
        
        availableCommands.put("quit", "exit the program");
        availableCommands.put("help", "show available availableCommands");
        availableCommands.put("draw", "draw the lucky numbers");
        
        loggedOutCommands.put("register", "create a new account");
        loggedOutCommands.put("login", "log in to an account");
        
        makeCommandsAvailable(loggedOutCommands);
        
        loggedInCommands.put("logout", "log out from your account");
        loggedInCommands.put("add", "pick and save numbers to your account");
        loggedInCommands.put("play", "play a round of lotto");
    }
    
    public void start() {
        System.out.println("Welcome to Lottokone!");
        printAvailableCommands();
        
        while (true) {
            System.out.print("\n");
            String input = reader.nextLine();
            if (!availableCommands.containsKey(input)) {
                System.out.println("Unknown or unavailable command");
                printAvailableCommands();
                continue;
            }
            
            if (input.equals("quit")) {
                break;
            } else if (input.equals("help")) {
                printAvailableCommands();
            } else if (input.equals("draw")) {
                draw();
            } else if (input.equals("register")) {
                register();
            } else if (input.equals("login")) {
                login();
            } else if (input.equals("logout")) {
                logout();
            } else if (input.equals("add")) {
                add();
            } else if (input.equals("play")) {
                play();
            } 
            
        }
    }

    private void printAvailableCommands() {
        StringBuilder help = new StringBuilder("\n");
        for (String s : availableCommands.keySet()) {
            help.append(s).append("\t").append(availableCommands.get(s)).append("\n");
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
//            makeCommandUnavailable("register");
//            makeCommandUnavailable("login");
//            makeCommandAvailable("logout");
//            makeCommandAvailable("add");
            makeCommandsAvailable(loggedInCommands);
            makeCommandsUnavailable(loggedOutCommands);
            printAvailableCommands();
        } else {
            System.out.println("Login failed");
        }
    }

    private void makeCommandsUnavailable(Map<String, String> commands) {
        for (String s : commands.keySet()) {
            this.availableCommands.remove(s);
        }
    }
    
    private void makeCommandsAvailable(Map<String, String> commands) {
        availableCommands.putAll(commands);
    }
    
//    private void makeCommandUnavailable(String cmd) {
//        unavailableCommands.put(cmd, availableCommands.remove(cmd));
//    }
//    
//    private void makeCommandAvailable(String cmd) {
//        availableCommands.put(cmd, unavailableCommands.remove(cmd));
//    }
    
    private void logout() {
        service.logout();
//        makeCommandAvailable("register");
//        makeCommandAvailable("login");
//        makeCommandUnavailable("logout");
        makeCommandsAvailable(loggedOutCommands);
        makeCommandsUnavailable(loggedInCommands);
        System.out.println("Logged out");
        printAvailableCommands();
    }

    private void add() {
        String help = "Enter " + service.getDrawSize() + " numbers of your "
                + "choice from a range of 1-" + service.getRange() 
                + " separated by commas (,). Empty input stops.";
        System.out.println(help);
        while (true) {
            String input = reader.nextLine();
            if (input.isEmpty()) {
                break;
            }
            if (service.add(input)) {
                System.out.println("Numbers added.");
            } else {
                System.out.println("Failed to add numbers. Check your input.");
                System.out.println(help);
            }
        }
        System.out.println("Numbers currently on your account:");
        service.getLoggedUser().getNumbersList().stream().forEach(numbers -> {
            System.out.println(numbers);});
        printAvailableCommands();
    }
    
    private void play() {
        System.out.println("[0]\t[all of the below]");
        printEnumeratedList(service.getLoggedUser().getNumbersList());
        System.out.println("Choose the saved numbers you want to play "
                + "separated by commas (,):");
        String ticketsInput = reader.nextLine();
        
        List<Numbers> selectedTickets = service.selectTickets(ticketsInput);
        if (selectedTickets == null) {
            System.out.println("Invalid input!");
            return;
        }
        
        int costs = service.buyTickets(selectedTickets.size());
        Numbers drawn = new Numbers(service.draw());
        
        System.out.println("The results have come in.");
        // todo: add dramatic effect by printing numbers one at a time with delay
        System.out.println("The winning numbers are " + drawn);
        
        List<Integer> hitsOnTickets = service.countHitsOnTickets(drawn, selectedTickets);
        List<Integer> winnings = service.calculateWinnings(hitsOnTickets);
        
        for (int i = 0; i < selectedTickets.size(); i++) {
            System.out.println(selectedTickets.get(i) + ": " + 
                    hitsOnTickets.get(i) + " matching numbers wins you " + 
                    (double) winnings.get(i) / 100 + "€");
        }
        
        int winSum = service.addWinnings(winnings);
        System.out.println("You won " + (double) winSum / 100 + 
                "€ and lost " + (double) costs / 100 + "€");
        System.out.println("In total, you have won " + 
                (double) service.getLoggedUser().getWinningsSum() / 100 + "€ and lost " + 
                (double) service.getLoggedUser().getLossesSum() / 100 + "€");
    }
    
    private void printEnumeratedList(List list) {
        int i = 1;
        for (Object o : list) {
            System.out.println("[" + i++ + "]\t" + o);
        }
    }
    
}
