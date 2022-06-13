package ua.edu.sumdu.j2se.krutko.tasks;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

public class Tasks {
    public static Iterable<Task> incoming(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {
        if (end.isBefore(LocalDateTime.MIN)) {
            throw new IllegalArgumentException("Час має бути менше за 0");
        }
        ListTypes.types typeTaskList = TaskListFactory.getTypeTaskList(tasks);
        AbstractTaskList incomingTaskList = TaskListFactory.createTaskList(typeTaskList);
        Stream.Builder<Task> stream = Stream.builder();
        tasks.forEach(stream::add);
        stream.build()
                .filter(Objects::nonNull)
                .filter(task -> task.nextTimeAfter(start) != null)
                .filter(task -> !task.nextTimeAfter(start).isAfter(end))
                .forEach(incomingTaskList::add);
        return incomingTaskList;
    }

    public static SortedMap<LocalDateTime, Set<Task>> calendar(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {
        SortedMap<LocalDateTime, Set<Task>> calendarTasks = new TreeMap<>();

        Iterable<Task> taskIterable = incoming(tasks, start, end);

        for (Task task : taskIterable) {
            LocalDateTime nextTime = task.nextTimeAfter(start);

            while (nextTime != null) {
                if (nextTime.isAfter(end)) {
                    break;
                }
                if (calendarTasks.containsKey(nextTime)) {
                    calendarTasks.get(nextTime).add(task);
                } else {
                    Set<Task> taskSet = new HashSet<>();
                    taskSet.add(task);
                    calendarTasks.put(nextTime, taskSet);
                }
                nextTime = task.nextTimeAfter(nextTime);
            }
        }
        return calendarTasks;
    }
}
