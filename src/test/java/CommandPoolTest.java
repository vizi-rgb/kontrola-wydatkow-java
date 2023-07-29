import org.javatest.command.Command;
import org.javatest.command.CommandBuilder;
import org.javatest.command.CommandPool;
import org.javatest.command.Handler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CommandPoolTest {

    @Test
    public void should_ThrowNullPointerException_WhenPassedNullCommand() {
        // given
        Command command = null;
        CommandPool commands = new CommandPool();

        // when

        // then
        Assertions.assertThrows(
                NullPointerException.class,
                () -> commands.add(command)
        );
    }

    @Test
    public void should_AddCommand_WhenPassedSingleCommand() {
        // given
        CommandBuilder builder = new CommandBuilder();
        CommandPool commands = new CommandPool();
        Handler handler = new Handler() {
            @Override
            public void handle(String[] options) {
                return;
            }
        };

        builder.withActuator("test").withDescription("desc").withHandler(handler);
        Command command = new Command(builder);

        // when
        commands.add(command);

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, commands.getPoolSize()),
                () -> {

                    CommandPool pool = new CommandPool();
                    for (Command c: pool) {
                        Assertions.assertTrue(c.getActuator().equals("test"));
                    }
                }
        );
    }

    @Test
    public void should_AddCommands_WhenPassedMultipleCommands() {
        // given
        CommandBuilder builder = new CommandBuilder();
        CommandPool commands = new CommandPool();
        Handler handler = new Handler() {
            @Override
            public void handle(String[] options) {
                return;
            }
        };


        Command c1 = builder.withActuator("test").withDescription("desc").withHandler(handler).build();
        Command c2 = builder.withActuator("test1").withDescription("desc").withHandler(handler).build();
        Command c3 = builder.withActuator("test2").withDescription("desc").withHandler(handler).build();

        // when
        commands.add(c1, c2, c3);
        commands.add(new Command[]{c3, c2, c1});

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(6, commands.getPoolSize())
        );
    }
}
