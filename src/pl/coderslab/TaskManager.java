package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javax.swing.text.DateFormatter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TaskManager {

    static String[][] tasks;
    public static void main(String[] args) {

        tasks = readFile("tasks.csv");
        showMenu();
    }

    private static void processCommands() {
        try(Scanner commandReader = new Scanner(System.in)){
            while(commandReader.hasNextLine()) {
                String command = commandReader.nextLine();

                switch (command) {
                    case "add": addTask();
                    showMenu(); break;
                    case "list": list();
                    showMenu(); break;
                    case "remove": removeTask();
                    showMenu(); break;
                    case "exit": exit(); break;
                    default: System.out.println("Please select correct option.");
                    showMenu(); break;
                 }
             }
            } catch (IllegalArgumentException e) {
            e.printStackTrace();
            showMenu();
        }
    }

    public static String[][] readFile (String filename) {
        File file = new File(filename);
        String[][] lines = new String[0][];
        try (Scanner scan = new Scanner(file)){
                    while (scan.hasNextLine()){
                        lines = Arrays.copyOf(lines, lines.length + 1);
                        lines[lines.length - 1] = scan.nextLine().split(", ");
                    }
            scan.close();
        }
        catch (FileNotFoundException e) {
          //  e.printStackTrace();
            System.out.println("Brak pliku z bazą zadan do wczytania.");
            System.exit(0);
        }

        return lines;

    }

    public static void showMenu() {
        System.out.println(ConsoleColors.BLUE + "Please select an option:" + ConsoleColors.RESET);
        System.out.println("add");
        System.out.println("remove");
        System.out.println("list");
        System.out.println("exit");
        processCommands();
    }
// Dodac metody data i bolean i rozwinac metode addTask
    public static void addTask() {


        tasks = Arrays.copyOf(tasks, tasks.length + 1);
            Scanner scan = new Scanner(System.in);
            System.out.println("Please add task description");
            String newTask = scan.nextLine();
            System.out.println("Please add tasks date in format yyyy-mm-dd");
            String newDate = scan.nextLine();
            System.out.println("Is yout task important? true/false");
            String newBool = scan.nextLine();


            tasks[tasks.length-1] = new String[3];
            tasks[tasks.length-1][0] = newTask;
        tasks[tasks.length-1][1] = newDate;
        tasks[tasks.length-1][2] = newBool;

        showMenu();
    }

    //to skopiowałem:
    public static boolean isNumberGreaterEqualZero(String input) {

        if (NumberUtils.isParsable(input)) {
            return Integer.parseInt(input) >= 0;
        } return false;
    }
    //to teź skopiowałem:
    public static int getTheNumber() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj numer zadania do usuniecia.");

        String n = scanner.nextLine();
        while (!isNumberGreaterEqualZero(n)) {
            System.out.println("Nieprawidlowa wartosc, podaj wartosc wieksza/rowna '0'");
            n = scanner.nextLine();
        }
        return Integer.parseInt(n);
    }
    public static void removeTask() {

        try {tasks = ArrayUtils.remove(tasks, getTheNumber());}
        catch (IndexOutOfBoundsException e) {
            System.out.println("Parametr spoza zakresu tablicy. Sprobuj raz jeszcze");
        }
    }

    public static void list() {
        for (int i = 0; i < tasks.length; i++) {
            System.out.println(i + " : " + tasks[i][0] + " " + tasks[i][1] + " " + tasks[i][2]);
        }
            showMenu();
    }
    public static void writeToFile(String filename) {

    try (FileWriter fw = new FileWriter(filename, false)) {
        {
            for (int i = 0; i < tasks.length; i++) {
                String nextLine = (tasks[i][0] + ", " + tasks[i][1] + ", " + tasks[i][2]);
                fw.append(nextLine).append("\n");
            }
        }
    }catch(IOException e) {
        e.printStackTrace();
    }
}
    public static void exit() {

        writeToFile("tasks.csv");
        System.out.println(ConsoleColors.RED + "Bye, bye.");
        System.exit(0);
    }

}
