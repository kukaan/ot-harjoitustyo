package lottokone;

import java.util.Random;
import java.util.Scanner;
import lottokone.domain.LottokoneService;
import lottokone.ui.LottokoneUI;

public class Main {
    
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        Random random = new Random();
        LottokoneService service = new LottokoneService(random);
        
        LottokoneUI ui = new LottokoneUI(reader, service);
        ui.start();
    }
}
