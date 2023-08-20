import org.javatest.command.CommandPool;
import org.javatest.command.CommandRunner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CommandRunnerTest {

   private static ByteArrayOutputStream outputStream;

    @BeforeAll
    public static void beforeAll() {
        outputStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(outputStream));
    }

    @Test
    public void should_ThrowIllegalArgumentException_WhenCommandPoolIsNullInConstructor() {
        // given
        CommandPool commands = null;

        // when

        // then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new CommandRunner(commands)
        );
    }

    @Test
    public void should_ThrowIllegalArgumentException_WhenArgsInRunMethodIsNull() {
        // given
        String[] args = null;
        CommandPool commands = new CommandPool();
        CommandRunner runner = new CommandRunner(commands);

        // when

        // then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> runner.run(args)
        );
    }

    @Test
    public void should_PrintHelp_WhenArgsLengthInRunMethodIsLessThan1() {
        // given
        String[] args = new String[]{};
        CommandPool commands = new CommandPool();
        CommandRunner runner = new CommandRunner(commands);

        // when
        runner.run(args);

        // then
        Assertions.assertTrue(outputStream.toString().startsWith("Syntax:"));
    }

    @Test
    public void should_PrintCommandNotFound_WhenPassedNonExistentOption() {
        // given
        String[] args = new String[]{"make"};
        CommandPool commands = new CommandPool();
        CommandRunner runner = new CommandRunner(commands);

        // when
        runner.run(args);

        // then
        Assertions.assertTrue(outputStream.toString().startsWith("Command not found"));
    }
}
