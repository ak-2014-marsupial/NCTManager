package ua.edu.sumdu.j2se.krutko.tasks;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractTaskList extends TaskListFactory implements Cloneable, Iterable<Task> {

    private int size;

    public abstract void add(Task task);

    public abstract boolean remove(Task task);

    public int size() {
        return size;
    }

    public abstract Task getTask(int index);
    public abstract Stream<Task> getStream();

    /**
     * Метод повертає підмножину задач, що заплановані на виконання.
     * хоча б раз після часу from і не пізніше to
     *
     * @param from час початку інтервалу
     * @param to   час кінця інтервалу
     * @return повертає підмножину задач, що заплановані на виконання
     * хоча б раз після часу from і не пізніше to
     */

    public final AbstractTaskList incoming(int from, int to) {
        if (to < 0) {
            throw new IllegalArgumentException("Час закінчення повинен бути більше 0");
        }
        ListTypes.types typeTaskList = TaskListFactory.getTypeTaskList(this);
        AbstractTaskList incomingTaskList = TaskListFactory.createTaskList(typeTaskList);
        List<Task> collect = this.getStream().collect(Collectors.toList());
        this.getStream()
                .filter(task -> task.nextTimeAfter(from) != -1
                        && task.nextTimeAfter(from) < to)
                .forEach(incomingTaskList::add);
        return incomingTaskList;
    }

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
