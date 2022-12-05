package com.android.tubes_pbp


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class RegisterActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(RegisterActivity::class.java)

    @Test
    fun registerActivityTest() {
        val materialButton = onView(
            allOf(
                withId(R.id.btnSignUp), withText("Sign Up"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                        1
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText = onView(
            allOf(
                withId(R.id.inputUsername),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.layoutUsername),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("dandy"), closeSoftKeyboard())

        val materialButton2 = onView(
            allOf(
                withId(R.id.btnSignUp), withText("Sign Up"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                        1
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.inputEmail),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.layoutEmail),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(replaceText("dandy"), closeSoftKeyboard())

        val materialButton3 = onView(
            allOf(
                withId(R.id.btnSignUp), withText("Sign Up"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                        1
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        materialButton3.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText3 = onView(
            allOf(
                withId(R.id.inputEmail), withText("dandy"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.layoutEmail),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText3.perform(replaceText("dandy@gmail.com"))

        val textInputEditText4 = onView(
            allOf(
                withId(R.id.inputEmail), withText("dandy@gmail.com"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.layoutEmail),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText4.perform(closeSoftKeyboard())

        val materialButton4 = onView(
            allOf(
                withId(R.id.btnSignUp), withText("Sign Up"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                        1
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        materialButton4.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText5 = onView(
            allOf(
                withId(R.id.inputPassword),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.layoutPassword),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText5.perform(replaceText("123"), closeSoftKeyboard())

        val materialButton5 = onView(
            allOf(
                withId(R.id.btnSignUp), withText("Sign Up"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                        1
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        materialButton5.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText10 = onView(
            allOf(
                withId(R.id.inputTanggalLahir),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.layoutTanggalLahir),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText10.perform(replaceText("12/12/2022"), closeSoftKeyboard())
        onView(isRoot()).perform(waitFor(3000))

        val materialButton7 = onView(
            allOf(
                withId(R.id.btnSignUp), withText("Sign Up"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                        1
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        materialButton7.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText6 = onView(
            allOf(
                withId(R.id.inputNomorTelepon),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.layoutNomorTelepon),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText6.perform(replaceText("0812"), closeSoftKeyboard())

        val materialButton8 = onView(
            allOf(
                withId(R.id.btnSignUp), withText("Sign Up"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                        1
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        materialButton8.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText7 = onView(
            allOf(
                withId(R.id.inputNomorTelepon), withText("0812"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.layoutNomorTelepon),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText7.perform(click())

        val textInputEditText8 = onView(
            allOf(
                withId(R.id.inputNomorTelepon), withText("0812"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.layoutNomorTelepon),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText8.perform(replaceText("08123456789"))

        val textInputEditText9 = onView(
            allOf(
                withId(R.id.inputNomorTelepon), withText("08123456789"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.layoutNomorTelepon),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText9.perform(closeSoftKeyboard())

        val materialButton9 = onView(
            allOf(
                withId(R.id.btnSignUp), withText("Sign Up"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                        1
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        materialButton9.perform(click())
        onView(isRoot()).perform(waitFor(3000))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

    fun waitFor(delay: Long): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isRoot()
            }

            override fun getDescription(): String {
                return "Wait for $delay milliseconds."
            }

            override fun perform(uiController: UiController, view: View) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }
}
