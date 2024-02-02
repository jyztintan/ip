package tasks;

import exceptions.DukeException;

import java.util.ArrayList;

/**
 * Represents a list of tasks in the task management application.
 * This class encapsulates operations such as adding, removing, marking tasks as done, and listing all tasks.
 */
public class TaskList {


    private ArrayList<Task> taskList;// The list of tasks

    /**
     * Constructs a TaskList with the specified list of tasks.
     *
     * @param taskList The initial list of tasks.
     */
    public TaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    /**
     * Returns the list of tasks.
     *
     * @return The list of tasks.
     */
    public ArrayList<Task> getTasks() {
        return this.taskList;
    }

    /**
     * Displays all tasks in the list.
     * If the list is empty, it prints a message indicating there are no tasks.
     */
    public void yapTasks() {
        if (taskList.size() == 0) {
            System.out.println("Nothin' to yap...");
        }
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println((i+1)+". "+taskList.get(i));
        }
    }

    /**
     * Marks a task as done.
     *
     * @param index The index of the task in the list to be marked as done, starting from 1.
     */
    public void markTaskAsDone(int index) {
        Task task = taskList.get(index - 1);
        task.markDone(false);
    }

    /**
     * Marks a task as done.
     *
     * @param index The index of the task in the list to be marked as done, starting from 1.
     */
    public void unmarkTaskAsDone(int index) {
        Task task = taskList.get(index - 1);
        task.unmarkDone();
    }

    /**
     * Adds a task to the list.
     *
     * @param task The task to be added to the list.
     */
    public void addTasktoTaskList(Task task) {
        if (task == null) {
            return;
        }
        taskList.add(task);
    }

    /**
     * Removes a task from the list.
     *
     * @param index The index of the task in the list to be removed, starting from 1.
     * @return The task that was removed.
     */
    public Task removeTaskfromTaskList(int index) {
        Task task = taskList.remove(index - 1);
        return task;
    }

    /**
     * Initializes a task based on the input message and task type.
     * This method attempts to parse the input and create the appropriate task object.
     *
     * @param message The input message containing task details.
     * @param taskType The type of the task (e.g., "todo", "deadline", "event").
     * @return The initialized task, or null if the task could not be created due to invalid input.
     */
    public Task initTask(String message, String taskType) {
        Task task;
        try {
            switch (taskType) {
            case "todo":
                try {
                    String[] inputs = message.split("todo ");
                    task = new ToDo(inputs[1]);
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new DukeException("Whats the task, yapper???");
                }
                break;
            case "deadline":
                try {
                    message = message.substring("deadline ".length());
                    String[] inputs = message.split("/by");
                    task = new Deadline(inputs[0].trim(), inputs[1].trim());
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new DukeException("Deadlines need a deadline, yapper!");
                } catch (StringIndexOutOfBoundsException e ) {
                    throw new DukeException("Whats the task, yapper???");
                }
                break;
            case "event":
                try {
                    message = message.substring("event ".length());
                    String[] inputs = message.split("/from");
                    String[] innerInputs = inputs[1].split("/to");
                    task = new Event(inputs[0].trim(), innerInputs[0].trim(), innerInputs[1].trim());
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new DukeException("YAPYAP, What time is your from and to?");
                } catch (StringIndexOutOfBoundsException e) {
                    throw new DukeException("Whats the task, yapper???");
                }
            default:
                //should not reach here because of filter in main logic
                task = new Task(message);
            }
        } catch (DukeException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return task;
    }

    public TaskList filter(String queryString) {
        ArrayList<Task> newList = new ArrayList<>();
        for (Task task : this.taskList) {
            if (task.getDescription().contains(queryString)) {
                newList.add(task);
            }
        }
        return new TaskList(newList);
    }

}
