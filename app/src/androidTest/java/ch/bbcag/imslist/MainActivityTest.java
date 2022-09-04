package ch.bbcag.imslist;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
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
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

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

    @Test
    public void mainActivityTest() {
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
        textInputEditText.perform(scrollTo(), replaceText("Java"), closeSoftKeyboard());

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

        ViewInteraction materialButton2 = onView(
                // id: material_timepicker_ok_button existiert nicht weil beim TimePicker Dialog keine Id gesetzt wurde und es kann keine gesetzt werden!
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
                .atPosition(0);
        materialTextView.perform(click());

        ViewInteraction textInputEditText2 = onView(
                childAtPosition(
                        childAtPosition(
                                withId(R.id.textfield_task1),
                                0),
                        0));
        textInputEditText2.perform(scrollTo(), replaceText("Kompetenz 1.3"), closeSoftKeyboard());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.submit_button), withText("Hinzuf�gen"),
                        childAtPosition(
                                allOf(withId(R.id.container),
                                        childAtPosition(
                                                withClassName(is("android.widget.ScrollView")),
                                                0)),
                                7)));
        materialButton3.perform(scrollTo(), click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.todo_content), withText("20.07.2022, 12:35 | Hoch"),
                        withParent(allOf(withId(R.id.todo_listitem),
                                withParent(withId(R.id.todolist)))),
                        isDisplayed()));
        textView.check(matches(withText("20.07.2022, 12:35 | Hoch")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.todo_content), withText("20.07.2022, 12:35 | Hoch"),
                        withParent(allOf(withId(R.id.todo_listitem),
                                withParent(withId(R.id.todolist)))),
                        isDisplayed()));
        textView2.check(matches(withText("20.07.2022, 12:35 | Hoch")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.todo_content), withText("20.07.2022, 12:35 | Hoch"),
                        withParent(allOf(withId(R.id.todo_listitem),
                                withParent(withId(R.id.todolist)))),
                        isDisplayed()));
        textView3.check(matches(withText("20.07.2022, 12:35 | Hoch")));

        DataInteraction relativeLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.todolist),
                        childAtPosition(
                                withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                0)))
                .atPosition(0);
        relativeLayout.perform(click());
    }
}
