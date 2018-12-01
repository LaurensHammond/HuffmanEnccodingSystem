/**
 * &copy. The assignment for CS21120 module 
 * at the Aberystwyth University 2016/2016.
 * 
 * The "Huffman encoding scheme" program implements an application that
 * compresses size of File. It uses algorithm of Huffman coding through
 * counting the number of occurrences of each character, sets frequency
 * for them and builds the binary tree joining characters of the lowest
 * frequency. Then it creates a book of characters which store the code
 * for all of them.
 * 
 * @author Konrad Lopuszynski
 * @title kol2CS21120Assign
 * @version 1.0
 * @since 06-12-2016
 * 
 */
package huffmanEncodingScheme;	
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;
/** 
 * 
 * The main class performs all the most important things and stores the
 * information about loaded file, characters and builded trees. It aslo
 * includes methods to perform the compressing and analyzing results of
 * it.
 * 
 * 
 * @author Konrad Lopuszynski
 */
public class kol2CS21120Assign {
	private Comparator<tree> compareByFrequency = new Comparator<tree>(){
		public int compare(tree tree1, tree tree2){
			return (int)((tree1.getFrequency()-tree2.getFrequency())*10000);
		}
	};
	private File myFile;
	private String read;
	private int lengthOfFile;
	private LinkedList<tree> chars_LL = new LinkedList<tree>();
	private LinkedList<tree> HuffmanTree = new LinkedList<tree>();
	private Hashtable<Character, String> book = new Hashtable<Character, String>();
	private Scanner scan;
	public kol2CS21120Assign() {
		
	}
	/**
	 * Performs the loading the file and stores the content in variable of
	 * String type. 
	 * 
	 * @throws FileNotFoundException supports errors if the file is not found
	 */
	public void readTheFile() throws FileNotFoundException {
	
			try{
				System.out.println("Enter the path to file: ");
				System.out.print(">>>");
				scan = new Scanner(System.in);
				myFile = new File(scan.nextLine());
				Scanner readtheFile = new Scanner(myFile);
				read = readtheFile.useDelimiter("//z").next();
				readtheFile.close();
			}catch(FileNotFoundException e){
				System.err.println("File doesn't exist");
				try {
					Thread.currentThread();
					Thread.sleep(700);
					}
					catch (InterruptedException f) {
					f.printStackTrace();
					}
				readTheFile();

			}
		
	}
	/**
	 * Clears the fields of object and set the initial values.
	 */
	public void clear(){
		myFile=null;
		read=null;
		lengthOfFile=0;
		chars_LL.clear();
		HuffmanTree.clear();
		book.clear();
	}
	/**
	 * Sets the length of text contained in file.
	 */
	public void setLengthOfFile() {
		if (read != null) {
			lengthOfFile = read.length();
		}
	}
	/**
	 * Calls all the functions needed to loaded the file, counting the number
	 * of characters and their frequency and building the Huffman binary tree.
	 * 
	 * @throws FileNotFoundException supports errors if the file is not found
	 */
	public void perform() throws FileNotFoundException{
		this.readTheFile();
		System.out.println("Loading file...");
		this.setLengthOfFile();
		System.out.println("Counting of characters and setting of characters' frequencies...");
		this.countCharacters();
		System.out.println("Building of binary tree and setting of code for each character...");
		this.buildTheTree();
		System.out.println("Done!");

	}
	/**
	 * Function to counting the quantity of characters. It adds the objects
	 * of character class to linked list and calls the function to sort it.
	 * 
	 */
	public void countCharacters() {
		character currentChar;
		for (int i = 0; i < lengthOfFile; i++) {
			int quant = 0;
			for (int j = 0; j < lengthOfFile; j++) {
				if ((read.charAt(i) == read.charAt(j)) && j<i)
					break;
				else
					if (read.charAt(i) == read.charAt(j)) {
						quant++;
					}
			}
			currentChar = new character(read.charAt(i), quant, lengthOfFile);
			
			if (chars_LL.isEmpty()) {
				chars_LL.add(currentChar);
			} else {
				boolean add = true;
				for (int a = 0; a < chars_LL.size(); a++) {
					character ch = (character) chars_LL.get(a);
					if (ch.getCharacter() == currentChar.getCharacter()) {
						add = false;
						break;
					}
				}
				if (add){
					chars_LL.add(currentChar);
					}
				}
		}
		sortLinkedList(chars_LL);
	}
	/**
	 * Creates copy of linked list storing the characters and performs
	 * the algorithm to create the Huffman tree. Create additional nodes
	 * throught joining the characters of the lowest frequency and sets
	 * the connections between them.   
	 */
	public void buildTheTree(){
		PriorityQueue<tree> currentTrees_PQ = new PriorityQueue<tree>(compareByFrequency);
		for(int i=0;i<chars_LL.size();i++){
			tree t = (tree) chars_LL.get(i);
			HuffmanTree.add(t);
			currentTrees_PQ.add(t);
		}
		while(currentTrees_PQ.size()>1){
			tree t2 = (tree) currentTrees_PQ.poll();

			tree t1 = (tree) currentTrees_PQ.poll();

			tree t3 = new tree(t2,t1);
				
				t3.setFreqByChildren();
			currentTrees_PQ.add(t3);
			HuffmanTree.add(t3);
		}
		
		setBinaryCode((tree)HuffmanTree.getLast(),"","");
	}
	
	/**
	 * Sorting of LinkedList by frequency of objects of tree class.
	 * It uses the bubble sort algorithm.
	 * 
	 * @param l LinkedList of objects of tree class.
	 */
	public void sortLinkedList(LinkedList<tree> l) {
		LinkedList<tree> char2_ll = new LinkedList<tree>();
		int size = l.size();
		double tab[][] = new double[size][2];
		boolean flag = true; 
		double temp, index; 

		for (int i = 0; i < size; i++) {
			tree ch = (tree) l.get(i);
			tab[i][0] = l.indexOf(ch);
			tab[i][1] = ch.getFrequency();
		}
	
		while (flag) {
			flag = false;
			for (int i = 0; i < size - 1; i++) {
				if (tab[i][1] < tab[i + 1][1])
				{
					index = tab[i][0];
					temp = tab[i][1];
					tab[i][0] = tab[i + 1][0];
					tab[i][1] = tab[i + 1][1];
					tab[i + 1][1] = temp;
					tab[i + 1][0] = index;
					flag = true;
				}
			}
		}
		
		for (int i = 0; i < size; i++) {
			tree t = (tree) l.get((int) tab[i][0]);
			char2_ll.add(i, t);		
		}
		l.clear();
		l.addAll(char2_ll);
		char2_ll.clear();
		
	}
	/**
	 * Sets the binary code of each node. This is a recursive function
	 * which sets the code and while the type of node does't equals "LEAVE"
	 * it calls itself for children of the node. The pasted bit is '0' for 
	 * left children (node of higher frequency) and '1' for right children.
	 * At this point the hashtable, storing a characters codes, is created 
	 * and character is sets as a key.
	 * 
	 * @param currentTree - node which the function sets the binary code for
	 * @param code - the code of parent node of this node
	 * @param addBit - value of bit what should be past to parent's code
	 */
	public void setBinaryCode(tree currentTree, String code, String addBit){
		
		currentTree.setCode(code+addBit);
		if(currentTree.getType()==PartOfTree.NODE){
			setBinaryCode(currentTree.getChild_L(), currentTree.getCode(), "0");
			setBinaryCode(currentTree.getChild_R(),currentTree.getCode(), "1");
		}else{
			char key = ((character)currentTree).getCharacter();
			String value = currentTree.getCode();
			book.put(key, value);
		}
	}
	/**
	 * Returns the compressed file as a string of bits using a hashtable storing
	 * values of binary code for each character.
	 * 
	 * @return string of bits for compressed file
	 * @throws FileNotFoundException supports errors if the file is not found
	 */
	public String compressedCode() throws FileNotFoundException{
		String textAsHuffmanCode = "";
		for(int i=0;i<read.length();i++){
			textAsHuffmanCode+=book.get(read.charAt(i));
		}
		return textAsHuffmanCode;
	}
	/**
	 * Prints the characters with their associated binary codes.
	 */
	public void printTheBook() {
		for (int i = 0; i < chars_LL.size(); i++) {
			if((int)((character) chars_LL.get(i)).getCharacter()==10){
				System.out.print("new line : ");
				System.out.println(book.get((char)((character)chars_LL.get(i)).getCharacter()));}
			else if((int)((character) chars_LL.get(i)).getCharacter()==13){
				System.out.print("carriage return : ");
				System.out.println(book.get((char)((character)chars_LL.get(i)).getCharacter()));}
				else{
					System.out.print("\'"+((character) chars_LL.get(i)).getCharacter()+"\' : ");
					System.out.println(book.get((char)((character)chars_LL.get(i)).getCharacter()));
				}
				
			}

	}
/**
 * Calculates the size of bits after compression.
 * 
 * @return the number of bits
 */
	public int compressedLenght(){
		int lenght=0;
		for(int i=0;i<chars_LL.size();i++){
			lenght += (chars_LL.get(i).getCode().length())*((character)chars_LL.get(i)).getQuantity();
		}
		return lenght;
	}
	/**
	 * Function used to performing and printing the results of analysis the compression.
	 * It shows the size of file before and after compression, the compresion ratio, height
	 * of Huffman binary tree and number of all nodes including the nodes of characters.
	 * Additionaly it print what part of the initial file is a compressed file.  
	 * 
	 * @throws FileNotFoundException supports errors if the file is not found
	 */
	public void analyze() throws FileNotFoundException{
		PriorityQueue<tree> nodes = new PriorityQueue<tree>(compareByFrequency);
		System.out.println("Analyzing...");
		int compressedLength = compressedLenght();
		System.out.println("1...");
		int fullDepth=0;
		for(int i=0;i<HuffmanTree.size();i++){
			fullDepth+=((tree)HuffmanTree.get(i)).getCode().length();
			nodes.add((tree)HuffmanTree.get(i));
			if(i%10==0)
			System.out.print(i+" ");
		}
		System.out.println("212121...");
		System.out.println("The size of file equals "+lengthOfFile*8+" bits.");
		System.out.println("The size of compressed file euqals "+compressedLength+" bits.");
		System.out.println("Compression ratio equals "+((float)((float)lengthOfFile*8)/(float)compressedLength));
		System.out.println("Height of Huffman binary tree is "+(((tree)nodes.peek()).getCode().length()));
		System.out.println("Number of all nodes equals "+nodes.size());
		System.out.format("Average depth equals %.5g%n",((float)fullDepth/nodes.size()));
		DecimalFormat df = new DecimalFormat("#.##");
		System.out.println("The encoded text is "+df.format((((((float)compressedLength)/((float)lengthOfFile*8))*100)))+"% of previous file.");
	}
	/**
	 * This function asks user for choose the one of following options:
	 * 
	 * <ol>
	 * 	<li>Compress the file. - Calls the perform() function to load the file and create Huffman binary tree as well as a book of characters</li>
	 * 	<li>Show the analysis of compression. - Checks the file has been already loaded and calls the analyze() function.</li>
	 * 	<li>Print the book of characters. -Checks the file has been already loaded and calls the printTheBook() function.</li>
	 * 	<li>Print encoded file. - Calls the text contained in file as the encoded string of bits.</li>
	 * 	<li>Exit. - The end of the program.</li>
	 * </ol>
	 * 
	 * @throws FileNotFoundException supports errors if the file is not found
	 */
	public void menu() throws FileNotFoundException{
		
		System.out.println("Menu:");
		System.out.println("1 - Compress the file.");
		System.out.println("2 - Show the analysis of compression.");
		System.out.println("3 - Print book of characters.");
		System.out.println("4 - Print encoded file.");
		System.out.println("5 - Exit.");
		System.out.print(">>>");
		scan = new Scanner(System.in);
		String a = scan.nextLine();
			while(a.charAt(0)!='5'){
			switch(a.charAt(0)){
			case '1':
				try {
					Thread.currentThread();
					Thread.sleep(700);
					}
					catch (InterruptedException e) {
					e.printStackTrace();
					}
				this.clear();
				this.perform();
				break;
			case '2':
				try {
					Thread.currentThread();
					Thread.sleep(700);
					}
					catch (InterruptedException e) {
					e.printStackTrace();
					}
				if(!chars_LL.isEmpty())
					this.analyze();
				else
					System.err.println("You have to compress a file!");

				try {
					Thread.currentThread();
					Thread.sleep(3000);
					}
					catch (InterruptedException e) {
					e.printStackTrace();
					}
				break;
			case '3':
				try {
					Thread.currentThread();
					Thread.sleep(700);
					}
					catch (InterruptedException e) {
					e.printStackTrace();
					}
				if(!chars_LL.isEmpty())
					this.printTheBook();
				else
					System.err.println("You have to compress a file!");
				break;
			case '4':
				if(!chars_LL.isEmpty())
					System.out.println(this.compressedCode());
				else
					System.err.println("You have to compress a file!");
				
				break;
			default:
				System.err.println("Wrong number!!! Try again!");
				try {
					Thread.currentThread();
					Thread.sleep(700);
					}
					catch (InterruptedException e) {
					e.printStackTrace();
					}
				System.out.print(">>>");
			}
			System.out.println();
			System.out.println("Menu:");
			System.out.println("1 - Compress the file");
			System.out.println("2 - Show the analysis of compression.");
			System.out.println("3 - Print book of characters.");
			System.out.println("4 - Print encoded file.");
			System.out.println("5 - Exit");
			System.out.print(">>>");
				scan = new Scanner(System.in);
				a = scan.nextLine();
				
			}
			System.out.println("Thank you!");
			
			
	}
	public static void main(String[] args) throws FileNotFoundException, InterruptedException {
		kol2CS21120Assign obj = new kol2CS21120Assign();
		obj.menu();
	}
}
