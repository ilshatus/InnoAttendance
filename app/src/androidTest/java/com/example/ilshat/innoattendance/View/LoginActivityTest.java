package com.example.ilshat.innoattendance.View;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.EditText;


import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


import com.example.ilshat.innoattendance.R;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> testRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void passwordErrorTest() {
        String username = "i.gibadullin@innopolis.ru";
        onView(withId(R.id.email)).perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.email_sign_in_button)).perform(click());
        onView(withId(R.id.password)).check(matches(textInputLayoutHasError(testRule.getActivity().getString(R.string.error_incorrect_password))));
    }

    @Test
    public void usernameErrorTest() {
        String password = "789";
        onView(withId(R.id.password)).perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.email_sign_in_button)).perform(click());
        onView(withId(R.id.email)).check(matches(textInputLayoutHasError(testRule.getActivity().getString(R.string.error_field_required))));
    }

    @Test
    public void successLoginClickSuccessTest() {
        String username = "i.gibadullin@innopolis.ru";
        String password = "789";
        onView(withId(R.id.email)).perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.email_sign_in_button)).perform(click());
        onView(withId(R.id.drawer_layout)).check(matches(isDisplayed()));
    }

    public static Matcher<View> textInputLayoutHasError(final String expectedErrorText) {
        return new TypeSafeMatcher<View>() {
            @Override public boolean matchesSafely(View view) {
                if (!(view instanceof EditText)) {
                    return false;
                }
                CharSequence error = ((EditText) view).getError();
                return error != null && expectedErrorText.equals(error.toString());
            }

            @Override public void describeTo(Description description) {}
        };
    }
}
