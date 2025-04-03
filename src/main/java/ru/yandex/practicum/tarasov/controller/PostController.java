package ru.yandex.practicum.tarasov.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.yandex.practicum.tarasov.model.Post;
import ru.yandex.practicum.tarasov.service.PostService;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("posts")
public class PostController {
    private final PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @RequestMapping
    public String getPosts(Model model,
                           @RequestParam("pageNumber") Optional<Integer> page,
                           @RequestParam("pageSize") Optional<Integer> size) {
        int currentPage = page.orElse(0);
        int pageSize = size.orElse(10);
        Page<Post> posts = postService.findAll(currentPage, pageSize);
        model.addAttribute("posts", posts.getContent());
        model.addAttribute("paging", posts);
        return "posts";
    }

    @RequestMapping("/{id}")
    public String getPost(@PathVariable("id") int id, Model model) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        return "post";
    }

    @RequestMapping("/{id}/edit")
    public String editPost(@PathVariable("id") int id, Model model) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        return "add-post";
    }

    @RequestMapping("/add")
    public String addPost() {
        return "add-post";
    }

    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String addPost( Post post,
                        @RequestParam(value = "image", required = false) MultipartFile image
                        ) {
        postService.save(post);
        //String uploadDir = System.getProperty("user.dir");
        //File imageFile = new File("/images/" + post.getId() + ".jpg");
        File imageFile = new File("/images/" + post.getId()).getAbsoluteFile();
        //var tmp = imageFile.getAbsoluteFile();
        try {
            //File temp = imageFile.getParentFile().getAbsoluteFile();
            //boolean test = imageFile.getParentFile().mkdirs();
            image.transferTo(imageFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/posts";
    }

    @PostMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String editPost(
                        Post post,
                        @PathVariable("id") long id,
                        @RequestParam(value = "image", required = false) MultipartFile image
    ) {
        post.setId(id);
        postService.save(post);
        //String uploadDir = System.getProperty("user.dir");
        //File imageFile = new File("/images/" + post.getId() + ".jpg");
        File imageFile = new File("/images/" + post.getId()).getAbsoluteFile();
        //var tmp = imageFile.getAbsoluteFile();
        try {
            //File temp = imageFile.getParentFile().getAbsoluteFile();
            //boolean test = imageFile.getParentFile().mkdirs();
            image.transferTo(imageFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/posts";
    }

    @PostMapping("{id}/delete")
    public String deletePost(@PathVariable("id") long id) {
        postService.delete(id);
        return "redirect:/posts";
    }

    @PostMapping("/{postId}/comments")
    public String addComment(Model model,
                            @PathVariable("postId") long postId,
                            @RequestParam("text") String commentText) {
        Post post = postService.addComment(postId, commentText);
        model.addAttribute("post", post);
        return "post";
    }

    @PostMapping("/{postId}/comments/{commentId}")
    public String changeComment(Model model,
                                @PathVariable("postId") long postId,
                                @PathVariable("commentId") long commentId,
                                @RequestParam("text") String commentText) {
        Post post = postService.changeComment(postId, commentId, commentText);
        model.addAttribute("post", post);
        return "post";
    }

    @PostMapping("/{postId}/comments/{commentId}/delete")
    public String deleteComment(Model model,
                                @PathVariable("postId") long postId,
                                @PathVariable("commentId") long commentId) {
        Post post = postService.deleteComment(postId, commentId);
        model.addAttribute("post", post);
        return "post";
    }
}
