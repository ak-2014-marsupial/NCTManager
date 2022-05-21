package ua.edu.sumdu.j2se.krutko.tasks;


public class Main {

    public static void main(String[] args) {

        Task task1 = new Task("Обід із гарною дівчиною", (24 + 8 * 30) * 24 + 16);
        Task task2 = new Task("Ранкова пробіжка", 3 * 30 * 24,
                9 * 30 * 24, 24);
        Task task3 = new Task("Приймання ліків", (20 + 8 * 30) * 24,
                (28 + 8 * 30) * 24,
                12);
        Task task4 = new Task("Зустріч з друзями", (9 * 30) * 24 + 18);
        task1.setActive(true);
        task2.setActive(true);
        task3.setActive(true);
        task4.setActive(true);


        ArrayTaskList arrayTaskList = new ArrayTaskList();
        arrayTaskList.add(task1);
        arrayTaskList.add(task2);
        arrayTaskList.add(task3);
        arrayTaskList.add(task4);
        System.out.println("size = " + arrayTaskList.size());

        for (int i = 0; i < arrayTaskList.size(); i++) {
            Task task = arrayTaskList.getTask(i);
            System.out.println(task.getTitle() + " " + task.getStartTime());

        }
        System.out.println("Remove task1 " + arrayTaskList.remove(task1));
        System.out.println("size = " + arrayTaskList.size());
        int from = (25 + 8 * 30) * 24 + 8;
        int to = (26 + 8 * 30) * 24 + 8;
        System.out.println();

        ArrayTaskList incoming = arrayTaskList.incoming(from, to);
        System.out.println("incoming size " + incoming.size());

        for (int i = 0; i < incoming.size(); i++) {
            Task task = incoming.getTask(i);
            System.out.println(task.getTitle());
        }

    }

}
