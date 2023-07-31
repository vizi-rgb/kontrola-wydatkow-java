import org.javatest.command.Command;
import org.javatest.command.CommandBuilder;
import org.javatest.command.Handler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CommandTest {

    @Test
    public void should_ThrowIllegalArgumentException_WhenPassedNullBuilder() {
        // given
        CommandBuilder builder = null;

        // when

        // then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new Command(builder)
        );
    }

    @Test
    public void should_MakeAValidCommand_WhenPassedValidBuilder() {
        // given
        CommandBuilder builder = new CommandBuilder();
        Handler handler = new Handler() {
            @Override
            public void handle(String[] options) {
                return;
            }
        };

        // when
        Command command = builder.withActuator("get").withDescription("desc").withHandler(handler).build();

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals("get", command.getActuator()),
                () -> Assertions.assertEquals("desc", command.getDescription()),
                () -> Assertions.assertEquals(handler, command.getHandler())
        );
    }
}
