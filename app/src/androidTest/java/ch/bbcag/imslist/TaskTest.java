package ch.bbcag.imslist;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ch.bbcag.imslist.model.Task;

public class TaskTest {

    private Task task;

    @Before
    public void setup() {
        task = new Task(1, 1, "Kompetenz 3.2");
    }

    @Test
    public void test() {
        assertEquals("Task{id=1, todoId=1, aim='Kompetenz 3.2'}", task.toString());
    }
}
