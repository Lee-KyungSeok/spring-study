package com.kyung.springjpa.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    @Autowired
    private PostRepository posts;

    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable("id") Post post) { // id 로 주고 Post 를 받으면 converter 가 아래 주석 작업을 수행해 준다
//        Optional<Post> byId = posts.findById(id);
//        Post post = byId.get();
        return post.getTitle();
    }

    // PagedResourcesAssembler 는 hateoas 기능을 제공한다.
    @GetMapping("/posts")
    public PagedResources<Resource<Post>> getPost(Pageable pageable, PagedResourcesAssembler<Post> assembler) {
        return assembler.toResource(posts.findAll(pageable));
    }
}
