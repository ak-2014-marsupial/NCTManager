package ua.edu.sumdu.j2se.krutko.tasks;

import static ua.edu.sumdu.j2se.krutko.tasks.ListTypes.types.ARRAY;
import static ua.edu.sumdu.j2se.krutko.tasks.ListTypes.types.LINKED;

public abstract class AbstractTaskList extends TaskListFactory {

    private int size;

    public abstract void add(Task task);

    public abstract boolean remove(Task task);

    public int size() {
        return  size;
    };

    public abstract Task getTask(int index);
    /**
     * Метод повертає підмножину задач, що заплановані на виконання.
     * хоча б раз після часу from і не пізніше to
     *
     * @param from час початку інтервалу
     * @param to   час кінця інтервалу
     * @return повертає підмножину задач, що заплановані на виконання
     * хоча б раз після часу from і не пізніше to
     */

    public AbstractTaskList incoming(int from, int to) {
        if (to < 0) {
            throw new IllegalArgumentException("Час закінчення повинен бути більше 0");
        }
        AbstractTaskList incomingTaskList = TaskListFactory.createTaskList(this instanceof ArrayTaskList ? ARRAY: LINKED);
        for (int i = 0; i < this.size(); i++) {
            if (this.getTask(i).nextTimeAfter(from) != -1
                    && this.getTask(i).nextTimeAfter(from) < to) {
                incomingTaskList.add(this.getTask(i));
            }
        }
        return incomingTaskList;
    }


}
