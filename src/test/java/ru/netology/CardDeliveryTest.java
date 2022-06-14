package ru.netology;

import com.codeborne.selenide.SelenideElement;
import com.google.common.annotations.VisibleForTesting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static java.nio.file.attribute.AclEntryPermission.DELETE;

public class CardDeliveryTest {

    @BeforeEach
    public void openPage() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldCheckCorrectForm() {

        $x("//input[@placeholder=\"Город\"]").val("Москва");
        $x("//input[@type=\"tel\"]").doubleClick().sendKeys("DELETE");
        String meetingDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//input[@placeholder=\"Дата встречи\"]").val("17.06.2022");
        $("[data-test-id='name'] input").val("Изотова Юлия");
        $("[data-test-id='phone'] input").val("+79111734314");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $x("//div[@class=\"notification__title\"]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=notification] .notification__content").should(exactText("Встреча успешно забронирована на " + meetingDate));

    }

    @Test
    void shouldCheckIncorrectCity() {

        $x("//input[@placeholder=\"Город\"]").val("Хогвартс");
        $x("//input[@type=\"tel\"]").doubleClick().sendKeys("DELETE");
        String meetingDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//input[@placeholder=\"Дата встречи\"]").val("17.06.2022");
        $("[data-test-id='name'] input").val("Изотова Юлия");
        $("[data-test-id='phone'] input").val("+79111734314");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id=city] .input__sub").should(exactText("Доставка в выбранный город недоступна"));

    }

    @Test
    void shouldCheckNameEmpty() {
        $x("//input[@placeholder=\"Город\"]").val("Москва");
        $x("//input[@type=\"tel\"]").doubleClick().sendKeys("DELETE");
        String meetingDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//input[@placeholder=\"Дата встречи\"]").val("17.06.2022");
        $("[data-test-id='name'] input").val("");
        $("[data-test-id='phone'] input").val("+79111734314");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id=name] .input__sub").should(exactText("Поле обязательно для заполнения"));

    }

    @Test
    void shouldCheckInvalidName() {
        $x("//input[@placeholder=\"Город\"]").val("Москва");
        $x("//input[@type=\"tel\"]").doubleClick().sendKeys("DELETE");
        String meetingDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//input[@placeholder=\"Дата встречи\"]").val("17.06.2022");
        $("[data-test-id='name'] input").val("Izotova Yulia");
        $("[data-test-id='phone'] input").val("+79111734314");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id=name] .input__sub").should(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }


    @Test
    void shouldCheckInvalidDate() {
        $x("//input[@placeholder=\"Город\"]").val("Москва");
        $x("//input[@type=\"tel\"]").doubleClick().sendKeys("DELETE");
        String meetingDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//input[@placeholder=\"Дата встречи\"]").val("15.06.2022");
        $("[data-test-id='name'] input").val("");
        $("[data-test-id='phone'] input").val("+79111734314");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id=date] .input__sub").should(exactText("Заказ на выбранную дату невозможен"));
    }


    @Test
    void shouldCheckInvalidPhone() {
        $x("//input[@placeholder=\"Город\"]").val("Москва");
        $x("//input[@type=\"tel\"]").doubleClick().sendKeys("DELETE");
        String meetingDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//input[@placeholder=\"Дата встречи\"]").val("17.06.2022");
        $("[data-test-id='name'] input").val("Изотова Юлия");
        $("[data-test-id='phone'] input").val("+7911173");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id=phone] .input__sub").should(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    void shouldCheckEmptyPhone() {
        $x("//input[@placeholder=\"Город\"]").val("Москва");
        $x("//input[@type=\"tel\"]").doubleClick().sendKeys("DELETE");
        String meetingDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//input[@placeholder=\"Дата встречи\"]").val("17.06.2022");
        $("[data-test-id='name'] input").val("Изотова Юлия");
        $("[data-test-id='phone'] input").val("");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id=phone] .input__sub").should(exactText("Поле обязательно для заполнения"));
    }


    @Test
    void shouldMissClickAgreement() {
        $x("//input[@placeholder=\"Город\"]").val("Москва");
        $x("//input[@type=\"tel\"]").doubleClick().sendKeys("DELETE");
        String meetingDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//input[@placeholder=\"Дата встречи\"]").val("17.06.2022");
        $("[data-test-id='name'] input").val("Изотова Юлия");
        $("[data-test-id='phone'] input").val("+79111734314");
        $x("//*[contains(text(),'Забронировать')]").click();
        //  $x("//div[@class=\"notification__title\"]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=agreement] .checkbox__text").should(exactText("Я соглашаюсь с условиями обработки и использования  моих персональных данных"));


    }


}
