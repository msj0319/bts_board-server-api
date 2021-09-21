package com.bts.api.board.controller;

import com.bts.api.board.domain.Posts;
import com.bts.api.board.repository.CommentRepository;
import com.bts.api.board.repository.CustomPostsRepositoryImpl;
import com.bts.api.board.repository.PostsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = PostController.class)
@Import(PostsRepository.class)
class PostControllerTest {
    //BoardApplication 의 더미데이터 테스트 지우고 실행
    @MockBean
    PostsRepository postsRepository;
    @MockBean
    CommentRepository commentRepository;
    @MockBean
    CustomPostsRepositoryImpl customPostsRepository;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
    }

    @DisplayName("모든 게시물 조회 테스트")
    @Test
    void getAllPostsTest() {
        //준비
        Posts posts = new Posts();
        posts.setAuthor("게시물 작성자");
        posts.setTitle("게시물 제목");
        posts.setContent("게시물 내용");
        List<Posts> postsList = new ArrayList<>();
        postsList.add(posts);
        Flux<Posts> postsFlux = Flux.fromIterable(postsList);
        //실행
        Mockito
                .when(postsRepository.findAll())
                .thenReturn(postsFlux);
        //단언
        webTestClient.get().uri("/board")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Posts.class);
        Mockito
                .verify(postsRepository, Mockito.times(1))
                .findAll(Sort.by(Sort.Direction.DESC, "p_id"));
    }

    @DisplayName("단일 게시물 조회 테스트")
    @Test
    void getThePostTest() {
        //준비
        Posts posts = new Posts();
        posts.setP_id("test_id");
        posts.setAuthor("게시물 작성자");
        posts.setTitle("게시물 제목");
        posts.setContent("게시물 내용");
        //실행
        Mockito
                .when(postsRepository.findById("test_id"))
                .thenReturn(Mono.just(posts));
        //단언
        webTestClient.get().uri("/board_post/{p_id}", "test_id")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                //.jsonPath("$.author").isNotEmpty()
                .jsonPath("$.p_id").isEqualTo("test_id")
                .jsonPath("$.author").isEqualTo("게시물 작성자")
                .jsonPath("$.title").isEqualTo("게시물 제목")
                .jsonPath("$.content").isEqualTo("게시물 내용");

    }

    @DisplayName("게시물 생성 테스트")
    @Test
    void createThePostTest() {
        //준비
        Posts posts = new Posts();
        posts.setAuthor("게시물 작성자");
        posts.setTitle("게시물 제목");
        posts.setContent("게시물 내용");
        posts.setCreatePostDate(LocalDateTime.now());
        posts.setModifiedPostDate(LocalDateTime.now());
        //실행
        Mockito
                .when(postsRepository.save(posts))
                .thenReturn(Mono.just(posts));
        //단언
        webTestClient.post()
                .uri("/board_write")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(posts), Posts.class)
                .exchange()
                .expectStatus().isOk();
    }

    @DisplayName("게시물 수정 테스트")
    @Test
    void updateThePostTest() {

    }

    @DisplayName("게시물 삭제 테스트")
    @Test
    void deleteThePostTest() {
        //준비
        Mono<Void> voidReturn = Mono.empty();
        //실행
        Mockito
                .when(postsRepository.deleteById("test_id"))
                .thenReturn(voidReturn);
        //단언
        webTestClient.get()
                .uri("/board_post/{p_id}", "test_id")
                .exchange()
                .expectStatus().isOk();
    }
}