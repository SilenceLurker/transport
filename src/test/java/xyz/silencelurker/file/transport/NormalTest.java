package xyz.silencelurker.file.transport;

import java.util.Base64;
import java.util.UUID;
import java.util.Base64.Decoder;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NormalTest {

    public static final String TARGET = "77726476706e69737468656265737421fae0598f69246b45300d8db9d6562d";

    Decoder decoder = Base64.getDecoder();

    @Test
    void base64decoder() {
        // System.err.println(decoder.decode(TARGET));
        System.out.println(UUID.randomUUID());
    }
}