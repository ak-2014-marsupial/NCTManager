package ua.edu.sumdu.j2se.krutko.tasks;


public class ArrayTaskList {
    private int size;
    private int capacity;
    private Task[] listOfTask;

    /**
     * Конструктор створює список задач із розміром capacity.
     */
    public ArrayTaskList() {
        capacity = 10;
        size = 0;
        listOfTask = new Task[capacity];
    }

    /**
     * Метод додає задачу до списку, та динамічно розширює масив.
     *
     * @param task задача, якщо має значення Null - не додається до списку
     */
    public void add(Task task) {
        if (size == capacity) {
            grow();
        }
        listOfTask[size] = task;
        size++;
    }

    private void grow() {
        capacity = (int) (capacity * 1.5);
        Task[] newListOfTask = new Task[capacity];
        for (int i = 0; i < listOfTask.length; i++) {
            newListOfTask[i] = listOfTask[i];
        }
        listOfTask = newListOfTask;
    }

    /**
     * Метод видаляє задачу зі списку.
     *
     * @param task задача
     * @return true- якщо задача була у списку,
     * false- якщо задачі не було у списку
     */
    public boolean remove(Task task) {
        for (int i = 0; i < this.size; i++) {
            if (task.equals(this.listOfTask[i])) {
                this.fastRemove(i);
                return true;
            }
        }
        return false;
    }

    private void fastRemove(int index) {
        int lengthToMove = this.size - index - 1;
        if (lengthToMove > 0) {
            System.arraycopy(this.listOfTask, index + 1,
                    this.listOfTask, index, lengthToMove);
        }
        this.listOfTask[--this.size] = null;
    }

    public int size() {
        return this.size;
    }

    /**
     * Метод повертає задачу за індексом у списку.
     *
     * @param index індекс задачі у списку
     * @return повертає задачу за індексом
     */
    public Task getTask(int index) {
        if (index > this.size || index < 0) {
            throw new IndexOutOfBoundsException("Index= " + index + " Size =" + size);
        }
        return this.listOfTask[index];
    }

    /**
     * Метод повертає підмножину задач, що заплановані на виконання.
     * хоча б раз після часу from і не пізніше to
     *
     * @param from час початку інтервалу
     * @param to   час кінця інтервалу
     * @return повертає підмножину задач, що заплановані на виконання
     * хоча б раз після часу from і не пізніше to
     */
    public ArrayTaskList incoming(int from, int to) {
        if (to < 0) {
            throw new IllegalArgumentException("Час закінчення повинен бути більше 0");
        }

        ArrayTaskList incomingArrayTaskList = new ArrayTaskList();
        for (int i = 0; i < this.size; i++) {
            if (this.listOfTask[i].nextTimeAfter(from) != -1
                    && this.listOfTask[i].nextTimeAfter(from) < to) {
                incomingArrayTaskList.add(this.listOfTask[i]);
            }
        }
        return incomingArrayTaskList;
    }

}


