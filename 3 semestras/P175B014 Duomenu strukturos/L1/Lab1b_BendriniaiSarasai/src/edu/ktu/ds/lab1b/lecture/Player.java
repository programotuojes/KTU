/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ktu.ds.lab1b.lecture;

import edu.ktu.ds.lab1b.util.Ks;
import edu.ktu.ds.lab1b.util.LinkedList;
import edu.ktu.ds.lab1b.util.Parsable;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Player implements Parsable<Player> {

    private String surname;
    private int goalAttempts;
    private int goals;

    public Player() {    
    }
    
    public Player(String surname, int goalAttempts, int goals) {
        this.surname = surname;
        this.goalAttempts = goalAttempts;
        this.goals = goals;
    }
    
    public Player(String data) {
        parse(data);
    }

    @Override
    public void parse(String data) {
        try {
            Scanner ed = new Scanner(data);
            surname = ed.next();
            goalAttempts = ed.nextInt();
            goals = ed.nextInt();
        } catch (InputMismatchException e) {
            Ks.ern("Blogas duomenų formatas apie žaidėją -> " + data);
        } catch (NoSuchElementException e) {
            Ks.ern("Trūksta duomenų apie žaidėją -> " + data);
        }
    }
    
    // ... geteriai ir seteriai 
    public double goalPercentage() {
        return goalAttempts == 0 ? 0.0 : 100.0 * goals / goalAttempts;
    }

    @Override
    public int compareTo(Player other) {
        return 1;
    }

    @Override
    public String toString() {
        return "pavardė=" + surname + ", mesta=" + goalAttempts + ", pataikyta=" + goals + "; ";
    }

    //=============================================
    static LinkedList<Player> team = new LinkedList<>();

    static void testPlayerList() {
        Player p1 = new Player("Seibutis", 9, 9);
        Player p2 = new Player("Mačiulis", 7, 8);
        Player p3 = new Player("Jankūnas", 6, 4);
        team.add(p1);
        team.add(p2);
        team.add(p3);
        Ks.oun("Komandos žaidėjų skaičius=" + team.size());
        Ks.oun("Žaidėjai:\n" + team.get(0) + team.get(1) + team.get(2));
        //-------------------
        Ks.oun("Žaidėjas(0)=" + team.get(0));
        Ks.oun("Žaidėjas(1)=" + team.get(1));
        Ks.oun("Žaidėjas(2)=" + team.get(2));
        team.add(new Player("Gailius", 0, 0));
        team.add(new Player("Javtokas", 3, 0));
        Ks.oun("Žaidėjas(3)=" + team.get(3));
        Ks.oun("Žaidėjas(4)=" + team.get(4));
        Ks.oun("Komandos žaidėjų skaičius=" + team.size());
        //------------------
        p1 = team.get(3);  // Gailius ...
        p2 = team.get(4);  // Javtokas ...
        p3 = team.get(5);  // planuojame gauti null
        Ks.oun("Rinkinys:\n" + p1 + "\n" + p2 + "\n" + p3);
        Ks.oun(team.isEmpty() ? "nieko nėra" : "šiek tiek yra");
        team.clear();
        Ks.oun(team.isEmpty() ? "nieko nėra" : "šiek tiek yra");
    }  // žaidėjųSąrašoTesto pabaiga

    static void printPlayers(LinkedList<Player> players) {
        for (Player p = players.get(0); p != null; p = players.getNext()) {
            Ks.oun("Žaidėjas-> " + p);
        }
    } // ---------------------------------------------------------------

    static void antipattern(LinkedList<Player> players) {
        for (int i = 0; i < players.size(); i++) {
            Ks.oun("Žaidėjas-> " + players.get(i));
        }
    } // ---------------------------------------------------------------

    public static void main(String[] args) {
        testPlayerList();
        printPlayers(team);
        antipattern(team);
    }
}
