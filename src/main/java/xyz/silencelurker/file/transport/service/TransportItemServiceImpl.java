package xyz.silencelurker.file.transport.service;

import java.util.Base64;

import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

/**
 * @author Silence_Lurker
 */
@Service
public class TransportItemServiceImpl implements ITransportItemService {

    private static final String DEFAULT_KEY = "DEFAULT_KEY_WITH_VERSION0.0.1";
    private static final String DEFAULT_KEY_DATA = "DEFAULT_KEY_WITH_VERSION0.0.1_FOR_DATA";
    private static final String DEFAULT_KEY_FILENAME = "DEFAULT_KEY_WITH_VERSION0.0.1_FOR_FILENAME";

    private static final String DATA_TITLE = "_FOR_DATA";
    private static final String FILENAME_TITLE = "_FOR_FILENAME";

    @Resource
    private RedisOperations<String, String> operations;

    private ValueOperations<String, String> valueOperations;

    private SetOperations<String, String> setOperations;

    private static Base64.Encoder encoder = Base64.getEncoder();
    private static Base64.Decoder decoder = Base64.getDecoder();

    public TransportItemServiceImpl(RedisOperations<String, String> operations) {
        setOperations = operations.opsForSet();
        valueOperations = operations.opsForValue();
    }

    @Override
    public String saveFile(byte[] data, String fileName) {
        valueOperations.set(DEFAULT_KEY_DATA, encoder.encodeToString(data));
        valueOperations.set(DEFAULT_KEY_FILENAME, fileName);

        setOperations.add("keys", DEFAULT_KEY);

        return DEFAULT_KEY;
    }

    @Override
    public void saveFileWithKey(byte[] data, String fileName, String key) {
        valueOperations.set(key + DATA_TITLE, encoder.encodeToString(data));
        valueOperations.set(key + FILENAME_TITLE, fileName);

        setOperations.add("keys", key);
    }

    @Override
    public byte[] getData() {
        return getDataByKey(DEFAULT_KEY);
    }

    @Override
    public String getFileName() {
        return getFileNameByKey(DEFAULT_KEY);
    }

    @Override
    public String getDataEncodeByBase64() {
        return getDataEncodeByBase64ByKey(DEFAULT_KEY);
    }

    @Override
    public byte[] getDataByKey(String key) {
        return decoder.decode(valueOperations.get(key + DATA_TITLE));
    }

    @Override
    public String getFileNameByKey(String key) {
        return valueOperations.get(key + FILENAME_TITLE);
    }

    @Override
    public String getDataEncodeByBase64ByKey(String key) {
        return valueOperations.get(key + DATA_TITLE);
    }

}
