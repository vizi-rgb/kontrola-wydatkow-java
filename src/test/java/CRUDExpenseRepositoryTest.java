import org.javatest.command.repository.CRUDExpenseRepository;
import org.javatest.command.repository.Expense;
import org.junit.jupiter.api.*;

import java.io.File;
import java.nio.file.Path;

public class CRUDExpenseRepositoryTest {

    private String testRepositoryName;
    private CRUDExpenseRepository crudExpenseRepository;

    @BeforeEach
    public void beforeAll() {
        testRepositoryName = "expenses-test";
        crudExpenseRepository = new CRUDExpenseRepository(testRepositoryName);
    }

    @AfterEach
    public void afterAll() {
        String path = String.valueOf(Path.of("src", "db", testRepositoryName));
        File databaseFile = new File(path);

        if (databaseFile.exists() && !databaseFile.isDirectory()) {
            databaseFile.delete();
        }
    }

    @Test
    public void should_AddExpense_WhenSaveInvoked() {
        // given
        Expense expense = new Expense(1L, 0.50, "test", "02.10.1993");

        // when
        crudExpenseRepository.save(expense);
        Expense ret = crudExpenseRepository.getById(1L)
                .orElse(new Expense(0, "", ""));

        // then
        Assertions.assertEquals(expense, ret);
    }

    @Test
    public void should_DeleteExpense_WhenDeleteInvoked() {
        // given
        Expense expense = new Expense(1L, 0.50, "test", "02.10.1993");
        crudExpenseRepository.save(expense);

        // when
        crudExpenseRepository.deleteById(1L);
        Expense expected = new Expense(0L, 0, null, null);

        // then
        Assertions.assertEquals(expected, crudExpenseRepository.getById(1L).get());
    }
}
