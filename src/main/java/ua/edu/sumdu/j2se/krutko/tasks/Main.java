package ua.edu.sumdu.j2se.krutko.tasks;

//import java.util.ArrayList;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Task> list = new ArrayList<>();

        Task task1 = new Task("Обід із гарною дівчиною", (24 + 8 * 30) * 24 + 16);
        Task task2 = new Task("Ранкова пробіжка", 3 * 30 * 24, 9 * 30 * 24, 24);
        Task task3 = new Task("Приймання ліків", (20 + 8 * 30) * 24, (28 + 8 * 30) * 24, 12);
        Task task4 = new Task("Зустріч з друзями", (9 * 30) * 24 + 18);
        task1.setActive(true);
        task2.setActive(true);
        task3.setActive(true);
        task4.setActive(true);
        list.add(task1);
        list.add(task2);
        list.add(task3);
        list.add(task4);
        for (Task task : list) {
            System.out.println(task.getTitle());
        }


    }

}
