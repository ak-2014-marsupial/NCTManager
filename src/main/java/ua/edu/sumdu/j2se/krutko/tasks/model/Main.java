package ua.edu.sumdu.j2se.krutko.tasks.model;


import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

public class Main {

    public static void main(String[] args) throws CloneNotSupportedException {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime end = now.plusDays(1);
        Task task1 = new Task("Обід із гарною дівчиною", LocalDateTime.of(2022, 8, 24, 16, 0, 0));
        Task task2 = new Task("Ранкова пробіжка", LocalDateTime.of(2022, 3, 1, 8, 15), LocalDateTime.of(2022, 9, 1, 8, 15), 86400);
        Task task3 = new Task("Приймання ліків", LocalDateTime.of(2022, 8, 20, 8, 15), LocalDateTime.of(2022, 8, 28, 8, 15), 43200);
        Task task4 = new Task("Зустріч з друзями", LocalDateTime.of(2022, 9, 1, 18, 0));
        Task task5 = new Task("Some ", now, end, 3600);
        task1.setActive(true);
        task2.setActive(true);
        task3.setActive(true);
        task4.setActive(true);
        task5.setActive(true);

        LocalDateTime current = now.plusDays(1).minusSeconds(1);
        System.out.printf("current= %s  end= %s \n", current, task5.getEndTime());


        ArrayTaskList arrayTaskList = new ArrayTaskList();
        arrayTaskList.add(task1);
        arrayTaskList.add(task2);
        arrayTaskList.add(task3);
        arrayTaskList.add(task4);
        LocalDateTime from = LocalDateTime.of(2022, 8, 25, 8, 0);
        LocalDateTime to = LocalDateTime.of(2022, 8, 26, 8, 0);

        System.out.println("List of Task from ArrayTaskList");
        System.out.println(arrayTaskList);
        System.out.println("===========");

        Iterable<Task> incoming = Tasks.incoming(arrayTaskList, from, to);
        for (Task task : incoming) {
            System.out.println(task);
        }
        System.out.println();
        System.out.printf("календар задач from %s to %s: \n", from, to);

        SortedMap<LocalDateTime, Set<Task>> calendar = Tasks.calendar(arrayTaskList, from, to);
        for (Map.Entry<LocalDateTime, Set<Task>> entry : calendar.entrySet()) {
            System.out.println(entry.getKey() + "  " + entry.getValue());
        }
        System.out.println();
        System.out.println("Перевірка на зміну time");
        LocalDateTime timeNow = LocalDateTime.now();

        Task task = new Task("some task", timeNow);
        System.out.printf("    time = %s  task = %s\n", timeNow,task);
        timeNow = timeNow.plusHours(10);
        System.out.printf("time+10h = %s  task = %s\n", timeNow,task);
        System.out.println();
        System.out.println("-----serialization:");
        System.out.println("arrayTaskList.size="+arrayTaskList.size());
        TaskIO.writeBinary(arrayTaskList, new File("outPut.txt"));
        ArrayTaskList tasksSer = new ArrayTaskList();
        TaskIO.readBinary(tasksSer , new File("outPut.txt"));
        System.out.println(tasksSer);
        System.out.println("-------json--------");
        AbstractTaskList arrayTaskList2 = new ArrayTaskList();
        TaskIO.writeText(arrayTaskList,new File("out_json.json"));
        TaskIO.readText(arrayTaskList2,new File("out_json.json"));
        System.out.println(arrayTaskList2);

    }
}