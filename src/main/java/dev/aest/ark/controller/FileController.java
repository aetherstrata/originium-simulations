package dev.aest.ark.controller;

import com.nimbusds.oauth2.sdk.Response;
import dev.aest.ark.service.ProfilePictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class FileController
{
    private final ProfilePictureService profilePictureService;

    @GetMapping("/uploads/user_images/{filename:.+}")
    public ResponseEntity<org.springframework.core.io.Resource> getUserProfilePicture(@PathVariable("filename") final String filename){
        Resource picture = profilePictureService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"%s\"".formatted(picture.getFilename()))
                .body(picture);
    }
}
