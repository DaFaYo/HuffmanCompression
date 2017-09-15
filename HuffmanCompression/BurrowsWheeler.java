/*
 *  Compilation:  javac BurrowsWheeler.java
 *  Execution:    java BurrowsWheeler - < input.txt
 *  Dependencies: BinaryIn.java BinaryOut.java CircularSuffixArray.java
 *  
 *  name: Daniel Faruk Younis
 *  login: danielfarukyounis@gmail.com
 * 
 * Burrows-Wheeler encoding. The Burrows-Wheeler transform of a string s 
 * of length N is defined as follows: Consider the result of sorting the N 
 * circular suffixes of s. The Burrows-Wheeler transform is the last column 
 * in the sorted suffixes array t[], preceded by the row number first in 
 * which the original string ends up. Continuing with the "ABRACADABRA!" example 
 * above, we highlight the two components of the Burrows-Wheeler transform in 
 * the table below.
 * 
 *      i     Original Suffixes          Sorted Suffixes       t    index[i]
 *     --    -----------------------     -----------------------    --------
 *      0    A B R A C A D A B R A !     ! A B R A C A D A B R A    11
 *      1    B R A C A D A B R A ! A     A ! A B R A C A D A B R    10
 *      2    R A C A D A B R A ! A B     A B R A ! A B R A C A D    7
 *     *3    A C A D A B R A ! A B R     A B R A C A D A B R A !   *0
 *      4    C A D A B R A ! A B R A     A C A D A B R A ! A B R    3
 *      5    A D A B R A ! A B R A C     A D A B R A ! A B R A C    5
 *      6    D A B R A ! A B R A C A     B R A ! A B R A C A D A    8
 *      7    A B R A ! A B R A C A D     B R A C A D A B R A ! A    1
 *      8    B R A ! A B R A C A D A     C A D A B R A ! A B R A    4
 *      9    R A ! A B R A C A D A B     D A B R A ! A B R A C A    6
 *     10    A ! A B R A C A D A B R     R A ! A B R A C A D A B    9
 *     11    ! A B R A C A D A B R A     R A C A D A B R A ! A B    2
 *   
 * 
 * Since the original string ABRACADABRA! ends up in row 3, we have first = 3. 
 * Thus, the Burrows-Wheeler transform is
 * 
 *  3
 *  ARD!RCAAAABB
 *
 *  % java BurrowsWheeler - < abra.txt | java HexDump 16
 *  00 00 00 03 41 52 44 21 52 43 41 41 41 41 42 42
 *  128 bits
 * 
 *  % java BurrowsWheeler - < abra.txt | java BurrowsWheeler +
 *  ABRACADABRA!
 * 
 * 
 * */

public class BurrowsWheeler {
    
    // alphabet size of extended ASCII
    private static final int R = 256;
    
    // apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
    public static void encode() {
        // read the input
        String s = BinaryStdIn.readString();
        CircularSuffixArray csx = new CircularSuffixArray(s);
        int N = csx.length();
        
        char[] t = new char[N];     
        int first = 0;
        for (int i = 0; i < N; i++) {
            int index = csx.index(i); 
            if (index == 0) {
                first = i;
                char c = s.charAt(N - 1);
                t[i] = c;
            }
            else {
                char c = s.charAt(index - 1);
                t[i] = c;
            }  
        }       
        
        // print number of bytes in original uncompressed message
        BinaryStdOut.write(first);
        for (int i = 0; i < N; i++) {
            BinaryStdOut.write(t[i]);
        }
        // close output stream
        BinaryStdOut.close();    
    }
    
    // apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
    public static void decode() {
        
        int first = BinaryStdIn.readInt();
        
        String s = BinaryStdIn.readString();
        char[] t = s.toCharArray();
        
        int N = t.length;
   
        // array that is the t[] array but sorted
        int[] next = new int[N];
        int[] aux = new int[N];
               
        // compute frequency counts
        int[] count = new int[R+1];
        for (int i = 0; i < N; i++)
            count[t[i] + 1]++;
        
        // compute cumulates
        for (int r = 0; r < R; r++)
            count[r+1] += count[r];
        
        // compute next
        for (int i = 0; i < N; i++) {
            int ind = count[t[i]];
            char c = t[i];
            next[count[c]++] = i;  
            aux[ind] = c;
        }
        
        BinaryStdOut.write((char) aux[first], 8);
        int nextIndex = first;
        for (int i = 0; i < N - 1; i++) {
            nextIndex = next[nextIndex];
            BinaryStdOut.write((char) aux[nextIndex], 8);
        }
        // close output stream
        BinaryStdOut.close();    
    }
    
    
    // if args[0] is '-', apply Burrows-Wheeler encoding
    // if args[0] is '+', apply Burrows-Wheeler decoding
    public static void main(String[] args) {
        if      (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException("Illegal command line argument");    
    }
}