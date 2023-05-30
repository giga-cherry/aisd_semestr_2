package ru.itis.kpfu.skvortsova.aatree;

public class AATree {

    private int insertTimes;
    private int findTimes;
    private int removeTimes;
    private static int counter;

    private AANode root;
    private static AANode nullNode;
        static       {
        nullNode = new AANode();
        nullNode.left = nullNode.right = nullNode;
        nullNode.level = 0;
        }

    private static AANode deletedNode;
    private static AANode lastNode;

    /**
     * Construct the tree.
     */
    public AATree() {
        root = nullNode;
        insertTimes=0;
        findTimes=0;
        removeTimes=0;
        counter=0;
    }

    public int getInsertTimes() {
        return insertTimes;
    }

    public int getFindTimes() {
        return findTimes;
    }

    public int getRemoveTimes() {
        return removeTimes;
    }

    /**
     * Insert into the tree. Does nothing if x is already present.
     *
     * @param x the item to insert.
     */
    public void insert(int x) {
        root = insert(x, root);
    }

    /**
     * Remove from the tree. Does nothing if x is not found.
     *
     * @param x the item to remove.
     */
    public void remove(int x) {
        deletedNode = nullNode;
        removeTimes++;
        root = remove(x, root);
    }

    /**
     * Find the smallest item in the tree.
     *
     * @return the smallest item or null if empty.
     */
    public int findMin() {
        if (isEmpty())
            return 0;

        AANode ptr = root;

        while (ptr.left != nullNode)
            ptr = ptr.left;

        return ptr.element;
    }

    /**
     * Find the largest item in the tree.
     *
     * @return the largest item or null if empty.
     */
    public int findMax() {
        if (isEmpty())
            return 0;

        AANode ptr = root;

        while (ptr.right != nullNode)
            ptr = ptr.right;

        return ptr.element;
    }

    /**
     * Find an item in the tree.
     *
     * @param x the item to search for.
     * @return the matching item of null if not found.
     */
    public int find(int x) {
        AANode current = root;
        nullNode.element = x;
        findTimes+=2;

        for (; ; ) {
            findTimes++;
            if (x < current.element) {
                findTimes++;
                current = current.left;
            }
            else if (x > current.element) {
                findTimes++;
                current = current.right;
            }
            else if (current != nullNode) {
                findTimes++;
                return current.element;
            }
            else
                return 0;
        }
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty() {
        root = nullNode;
    }

    /**
     * Test if the tree is logically empty.
     *
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return root == nullNode;
    }

    /**
     * Print the tree contents in sorted order.
     */
    public void printTree() {
        if (isEmpty())
            System.out.println("Empty tree");
        else
            printTree(root);
    }

    /**
     * Internal method to insert into a subtree.
     *
     * @param x the item to insert.
     * @param t the node that roots the tree.
     * @return the new root.
     */
    private AANode insert(int x, AANode t) {
        if (t == nullNode) {
            insertTimes++;
            t = new AANode(x, nullNode, nullNode);
        }
        else if (x < t.element) {
            insertTimes++;
            t.left = insert(x, t.left);
        }
        else if (x > t.element) {
            insertTimes++;
            t.right = insert(x, t.right);
        }
        else {
            return t;
        }

        t = skew(t);
        t = split(t);
        insertTimes+=counter;
        counter=0;
        return t;
    }

    /**
     * Internal method to remove from a subtree.
     *
     * @param x the item to remove.
     * @param t the node that roots the tree.
     * @return the new root.
     */
    private AANode remove(int x, AANode t) {
        if (t != nullNode) {
            removeTimes++;
            // Step 1: Search down the tree and set lastNode and deletedNode
            lastNode = t;
            removeTimes++;
            if (x < t.element) {
                removeTimes++;
                t.left = remove(x, t.left);
            }
            else {
                deletedNode = t;
                removeTimes++;
                t.right = remove(x, t.right);
            }

            // Step 2: If at the bottom of the tree and
            //         x is present, we remove it
            if (t == lastNode) {
                removeTimes++;
                if (deletedNode == nullNode || x != deletedNode.element) {
                    removeTimes++;
                    return t;   // Item not found; do nothing
                }
                deletedNode.element = t.element;
                t = t.right;
                removeTimes+=2;
            }

            // Step 3: Otherwise, we are not at the bottom; rebalance
            else if (t.left.level < t.level - 1 || t.right.level < t.level - 1) {
                removeTimes++;
                if (t.right.level > --t.level) {
                    removeTimes++;
                    t.right.level = t.level;
                }

                t = skew(t);
                t.right = skew(t.right);
                t.right.right = skew(t.right.right);
                t = split(t);
                t.right = split(t.right);
                removeTimes+=counter;
                counter=0;
            }
        }
        return t;
    }

    /**
     * Internal method to print a subtree in sorted order.
     *
     * @param t the node that roots the tree.
     */
    private void printTree(AANode t) {
        if (t != t.left) {
            printTree(t.left);
            System.out.println(t.element);
            printTree(t.right);
        }
    }

    /**
     * Skew primitive for AA-trees.
     *
     * @param t the node that roots the tree.
     * @return the new root after the rotation.
     */
    private AANode skew(AANode t) {
        if (t.left.level == t.level)
            counter++;
            t = rotateWithLeftChild(t);
        return t;
    }

    /**
     * Split primitive for AA-trees.
     *
     * @param t the node that roots the tree.
     * @return the new root after the rotation.
     */
    private AANode split(AANode t) {
        if (t.right.right.level == t.level) {
            counter++;
            t = rotateWithRightChild(t);
            t.level++;
            counter++;
        }
        return t;
    }

    /**
     * Rotate binary tree node with left child.
     */
    static AANode rotateWithLeftChild(AANode k2) {
        AANode k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        counter+=3;
        return k1;
    }

    /**
     * Rotate binary tree node with right child.
     */
    static AANode rotateWithRightChild(AANode k1) {
        AANode k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        counter+=3;
        return k2;
    }
}
