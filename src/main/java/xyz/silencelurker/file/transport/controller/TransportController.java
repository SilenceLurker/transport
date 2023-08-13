package xyz.silencelurker.file.transport.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import xyz.silencelurker.file.transport.entity.RepositoryItem;
import xyz.silencelurker.file.transport.entity.TransportItem;
import xyz.silencelurker.file.transport.service.ITransportItemService;
import xyz.silencelurker.file.transport.type.Type;

/**
 * @author Silence_Lurker
 */
@CrossOrigin
@RestController
@RequestMapping("/transport")
public class TransportController {
    @Resource
    private ITransportItemService transportItemService;

    private static final String TYPE_FILE = "File";
    private static final String TYPE_STRING = "String";
    private static final String TYPE_FOLDER = "Folder";

    @PostMapping("/upload")
    public String upload(@RequestBody RepositoryItem repositoryItem) {
        TransportItem transportItem = new TransportItem();

        var type = repositoryItem.getType();
        var data = repositoryItem.getData();
        var fileName = repositoryItem.getFileName();
        var key = repositoryItem.getKey();
        Type itemType;

        if (TYPE_FOLDER.equalsIgnoreCase(type)) {
            itemType = Type.Folder;
        } else if (TYPE_FILE.equalsIgnoreCase(type)) {
            itemType = Type.File;
        } else if (TYPE_STRING.equalsIgnoreCase(type)) {
            itemType = Type.String;
        } else {
            itemType = Type.String;
        }

        transportItem.setData(data.getBytes());
        transportItem.setFileName(fileName);
        transportItem.setType(itemType);

        if (key == null || key.isEmpty()) {
            return transportItemService.saveFile(transportItem.getData(), transportItem.getFileName());
        }

        transportItemService.saveFileWithKey(transportItem.getData(), transportItem.getFileName(), key);
        return key;

    }

    @GetMapping("/data")
    String getData(@RequestParam(required = false) String key) {
        if (key == null || key.isEmpty()) {
            return new String(transportItemService.getData());
        }
        return new String(transportItemService.getDataByKey(key));
    }

    @GetMapping("/fileName")
    String getFileName(@RequestParam(required = false) String key) {
        if (key == null || key.isEmpty()) {
            return transportItemService.getFileName();
        }
        return transportItemService.getFileNameByKey(key);
    }
}
