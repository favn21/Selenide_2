package org.example;
import com.codeborne.selenide.*;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class TheInternetTests {

    @BeforeEach
    public void setUp() {
        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadTimeout = 60000;
        Configuration.pageLoadStrategy = "eager";
        open("http://the-internet.herokuapp.com/");
    }

    @Test
    @DisplayName("Проверка ожидания появления элемента")
    public void testElementAppears() {
        $("a[href='/dynamic_loading']").click();
        $("a[href='/dynamic_loading/1']").click();
        $("#start button").click();
        $("#loading").should(disappear);
        $("#finish").should(appear).shouldHave(text("Hello World!"));
    }

    @Test
    @DisplayName("Проверка ожидания исчезновения элемента")
    public void testElementDisappears() {
        $("a[href='/dynamic_loading']").click();
        $("a[href='/dynamic_loading/2']").click();
        $("#start button").click();
        $("#loading").should(appear);
        $("#loading").should(disappear);
        $("#finish").shouldHave(text("Hello World!"));
    }

    @Test
    @DisplayName("Проверка определенного текста")
    public void testSpecificText() {
        $("a[href='/challenging_dom']").click();
        $("#content h3").shouldHave(text("Challenging DOM"));
    }

    @Test
    @DisplayName("Проверка определенного значения")
    public void testSpecificValue() {
        $("a[href='/inputs']").click();
        SelenideElement input = $("input[type='number']");
        input.setValue("12345");
        input.shouldHave(value("12345"));
    }

    @Test
    @DisplayName("Проверка атрибута")
    public void testHideElement() {
        $("a[href='/dynamic_controls']").click();
        $("#checkbox-example button").click();
        $("#checkbox").should(disappear);
        $("#message").shouldHave(text("It's gone!"));
    }

    @Test
    @DisplayName("Проверка таймаутов")
    public void testTimeout() {
        Configuration.timeout = 10000;
        $("a[href='/dynamic_loading']").click();
        $("a[href='/dynamic_loading/1']").click();
        $("#start button").click();
        $("#finish").should(appear).shouldHave(text("Hello World!"));
    }

    @Test
    @DisplayName("Проверка sleep")
    public void testSleep() {
        $("a[href='/dynamic_loading']").click();
        $("a[href='/dynamic_loading/1']").click();
        $("#start button").click();
        sleep(5000);
        $("#finish").should(appear).shouldHave(text("Hello World!"));
    }

    @Test
    @DisplayName("Проверка ожидания появления элемента")
    public void testWaitForElementToAppear() {
        $("a[href='/dynamic_controls']").click();
        $("#input-example button").click();
        $("#input-example input").should(appear).shouldBe(enabled);
    }

    @Test
    @DisplayName("Проверка исчезновения элемента")
    public void testWaitForElementToDisappear(){
        open("http://the-internet.herokuapp.com/disappearing_elements");
        SelenideElement element = $("ul li:nth-child(2) a");
        element.shouldBe(visible);
        element.click();
        element.should(disappear);
    }

    @Test
    @DisplayName("Проверка ожидания определенного значения (проверка текста в ячейке таблицы)")
    public void testWaitForSpecificValueInTable() {
        $("a[href='/tables']").click();
        SelenideElement table = $("table#table1");
        table.should(appear);
        SelenideElement row = table.$$("tbody tr").findBy(text("Jason"));
        row.should(appear);
        row.shouldHave(text("jdoe@hotmail.com"));
    }
}
