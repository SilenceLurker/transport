package xyz.silencelurker.file.transport.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j2;

/**
 * @author Silence_Lurker
 * @version 0.1.1-alpine
 */
@Log4j2
@RequestMapping("/transport")
@RestController
public class TransportController {

    private static final String DEFAULT_LOCATION = "./temp/";
    private static final String TEST_OBJECT = "Test Object!";

    @GetMapping("/test")
    public boolean test() throws IOException {
        File testFile = new File(DEFAULT_LOCATION + "testFile.data");
        log.info("执行测试");

        if (testFile.exists()) {
            FileInputStream inputStream = new FileInputStream(testFile);

            String data = new String(inputStream.readAllBytes());

            inputStream.close();
            if (data.equalsIgnoreCase(TEST_OBJECT)) {
                return true;
            }

        }

        new File(DEFAULT_LOCATION).mkdirs();

        testFile.createNewFile();

        FileOutputStream outputStream = new FileOutputStream(testFile);

        outputStream.write(TEST_OBJECT.getBytes());

        outputStream.close();

        return true;

    }

    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFile(@RequestParam(required = false) String location,
            @RequestParam(required = false, defaultValue = "false") boolean replace, MultipartFile file)
            throws IOException {

        log.info("上传文件请求接收。");

        File target;

        File dir = new File(DEFAULT_LOCATION + location);

        if (!(dir.exists())) {
            log.info("目录不存在，尝试创建目录。");
            dir.mkdirs();
            log.info("目录创建成功。");
        }

        if (location == null) {
            target = new File(DEFAULT_LOCATION + file.getOriginalFilename());
        } else {
            target = new File(DEFAULT_LOCATION + location + file.getOriginalFilename());
        }
        if (target.exists()) {
            if (replace) {
                log.info("确认文件替换");
            } else {
                return ResponseEntity.badRequest().body("File Already Exists!");
            }
        }

        var stream = file.getInputStream();

        FileOutputStream outputStream = new FileOutputStream(target);

        // for (int i = stream.read(); i != -1; i = stream.read()) {
        // outputStream.write(i);
        // }

        outputStream.write(stream.readAllBytes());

        outputStream.close();
        stream.close();

        return ResponseEntity.ok().body("Upload Success!");
    }

    @DeleteMapping("/deleteFile")
    public ResponseEntity<String> deleteFile(@RequestParam(required = false, defaultValue = "null/") String location,
            @RequestParam(required = false, defaultValue = "NOT_EXISTS_FILE.FAIL") String fileName) {

        File target = new File(DEFAULT_LOCATION + location + fileName);

        if (target.exists()) {
            target.delete();
            return ResponseEntity.ok().body("Delete Compile!");
        }

        return ResponseEntity.badRequest().body("Delete Fail, File not Exists!");
    }

    @GetMapping("/getFile")
    public ResponseEntity<Resource> getFile(@RequestParam(required = false, defaultValue = "") String location,
            @RequestParam(required = false, defaultValue = "NOT_EXISTS_FILE.FAIL") String fileName,
            @RequestParam(required = false, defaultValue = "false") boolean download) {
        FileSystemResource file;

        log.info("检查目标文件信息");
        if (fileName == null) {
            file = new FileSystemResource(DEFAULT_LOCATION + "test.jpg");
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(file);
        } else if (location == null) {
            file = new FileSystemResource(DEFAULT_LOCATION + fileName);
        } else {
            file = new FileSystemResource(DEFAULT_LOCATION + location + fileName);
        }

        MediaType type = MediaType.MULTIPART_FORM_DATA;

        if (!file.exists()) {
            log.info("未找到匹配文件，导向至错误页面");
            file = new FileSystemResource("./page/NotFound.html");
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_HTML).body(file);
        }

        if (fileName.contains(".jpg") || fileName.contains(".jpeg")) {
            type = MediaType.IMAGE_JPEG;
        } else if (fileName.contains(".gif")) {
            type = MediaType.IMAGE_GIF;
        } else if (fileName.contains(".png")) {
            type = MediaType.IMAGE_PNG;
        } else if (fileName.contains(".md")) {
            type = MediaType.TEXT_MARKDOWN;
        } else if (fileName.contains(".html")) {
            type = MediaType.TEXT_HTML;
        }

        if (download) {
            type = MediaType.MULTIPART_FORM_DATA;
        }

        return ResponseEntity.ok().contentType(type).body(file);
    }

    @GetMapping("/getDirInfo")
    public ResponseEntity<?> getDirInfo(@RequestParam(required = false, defaultValue = "") String location) {
        File target = new File(DEFAULT_LOCATION + location);

        String[] fileList;
        List<String> list = new ArrayList<String>();

        if (!target.exists()) {
            return ResponseEntity.badRequest().body("目录不存在");
        }

        if (target.isDirectory()) {
            fileList = target.list();
        } else {
            return ResponseEntity.ok().body(target);
        }

        for (var str : fileList) {
            var s = new File(DEFAULT_LOCATION + str);

            if (s.isDirectory()) {
                str += "/";
            }

            list.add(str);
        }

        return ResponseEntity.ok().body(list);

    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<?> download(@PathVariable String fileName,
            @RequestParam(required = false, defaultValue = "") String location) {
        MediaType type = MediaType.MULTIPART_FORM_DATA;

        File file = new File(location + fileName);

        if (!file.exists()) {
            return ResponseEntity.badRequest().body("File Not Exists!");
        }

        var resource = new FileSystemResource(file);

        log.info(resource.getFilename());

        return ResponseEntity.ok().contentType(type).body(resource);
    }

}
