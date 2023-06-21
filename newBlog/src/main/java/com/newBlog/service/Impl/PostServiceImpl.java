package com.newBlog.service.Impl;

import com.newBlog.entity.Post;
import com.newBlog.exception.ResourceNotFoundException;
import com.newBlog.payload.PostDto;
import com.newBlog.payload.PostResponse;

import com.newBlog.repository.PostRepository;
import com.newBlog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {


    private PostRepository postRepository;


    private ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

  //  public PostServiceImpl(PostRepository postRepository) {

   //     this.postRepository = postRepository;
  //  }




    @Override
    public PostResponse getAllPosts(int pageNo,int pageSize,String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        PageRequest pageable = PageRequest.of(pageNo,pageSize, Sort.by(sortBy));
        Page<Post> content = postRepository.findAll(pageable);
        List<Post> posts = content.getContent();

       List<PostDto> dtos =  posts.stream().map(this::mapToDto).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(dtos);
        postResponse.setPageNo(content.getNumber());
        postResponse.setPageSize(content.getSize());
        postResponse.setTotalpage(content.getTotalPages());
        postResponse.setTotalElements(content.getTotalElements());
        postResponse.setLast(content.isLast());

       return postResponse;

    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Post Not found with id: "+id)
        );

        PostDto postDto = mapToDto(post);
        return postDto;
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
       Post post= postRepository.findById(id).orElseThrow(
               ()->new ResourceNotFoundException("Post not found with id:"+id)

        );
       post.setTitle(postDto.getTitle());
       post.setContent(postDto.getContent());
       post.setDescription(postDto.getDescription());

        Post updatedpost = postRepository.save(post);
       return mapToDto(updatedpost);

    }

    @Override
    public void deletePost(long id) {
        Post post= postRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Post not found with id:"+id)

        );
        postRepository.delete(post);


    }

    @Override
    public Post createPost(PostDto postDto) {
        Post posts = mapper.map(postDto, Post.class);
//        Post post = mapToEntity(postDto);
//        Post save = postRepository.save(post);
//        PostDto dto = mapToDto(save);
        return posts;


    }

    @Override
    public Post createPost(Post post) {
        Post map = mapper.map(post, Post.class);
        return map;
    }

    Post mapToEntity(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());

        return post;
    }

    PostDto mapToDto(Post post) {
        PostDto dto = mapper.map(post, PostDto.class);

//        PostDto dto = new PostDto();
//
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setContent(post.getContent());
//        dto.setDescription(post.getDescription());
        return dto;
    }


}