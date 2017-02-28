import java.util.*; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;   

public class Provider {
    String name;
    ArrayList<Tournament> tournamentList = new ArrayList<Tournament>();

    public Provider(String n) {
        name = n;
        // tournamentList = null;
    }
    public void go() {
        System.out.println("Provider: " + name);
        System.out.println(tournamentList);
        // for (Tournament trn: tournamentList) {
        //     String n = trn.getName();
        //     int t = trn.getNumTeams();
        //     System.out.println(n + " " + t);
        // }
        tournamentList.forEach(trn -> {
            System.out.println(trn.getName() + " " + trn.getNumTeams());
            trn.listTeams();
        });
    }

    public void createTournament(String n, int t) {
        Tournament trn = new Tournament(n, t);
        tournamentList.add(trn);
        
    }
}

class ProviderTest {
    public static void main(String[] args) {
        Provider test = new Provider("ogacon");
        test.createTournament("IEM", 8);
        test.createTournament("Worlds", 16);
        test.tournamentList.get(0).addTeam("TSM");
        test.tournamentList.get(0).addTeam("CLG");
        test.tournamentList.get(1).addTeam("C9");
        test.tournamentList.get(1).addTeam("Echo Fox");
        test.go();
    }
}