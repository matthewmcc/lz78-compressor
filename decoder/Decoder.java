import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Decoder {
    
    // Togetther these arraylists create the dictionary
    public static List<Integer> phraseNums = new ArrayList<Integer>();
    public static List<Byte> data = new ArrayList<Byte>();
    
    public static BufferedOutputStream bos;
    
    /**
     * Decompress a LZ78 tuple set and output a the original file.
     * @param args cli tuples if the user decides to use that option
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws IOException {
        
        // Reader for system.in
        Scanner sc = new Scanner(System.in);
        
        // Blank initial phrase
        phraseNums.add(0);
        data.add((byte) 0);
        
        // Adds all input tuple to dictionary array lists
        while (sc.hasNext()) {
            phraseNums.add(sc.nextInt());
            data.add(sc.nextByte());
        }
        
        decodeLZ();
        sc.close();
    }
    
    /**
     * DecodeLZ will read in tuples and construct a dictionary from tuples provided
     */
    public static void decodeLZ() throws IOException {
        
        // Creating output file and bufferedoutputstream for output
        String workingDirectory = System.getProperty("user.dir");
        File file = new File(workingDirectory + File.separator + "out.txt");
        bos = new BufferedOutputStream(new FileOutputStream(file));
        
        // Calls writer for all input tuples
        for (int i = 1; i < phraseNums.size() - 1; i++) {
            recursiveWriter(phraseNums.get(i), (byte) data.get(i));
        }
        
        // Writes final phrase to the output
        recursiveWriter(phraseNums.get(phraseNums.size() - 1), (Byte) null);
        
        bos.flush();
        bos.close();
        
        System.exit(0);
    }
    
    // Prints phrase to output file
    public static void recursiveWriter(int pNum, Byte bData) throws IOException {
        
        // Calls self to write to output untill phrase end
        if (pNum != 0) {
            recursiveWriter(phraseNums.get(pNum), data.get(pNum));
        }
        
        // Writes byte to output then returns
        if (bData != null)
            bos.write(bData);
        return;
    }
}