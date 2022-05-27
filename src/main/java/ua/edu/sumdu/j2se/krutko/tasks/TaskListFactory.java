package ua.edu.sumdu.j2se.krutko.tasks;


public class TaskListFactory {
    public static AbstractTaskList createTaskList(ListTypes.types type) throws IllegalArgumentException {
        AbstractTaskList taskList;
        switch (type) {
            case ARRAY: {
                 taskList = new ArrayTaskList();
                break;
            }
            case LINKED: {
                taskList = new LinkedTaskList();
                break;
            }
            default: {
                throw new IllegalArgumentException("Неочікуване значення " + type);
            }
        }
        return taskList;
    }
}
