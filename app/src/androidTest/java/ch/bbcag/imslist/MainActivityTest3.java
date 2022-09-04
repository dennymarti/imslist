package ch.bbcag.imslist;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest3 {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mainActivityTest3() {
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.create_activity),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction textInputEditText = onView(
                childAtPosition(
                        childAtPosition(
                                withId(R.id.textfield_title),
                                0),
                        0));
        textInputEditText.perform(scrollTo(), replaceText("PHP"), closeSoftKeyboard());

        ViewInteraction materialAutoCompleteTextView = onView(
                allOf(withId(R.id.textview_date),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.textfield_date),
                                        0),
                                0)));
        materialAutoCompleteTextView.perform(scrollTo(), click());

        ViewInteraction materialButton = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton.perform(scrollTo(), click());

        ViewInteraction materialAutoCompleteTextView2 = onView(
                allOf(withId(R.id.textview_time),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.textfield_time),
                                        0),
                                0)));
        materialAutoCompleteTextView2.perform(scrollTo(), click());

        ViewInteraction chip = onView(
                // id: material_timepicker_ok_button existiert nicht weil beim TimePicker Dialog keine Id gesetzt wurde und es kann keine gesetzt werden!
                allOf(withId(androidx.appcompat.R.id.material_hour_tv), withText("00"),
                        childAtPosition(
                                allOf(withId(androidx.appcompat.R.id.material_clock_display),
                                        childAtPosition(
                                                withId(androidx.appcompat.R.id.material_timepicker_view),
                                                0)),
                                0),
                        isDisplayed()));
        chip.perform(click());

        ViewInteraction chip2 = onView(
                allOf(withId(androidx.appcompat.R.id.material_hour_tv), withText("00"),
                        childAtPosition(
                                allOf(withId(androidx.appcompat.R.id.material_clock_display),
                                        childAtPosition(
                                                withId(androidx.appcompat.R.id.material_timepicker_view),
                                                0)),
                                0),
                        isDisplayed()));
        chip2.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(androidx.appcompat.R.id.material_timepicker_ok_button), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction materialAutoCompleteTextView3 = onView(
                allOf(withId(R.id.dropdown_priority),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.textfield_priority),
                                        0),
                                0)));
        materialAutoCompleteTextView3.perform(scrollTo(), click());

        DataInteraction materialTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(2);
        materialTextView.perform(click());

        ViewInteraction textInputEditText2 = onView(
                childAtPosition(
                        childAtPosition(
                                withId(R.id.textfield_task1),
                                0),
                        0));
        textInputEditText2.perform(scrollTo(), replaceText("MVC Projekt"), closeSoftKeyboard());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.submit_button), withText("Hinzuf�gen"),
                        childAtPosition(
                                allOf(withId(R.id.container),
                                        childAtPosition(
                                                withClassName(is("android.widget.ScrollView")),
                                                0)),
                                7)));
        materialButton3.perform(scrollTo(), click());

        ViewInteraction button = onView(
                allOf(withId(R.id.todo_button),
                        childAtPosition(
                                allOf(withId(R.id.todo_listitem),
                                        withParent(withId(R.id.todolist))),
                                2),
                        isDisplayed()));
        button.perform(click());

        ViewInteraction button2 = onView(
                allOf(withId(R.id.todo_finishedBtn), withText("Erledigen"),
                        childAtPosition(
                                allOf(withId(R.id.todo_menu),
                                        childAtPosition(
                                                withId(R.id.todo_listitem),
                                                3)),
                                0),
                        isDisplayed()));
        button2.perform(click());

        ViewInteraction button3 = onView(
                allOf(withId(R.id.todo_finishedBtn), withText("Undo"),
                        childAtPosition(
                                allOf(withId(R.id.todo_menu),
                                        childAtPosition(
                                                withId(R.id.todo_listitem),
                                                3)),
                                0),
                        isDisplayed()));
        button3.perform(click());

        ViewInteraction button4 = onView(
                allOf(withId(R.id.todo_delete_button), withText("L�schen"),
                        childAtPosition(
                                allOf(withId(R.id.todo_menu),
                                        childAtPosition(
                                                withId(R.id.todo_listitem),
                                                3)),
                                1),
                        isDisplayed()));
        button4.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
