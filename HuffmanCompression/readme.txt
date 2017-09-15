
Burrows-Wheeler Data Compression Algorithm

This directory contains a solution for the COS 226 Programming Assignment.
In this exercise we implement the Burrows-Wheeler data compression algorithm. 
This revolutionary algorithm outcompresses gzip and PKZIP, is relatively easy to implement, 
and is not protected by any patents. 
It forms the basis of the Unix compression utililty [bzip2](http://www.bzip.org/). 

For more information, see: 


https://www.cs.princeton.edu/courses/archive/fall14/cos226/assignments/burrows.html


Note: Java installation required. 

Than you can use the following commands from the **command line** (use these instead of the ones on the webpage.):



Binary input and binary output:

more ./files/abra.txt

java -cp '.:algs4.jar' HexDump 16 < ./files/abra.txt



Huffman compression and expansion:

java -cp '.:algs4.jar' Huffman - < ./files/abra.txt | java -cp '.:algs4.jar' HexDump 16


java -cp '.:algs4.jar' Huffman - < ./files/abra.txt | java -cp '.:algs4.jar' Huffman + ABRACADABRA!



Move-to-front encoding:

java -cp '.:algs4.jar' MoveToFront - < ./files/abra.txt | java -cp '.:algs4.jar' HexDump 16


Move-to-front decoding:

java -cp '.:algs4.jar' MoveToFront - < ./files/abra.txt | java -cp '.:algs4.jar' MoveToFront + ABRACADABRA!



Burrows-Wheeler transform:

java -cp '.:algs4.jar' BurrowsWheeler - < ./files/abra.txt | java -cp '.:algs4.jar' HexDump 16


Burrows-Wheeler inverse transform:

java -cp '.:algs4.jar' BurrowsWheeler - < ./files/abra.txt | java -cp '.:algs4.jar' BurrowsWheeler + ABRACADABRA!



---


Compression / Decompression

So to compress for instance the Hamlet.txt file you can execute:



java -cp '.:algs4.jar' BurrowsWheeler - < files/hamlet.txt | java -cp '.:algs4.jar' MoveToFront - | java -cp '.:algs4.jar' Huffman - > files/hamletOutputFileName


And to decompress execute:

java -cp '.:algs4.jar' Huffman + < files/hamletOutputFileName | java -cp '.:algs4.jar' MoveToFront + | java -cp '.:algs4.jar' BurrowsWheeler + > files/hamlet2.txt



Than compare the files hamlet.txt and hamlet2.txt to see they are the same. Hence the operation keeps the content unchanged:

cmp files/hamlet.txt files/hamlet2.txt

To time the compress / decompress operation, you can put the codes above in a file and name it compress.bat/ decompress.bat, respectively. For example decompress.bat would contain:

echo %time%
java -cp '.:algs4.jar' Huffman + < files/hamletOutputFileName | java -cp '.:algs4.jar' MoveToFront + | java -cp '.:algs4.jar' BurrowsWheeler + > files/hamlet2.txt
echo %time%





