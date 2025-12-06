package com.example.osullivanmoviesapp;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.osullivanmoviesapp.Modele.DatabaseHelper;
import com.example.osullivanmoviesapp.Vue.ConnectionActivity;

import org.junit.Rule;
import org.junit.Test;

public class TestActivities {

    public void WaitingTime(){
        try{
            Thread.sleep(2000);
        }catch(
                InterruptedException e
        ){
            e.printStackTrace();
        }
    }

    @Rule
    public ActivityScenarioRule<ConnectionActivity> activityRule =
            new ActivityScenarioRule<>(ConnectionActivity.class);

    //Test 1
    @Test
    public void testCreateAccount() {
        Espresso.onView(withId(R.id.RegisterBtnLogin)).perform(click());

        String uniqueUser = "User" + System.currentTimeMillis();

        Espresso.onView(withId(R.id.edNameRegister)).perform(replaceText("LeSpirituel"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.edEmailRegister)).perform(replaceText("spirituel@gmail.com"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.edPasswordRegister)).perform(replaceText("SSFj"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.btnRegister)).perform(click());
        Espresso.onView(withId(R.id.edPasswordLogin)).perform(replaceText("SSFj"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.LoginBtn)).perform(click());
        WaitingTime();
    }

    //Test 2
    @Test
    public void testRegex(){
        // Test usernames VALIDES (lettres, chiffres, tirets uniquement)
        assertTrue(DatabaseHelper.isValidUsername("User123"));
        assertTrue(DatabaseHelper.isValidUsername("john-doe"));
        assertTrue(DatabaseHelper.isValidUsername("ADMIN"));
        assertTrue(DatabaseHelper.isValidUsername("test-user-2024"));
        assertTrue(DatabaseHelper.isValidUsername("abc"));
        assertTrue(DatabaseHelper.isValidUsername("123"));
        assertTrue(DatabaseHelper.isValidUsername("a"));
        assertTrue(DatabaseHelper.isValidUsername("-"));

        // Test usernames INVALIDES (caractères non autorisés)
        assertFalse(DatabaseHelper.isValidUsername("user name")); // espace
        assertFalse(DatabaseHelper.isValidUsername("user@email")); // @
        assertFalse(DatabaseHelper.isValidUsername("user_name")); // underscore
        assertFalse(DatabaseHelper.isValidUsername("user.name")); // point
        assertFalse(DatabaseHelper.isValidUsername("user#123")); // #
        assertFalse(DatabaseHelper.isValidUsername("user!")); // !
        assertFalse(DatabaseHelper.isValidUsername("user$")); // $
        assertFalse(DatabaseHelper.isValidUsername("")); // vide
        assertFalse(DatabaseHelper.isValidUsername(null)); // null
        assertFalse(DatabaseHelper.isValidUsername(" ")); // espace seul
    }

    //Test 3
    @Test
    public void testMoviesDisplay() {
        testCreateAccount();
        Espresso.onView(withId(R.id.btnExploreMovies)).perform(click());
        WaitingTime();
        Espresso.onView(withId(R.id.recycleview)).check(matches(isDisplayed()));
    }

    //Test 4
    @Test
    public void testMovieDetailsDisplay(){
        testMoviesDisplay();
        Espresso.onView(withId(R.id.recycleview)).perform(actionOnItemAtPosition(0, click()));
        WaitingTime();
        Espresso.onView(withId(R.id.movieTitle)).check(matches(isDisplayed()));

    }

    //Test 5
    @Test
    public void testQuizCompletion(){
        testCreateAccount();
        Espresso.onView(withId(R.id.btnQuiz)).perform(click());
//        Espresso.onView(withId(R.id.btnQuiz1)).perform(click());
//        Espresso.onView(withId(R.id.nextButton)).perform(click());
//        Espresso.onView(withId(R.id.btnQuiz1)).perform(click());
//        Espresso.onView(withId(R.id.nextButton)).perform(click());
//        Espresso.onView(withId(R.id.btnQuiz1)).perform(click());
//        Espresso.onView(withId(R.id.nextButton)).perform(click());
//        Espresso.onView(withId(R.id.btnQuiz1)).perform(click());
//        Espresso.onView(withId(R.id.nextButton)).perform(click());
//        Espresso.onView(withId(R.id.btnQuiz1)).perform(click());
//        Espresso.onView(withId(R.id.nextButton)).perform(click());
//        WaitingTime();

        for ( int i = 0; i < 5; i++){
            Espresso.onView(withId(R.id.btnQuiz1)).perform(click());
            Espresso.onView(withId(R.id.nextButton)).perform(click());
        }
        WaitingTime();
    }

    //Test 6
    @Test
    public void testContactActivity(){
        testCreateAccount();
        Espresso.onView(withId(R.id.btnContact)).perform(click());
        Espresso.onView(withId(R.id.btnSMS)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.btnCall)).check(matches(isDisplayed()));
    }
}