package com.newBlog.service;

import com.newBlog.entity.Post;
import com.newBlog.payload.PostDto;
import com.newBlog.payload.PostResponse;

public interface PostService {



    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(long id);

    PostDto updatePost(PostDto postDto, long id);

    void deletePost(long id);

    Post createPost(PostDto postDto);

    Post createPost(Post post);
}
