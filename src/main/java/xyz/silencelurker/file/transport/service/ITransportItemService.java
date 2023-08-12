package xyz.silencelurker.file.transport.service;

/**
 * @author Silence_lurker
 * @version 0.0.1-alpine
 */
public interface ITransportItemService {
    /**
     * 保存文件，并通过默认key存储并返回
     * 
     * @param data
     * @param fileName
     * 
     * @return key
     */
    String saveFile(byte[] data, String fileName);

    /**
     * 自定义键值存储文件
     * 
     * @param data     文件的字节流数据
     * @param fileName 文件名信息
     * @param key      自定义键值
     */
    void saveFileWithKey(byte[] data, String fileName, String key);

    /**
     * 通过默认键值获取文件内容
     * 
     * @return 文件内容
     */
    byte[] getData();

    /**
     * 通过默认键值获取文件名
     * 
     * @return 文件名
     */
    String getFileName();

    /**
     * 通过Base64编码获取文件内容（字符串形式）
     * 
     * @return 文件内容
     */
    String getDataEncodeByBase64();

    /**
     * 通过键值获取文件内容字节流信息
     * 
     * @param key
     * @return
     */
    byte[] getDataByKey(String key);

    /**
     * 通过键值获取文件名信息
     * 
     * @param key
     * @return
     */
    String getFileNameByKey(String key);

    /**
     * 通过键值获取文件通过Base64编码的内容信息（字符串形式）
     * 
     * @param key
     * @return
     */
    String getDataEncodeByBase64ByKey(String key);

}
