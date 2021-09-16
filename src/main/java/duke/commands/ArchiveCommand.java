package duke.commands;

import duke.functionality.Storage;
import duke.functionality.Ui;
import duke.tasks.Task;
import duke.tasks.TaskList;

/**
 * Represents the command when the user wants to archive certain tasks.
 */
public class ArchiveCommand extends Command {
    private int[] arrOfTaskNums;

    public ArchiveCommand (int[] arrOfTaskNums) {
        this.arrOfTaskNums = arrOfTaskNums;
    }

    /**
     * Executes the command and archives the task. Deletes it from main storage list.
     *
     * @param storage The storage system of the bot involved with this command.
     * @param ui The ui of the bot involved with this command.
     * @return Message indicating whether the task has been successfully archived.
     */
    public String execute(Storage storage, Ui ui) {
        int taskListLen;
        int lenBeforeDelete;
        int archiveTaskListLen;

        for (int i = 0; i < arrOfTaskNums.length; i++) {
            try {
                archiveTaskListLen = storage.archiveListLen();
                if (archiveTaskListLen >= TaskList.MAX_TASKS) {
                    return ui.maxArchiveTaskReachedMessage();
                }

                // Delete from main task list
                taskListLen = storage.taskListLen();
                if (i + 1 > taskListLen) {
                    return ui.missingTaskMessage(i + 1);
                }
                lenBeforeDelete = taskListLen;
                Task taskToArchive = storage.deleteTask(i);
                storage.saveToFile();
                assert(lenBeforeDelete == storage.taskListLen() + 1)
                        : "Task has not been deleted properly from storage during execution of ArchiveCommand.";


                // Add to archive list
                storage.archiveTask(taskToArchive);
                storage.saveToArchive();
                assert(storage.archiveListLen() == archiveTaskListLen + 1)
                        : "Task has not been added properly to archive storage during execution of ArchiveCommand.";

            } catch (IndexOutOfBoundsException e) {
                return Ui.showErrorMessage("Please enter a valid index!");
            }
        }

        return ui.tasksArchivedMessage();

    }
}
