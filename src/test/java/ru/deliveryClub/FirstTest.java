package ru.deliveryClub;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.Assert.assertEquals;

public class FirstTest extends WebDriverSettings {

    @Test
    public void testFoodOrder() {

        //Открываем страницу
        driver.get("https://www.delivery-club.ru/");

        //Проверяем верность title страницы
        String title = "Быстрая доставка еды Delivery Club";
        assertEquals(title, driver.getTitle());

        //Кликаем по кнопке с добавлением адреса доставки
        click(By.className("DesktopAddressButton_address"));

        //Если геолокация подставила автоматическим адрес, то убираем его нажатием крестика
        if(driver.findElement(By.xpath("//div[@data-testid='address-button-add']")).isDisplayed()) {
            driver.findElement(By.xpath("//div[@data-testid='address-button-add']")).click();
            click(By.cssSelector("svg.UiKitUiKitIcon_m.UiKitUiKitIcon_root.AppAddressInput_closeIcon"));
        }

        //Вводим адрес для доставки
        inputText("Брянск, Советская улица, 110, подъезд 2", By.xpath("//input[@data-testid='address-input']"));

        //Подтверждаем введенный адрес
        click(By.xpath("//button[@data-testid='desktop-location-modal-confirm-button']"));

        //Выбираем ресторан
        inputText("Крошка картошка", By.xpath("//input[@data-testid='search-input']"));
        click(By.xpath("//h2[text()='Крошка картошка']"));

        //Ждем подтверждения загрузки страницы с новым title
        title = "Крошка картошка заказать доставку еды. Меню ресторана «Крошка картошка» в Брянске";
        wait.until(ExpectedConditions.titleIs(title));

        //Выбираем пункт меню
        click(By.xpath("//li[text()='Гриль Чиз']"));

        //Выбираем позицию
        click(By.xpath("//div[text()='Гриль-чиз Перечный жюльен']"));

        //Проверяем описание выбранной позиции
        checkText("Картофель, сыр творожный, жюльен перечный с курицей и грибами, сыр моцарелла"
                //, By.xpath("//div[@class='Modal_modalWrapper']/div/div[2]/div"));
        , By.cssSelector("div.HTMLDescription_root.ModalMenuItemOptions_htmlDescription"));

        //Дублируем заказ
        click(By.xpath("//div[@data-testid='ui-counter-plus-button']"));

        //Проверяем стоимость
        checkText("630 ₽", By.xpath("/html/body/div[4]/div/div/div/div[3]/div/div[2]/div[2]/span"));

        //Добавляем заказ в корзину
        click(By.xpath("//button[@data-testid='desktop-menu-item-options-confirm']"));

    }

    //Дождаться элемента и кликнуть по нему
    public void click(By path) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(path));
            driver.findElement(path).click();
            //Если клик по элементу вызывает ошибку, кликаем по его родителю
        } catch (ElementClickInterceptedException exception) {
            driver.findElement(path).findElement(By.xpath(".//..")).click();
        }
    }

    //Дождаться элемента и проверить его текст
    public void checkText(String text, By path) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(path));
        Assert.assertEquals(text, driver.findElement(path).getText());
    }

    //Дождаться поисковую строку и ввести текст
    public void inputText(String text, By path) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(path));
        WebElement element = driver.findElement(path);
        element.sendKeys(text);
        element.sendKeys(Keys.ENTER);
    }


}
