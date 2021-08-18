import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Duke {
    private final int MAX_TASKS = 100;
    private static List<Task> taskList = new ArrayList<>();

    private static String addTask(String taskName) {
        if (taskList.size() < 100) {
            taskList.add(new Task(taskName));
            return "added: " + taskName;
        } else {
            return "Sorry! You have max number of tasks stored already.";
        }
    }

    private static String printTaskList() {
        int listLen = taskList.size();
        String msg = "Here are the tasks in your list:";
        for (int i = 0; i < listLen; i++) {
            int currTaskNum = i + 1;
            msg = msg + "\n" + currTaskNum + ". " + taskList.get(i).toString();
        }
        return msg;
    }

    private static String markTaskDone(int taskNum) {
        Task taskToMark = taskList.get(taskNum);
        taskToMark.markDone();
        return "Nice! I've marked this task as done: " + "\n  " + taskToMark.toString();
    }

    private static String addTodo(String taskName) {
        int taskListLen = taskList.size();
        if (taskListLen < 100) {
            ToDo newTodo = new ToDo(taskName);
            taskList.add(newTodo);
            taskListLen += 1;
            return String.format("Got it. I've added this task:\n  %s\nNow you have %d tasks in the list.",
                    newTodo.toString(), taskListLen);
        } else {
            return "Sorry! You have max number of tasks stored already.";
        }
    }

    private static String addDeadline(String taskName, String byWhen) {
        int taskListLen = taskList.size();
        if (taskListLen < 100) {
            Deadline newDeadline = new Deadline(taskName, byWhen);
            taskList.add(newDeadline);
            taskListLen += 1;
            return String.format("Got it. I've added this task:\n  %s\nNow you have %d tasks in the list.",
                    newDeadline.toString(), taskListLen);
        } else {
            return "Sorry! You have max number of tasks stored already.";
        }
    }

    private static String addEvent(String taskName, String when) {
        int taskListLen = taskList.size();
        if (taskListLen < 100) {
            Event newEvent = new Event(taskName, when);
            taskList.add(newEvent);
            taskListLen += 1;
            return String.format("Got it. I've added this task:\n  %s\nNow you have %d tasks in the list.",
                    newEvent.toString(), taskListLen);
        } else {
            return "Sorry! You have max number of tasks stored already.";
        }
    }


    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);

        Scanner sc = new Scanner(System.in);
        System.out.println("Duke is gone. Hello, this is Duchess.\nHow can I help you?");

        while (true) {
            String user_input = sc.nextLine();
            String command = user_input.split(" ")[0];
            if (command.equals("bye")) {
                System.out.println("It has been a pleasure, goodbye!");
                sc.close();
                break;
            } else if (command.equals("todo")) {
                String taskName = user_input.split(" ", 2)[1];
                System.out.println(addTodo(taskName));
            } else if (command.equals("deadline")) {
                String msg = user_input.split(" ", 2)[1];
                String[] msg_split = msg.split(" /by ");
                System.out.println(addDeadline(msg_split[0], msg_split[1]));
            } else if (command.equals("event")) {
                String msg = user_input.split(" ", 2)[1];
                String[] msg_split = msg.split(" /at ");
                System.out.println(addEvent(msg_split[0], msg_split[1]));
            } else if (command.equals("list")) {
                System.out.println(printTaskList());
            } else if (command.equals("done")) {
                int taskNum = Integer.parseInt(user_input.split(" ")[1]) - 1;
                System.out.println(markTaskDone(taskNum));
            } else {
                System.out.println(addTask(user_input));
            }
        }
    }
}
