import java.util.Scanner;

public class RedBlackTree {
    private class Node {
        private int value;
        private Color color;
        private Node leftKid;
        private Node rightKid;

        public String toString(Node node) {
            String col = null;
            if (node.color == Color.Black) col = "B";
            if (node.color == Color.Red) col = "R";
            return node.value + col;
        }
    }

    private enum Color {
        Red, Black
    }


    private static Node root;

    public boolean add(int value) {
        if (root == null) {
            root = new Node();
            root.color = Color.Black;
            root.value = value;
            return true;
        } else {
            boolean result = addNode(root, value);
            root = rebalance(root);
            root.color = Color.Black;
            return result;
        }
    }

    private boolean addNode(Node node, int value) {
        if (node.value == value) return false;
        if (node.value > value) {
            if (node.leftKid != null) {
                boolean result = addNode(node.leftKid, value);
                node.leftKid = rebalance(node.leftKid);
                return result;
            }
            if (node.leftKid == null) {
                node.leftKid = new Node();
                node.leftKid.color = Color.Red;
                node.leftKid.value = value;
                return true;
            }
        } else {
            if (node.rightKid != null) {
                boolean result = addNode(node.rightKid, value);
                node.rightKid = rebalance(node.rightKid);
                return result;
            }
            if (node.rightKid == null) {
                node.rightKid = new Node();
                node.rightKid.color = Color.Red;
                node.rightKid.value = value;
                return true;
            }
        }
        return false;
    }

    private Node rebalance(Node node) {
        Node result = node;
        boolean needRebalance;
        do {
            needRebalance = false;
            if (result.rightKid != null && result.rightKid.color == Color.Red &&
                    (result.leftKid == null || result.leftKid.color == Color.Black)) {
                needRebalance = true;
                result = rightSwap(result);
            }

            if (result.leftKid != null && result.leftKid.color == Color.Red &&
                    result.leftKid.leftKid != null && result.leftKid.leftKid.color == Color.Red) {
                needRebalance = true;
                result = leftSwap(result);

            }

            if (result.leftKid != null && result.leftKid.color == Color.Red &&
                    result.rightKid != null && result.rightKid.color == Color.Red) {
                needRebalance = true;
                colorSwap(result);
            }
        } while (needRebalance);
        return result;
    }

    private Node rightSwap(Node node) {
        Node rightKid = node.rightKid;
        Node betweenKid = rightKid.leftKid;
        rightKid.leftKid = node;
        node.rightKid = betweenKid;
        rightKid.color = node.color;
        node.color = Color.Red;
        return rightKid;
    }

    private Node leftSwap(Node node) {
        Node leftKid = node.leftKid;
        Node betweenKid = leftKid.rightKid;
        leftKid.rightKid = node;
        node.leftKid = betweenKid;
        leftKid.color = node.color;
        node.color = Color.Red;
        return leftKid;


    }

    private void colorSwap(Node node) {
        node.rightKid.color = Color.Black;
        node.leftKid.color = Color.Black;
        node.color = Color.Red;

    }

    public static void drawing(Node root) {
        try {
            String s = "_";
            System.out.println(s.repeat(50) + root.toString(root) + s.repeat(50));
            System.out.println(s.repeat(30) + root.leftKid.toString(root.leftKid) + s.repeat(30) +
                    root.rightKid.toString(root.rightKid) + s.repeat(37));
            System.out.println(s.repeat(25) + root.leftKid.leftKid.toString(root.leftKid.leftKid) + s.repeat(8) +
                    root.leftKid.rightKid.toString(root.leftKid.rightKid) + s.repeat(20) +
                    root.rightKid.leftKid.toString(root.rightKid.leftKid) + s.repeat(10) +
                    root.rightKid.rightKid.toString(root.rightKid.rightKid) + s.repeat(30));
            System.out.println(s.repeat(20) + root.leftKid.leftKid.leftKid.toString(root.leftKid.leftKid.leftKid) + s.repeat(5) +
                    root.leftKid.leftKid.rightKid.toString(root.leftKid.leftKid.rightKid) + s.repeat(5) +
                    root.leftKid.rightKid.leftKid.toString(root.leftKid.rightKid.leftKid) + s.repeat(5) +
                    root.leftKid.rightKid.rightKid.toString(root.leftKid.rightKid.rightKid) + s.repeat(10) +
                    root.rightKid.leftKid.leftKid.toString(root.rightKid.leftKid.leftKid) + s.repeat(5) +
                    root.rightKid.leftKid.rightKid.toString(root.rightKid.leftKid.rightKid) + s.repeat(5) +
                    root.rightKid.rightKid.leftKid.toString(root.rightKid.rightKid.leftKid) + s.repeat(5) +
                    root.rightKid.rightKid.rightKid.toString(root.rightKid.rightKid.rightKid) + s.repeat(23));
        }catch(NullPointerException e){
            System.out.println("Вы, скорее всего, либо где-то ввели одинаковые числа либо наша отрисовка не умещает ваше дерево на 4 строчках и требуется больше строк.\n" +
                    "Просим запустить программу заново...");
        }
    }


    public static void main(String[] args) {
        RedBlackTree myTree = new RedBlackTree();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Небходимо ввести 15 неповторяющихся чисел для отрисовки красно-черного дерева");
        try {
            for (int i = 0; i < 15; i++) {
                System.out.print("Введите число: ");
                int value = scanner.nextInt();
                myTree.add(value);
            }
        } catch (Exception e) {
            System.out.println("Просим быть внимательнее при вводе чисел, запустите программу заново ");
            return;
        } finally {
            scanner.close();
        }
        System.out.println("Рисую левосторонее красно-черное дерево");
        myTree.drawing(root);


    }


}
