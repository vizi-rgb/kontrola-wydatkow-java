import org.javatest.command.MyDateFormat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MyDateFormatTest {

    @Test
    public void should_PadTheDate_WhenDayLessThan10() {
        // given
        int day = 9, month = 12, year = 1990;
        String expected = "09.12.1990";

        // when
        MyDateFormat date = new MyDateFormat(day, month, year);

        // then
        Assertions.assertEquals(expected, date.toString());
    }

    @Test
    public void should_PadTheMonth_WhenMonthLessThan10() {
        // given
        int day = 20, month = 9, year = 1990;
        String expected = "20.09.1990";

        // when
        MyDateFormat date = new MyDateFormat(day, month, year);

        // then
        Assertions.assertEquals(expected, date.toString());
    }

    @Test
    public void should_PadBothDayAndMonth_WhenDayAndMonthBothLessThan10() {
        // given
        int day = 9, month = 9, year = 1990;
        String expected = "09.09.1990";

        // when
        MyDateFormat date = new MyDateFormat(day, month, year);

        // then
        Assertions.assertEquals(expected, date.toString());
    }

    @Test
    public void should_NotPadAnything_WhenDayAndMonthGreaterThan10() {
        // given
        int day = 12, month = 12, year = 1990;
        String expected = "12.12.1990";

        // when
        MyDateFormat date = new MyDateFormat(day, month, year);

        // then
        Assertions.assertEquals(expected, date.toString());
    }

    @Test
    public void should_ThrowIllegalArgumentException_WhenDayLessThan1() {
        // given
        int day = 0, month = 1, year = 1990;

        // when

        // then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new MyDateFormat(day, month, year)
        );
    }

    @Test
    public void should_ThrowIllegalArgumentException_WhenMonthLessThan1() {
        // given
        int day = 1, month = 0, year = 1990;

        // when

        // then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new MyDateFormat(day, month, year)
        );
    }

    @Test
    public void should_ThrowIllegalArgumentException_WhenDayGreaterThan31() {
        // given
        int day = 32, month = 1, year = 1990;

        // when

        // then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new MyDateFormat(day, month, year)
        );
    }

    @Test
    public void should_ThrowIllegalArgumentException_WhenMonthGreaterThan12() {
        // given
        int day = 1, month = 13, year = 1990;

        // when

        // then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new MyDateFormat(day, month, year)
        );
    }
}
