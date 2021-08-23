public class Parser {
    public static Command parseInput(String userInput) throws DukeException {
        //TODO need to catch DukeException and IndexOutOfBoundsException somewhere
        String[] inputSplit = userInput.split(" ", 2);
        String command = inputSplit[0];

        if (command.equals("bye")) {
            return new ExitCommand();

        } else if (command.equals("list")) {
            return new ListCommand();

        } else if (command.equals(Todo.taskTag())) {
            if (checkInputLength(inputSplit)) {
                throw DukeException.missingInput("todo");
            }
            String taskName = inputSplit[1];
            return new AddCommand(Todo.taskTag(), taskName, null);

        } else if (command.equals(Deadline.taskTag())) {
            if (checkInputLength(inputSplit)) {
                throw DukeException.missingInput("deadline");
            }
            String args = inputSplit[1];
            String[] argsSplit = args.split(" /by ", 2);
            if (checkInputLength(argsSplit)) {
                throw new DukeException("☹ OOPS!!! Please indicate when the deadline for the task is.");
            }
            return new AddCommand(Deadline.taskTag(), argsSplit[0], argsSplit[1]);

        } else if (command.equals(Event.taskTag())) {
            if (checkInputLength(inputSplit)) {
                throw DukeException.missingInput("event");
            }
            String args = inputSplit[1];
            String[] argsSplit = args.split(" /at ", 2);
            if (checkInputLength(argsSplit)) {
                throw new DukeException("☹ OOPS!!! Please indicate the start and end time of the event.");
            }
            return new AddCommand(Event.taskTag(), argsSplit[0], argsSplit[1]);

        } else if (command.equals("done")) {
            if (checkInputLength(inputSplit)) {
                throw new DukeException("☹ OOPS!!! Please indicate which task you want to delete.");
            }
            String strTaskNum = inputSplit[1].split(" ")[0];
            int taskNum = Integer.parseInt(strTaskNum) - 1;
            return new DoneCommand(taskNum);
        } else if (command.equals("delete")) {
            if (checkInputLength(inputSplit)) {
                throw new DukeException("☹ OOPS!!! Please indicate which task you want to delete.");
            }
            String strTaskNum = inputSplit[1].split(" ")[0];
            int taskNum = Integer.parseInt(strTaskNum) - 1;
            return new DeleteCommand(taskNum);
        } else {
            return new UnknownCommand();
        }
    }

    private static boolean checkInputLength(String[] inputSplit) {
        return inputSplit.length < 2;
    }
}
