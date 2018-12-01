package huffmanEncodingScheme;
/**
 * Class defines the single node of Huffman binary tree. Includes the
 * type of node (NODE or LEAF), binary code of node, connections with
 * two children and value of frequency.
 * 
 * @author Konrad
 *
 */
public class tree {
	private PartOfTree type;
	private String code;
	private tree childLeft;
	private tree childRight;
	private double frequency;
/**
 * Creates the object of tree class.
 */
	public tree(){}
/**
 * Creates the object of tree class and connects it to two children.
 * This if constructor used to construct the Huffman binary tree. 
 * It attributes the NODE type because a node includes the children. 
 * 
 * @param child_L left child of node
 * @param child_R right child of node
 */
	public tree(tree child_L, tree child_R){
		this.type = PartOfTree.NODE;
		
		childLeft = child_L;
		childRight = child_R;
	}
/**
 * Gets the type of node. It is enumerated type called PartOfTree and
 * can be set as NODE or LEAF.
 * @return type of node
 */
	public PartOfTree getType() {
		return type;
	}
/**
 * Sets the type of node. It is enumerated type called PartOfTree and
 * can be set as NODE or LEAF.
 * 
 * @param type of enumerated type called PartOfTree
 */
	public void setType(PartOfTree type) {
		this.type = type;
	}
/**
 * Gets the left children of node which is of the tree class as well. 
 * 
 * @return left child of node
 */
	public tree getChild_L() {
		return childLeft;
	}
/**
 * Connects node with the left child of this node.
 * @param child_L left child of node
 */
	public void setChild_L(tree child_L) {
		this.childLeft = child_L;
	}
/**
 * Gets the right children of node which is of the tree class as well. 
 * 
 * @return right child of node
 */
	public tree getChild_R() {
		return childRight;
	}
/**
 * Connects node with the right child of this node.
 * @param child_R right child of node
 */
	public void setChild_R(tree child_R) {
		this.childRight = child_R;
	}
/**
 * Gets the value of frequency of this node.
 * @return frequency
 */
	public double getFrequency() {
		return frequency;
	}
/**
 * Sets the value of frequency of this node through join the frequencies
 * of children of this node. 
 */
	public void setFreqByChildren(){
		frequency = childLeft.getFrequency()+childRight.getFrequency();
	}
/**
 * Sets the value of frequency of this node.
 * 
 * @param frequency sum of the children frequencies
 */
	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}
/** 
 * Gets the binary code for node
 * @return binary code
 */
	public String getCode() {
		return code;
	}
/**
 * Sets the binary code of node.
 * @param code binary code of object
 */
	public void setCode(String code) {
		this.code = code;
	}
}
