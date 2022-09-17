// Java program for insertion in AVL Tree

import java.util.Arrays;

class BalancedBST {

    Node root;

    // A utility function to get the height of the tree
    int height(Node N) {
        if (N == null) return 0;
        return N.height;
    }

    // A utility function to get maximum of two integers
    int max(int a, int b) {
        return Math.max(a, b);
    }

    // A utility function to right rotate subtree rooted with y
    // See the diagram given above.
    Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;

        // Return new root
        return x;
    }

    // A utility function to left rotate subtree rooted with x
    // See the diagram given above.
    Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;

        // Return new root
        return y;
    }

    // Get Balance factor of node N
    int getBalance(Node N) {
        if (N == null) return 0;

        return height(N.left) - height(N.right);
    }

    Node insert(int key, String value) {
        return insert(root, key, value);
    }// my name is salman

    Node insert(Node node, int key, String value) {

        /* 1. Perform the normal BST insertion */
        if (node == null) return (new Node(key, value));

        if (key < node.key) node.left = insert(node.left, key, value);
        else if (key > node.key) node.right = insert(node.right, key, value);
        else // Duplicate keys not allowed
            return node;

        /* 2. Update height of this ancestor node */
        node.height = 1 + max(height(node.left), height(node.right));

		/* 3. Get the balance factor of this ancestor
			node to check whether this node became
			unbalanced */
        int balance = getBalance(node);

        // If this node becomes unbalanced, then there
        // are 4 cases Left-Left Case
        if (balance > 1 && key < node.left.key) return rightRotate(node);

        // Right-Right Case
        if (balance < -1 && key > node.right.key) return leftRotate(node);

        // Left Right Case
        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        /* return the (unchanged) node pointer */
        return node;
    }

    String search(int key) {
        return search(root, key);
    }

    // searching the tree for the key and return string containing the value
    String search(Node node, int key) {
        if (node == null) return null;
        if (key == node.key) {
            if (node.value.split(",").length == 1) return "null";
            return node.value.split(",")[1];
        }
        if (key < node.key) return search(node.left, key);
        return search(node.right, key);
    }

    int modifiedSearch(int key) {
        return modifiedSearch(root, key);
    }

    private int modifiedSearch(Node root, int key) {
        if (root == null) return 0;
        if (key == Integer.parseInt(root.value.split(",")[0])) return root.key;
        if (key < Integer.parseInt(root.value.split(",")[0])) return modifiedSearch(root.left, key);
        return modifiedSearch(root.right, key);
    }

    // inorder traversal
    void display() {
        display(root);
    }

    void display(Node root) {
        if (root != null) {
            // implemented pre-order
            System.out.print(root.key + "=" + root.value + ", ");
            display(root.left);
            display(root.right);
        }
    }

    public static void main(String[] args) {
        BalancedBST tree = new BalancedBST();
        // have to set the root every time again because the root is changing
        tree.root = tree.insert(10, "1, Salman");
        tree.root = tree.insert(20, "2, Is my name");
        tree.root = tree.insert(30, "3, Doing");
        tree.root = tree.insert(40, "4,");
        tree.root = tree.insert(50, "5, On CS");
        tree.root = tree.insert(60, "6, Eating");
        tree.root = tree.insert(70, "8,");
        //
//        String tst = "[Enter][Alt][Tab]my name is salman[Enter][Enter]";
//        System.out.println(get14(tst));
//        System.out.println(new String(" hello   \n  there   ").trim().replaceAll("\\s{2,}", " "));
//        System.out.println(tst.replaceAll("\n", "[Enter]"));

//        System.out.println(tree.search(40));
//        System.out.println(tree.search(50));
//        System.out.println(tree.search(70));
//        tree.display();
//        System.out.println(tree.search(10));
//        String[] treeArray = tree.search(10).split(", ");
//        System.out.println(treeArray[0]);
//        System.out.println(treeArray[1]);
//        tree.display();
//        System.out.println();
//        System.out.println(tree.modifiedSearch(4)); // 40
//        System.out.println();
//        System.out.println(tree.modifiedSearch(6)); // 60

        StringBuilder sb = new StringBuilder();
        sb.append(FileWriting.print());
        format(sb, "%7s |      %-20s   |    %-19s   | %-32s | %-50s | %-20s |\n", "Index", "Date/Time", "    Source", "  Title/Description", "Values", "Clipboard");
        sb.append(FileWriting.print());
        System.out.println(sb);

    }

    // method that will return the string till 14th index and if the string is less than 14 then it will return the string
    public static String get14(String str) {
        if (str.length() < 14) return str;
        return str.substring(0, 14);
    }

    // return String.format to StringBuilder
    public static StringBuilder format(StringBuilder sb, String format, Object... args) {
        return sb.append(String.format(format, args));
    }

    static class Node {
        int key, height;
        String value;
        Node left, right;

        Node(int d, String v) {
            key = d;
            value = v;
            height = 1;
        }
    }
}