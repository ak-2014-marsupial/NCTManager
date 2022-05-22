package ua.edu.sumdu.j2se.krutko.tasks;


public class LinkedTaskList {
    private Node<Task> head;
    private Node<Task> tail;
    private int size;

    private static class Node<Task> {
        private Task element;
        private Node<Task> next;
        private Node<Task> prev;


        Node(Task element) {
            this.element = element;
        }
    }

    /**
     * Метод додає задачу в кінець списку.
     *
     * @param element задача
     */
    public void add(Task element) {
        Node<Task> newNode = new Node<>(element);
        if (head == null) {
            newNode.next = null;
            newNode.prev = null;
            head = newNode;
            tail = newNode;

        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    /**
     * Метод видаляє задачу зі списку.
     *
     * @param element задача
     * @return true- якщо задача була у списку,
     * false- якщо задачі не було у списку
     */
    public boolean remove(Task element) {
        Node tmp;
        if (element == null) {
            for (tmp = this.head; tmp != null; tmp = tmp.next) {
                if (tmp.element == null) {
                    this.unlink(tmp);
                    return true;
                }
            }
        } else {
            for (tmp = this.head; tmp != null; tmp = tmp.next) {
                if (element.equals(tmp.element)) {
                    this.unlink(tmp);
                    return true;
                }
            }
        }
        return false;
    }

    private Task unlink(Node <Task> tmp) {
        Task element = tmp.element;
        Node next = tmp.next;
        Node prev = tmp.prev;

        if (prev == null) {         //якщо tmp початковий елемент
            this.head = next;
        } else {
            prev.next = tmp.next;
            tmp.prev = null;
        }
        if (next == null) {         //якщо tmp кінцевий елемент
            this.tail = prev;
        } else {
            next.prev = prev;
            tmp.next = null;
        }
        tmp.element = null;
        --this.size;
        return (Task) element;

    }

    public LinkedTaskList incoming(int from, int to) {
        if (from < 0 || to < 0) {
            throw new IllegalArgumentException("Час не може бути менше за 0");
        }

        LinkedTaskList incomingList = new LinkedTaskList();
        for (int i = 0; i < size; i++) {
            if (getTask(i).nextTimeAfter(from) != -1
                    && getTask(i).nextTimeAfter(from) <= to) {
                incomingList.add(getTask(i));
            }
        }
        return incomingList;
    }

    public int size() {
        return this.size;
    }

    public Task getTask(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index = " + index
                    + " size =" + size);
        }
        if (index < (size >> 1)) {
            Node<Task> node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node.element;
        } else {
            Node<Task> node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            return node.element;
        }
    }

}

