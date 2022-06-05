package ua.edu.sumdu.j2se.krutko.tasks;

/**
 * Клас Task містить всю інформацію для планування задачі.
 * Задача може бути двох типів:
 * - яка не буде повторюватися. Вона буде призначатися лише на одну дату.
 * - яка буде повторюватися від початку до кінцевої точки з деяким інтервалом.
 */
public class Task implements Cloneable {
    private String title;
    private int time;
    private int start;
    private int end;
    private int interval = 0;
    private boolean active = false;

    /**
     * Конструктор створення неактивної, задачі що не повторюється.
     *
     * @param title назва задачі
     * @param time  час виконання задачі
     */
    public Task(String title, int time) {
        if (time < 0) {
            throw new IllegalArgumentException("Час не може бути менше за 0");
        }
        this.title = title;
        this.time = time;
    }


    /**
     * Конструктор створення неактивної, повторюваної задачі.
     *
     * @param title    Назва задачі
     * @param start    Час початку задачі
     * @param end      Час закінчення задачі
     * @param interval інтервал повторення задачі
     */
    public Task(String title, int start, int end, int interval) {
        if (start < 0 || end < 0 || interval < 0) {
            throw new IllegalArgumentException("Час не може бути менше за 0");
        }
        this.title = title;
        setTime(start, end, interval);
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getTime() {
        if (isRepeated()) {
            return this.start;
        } else {
            return this.time;
        }
    }

    /**
     * Встановлює час для задачі, що не повторюється.
     * якщо це було повторюване завдання, воно стає таким, що не повторюється
     *
     * @param time - час виконання задачі
     */
    public void setTime(int time) {
        if (time < 0) {
            throw new IllegalArgumentException("Час не може бути менше за 0");
        }
        if (isRepeated()) {
            setTime(0, 0, 0);
        }
        this.time = time;
    }

    /**
     * Повертає час виконання для задач, що не повторюються,
     * або час початку виконання для задач, що повторюються.
     *
     * @return час початку, або час виконання задачі.
     */

    public int getStartTime() {
        if (isRepeated()) {
            return this.start;
        } else {
            return this.time;
        }
    }

    public int getEndTime() {
        if (isRepeated()) {
            return this.end;
        } else {
            return this.time;
        }
    }

    public int getRepeatInterval() {
        if (isRepeated()) {
            return this.interval;
        } else {
            return 0;
        }
    }

    public void setTime(int start, int end, int interval) {
        if (start < 0 || end < 0 || interval < 0) {
            throw new IllegalArgumentException("Час не може бути менше за 0");
        }
        this.start = start;
        this.end = end;
        this.interval = interval;
        this.time = 0;
    }

    public boolean isRepeated() {
        return this.interval != 0;
    }

    /**
     * Розраховує наступний момент виконання задачі відносно current часу.
     *
     * @param current час відносно, якого проводяться обчислення
     * @return Повертає наступний момент виконання задачі відносно current часу,
     * якщо current час більше за getEndTime, повертає -1
     * якщо current час менше за getStartTime, повертає getStartTime
     */
    public int nextTimeAfter(int current) {
        if (current < 0) {
            throw new IllegalArgumentException("Час не може бути менше за 0");
        }
        if (current >= this.getEndTime() || !this.isActive()) {
            return -1;
        } else if (current < this.getStartTime()) {
            return this.getStartTime();
        } else {
            int tmp = this.getStartTime();
            while (tmp < this.getEndTime()) {
                if (tmp <= current) {
                    tmp += this.interval;
                } else {
                    break;
                }
            }
            return tmp < this.getEndTime() ? tmp : -1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Task task = (Task) o;
        return time == task.time
                && start == task.start
                && end == task.end
                && interval == task.interval
                && active == task.active
                && title.equals(task.title);

    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + time;
        result = 31 * result + start;
        result = 31 * result + end;
        result = 31 * result + interval;
        result = 31 * result + (active ? 1 : 0);

        return result;
    }

    @Override
    public String toString() {
        return "Task{"
                + "title='" + title + '\''
                + (time == 0 ? "" : ", time=" + time)
                + (start == 0 ? "" : ", start=" + start)
                + (end == 0 ? "" : ", end=" + end)
                + (interval == 0 ? "" : ", interval=" + interval)
                + ", active=" + active + '}';
    }

    @Override
    public Task clone() throws CloneNotSupportedException {
        Task taskClone = (Task) super.clone();
        taskClone.active = active;
        taskClone.title = title;
        taskClone.interval = interval;
        taskClone.end = end;
        taskClone.start = start;
        taskClone.time = time;
        return taskClone;
    }
}
