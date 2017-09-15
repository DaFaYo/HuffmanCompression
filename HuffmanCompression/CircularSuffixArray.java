/*
 *  Compilation:  javac CircularSuffixArray.java
 *  Execution:    java CircularSuffixArray < input.txt
 *  Dependencies: BinaryIn.java BinaryOut.java SuffixArrayX.java
 *  
 *  name: Daniel Faruk Younis
 *  login: danielfarukyounis@gmail.com
 * 
 * To efficiently implement the key component in the Burrows-Wheeler transform, 
 * we will use a fundamental data structure known as the circular suffix array, 
 * which describes the abstraction of a sorted array of the N circular suffixes 
 * of a string of length N. As an example, consider the string "ABRACADABRA!" 
 * of length 12. The table below shows its 12 circular suffixes and the result 
 * of sorting them. 
 * 
 * 
 *      i       Original Suffixes           Sorted Suffixes         index[i]
 *     --    -----------------------     -----------------------    --------
 *      0    A B R A C A D A B R A !     ! A B R A C A D A B R A    11
 *      1    B R A C A D A B R A ! A     A ! A B R A C A D A B R    10
 *      2    R A C A D A B R A ! A B     A B R A ! A B R A C A D    7
 *      3    A C A D A B R A ! A B R     A B R A C A D A B R A !    0
 *      4    C A D A B R A ! A B R A     A C A D A B R A ! A B R    3
 *      5    A D A B R A ! A B R A C     A D A B R A ! A B R A C    5
 *      6    D A B R A ! A B R A C A     B R A ! A B R A C A D A    8
 *      7    A B R A ! A B R A C A D     B R A C A D A B R A ! A    1
 *      8    B R A ! A B R A C A D A     C A D A B R A ! A B R A    4
 *      9    R A ! A B R A C A D A B     D A B R A ! A B R A C A    6
 *     10    A ! A B R A C A D A B R     R A ! A B R A C A D A B    9
 *     11    ! A B R A C A D A B R A     R A C A D A B R A ! A B    2
 * 
 * We define index[i] to be the index of the original suffix that appears ith 
 * in the sorted array. For example, index[11] = 2 means that the 2nd original 
 * suffix appears 11th in the sorted order (i.e., last alphabetically). 
 * 
 * This data type uses linear space; the methods length() and index() take 
 * constant time.
 * 
 **/

public class CircularSuffixArray {
    
    private int N;
    private int[] index;
    
    // circular suffix array of s
    public CircularSuffixArray(String s) {
        
        MySuffixArrayX msax = new MySuffixArrayX(s);
        N = msax.N;
        index = msax.index;
    } 
    
    // length of s    
    public int length() {
        return N;
    }
    
    // returns index of ith sorted suffix    
    public int index(int i) {
        if (i < 0 || i >= N) throw new IndexOutOfBoundsException();
        return index[i];
    }
    
    // Inner class    
    // ======================================================================
    
    private class MySuffixArrayX {
        private static final int CUTOFF = 11;   // cutoff to insertion sort (any value between 0 and 12)
        
        private final char[] text;
        private final int[] index;   // index[i] = j means text.substring(j) is ith largest suffix
        private final int N;         // number of characters in text
        
        /**
         * Initializes a circular suffix array for the given <tt>text</tt> string.
         * @param text the input string
         */
        public MySuffixArrayX(String text) {
            N = text.length();
            this.text = text.toCharArray();
            this.index = new int[N];
            for (int i = 0; i < N; i++)
                index[i] = i;
            
            // shuffle
            
            sort(0, N-1, 0);
        }
        
        // 3-way string quicksort lo..hi starting at dth character
        private void sort(int lo, int hi, int d) { 
            // cutoff to insertion sort for small subarrays
            if (hi <= lo + CUTOFF) {
                insertion(lo, hi, d);
                return;
            } 
            
            int lt = lo, gt = hi;
            char v = text[(index[lo] + d) % N];
            int i = lo + 1;
            while (i <= gt) {
                int t = text[(index[i] + d) % N];
                if      (t < v) exch(lt++, i++);
                else if (t > v) exch(i, gt--);
                else            i++;
            }            
            
            // a[lo..lt-1] < v = a[lt..gt] < a[gt+1..hi]. 
            sort(lo, lt-1, d);
            if (d < N) sort(lt, gt, d+1);
            sort(gt+1, hi, d);
        }
        
        // sort from a[lo] to a[hi], starting at the dth character
        private void insertion(int lo, int hi, int d) {
            for (int i = lo; i <= hi; i++)
                for (int j = i; j > lo && less(index[j], index[j-1], d); j--)
                exch(j, j-1);
        }
        
        // is text[i+d..N-1 0.. i+d-1] < text[j+d..N-1 0.. j+d-1] ?
        private boolean less(int i, int j, int d) {
            if (i == j) return false;
            int k = i + d;
            int l = j + d;
            int m = k;
            int n = l;
            while (k < (N + m) && l < (N + n)) {
                if (text[k % N] < text[l % N]) return true;
                if (text[k % N] > text[l % N]) return false;
                k++;
                l++;
            }
            return (k % N) > (l % N);
        }
        
        // exchange index[i] and index[j]
        private void exch(int i, int j) {
            int swap = index[i];
            index[i] = index[j];
            index[j] = swap;
        }
        
        public int length() {
            return N;
        }
        
        public int index(int i) {
            if (i < 0 || i >= N) throw new IndexOutOfBoundsException();
            return index[i];
        }       
    }
    
    // Unit tests the CircularSuffixArray data type.
    public static void main(String[] args) {
        /*
         String s = BinaryStdIn.readString();
         CircularSuffixArray suffix = new CircularSuffixArray(s);
         
         StdOut.println("  i ind ");
         StdOut.println("----------");
         
         for (int i = 0; i < suffix.length(); i++)             
         StdOut.printf("%3d %3d\n", i, suffix.index(i));
         */                                       
    }            
}