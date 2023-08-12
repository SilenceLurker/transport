package xyz.silencelurker.file.transport.entity;

import lombok.Data;
import xyz.silencelurker.file.transport.type.Type;

/**
 * @author Silence_lurker
 */
@Data
public class TransportItem {
    private String fileName;
    private Type type;
    private byte[] data;
}
