package com.newBlog.Controller;
import com.newBlog.entity.Post;
import com.newBlog.payload.PostDto;
import com.newBlog.payload.PostResponse;
import com.newBlog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;
    public PostController(PostService postService){
        this.postService=postService;
    }

    //http://localhost:8080/api/posts
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
  public ResponseEntity<?> createPost(@Valid @RequestBody Post post, BindingResult result){

        if (result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
       Post dto = postService.createPost(post);
       return new ResponseEntity<>(dto, HttpStatus.CREATED);

   }
    //http://localhost:8080/posts?pageNo=1&pagesize=5&sortBy=title,

    @GetMapping
  public  PostResponse getAllPosts(
          @RequestParam(value = "pageNo",  defaultValue = "0",required=false)int pageNo,
          @RequestParam(value ="pageSize",defaultValue = "5",required = false)int pageSize,
          @RequestParam(value ="sortBy",defaultValue = "id",required = false)String sortBy,
          @RequestParam(value ="sortDir",defaultValue ="asc",required = false)String sortDir
    ) {
      PostResponse postResponse = postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
      return postResponse;
  }
    //http://localhost:8080/posts/{id}

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id")long id){
     PostDto dto=postService.getPostById(id);
    return new ResponseEntity<>(dto,HttpStatus.OK);

    }
    //http://localhost:8080/posts/{id}
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto>updatePost(@RequestBody PostDto postDto,
                                             @PathVariable("id") long id

                                             ){
        PostDto dto =postService.updatePost(postDto, id);

        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    //http://localhost:8080/api/posts/{id}
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String>deletePost(@PathVariable("id")long id){
     postService.deletePost(id);

        return new ResponseEntity<>("Post is deleted", HttpStatus.OK);
    }
}
