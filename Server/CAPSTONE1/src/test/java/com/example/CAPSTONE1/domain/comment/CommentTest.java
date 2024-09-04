package com.example.CAPSTONE1.domain.comment;

import com.example.CAPSTONE1.comment.dto.response.CommentResponse;
import com.example.CAPSTONE1.comment.entity.Comment;
import com.example.CAPSTONE1.comment.repo.CommentRepo;
import com.example.CAPSTONE1.comment.service.CommentService;
import com.example.CAPSTONE1.common.exception.CommonException;
import com.example.CAPSTONE1.article.entity.Article;
import com.example.CAPSTONE1.article.repo.ArticleRepo;
import com.example.CAPSTONE1.user.entity.ROLE;
import com.example.CAPSTONE1.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentTest {
    @Mock
    private CommentRepo commentRepo;  // Mock CommentRepo 생성

    @Mock
    private ArticleRepo articleRepo;  // Mock CommunityRepo 생성

    @InjectMocks
    private CommentService commentService;

    @Test
    public void readComment_WhenPostExists_ShouldReturnComments() {
        // Arrange: 테스트 준비 단계
        Long postId = 1L;
        Comment comment1 = new Comment();
        Comment comment2 = new Comment();
        List<Comment> commentList = List.of(comment1, comment2);
        Article article = new Article(1L,"test","test content",0,0,new User(1L,"test","test@",ROLE.NORMAL,"test nick",LocalDateTime.now()),commentList);  // Mock Community 객체

        when(articleRepo.findById(postId)).thenReturn(Optional.of(article));  // Mock 동작 설정: postId로 Community 반환

        // Act: 실제로 서비스 메서드 호출
        List<CommentResponse.CommentReadResponse> result = commentService.readComment(1L);

        // Assert: 기대 결과 검증
        assertEquals(2, result.size());  // 두 개의 댓글이 반환되었는지 확인
    }

    @Test
    public void readComment_WhenPostDoesNotExist_ShouldThrowException() {
        // Arrange: postId에 대한 Community가 존재하지 않는 상황
        Long postId = 1L;

        when(articleRepo.findById(postId)).thenReturn(Optional.empty());  // Community가 없는 경우

        // Act & Assert: 예외가 발생하는지 확인
        assertThrows(CommonException.class, () -> commentService.readComment(postId));

    }
}
