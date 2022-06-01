package ua.edu.sumdu.j2se.krutko.tasks;


import java.util.Arrays;
import java.util.Iterator;

public class ArrayTaskList extends AbstractTaskList {
    private int size;
    private int capacity = 10;
    private Task[] listOfTask;

    /**
     * Конструктор створює список задач із розміром capacity.
     */
    public ArrayTaskList() {
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

    @Override
    public ArrayTaskList clone() throws CloneNotSupportedException {
        ArrayTaskList arrayTaskListClone = (ArrayTaskList) super.clone();
        arrayTaskListClone.listOfTask = Arrays.copyOf(this.listOfTask, this.size());
        for (int i = 0; i < this.size(); i++) {
            arrayTaskListClone.listOfTask[i] = this.getTask(i).clone();
        }
        return arrayTaskListClone;
    }

    @Override
    public Iterator<Task> iterator() {
        return new IteratorList();
    }

    private class IteratorList implements Iterator<Task> {
        private int currentIndex = 0;
        private int lastElementReturned = -1;

        @Override
        public boolean hasNext() {
            return currentIndex != size;
        }

        @Override
        public Task next() {
            lastElementReturned = currentIndex;
            return listOfTask[currentIndex++];
        }

        /**
         * Видаляє з базової колекції останній елемент, повернутий цим ітератором.
         */
        @Override
        public void remove() {
            if (currentIndex > 0) {
                ArrayTaskList.this.remove(listOfTask[lastElementReturned]);
                currentIndex--;
            } else {
                throw new IllegalStateException();
            }
        }
    }
}


