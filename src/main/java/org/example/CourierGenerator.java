package org.example;

import java.util.Random;

public class CourierGenerator {

    public static Courier getDefaultCourier() {
        return new Courier("nasty" + new Random().nextInt(100), "1234" + new Random().nextInt(100), "name");
    }
}
