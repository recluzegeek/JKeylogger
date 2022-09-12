public class Queue<T> {
    private Node<T> front;
    private Node<T> rear;


    Queue() {
        this.front = rear = null;
    }

    void enqueue(T data){
        Node<T> newNode = new Node<>(data);
        if (front == null)  this.front = rear = newNode;
        else{
            rear.next = newNode;
            rear = newNode;
        }
    }

    T remove(){
        if (front == null) return null;
        // Store previous front and move front one node ahead
        Node<T> temp = front;
        front = front.next;

        // If front becomes NULL, then change rear also as NULL
        if (this.front == null)
            this.rear = null;
        return temp.data;
    }

    boolean isEmpty(){
        return front == null;
    }

    T peek(){
        return front.data;
    }

    public String toString(){
        Node<T> current = front;
        StringBuilder dataString = new StringBuilder("[");
        while (current != null) {
            dataString.append(current.data);
            if (current.next != null) {
                dataString.append(", ");
            }
            current = current.next;
        }
        dataString.append("]");
        return dataString.toString();
    }


    private static class Node<T> {
        private Node<T> next;
        private T data;

        private Node(T data) {
            this.next = null;
            this.data = data;
        }
    }

    public static void main(String[] args) {
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(930);
        queue.enqueue(930390);
        queue.enqueue(10);
        queue.enqueue(90);
        queue.enqueue(40);
        System.out.println(queue);
        queue.remove();
        queue.remove();
        System.out.println(queue);
    }
}
