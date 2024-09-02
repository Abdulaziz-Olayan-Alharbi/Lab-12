package com.example.lab12.Controller;

import com.example.lab12.Api.ApiResponse;
import com.example.lab12.Model.Blog;
import com.example.lab12.Model.User;
import com.example.lab12.Service.BlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/blog")
public class BlogController {
    private final BlogService blogService;

    @GetMapping("/get")
    public ResponseEntity getAllBlogs() {
        return ResponseEntity.status(200).body(blogService.getAllBlogs());
    }

    @PostMapping("/add")
    public ResponseEntity addBlog(@AuthenticationPrincipal User user , @Valid @RequestBody Blog blog) {
        blogService.addBlog(user, blog);
        return ResponseEntity.status(200).body(new ApiResponse("Blog added successfully"));
    }

    @PutMapping("/update/{blogId}")
    public ResponseEntity updateBlog(@AuthenticationPrincipal User user ,@PathVariable Integer blogId ,@Valid @RequestBody Blog blog) {
        blogService.updateBlog(blogId,user,blog);
        return ResponseEntity.status(200).body(new ApiResponse("Blog updated successfully"));
    }

    @DeleteMapping("/delete/{blogId}")
    public ResponseEntity deleteBlog(@AuthenticationPrincipal User user ,@PathVariable Integer blogId) {
        blogService.deleteBlog(blogId,user);
        return ResponseEntity.status(200).body(new ApiResponse("Blog deleted successfully"));
    }

    @GetMapping("/get-mine")
    public ResponseEntity getMineBlog(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(blogService.getAllUserBlogs(user));
    }

    @GetMapping("/get-id/{blogId}")
    public ResponseEntity getBlog(@AuthenticationPrincipal User user , @PathVariable Integer blogId) {
        return ResponseEntity.status(200).body(blogService.getBlogById(blogId,user));
    }

    @GetMapping("/get-title/{title}")
    public ResponseEntity getBlogTitle(@PathVariable String title) {
        return ResponseEntity.status(200).body(blogService.getBlogByTitle(title));
    }






}
