package ua.edu.sumdu.j2se.krutko.tasks;


import java.io.Serializable;
import java.util.stream.Stream;

public abstract class AbstractTaskList extends TaskListFactory implements Cloneable, Iterable<Task>, Serializable {


    public abstract void add(Task task);

    public abstract boolean remove(Task task);

    public abstract int size();

    public abstract Task getTask(int index);
    public abstract Stream<Task> getStream();


    @Override
    public boolean equals(Object o) {
        boolean result = true;
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbstractTaskList that = (AbstractTaskList) o;
        if (this.size() != that.size()) {
            return false;
        }
        for (int i = 0; i < that.size(); i++) {
            result = this.getTask(i).equals(that.getTask(i));
            if (!result) {
                break;
            }
        }
        return result;
    }

    @Override
    public int hashCode() {
        int result = 31;
        for (int i = 0; i < this.size(); i++) {
            result = result + this.getTask(i).hashCode();
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.size(); i++) {
            builder.append(this.getTask(i).toString()).append("\n");
        }
        return builder.toString();
    }

}
