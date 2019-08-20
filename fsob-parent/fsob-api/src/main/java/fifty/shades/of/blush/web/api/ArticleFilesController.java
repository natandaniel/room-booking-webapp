package fifty.shades.of.blush.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fifty.shades.of.blush.data.exception.FileStorageException;
import fifty.shades.of.blush.data.exception.MyFileNotFoundException;
import fifty.shades.of.blush.web.api.services.ArticleFilesService;

@RestController
@RequestMapping(path = "/api/articles")
@CrossOrigin(origins = "*")
public class ArticleFilesController {
	
    @Autowired
    ArticleFilesService articleFilesService;

    @PostMapping("/uploadFile/{articleId}")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("articleId") Long articleId) throws Exception {
       
        return articleFilesService.uploadFile(file, articleId);
    }

    @PostMapping("/uploadMultipleFiles/{articleId}")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, @PathVariable("articleId") Long articleId) throws FileStorageException {
    	
    	return articleFilesService.uploadMultipleFiles(files, articleId);
    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileId) throws MyFileNotFoundException {
        
    	return articleFilesService.downloadFile(fileId);
    }

}