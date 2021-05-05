package utils;

import com.github.javafaker.Faker;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;

public class RandomData {
    private final static Faker faker = new Faker();

    public static String getRandomUsername() {
        return faker.name().username();
    }
    public static String randomString() {
        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = true;
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }

    public static String getRandomPassword() {
        byte[] encodedBytes = Base64.encodeBase64(randomString().getBytes());
        return new String(encodedBytes);
    }

    public static String getRandomEmail() {
        return faker.internet().emailAddress();
    }

}
