import org.javatest.command.ExpenseSaverManager;
import org.javatest.command.DBExpenseSaver;
import org.javatest.command.ExpenseSaver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExpenseSaverManagerTest {

    @Test
    public void should_ThrowIllegalArgumentException_WhenTryingToOverridePreviouslyAssignedExpenseSaver() {
        // given
        ExpenseSaver<Record> expenseSaver1 = new DBExpenseSaver<>();
        ExpenseSaver<Record> expenseSaver2 = new DBExpenseSaver<>();

        // when

        // then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    ExpenseSaverManager.setExpenseSaver(expenseSaver1);
                    ExpenseSaverManager.setExpenseSaver(expenseSaver2);
                }
        );
    }

    @Test
    public void should_ReturnNull_WhenUsingGetBeforeExpenseSaverIsSet() {
        // given

        // when
        ExpenseSaver<Record> ret = ExpenseSaverManager.getExpenseSaver();

        // then
        Assertions.assertNull(ret);
    }

    @Test
    public void should_ReturnExpenseSaver_WhenUsingGetExpanseSaverAndExpenseSaverWasSet() {
        // given
        ExpenseSaver<Record> expenseSaver = new DBExpenseSaver<>();

        // when
        ExpenseSaverManager.setExpenseSaver(expenseSaver);
        ExpenseSaver<Record> ret = ExpenseSaverManager.getExpenseSaver();

        // then
        Assertions.assertEquals(expenseSaver, ret);
    }
}
