package org.example;

public class OrderGenerator {

    public static Order getOrderWithBlackColour() {
        return new Order("Настя", "Михайлова", "Нижний Новгород", "Горьковская", "89999999999", 2, "20.11.2022", "Тест", new String[]{"BLACK"});
    }

    public static Order getOrderWithGreyColour() {
        return new Order("Настя", "Михайлова", "Нижний Новгород", "Горьковская", "89999999999", 2, "20.11.2022", "Тест", new String[]{"GREY"});
    }

    public static Order getOrderWithTwoColours() {
        return new Order("Настя", "Михайлова", "Нижний Новгород", "Горьковская", "89999999999", 2, "20.11.2022", "Тест", new String[]{"BLACK", "GREY"});
    }

    public static Order getOrderWithoutColours() {
        return new Order("Настя", "Михайлова", "Нижний Новгород", "Горьковская", "89999999999", 2, "20.11.2022", "Тест");
    }
}
