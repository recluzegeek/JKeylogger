

class BalancedBST {

    Node root;


    int height(Node N) {
        if (N == null) return 0;
        return N.height;
    }


    int max(int a, int b) {
        return Math.max(a, b);
    }


    Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;

        return x;
    }


    Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;

        return y;
    }


    int getBalance(Node N) {
        if (N == null) return 0;

        return height(N.left) - height(N.right);
    }

    Node insert(int key, String value) {
        return insert(root, key, value);
    }

    Node insert(Node node, int key, String value) {

        if (node == null) return (new Node(key, value));

        if (key < node.key) node.left = insert(node.left, key, value);
        else if (key > node.key) node.right = insert(node.right, key, value);
        else
            return node;

        node.height = 1 + max(height(node.left), height(node.right));

        int balance = getBalance(node);

        if (balance > 1 && key < node.left.key) return rightRotate(node);

        if (balance < -1 && key > node.right.key) return leftRotate(node);

        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    String search(int key) {
        return search(root, key);
    }


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


    void display() {
        display(root);
    }

    void display(Node root) {
        if (root != null) {

            System.out.print(root.key + "=" + root.value + ", ");
            display(root.left);
            display(root.right);
        }
    }

    public static String get14(String str) {
        if (str.length() < 14) return str;
        return str.substring(0, 14);
    }


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