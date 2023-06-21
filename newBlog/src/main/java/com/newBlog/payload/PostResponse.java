package com.newBlog.payload;

import lombok.Data;

import java.util.List;
@Data
public class PostResponse  {
   private List<PostDto>content;
    private int pageNo;
    private  int pageSize;
    private   long totalElements;
    private   int totalpage;
    private boolean isLast;
}
