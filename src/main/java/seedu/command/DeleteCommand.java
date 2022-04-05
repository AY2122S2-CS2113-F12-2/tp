package seedu.command;

import seedu.equipment.EquipmentType;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Subclass of Command. Handles deleting of equipment from equipmentInventory.
 */
public class DeleteCommand extends Command {
    private final ArrayList<String> commandStrings;
    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_DESCRIPTION = ": Deletes the equipment with the specified serial number. "
            + System.lineSeparator()
            + "Parameters: s/SERIAL_NUMBER" + System.lineSeparator()
            + "Example: "
            + "delete s/SM57-1";
    public static final String ONLY_SN_ACCEPTED = "Only serial number accepted for deleting equipment";

    /**
     * constructor for DeleteCommand. Initialises successMessage and usageReminder from Command.
     *
     * @param commandStrings parsed user input which contains details of equipment to be deleted
     */
    public DeleteCommand(ArrayList<String> commandStrings) {
        this.commandStrings = commandStrings;
        successMessage = "Equipment successfully deleted: %1$s, serial number %2$s";
        usageReminder = COMMAND_WORD + COMMAND_DESCRIPTION;
    }

    /**
     * Deletes equipment specified by serial number.
     *
     * @return CommandResult with message from execution of this command
     */
    public CommandResult execute() {
        String equipmentName;
        String serialNumber;

        try {
            serialNumber = prepareDelete();
            if (serialNumber.equals("")) {
                return new CommandResult(ONLY_SN_ACCEPTED);
            }
            equipmentName = equipmentManager.getEquipmentList().get(serialNumber).getItemName();
        } catch (NullPointerException e) {
            return new CommandResult(INVALID_SERIAL_NUMBER);
        } catch (AssertionError e) {
            return new CommandResult(ONLY_SN_ACCEPTED);
        }

        equipmentManager.deleteEquipment(serialNumber);

        return new CommandResult(String.format(successMessage, equipmentName, serialNumber));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DeleteCommand)) {
            return false;
        }
        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return this.commandStrings.equals(otherDeleteCommand.commandStrings);
    }


    protected String prepareDelete() throws AssertionError {
        String argString = commandStrings.get(0);
        int delimiterPos = argString.indexOf('/');
        // the case where delimiterPos = -1 is impossible as
        // ARGUMENT_FORMAT and ARGUMENT_TRAILING_FORMAT regex requires a '/'
        assert delimiterPos != -1 : "Each args will need to include minimally a '/' to split arg and value upon";
        String argType = argString.substring(0, delimiterPos);
        String argValue = argString.substring(delimiterPos + 1);
        assert argType.equals("s") : ONLY_SN_ACCEPTED;
        if (argType.equals("s")) {
            return argValue;
        }
        return "";
    }
}
