import org.javatest.command.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CommandBuilderTest {

    @Test
    public void should_ThrowIllegalArgumentException_WhenPassedNullActuator() {
        // given
        CommandBuilder commandBuilder = new CommandBuilder();
        String nullActuator = null;

        // when

        // then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> commandBuilder.withActuator(nullActuator));
    }

    @Test
    public void should_ThrowIllegalArgumentException_WhenPassedBlankActuator() {
        // given
        CommandBuilder commandBuilder = new CommandBuilder();
        String emptyActuator = "";
        String whitespaceActuator = " \n\t ";

        // when

        // then
        Assertions.assertAll(
                () -> Assertions.assertThrows(
                        IllegalArgumentException.class,
                        () -> commandBuilder.withActuator(emptyActuator)
                ),

                () -> Assertions.assertThrows(
                       IllegalArgumentException.class,
                        () -> commandBuilder.withActuator(whitespaceActuator)
                )
        );
    }

    @Test
    public void should_ThrowIllegalArgumentException_WhenPassedNullHandler() {
        // given
        CommandBuilder commandBuilder = new CommandBuilder();
        Handler nullHandler = null;

        // when

        // then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> commandBuilder.withHandler(nullHandler));
    }

    @Test
    public void should_BuildCommand_WhenPassedGoodParams() {
        // given
        CommandBuilder commandBuilder = new CommandBuilder();
        Handler handler = new Handler() {
            @Override
            public void handle(String[] options) {
                return;
            }
        };

        // when
        Command newCommand = commandBuilder
                .withActuator("test")
                .withDescription("No description")
                .withHandler(handler)
                .build();

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals("test", newCommand.getActuator()),
                () -> Assertions.assertEquals("No description", newCommand.getDescription()),
                () -> Assertions.assertEquals(handler, newCommand.getHandler())
        );
    }
}