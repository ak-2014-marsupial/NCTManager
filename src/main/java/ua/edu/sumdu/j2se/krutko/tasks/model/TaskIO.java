package ua.edu.sumdu.j2se.krutko.tasks.model;


import com.google.gson.Gson;

import java.io.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class TaskIO {
    private TaskIO() {
    }

    public static void write(AbstractTaskList tasks, OutputStream out) throws IOException {
        try (DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(out))) {
            outputStream.writeInt(tasks.size());
            for (Task task : tasks) {
                outputStream.writeInt(task.getTitle().length());
                outputStream.writeUTF(task.getTitle());
                if (task.isActive()) {
                    outputStream.writeInt(1);
                } else {
                    outputStream.writeInt(0);
                }
                int interval = task.getRepeatInterval();
                outputStream.writeInt(interval);
                Long timeStart = convertLocalDateTimeToLong(task.getStartTime());
                outputStream.writeLong(timeStart);
                if (interval != 0) {
                    long timeEnd = convertLocalDateTimeToLong(task.getEndTime());
                    outputStream.writeLong(timeEnd);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Long convertLocalDateTimeToLong(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneOffset.UTC).toInstant().toEpochMilli();
    }

    public static void read(AbstractTaskList tasks, InputStream input) {

        try (DataInputStream inputStream = new DataInputStream(new BufferedInputStream(input))) {
            Task task;
            int size = inputStream.readInt();
            for (int i = 0; i < size; i++) {
                int length = inputStream.readInt();
                String title = inputStream.readUTF();
                int isActive = inputStream.readInt();
                int interval = inputStream.readInt();
                boolean active = isActive == 1;
                LocalDateTime timeStart = convertToLocalDateTimeFromLong(inputStream.readLong());
                if (interval == 0) {
                    task = new Task(title, timeStart);
                } else {
                    LocalDateTime timeEnd = convertToLocalDateTimeFromLong(inputStream.readLong());
                    task = new Task(title, timeStart, timeEnd, interval);
                }
                task.setActive(active);
                tasks.add(task);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static LocalDateTime convertToLocalDateTimeFromLong(Long dateLong) {
        return Instant.ofEpochMilli(dateLong).atZone(ZoneOffset.UTC).toLocalDateTime();
    }

    public static void writeBinary(AbstractTaskList tasks, File file) {
        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
            write(tasks, outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void readBinary(AbstractTaskList tasks, File file) {
        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
            read(tasks, inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void write(AbstractTaskList tasks, Writer out) {
        Gson gson = new Gson();
        String json = gson.toJson(tasks);
        try {
            out.write(json);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void read(AbstractTaskList tasks, Reader in) {
        Gson gson = new Gson();
        AbstractTaskList tasksTmp = gson.fromJson(in, tasks.getClass());
        tasksTmp.forEach(tasks::add);
    }

    public static void writeText(AbstractTaskList tasks, File file) {
        try (FileWriter writer = new FileWriter(file)) {
            write(tasks, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void readText(AbstractTaskList tasks, File file) {
        try (FileReader reader = new FileReader(file)) {
            read(tasks, reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
