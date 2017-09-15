/*
 *  Compilation:  javac MoveToFront.java
 *  Execution:    java MoveToFront < input.txt
 *  Dependencies: BinaryIn.java BinaryOut.java
 *  
 *  name: Daniel Faruk Younis
 *  login: danielfarukyounis@gmail.com
 * 
 *   The main idea of move-to-front encoding is to maintain an ordered 
 *   sequence of all of the characters in the alphabet, and repeatedly 
 *   read in a character from the input message, print out the position 
 *   in which that character appears, and move that character to the 
 *   front of the sequence. As a simple example, if the initial ordering 
 *   over a 6-character alphabet is A B C D E F, and we want to encode 
 *   the input CAAABCCCACCF, then we would update the move-to-front 
 *   sequences as follows:
 *  
 *      move-to-front    in   out
 *      -------------    ---  ---
 *       A B C D E F      C    2 
 *       C A B D E F      A    1
 *       A C B D E F      A    0
 *       A C B D E F      A    0
 *       A C B D E F      B    2
 *       B A C D E F      C    2
 *       C B A D E F      C    0
 *       C B A D E F      C    0
 *       C B A D E F      A    2
 *       A C B D E F      C    1
 *       C A B D E F      C    0
 *       C A B D E F      F    5
 *       F C A B D E  
 *  
 *  If the same character occurs next to each other many times in the input, 
 *  then many of the output values will be small integers, such as 0, 1, and 2. 
 *  The extremely high frequency of certain characters makes an ideal scenario 
 *  for Huffman coding. 
 * 
 *  % java MoveToFront - < abra.txt | java MoveToFront +
 *  ABRACADABRA!
 * 
 * 
 **/

import java.util.List;
import java.util.LinkedList;

public class MoveToFront {
    
    // alphabet size of extended ASCII
    private static final int R = 256;
    
    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        
        List<Character> chars = linkedList();       
        // read the input
        while (!BinaryStdIn.isEmpty())
        {
            char c = BinaryStdIn.readChar();   
            int index = chars.indexOf(c);
            chars.remove(index);
            chars.add(0, c);
            BinaryStdOut.write(index, 8);
        }
        BinaryStdOut.close();
    }
    
    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        
        List<Character> chars = linkedList();
        // read the input
        while (!BinaryStdIn.isEmpty())
        {
            int index = BinaryStdIn.readInt(8);   
            char c = chars.remove(index);
            chars.add(0, c);
            BinaryStdOut.write(c, 8);
        }
        BinaryStdOut.close();
    }
    
    private static List<Character> linkedList() {
        LinkedList<Character> lst = new LinkedList<Character>();            
        for (int i = 0; i < R; i++) 
            lst.add(i, (char) i);
        return lst;    
    }
    
    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        if      (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException("Illegal command line argument");      
    }
}