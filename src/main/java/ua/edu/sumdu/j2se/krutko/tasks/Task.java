package ua.edu.sumdu.j2se.krutko.tasks;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Клас Task містить всю інформацію для планування задачі.
 * Задача може бути двох типів:
 * - яка не буде повторюватися. Вона буде призначатися лише на одну дату.
 * - яка буде повторюватися від початку до кінцевої точки з деяким інтервалом.
 */
public class Task implements Cloneable, Serializable {
    private String title;
    private LocalDateTime time;
    private LocalDateTime start;
    private LocalDateTime end;
    private int interval;
    private boolean active;

    /**
     * Конструктор створення неактивної, задачі що не повторюється.
     *
     * @param title назва задачі
     * @param time  час виконання задачі
     */
    public Task(String title, LocalDateTime time) throws IllegalArgumentException {
        if (time == null) {
            throw new IllegalArgumentException("Час не може бути null");
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
    public Task(String title, LocalDateTime start, LocalDateTime end, int interval) throws IllegalArgumentException {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Час не може бути null");
        }
        if (interval < 0) {
            throw new IllegalArgumentException("Інтервал не може бути менше 0");
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

    public LocalDateTime getTime() {
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
    public void setTime(LocalDateTime time) {
        if (time == null) {
            throw new IllegalArgumentException("Час не може бути null");
        }
        if (isRepeated()) {
            setTime(LocalDateTime.MIN, LocalDateTime.MIN, 0);
        }
        this.time = time;
    }

    /**
     * Повертає час виконання для задач, що не повторюються,
     * або час початку виконання для задач, що повторюються.
     *
     * @return час початку, або час виконання задачі.
     */

    public LocalDateTime getStartTime() {
        if (isRepeated()) {
            return this.start;
        } else {
            return this.time;
        }
    }

    public LocalDateTime getEndTime() {
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

    public void setTime(LocalDateTime start, LocalDateTime end, int interval) {
        if (start == null || end == null ) {
            throw new IllegalArgumentException("Час не може бути null");
        }
        if (interval < 0) {
            throw new IllegalArgumentException("Інтервал не може бути менше 0");
        }
        this.start = start;
        this.end = end;
        this.interval = interval;
        this.time = LocalDateTime.MIN;
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
    public LocalDateTime nextTimeAfter(LocalDateTime current) {
        if (current == null) {
            throw new IllegalArgumentException("Час не може бути null");
        }
        if ( current.isAfter(getEndTime()) || !isActive()  ) {
            return null;
        }
        if (!isRepeated()) {
            if (current.isBefore(getStartTime())) {
                return getStartTime();
            }
        } else {
            LocalDateTime tmp = getStartTime();
            while (!tmp.isAfter(getEndTime())) {
                if (tmp.isAfter(current)) {
                    return tmp;
                }
                tmp = tmp.plusSeconds(interval);
            }
        }
        return null;
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
        return  Objects.equals(time,task.time)
                && Objects.equals(start,task.start)
                && Objects.equals(end , task.end)
                && interval == task.interval
                && active == task.active
                && title.equals(task.title);

    }

    @Override
    public int hashCode() {
        int result = title == null ? 0 : title.hashCode();
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result + (end != null ? end.hashCode() : 0);
        result = 31 * result + interval;
        result = 31 * result + (active ? 113 : 0);

        return result;
    }

    @Override
    public String toString() {
        return "Task{"
                + "title='" + title + '\''
                + (time.isAfter(LocalDateTime.MIN) ? ", time=" + time : "")
                + (start == null ? "" : ", start=" + start)
                + (end == null ? "" : ", end=" + end)
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
