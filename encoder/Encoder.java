import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Encoder {
    
    static Trie trie;
    
    // Gets beginning time of the program
    private static long startTime = System.currentTimeMillis();
    
    public static void main(String[] args) throws IOException {
        
        // Creating output file and writer for output
        String workingDirectory = System.getProperty("user.dir");
        File tempFile = new File(workingDirectory + File.separator + "temp.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        
        // New trie and tuple for lz78 input
        trie = new Trie();
        Tuple tup;
        
        // Takes input as it's read in system.in
        while (System.in.available() != 0) {
            byte[] b = new byte[System.in.available()];
            
            // Reads inputstream to byte array
            System.in.read(b);
            
            // Inserts bytes into trie and prints tuples to output file.
            for (byte c : b) {
                tup = trie.insert(c);
                
                if (tup == null) continue;
                else {
                    writer.write(tup.phraseNum + " ");
                    writer.write(tup.data + '\n');
                }
            }
        }
        
        // Prints the last tuple to the output file
        tup = trie.flush();
        if (tup != null) {
            writer.write(tup.phraseNum + " ");
            writer.write(tup.data + '\n');
        }
        
        // Calculates memory use of program
        long free = Runtime.getRuntime().freeMemory();
        long total = Runtime.getRuntime().totalMemory();
        
        // Closes file
        writer.flush();
        writer.close();
        
        // Calculates runtime
        long endTime = System.currentTimeMillis();
        System.out.println("It took " + (endTime - startTime) + " milliseconds, free Memory " +
                           free + ", total Memory " + total);
    }
}