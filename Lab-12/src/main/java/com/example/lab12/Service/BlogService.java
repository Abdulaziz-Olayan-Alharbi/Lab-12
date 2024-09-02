package com.example.lab12.Service;

import com.example.lab12.Api.ApiException;
import com.example.lab12.Model.Blog;
import com.example.lab12.Model.User;
import com.example.lab12.Repository.AuthRepository;
import com.example.lab12.Repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    private final AuthRepository authRepository;

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    public void addBlog(User user, Blog blog) {
        blog.setUser(user);
        blogRepository.save(blog);
    }

    public void updateBlog(Integer blogId,User user, Blog blog) {
        Blog oldBlog = blogRepository.findBlogById(blogId);
        if (oldBlog == null) {
            throw new ApiException("Blog not found");
        }
        User u = authRepository.findUserById(user.getId());
        if (!blog.getUser().getId().equals(u.getId())) {
            throw new ApiException("User does not belong to this blog");
        }
        oldBlog.setTitle(blog.getTitle());
        oldBlog.setBody(blog.getBody());
        blogRepository.save(oldBlog);
    }

    public void deleteBlog(Integer blogId, User user) {
        Blog blog = blogRepository.findBlogById(blogId);
        if (blog == null) {
            throw new ApiException("Blog not found");
        }
        User u = authRepository.findUserById(user.getId());
        if (!blog.getUser().getId().equals(u.getId())) {
            throw new ApiException("User does not belong to this blog");
        }
        blogRepository.delete(blog);
    }

    public List<Blog> getAllUserBlogs(User user) {
        return blogRepository.findBlogByUser(user);
    }

    public Blog getBlogById(Integer blogId , User user) {
        Blog blog = blogRepository.findBlogById(blogId);
        if (blog == null) {
            throw new ApiException("Blog not found");
        }
        User u = authRepository.findUserById(user.getId());
        if (!blog.getUser().getId().equals(u.getId())) {
            throw new ApiException("User does not belong to this blog");
        }
        return blog;
    }

    public Blog getBlogByTitle(String title) {
        Blog blog = blogRepository.findBlogByTitle(title);
        if (blog == null) {
            throw new ApiException("Blog not found");
        }
        return blog;
    }


}
