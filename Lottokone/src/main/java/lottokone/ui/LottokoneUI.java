
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
    private Map<String, String> loggedInCommands;
    private Map<String, String> loggedOutCommands;

    public LottokoneUI(Scanner reader, LottokoneService service) {
        this.reader = reader;
        this.service = service;
        
        availableCommands = new TreeMap<>();
        loggedInCommands = new TreeMap<>();
        loggedOutCommands = new TreeMap<>();
        
        
        availableCommands.put("quit", "exit the program");
        availableCommands.put("help", "show available commands");
        availableCommands.put("draw", "draw the lucky numbers");
        
        loggedOutCommands.put("register", "create a new account");
        loggedOutCommands.put("login", "log in to an account");
        
        makeCommandsAvailable(loggedOutCommands);
        
        loggedInCommands.put("logout", "log out from your account");
        loggedInCommands.put("add", "pick and save numbers to your account");
        loggedInCommands.put("play", "play a round of lotto");
        loggedInCommands.put("autoplay", "play multiple rounds of lotto");
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
            } else if (input.equals("autoplay")) {
                autoplay();
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
        if (service.createUser(username)) {
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
    
    private void logout() {
        service.logout();
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
        List<Numbers> selectedTickets = selectTickets();
        
        int costs = service.buyTickets(selectedTickets.size());
        Numbers drawn = new Numbers(service.draw());
        
        System.out.println("The results are in.");
        System.out.println("The winning numbers are " + drawn);
        
        List<Integer> hitsOnTickets = service.countHitsOnTickets(drawn, selectedTickets);
        List<Integer> winnings = service.calculateWinnings(hitsOnTickets);
        int winSum = service.addWinnings(winnings);
        
        for (int i = 0; i < selectedTickets.size(); i++) {
            System.out.println(selectedTickets.get(i) + ": " + 
                    hitsOnTickets.get(i) + " matching numbers wins you " + 
                    (double) winnings.get(i) / 100 + "€");
        }
        
        System.out.print("You won ");
        printResults(winSum, costs);
    }
    
    private void printEnumeratedList(List list) {
        int i = 1;
        for (Object o : list) {
            System.out.println("[" + i++ + "]\t" + o);
        }
    }
    
    private List<Numbers> selectTickets() {
        System.out.println("[0]\t[all of the below]");
        printEnumeratedList(service.getLoggedUser().getNumbersList());
        System.out.println("Choose the saved numbers you want to play "
                + "separated by commas (,):");
        String ticketsInput = reader.nextLine();
        
        List<Numbers> selectedTickets = service.selectTickets(ticketsInput);
        if (selectedTickets == null) {
            System.out.println("Invalid input!");
            return null;
        }
        
        return selectedTickets;
    }
    
    public void autoplay() {
        System.out.println("How many rounds? (max 1000000)");
        String roundsInput = reader.nextLine();
        int rounds = service.validateIntegerInput(roundsInput, 1, 1000000);
        if (rounds == -1) {
            System.out.println("Invalid input!");
            return;
        }
        
        List<Numbers> selectedTickets = selectTickets();
        
        int costs = service.buyTickets(selectedTickets.size() * rounds);
        int winSum = 0;
        for (int i = 0; i < rounds; i++) {
            Numbers drawn = new Numbers(service.draw());
            List<Integer> hitsOnTickets = service.countHitsOnTickets(drawn, selectedTickets);
            List<Integer> winnings = service.calculateWinnings(hitsOnTickets);
            winSum += service.addWinnings(winnings);
        }
        
        System.out.print("In " + rounds + " rounds, you won ");
        printResults(winSum, costs);
    }

    private void printResults(int winSum, int costs) {
        System.out.println((double) winSum / 100 + 
                "€ and lost " + (double) costs / 100 + "€");
        System.out.println("In total, you have won " + 
                (double) service.getLoggedUser().getMoneyWon() / 100 + "€ and lost " + 
                (double) service.getLoggedUser().getMoneyLost() / 100 + "€");
    }
}
