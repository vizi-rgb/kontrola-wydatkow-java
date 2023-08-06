import org.javatest.command.repository.CRUDExpenseRepository;
import org.javatest.command.repository.Expense;
import org.junit.jupiter.api.Test;

public class CRUDExpenseRepositoryTest {

    private final CRUDExpenseRepository crudExpenseRepository = new CRUDExpenseRepository();

    @Test
    public void test() {
        Expense[] expense = {
                new Expense(170, "Nothing", "23.06.2022"),
                new Expense(2451, "Hello", "15.06.2022"),
                new Expense(156.66, "Hello", "05.01.2020"),
                new Expense(12.33, "SMELLO", "15.06.2022"),
                new Expense(100000.34, "Ferrarka", "06.01.1999")
        };
        for (Expense e : expense) {
            crudExpenseRepository.save(e);
        }
    }

    @Test
    public void test_delete() {
        crudExpenseRepository.deleteById(1);
    }

    @Test
    public void test_get() {
        crudExpenseRepository.getById(3).ifPresent(System.out::println);
    }


}
