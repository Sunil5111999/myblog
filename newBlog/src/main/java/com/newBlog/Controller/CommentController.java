package com.newBlog.Controller;

import com.newBlog.entity.Comment;
import com.newBlog.payload.CommentDto;
import com.newBlog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //http://localhost:8080/api/posts/1/comments
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto>createComment(
            @PathVariable("postId")long postId,
            @RequestBody CommentDto commentDto
    ){
      CommentDto dto = commentService.createComment(postId, commentDto);
      return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    //http://localhost:8080/api/posts/1/comments

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto>getCommentByPostId(@PathVariable("postId") long postId){
        return  commentService.getCommentByPostId(postId);
    }
    //http://localhost:8080/api/posts/1/comments/1
@GetMapping("/posts/{postId}/comments/{commandId}")
    public ResponseEntity<CommentDto>getCommentById(@PathVariable("postId") long postId,@PathVariable("commandId") long commandId){
    CommentDto dto = commentService.getCommentById(postId, commandId);
    return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    //http:localhost:8080/api/posts/{postsId}/comments/{id}
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto>updateComment(
            @PathVariable("postId")long postId,
            @PathVariable("id")long id ,
            @RequestBody CommentDto commentDto

            ){
         CommentDto dto = commentService.updateComment(postId,id,commentDto);
         return new ResponseEntity<>(dto,HttpStatus.OK);

    }
    //http:localhost:8080/api/posts/{postsId}/comments/{id}

    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String>deleteComment(
            @PathVariable("postId")long postId,
            @PathVariable("id")long id
    ){
        commentService.deleteComment(postId,id);

        return new ResponseEntity<>("Comment is Deleted",HttpStatus.OK);
    }
}
