package xyz.silencelurker.file.transport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.ValueOperations;

import jakarta.annotation.Resource;

@SpringBootTest
class TransportApplicationTests {

	@Resource
	RedisOperations<String, String> operations;
	private ValueOperations<String, String> valueOperations;

	@BeforeEach
	void before() {

		valueOperations = operations.opsForValue();

		valueOperations.set("Test Data 1", "Data 1");
		valueOperations.set("Test Data 2", "Data 2");
		valueOperations.set("Test Data 3", "Data 3");
	}

	@Test
	void contextLoads() {
		System.out.println("Context Test Success!");
		System.out.println("----------------- Redis Read Test:  ---------------");
		System.out.println(valueOperations.get("Test Data 1"));
		Assertions.assertEquals("Data 1", valueOperations.get("Test Data 1"));

		valueOperations.set("Test Data 1", "Data 9");

		System.out.println(valueOperations.get("Test Data 1"));
		Assertions.assertEquals("Data 9", valueOperations.get("Test Data 1"));
	}

	@Test
	void fileTest() {
		File file = new File("./test.txt");
		FileReader reader = null;
		FileInputStream inputStream = null;
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			System.out.println(file.getAbsolutePath());

			FileWriter writer = new FileWriter(file, true);
			FileOutputStream outputStream = new FileOutputStream(file, true);

			writer.write("This is a Test Information! It should be write by writer!\r\n");
			outputStream.write("This is a Test Information! It should be write by stream!\r\n".getBytes());

			reader = new FileReader(file);
			inputStream = new FileInputStream(file);

			var encoder = Base64.getEncoder();

			valueOperations.set("Test File", encoder.encodeToString(inputStream.readAllBytes()));

			outputStream.close();
			writer.close();
			reader.close();
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("File encode By Base64:" + valueOperations.get("Test File"));
	}

	@Test
	void readFileTest() {
		byte[] data = valueOperations.get("Test File").getBytes();

		var decoder = Base64.getDecoder();

		File file = new File("redis");

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);

			fileOutputStream.write(decoder.decode(data));

			fileOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void keySetTest() {
		var setOperations = operations.opsForSet();

		setOperations.add("keys", "Test Key 1", "Test Key 2", "Test Key 3", "Test Key 1");

		for (var v : setOperations.members("keys")) {
			System.out.println(v);

		}

	}

	@AfterEach
	void afterAll() {
		System.out.println(
				"Already delete Key:" + "'Test Data 1'" + " And Value :" + valueOperations.getAndDelete("Test Data 1"));
		System.out.println(
				"Already delete Key:" + "'Test Data 2'" + " And Value :" +
						valueOperations.getAndDelete("Test Data 2"));
		System.out.println(
				"Already delete Key:" + "'Test Data 3'" + " And Value :" +
						valueOperations.getAndDelete("Test Data 3"));
	}

}
