package ch.bbcag.imslist;

import static org.junit.Assert.assertEquals;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import ch.bbcag.imslist.model.Todo;

public class TodoTest {

    private Todo todo;
    private Date date;

    @Before
    public void setup() {
        date = new GregorianCalendar(2022, Calendar.AUGUST, 12).getTime();
        todo = new Todo(1, "Java", date, "Hoch", false);
    }

    @Test
    public void test() {
        assertEquals("Todo{id=1, title='Java', deadline=Fri Aug 12 00:00:00 GMT+02:00 2022, priority='Hoch', isChecked=false}", todo.toString());
    }
}
