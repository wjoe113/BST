package Minor.P2.DS;

import java.io.IOException;

// -------------------------------------------------------------------------
/**
 *  In this code I create a binary search tree that takes T elements
 *	This minor project is the second part of the large project
 *
 *  @author Joe
 *  @version June 11, 2014
 */

public class BST<T extends Comparable<? super T>>{
	/**
	 * Binary nodes class. This class will initialize two types
	 * of nodes: one with a root and no children, the other with
	 * a root and one or two children.
	 * 
	 * @author Joe
	 * @version June 11, 2014
	 *
	 */
    class BinaryNode{
    	T element; // element in the node
        BinaryNode left; // left pointer
        BinaryNode right; // right pointer
        
        /**
         * Initializes a binary node that has no children
         * 
         * @param elem = element passed in to the string
         */
        public BinaryNode(T e){
            element = e;
            left = null;
            right = null;
        }

        /**
         * Initializes a binary node with children
         * 
         * @param e = element passed in to the string
         * @param lt = left tree
         * @param rt = right tree
         * 
         */
        public BinaryNode(T e, BinaryNode lt, BinaryNode rt){
            element = e;
            left = lt;
            right = rt;
        }

    }

    boolean success = false;
    BinaryNode pool;
    BinaryNode root;
    int pSize;
    int pEdit = pSize;
    
    /**
     * Makes a binary search tree with no node pool
     * 
     */
    public BST(){
        root = null;
        pool = null;
        pSize = 0;
    }

    /**
     * Makes a BST with node pool of size Sz
     * @param Sz = size of the node pool
     */
    public BST(int Sz){
        root = null;
        pool = null;
        pSize = Sz;
    }

    /**
     * Checks if the BST is empty
     * @return true if BST has no nodes
     */
    public boolean isEmpty(){
        if(root == null)
        {
            return true;
        }
        else
        {
        	return false;
        }
    }

    /**
     * Traverses the tree and finds a node with element x
     * @param x = element we are searching for
     * @return the element if we found it
     */
    public T find(T x){
        return find(x, root);
    }

    private T find(T x, BinaryNode currentNode){
    	if(root == null){
    		return null;
    	}
        if(currentNode == null){
            return null;
        }
        if(x.compareTo(currentNode.element) < 0){
            return find(x, currentNode.left);
        }
        else if( x.compareTo(currentNode.element) > 0){
            return find(x, currentNode.right);
        }
        return currentNode.element;
    }

    /**
     * Inserts a node in to the BST
     * @param x = element of the node being inserted
     * @return true if the binary node was inserted
     */
    public boolean insert(T x){
        success = false;
        //subPool(x);    <-- activate when node pool is active
        root = insert(x, root);
        return success;
    }
    
    /**
     * Insert helper method
     * @param x = element in the node being inserted
     * @param sRoot = root of the subtree
     * @return the binary node created
     */
    private BinaryNode insert(T x, BinaryNode sRoot){
        if(sRoot == null){
            success = true;
            return new BinaryNode(x, null, null);
        }
        if(x.compareTo(sRoot.element) < 0){
            sRoot.left = insert(x, sRoot.left);
        }
        else if (x.compareTo(sRoot.element) > 0){
            sRoot.right = insert(x, sRoot.right);
        }
        return sRoot;
    }

    /**
     * Removes a node with element x
     * @param x = element being deleted
     * @return true if the node was deleted
     */
    public boolean remove(T x){
        if (root == null){
            return false;
        }
        success = false;
        //addPool();    <-- activate when node pool is active
        root = remove(x, root);
        return success;
        }

    private BinaryNode remove(T x, BinaryNode currentNode){
    	if(currentNode == null){
			return null;
    	}
    	if(x.equals(currentNode.element)){
            if (currentNode.left == null && currentNode.right == null) {
            	currentNode = null;
            	success = true;
                return currentNode;
            }
            if (currentNode.left == null) {
            	success = true;
                return currentNode = currentNode.right;
            }
            if (currentNode.right == null) {
            	success = true;
                return currentNode = currentNode.left;
            }
            BinaryNode smallVal = treeMin(currentNode.right);
            remove(smallVal.element, currentNode);
            smallVal.left = currentNode.left;
            smallVal.right = currentNode.right;
            success = true;
            return smallVal;
        }
        
        else if (x.compareTo(currentNode.element) < 0) {
        	currentNode.left = remove(x, currentNode.left);
            return currentNode;
        }
        
        else {
        	currentNode.right = remove(x, currentNode.right);
            return currentNode;
        }
    }

    /**
     * Prunes (deletes) the subtree of the node with element x
     * @param x = element being pruned
     * @return true if the subtree was deleted
     */
    public boolean prune(T x){
        if (root == null){
            return false;
        }
        boolean success = false;
        return prune(x, root, success);
    }

    private boolean prune(T x, BinaryNode currentNode, boolean success){
        if (x.compareTo(currentNode.element) < 0){
            success = prune(x, currentNode.left, success);
        }
        
        else if (x.compareTo(currentNode.element) > 0){
            success = prune(x, currentNode.right, success);
        }else{
            success = true;
            currentNode = null;
        }
        return success;
    }

    /**
     * Clears the tree
     */
    public void clear(){
        root = null;
    }

    /**
     * Checks if the BST is equivalent to the tree passed
     * @param other = second tree that compares the first
     */
    public boolean equals(Object obj){
    	{
    		if (!(obj instanceof BST)){
    			return false;
    		}
    		
    		@SuppressWarnings("unchecked")
			BST<T> comp = (BST<T>) obj;
    		
    		return equals(this.root, comp.root);
    	}
    }
    
    	private boolean equals(BinaryNode original, BinaryNode compare){
    		if ((original == null) && (compare == null)){
    			return true;
    		}
    		
            else if ((original == null) || (compare == null)){
                return false;
            }
     		else if (!(original.element.equals (compare.element))){
    			return false;
     		}else {
    			return (equals (original.left, compare.left)  && 
    				    equals (original.right, compare.right));
     		}
    		
    	}

    /**
     * Returns the number of levels in the tree
     * @return x = height of the tree
     */
    public int height(){
    	if(this.isEmpty()){
            return 0;
        }else{
            return height(root);
        }
    }
    
    private int height(BinaryNode currentNode){
        if(currentNode == null){
            return -0;
        }

        if(height(currentNode.left) > height(currentNode.right)){
            return height(currentNode.left) + 1;
        }else{
            return height(currentNode.right) + 1;
        }
      }
    
    /**
     * treeMin finds the lowest value in the tree (left-most)
     * @param r = root of the tree
     * @return the lowest greater bound of x
     */
    public BinaryNode treeMin(BinaryNode r){
        if(r == null){
        	return null;
        }
        else if(r.left==null){
        	return r;
        }else{
        	return treeMin(r.left);
        }
    }
      
    /**
     * Display method provided by Monk
     * @param Tree
     * @param ptsPerLine
     * @return
     * @throws IOException
     */
    public String Display(BST<Integer> Tree) throws IOException {

 	   if ( Tree.root == null ) {
 		  return new String("   Tree is empty.\n");
 	   }
 	   return DisplayHelper(Tree.root, 0);
 	}

 	public String DisplayHelper(BST<Integer>.BinaryNode sRoot, int Level) throws IOException {

 	   String Notes = new String();
 	   if (sRoot == null) return Notes;

 	   Notes += DisplayHelper(sRoot.left, Level + 1);

        for (int i = 0; i < Level; i++) {
     	   Notes += "---";
        }
        if ( Level > 0 )
     	  Notes += " ";
 	   Notes += sRoot.element + "\n";

 	   Notes += DisplayHelper(sRoot.right, Level + 1);
 	   return Notes;
 	}
}