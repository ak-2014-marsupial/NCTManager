package ua.edu.sumdu.j2se.krutko.tasks;


public class Main {

    public static void main(String[] args) {

        Task task1 = new Task("Обід із гарною дівчиною", (24 + 8 * 30) * 24 + 16);
        Task task2 = new Task("Ранкова пробіжка", 3 * 30 * 24, 9 * 30 * 24, 24);
        Task task3 = new Task("Приймання ліків", (20 + 8 * 30) * 24, (28 + 8 * 30) * 24, 12);
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
        int from = (25 + 8 * 30) * 24 + 8;
        int to = (26 + 8 * 30) * 24 + 8;

        System.out.println("List of Task from ArrayTaskList");
        for (int i = 0; i < arrayTaskList.size(); i++) {
            Task task = arrayTaskList.getTask(i);
            System.out.printf("     %s  %s \n" , task.getTitle() , task.getStartTime());
        }
        System.out.printf("LinkedList incoming from %s  to %s :\n", from, to);
        AbstractTaskList list2 = arrayTaskList.incoming(from, to);
        for (int i = 0; i < list2.size(); i++) {
            System.out.printf("   %s \n",list2.getTask(i).getTitle());
        }
        System.out.println("--------------");


        System.out.println("List of Task from LinkedTaskList");
        LinkedTaskList linkedTaskList = new LinkedTaskList();
        linkedTaskList.add(task1);
        linkedTaskList.add(task2);
        linkedTaskList.add(task3);
        linkedTaskList.add(task4);

        for (int i = 0; i < linkedTaskList.size(); i++) {
            Task task = linkedTaskList.getTask(i);
            System.out.printf("     %s  %s \n" , task.getTitle() , task.getStartTime());
        }

        System.out.printf("LinkedList incoming from %s  to %s : \n", from, to);
        AbstractTaskList list1 = linkedTaskList.incoming(from, to);
        for (int i = 0; i < list1.size(); i++) {
            System.out.printf("   %s \n",list1.getTask(i).getTitle());
        }


    }

}
