import org.javatest.command.repository.Repository;
import org.javatest.command.repository.RepositoryManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RepositoryManagerTest {

    private RepositoryManager<Repository<Object>> repositoryManager;

    @BeforeEach
    public void setup() {
        this.repositoryManager = new RepositoryManager<>();
    }


    @Test
    public void should_ReturnNull_WhenGetInvokedBeforeSet() {
        // given

        // when
        final Repository<Object> ret = repositoryManager.getRepository();

        // then
        Assertions.assertNull(ret);
    }

    @Test
    public void should_ReturnRepository_WhenGetInvokedAfterSet() {
        // given
        final Repository<Object> repository = new Repository<Object>() {
            @Override
            public void save(Object object) {
                return;
            }
        };

        // when
        repositoryManager.setRepository(repository);
        final Repository<Object> ret = repositoryManager.getRepository();

        // then
        Assertions.assertEquals(repository, ret);
    }

    @Test
    public void should_ThrowIllegalArgumentException_WhenSetterIsPassedNull() {
        // given
        final Repository<Object> repository = null;

        // when

        // then
        Assertions.assertThrows(IllegalArgumentException.class, () -> repositoryManager.setRepository(repository));
    }

    public void should_SetRepository_WhenPassedNonNullRepository() {
        // given
        Repository<Object> repository = new Repository<Object>() {
            @Override
            public void save(Object object) {
                return;
            }
        };

        // when
        repositoryManager.setRepository(repository);
        final Repository<Object> ret = repositoryManager.getRepository();

        // then
        Assertions.assertEquals(repository, ret);
    }


}
