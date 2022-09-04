package ch.bbcag.imslist;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import ch.bbcag.imslist.converters.Converters;

public class ConvertersTest {

    private Date date;

    @Before
    public void setup() {
        date = new GregorianCalendar(2022, Calendar.APRIL, 1).getTime();
    }

    @Test
    public void test() {
        long timestamp = Converters.dateToTimestamp(date);
        assertEquals(1648764000000l, timestamp);
    }
}
