////∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗*
//∗ @file: Proj2.java
//∗ @description: This program parses through my World.csv file and stores them as datapoints for BST and AVL tree operations,
//*               timing the differences to compare the efficiency of both trees.
//∗ @author: Alan Lin
//∗ @date: October 18, 2025
///∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class Proj2 {
    public static void main(String[] args) throws IOException {
        // Use command line arguments to specify the input file
        if (args.length != 2) {
            System.err.println("Usage: java TestAvl <input file> <number of lines>");
            System.exit(1);
        }

        String inputFileName = args[0];
        int numLines = Integer.parseInt(args[1]);

        // For file input
        FileInputStream inputFileNameStream = null;
        Scanner inputFileNameScanner = null;

        // Open the input file
        inputFileNameStream = new FileInputStream(inputFileName);
        inputFileNameScanner = new Scanner(inputFileNameStream);

        // ignore first line
        inputFileNameScanner.nextLine();

        // Making dataList to store terminal command
        ArrayList<World> dataList = new ArrayList<>();
        int index = 0;
        while(inputFileNameScanner.hasNextLine() && index < numLines){
            String line = inputFileNameScanner.nextLine();
            String[] entry = line.split(",");

            int year = Integer.parseInt(entry[0]);
            long population = Long.parseLong(entry[1]);

            dataList.add(new World(year, population));
            index++;
        }

        inputFileNameScanner.close();
        inputFileNameStream.close();

        //Create BSTs and AVL Trees, using Collections.sort() and Collection.shuffle()
        ArrayList<World> sorted = new ArrayList<>(dataList);
        Collections.sort(sorted);

        ArrayList<World> random = new ArrayList<>(dataList);
        Collections.shuffle(random);

        BST<World> sortedBST = new BST<>();
        BST<World> randomBST = new BST<>();
        AVLTree<World> sortedAVL = new AVLTree<>();
        AVLTree<World> randomAVL = new AVLTree<>();

        for(World w : sorted){
            sortedBST.insert(w);
            sortedAVL.insert(w);
        }

        for(World w : random){
            randomBST.insert(w);
            randomAVL.insert(w);
        }

        // Making Output File
        File outputFile = new File("output.txt");
        boolean newFile = !outputFile.exists();
        
        PrintWriter output = new PrintWriter(new FileOutputStream("output.txt", true));
        
        // If this is a new file, insert header for CSV Format
        if(newFile){
            output.println("#numLines,sortedBST_insertionTime,sortedAVL_insertionTime,sortedBST_searchTime,sortedAVL_searchTime,randomBST_insertionTime,randomAVL_insertionTime,randomBST_searchTime,randomAVL_searchTime");
        }
        
        // Timing the operations for both BST and AVL trees, divided by 1,000,000 to convert to seconds
        Long start;
        Long end;
        double timeBST;
        double timeAVL;

        start = System.nanoTime();
        for(World w : dataList) sortedBST.insert(w);
        end = System.nanoTime();
        timeBST = (end - start) / 1000000.;

        start = System.nanoTime();
        for(World w : dataList) sortedAVL.insert(w);
        end = System.nanoTime();
        timeAVL = (end - start) / 1000000.;
        output.print(numLines + "," + timeBST + "," + timeAVL);

        start = System.nanoTime();
        for(World w : dataList) sortedBST.search(w);
        end = System.nanoTime();
        timeBST = (end - start) / 1000000.;

        start = System.nanoTime();
        for(World w : dataList) sortedAVL.search(w);
        end = System.nanoTime();
        timeAVL = (end - start) / 1000000.;
        output.print("," + timeBST + "," + timeAVL);

        start = System.nanoTime();
        for(World w : dataList) randomBST.insert(w);
        end = System.nanoTime();
        timeBST = (end - start) / 1000000.;

        start = System.nanoTime();
        for(World w : dataList) randomAVL.insert(w);
        end = System.nanoTime();
        timeAVL = (end - start) / 1000000.;
        output.print("," + timeBST + "," + timeAVL);

        start = System.nanoTime();
        for(World w : dataList) randomBST.search(w);
        end = System.nanoTime();
        timeBST = (end - start) / 1000000.;

        start = System.nanoTime();
        for(World w : dataList) randomAVL.search(w);
        end = System.nanoTime();
        timeAVL = (end - start) / 1000000.;
        output.print("," + timeBST + "," + timeAVL + "\n");

        output.close();
    }

}
