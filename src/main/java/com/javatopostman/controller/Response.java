package com.javatopostman.controller;

import java.math.BigDecimal;

public class Response {

    private String random;
    private int randomInt;
    private boolean randomBoolean;
    private double randonDouble;
    private BigDecimal randomBigDecimal;

    public String getRandom() {
        return random;
    }

    public void setRandom(String random) {
        this.random = random;
    }

    public int getRandomInt() {
        return randomInt;
    }

    public void setRandomInt(int randomInt) {
        this.randomInt = randomInt;
    }

    public boolean isRandomBoolean() {
        return randomBoolean;
    }

    public void setRandomBoolean(boolean randomBoolean) {
        this.randomBoolean = randomBoolean;
    }

    public double getRandonDouble() {
        return randonDouble;
    }

    public void setRandonDouble(double randonDouble) {
        this.randonDouble = randonDouble;
    }

    public BigDecimal getRandomBigDecimal() {
        return randomBigDecimal;
    }

    public void setRandomBigDecimal(BigDecimal randomBigDecimal) {
        this.randomBigDecimal = randomBigDecimal;
    }
}
