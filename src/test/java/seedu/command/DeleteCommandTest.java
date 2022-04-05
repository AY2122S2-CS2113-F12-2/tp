package seedu.command;

import org.junit.jupiter.api.Test;
import seedu.equipment.DuplicateSerialNumberException;
import seedu.equipment.EquipmentManager;
import seedu.equipment.EquipmentType;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class DeleteCommandTest {
    DeleteCommand deleteCommand;

    @Test
    void execute_validSerialNumber_success() {
        deleteCommand = new DeleteCommand(new ArrayList<>(
                Arrays.asList("s/S1404115ASF")
        ));
        deleteCommand.setEquipmentManager(new EquipmentManager());
        EquipmentManager equipmentManager = deleteCommand.equipmentManager;
        try {
            equipmentManager.addEquipment("Speaker B",
                    "S1404115ASF",
                    EquipmentType.valueOf("SPEAKER"),
                    1000,
                    "Loud Technologies",
                    "2022-02-23");
        } catch (DuplicateSerialNumberException e) {
            fail();
        }

        int equipmentListSize = equipmentManager.getEquipmentList().size();
        assertEquals(1, equipmentListSize);

        CommandResult actualResult = deleteCommand.execute();
        CommandResult expectedResult =
                new CommandResult("Equipment successfully deleted: Speaker B, serial number S1404115ASF");

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void execute_invalidSerialNumber_fail() {
        deleteCommand = new DeleteCommand(new ArrayList<>(
                Arrays.asList("s/S123445ASF")
        ));
        deleteCommand.setEquipmentManager(new EquipmentManager());

        CommandResult actualResult = deleteCommand.execute();
        CommandResult expectedResult =
                new CommandResult("No such serial number, please enter an existing "
                        + "serial number");

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void execute_notSerialNumber_fail() {
        deleteCommand = new DeleteCommand(new ArrayList<>(
                Arrays.asList("n/Speaker3")
        ));
        deleteCommand.setEquipmentManager(new EquipmentManager());

        CommandResult actualResult = deleteCommand.execute();
        CommandResult expectedResult =
                new CommandResult("Only serial number accepted for deleting equipment");

        assertEquals(expectedResult, actualResult);
    }
}