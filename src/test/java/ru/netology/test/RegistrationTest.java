package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.domain.Registration;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchesText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Condition.visible;
import static ru.netology.UserGenerator.*;


public class RegistrationTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSubmitActiveUser() {
        Registration validActiveUser = getValidActiveUser();
        $("[name=login]").setValue(validActiveUser.getLogin());
        $("[name=password]").setValue(validActiveUser.getPassword());
        $(".button__text").click();
        $(".App_appContainer__3jRx1 h2.heading").waitUntil(exist, 15000);
        $(".App_appContainer__3jRx1 h2.heading").shouldHave(matchesText("Личный кабинет"));
    }


    @Test
    void shouldNotSubmitBlockedUser() {
        Registration validBlockedUser = getValidBlockedUser();
        $("[name=login]").setValue(validBlockedUser.getLogin());
        $("[name=password]").setValue(validBlockedUser.getPassword());
        $(".button__text").click();
        $(".notification_status_error").waitUntil(visible, 15000);
        $(".notification_visible[data-test-id=error-notification]").shouldHave(Condition.matchesText("Ошибка! Пользователь заблокирован"));
    }

    @Test
    void shouldNotSubmitWithIncorrectPassword() {
        Registration userWithIncorrectPassword = getUserWithIncorrectPassword();
        $("[name=login]").setValue(userWithIncorrectPassword.getLogin());
        $("[name=password]").setValue(userWithIncorrectPassword.getPassword());
        $(".button__text").click();
        $(".notification_status_error").waitUntil(visible, 15000);
        $(".notification_visible[data-test-id=error-notification]").shouldHave(Condition.matchesText("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldNotSubmitWithIncorrectLogin() {
        Registration userWithIncorrectLogin = getUserWithIncorrectLogin();
        $("[name=login]").setValue(userWithIncorrectLogin.getLogin());
        $("[name=password]").setValue(userWithIncorrectLogin.getPassword());
        $(".button__text").click();
        $(".notification_status_error").waitUntil(visible, 15000);
        $(".notification_visible[data-test-id=error-notification]").shouldHave(Condition.matchesText("Ошибка! Неверно указан логин или пароль"));
    }
}


