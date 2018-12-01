package huffmanEncodingScheme;
/**
 * Class defines the single characters found in file. This is a subclass
 * which extends the tree superclass. It can store information about the
 * character and quantity of this character. The type of character object
 * is always LEAF.
 * 
 * @author Konrad Lopuszynski
 *
 */
public class character extends tree{
	private char character;
	private long quantity;
	
/**
 * Creates an object of this class and sets a character and the number of
 * occurrences in the file.
 * 
 * @param c character
 * @param q quantity
 */
	public character(char c, int q, int fulllength){
		super.setType(PartOfTree.LEAF);
		character = c;
		quantity = q;
		double f = (double)q/(double)fulllength;
		super.setFrequency(f);
	}
public long getQuantity() {
	return quantity;
}
/**
 * Gets the character from object
 * @return character
 */
	public char getCharacter() {
		 return character;
	}

}
