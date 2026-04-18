package com.artichourey.insighthub.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.artichourey.insighthub.dtos.CommentRequestDto;
import com.artichourey.insighthub.dtos.CommentResponseDto;
import com.artichourey.insighthub.entities.Comment;
import com.artichourey.insighthub.entities.Post;
import com.artichourey.insighthub.entities.User;
import com.artichourey.insighthub.exception.ResourceNotFoundException;
import com.artichourey.insighthub.mapper.CommentMapper;
import com.artichourey.insighthub.repositories.CommentRepository;
import com.artichourey.insighthub.repositories.PostRepository;
import com.artichourey.insighthub.repositories.UserRepository;
import com.artichourey.insighthub.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j

public class CommentServiceImpl implements CommentService {

	private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;

    @Override
    public CommentResponseDto addComment(Long postId, CommentRequestDto request, String username) {

        log.info("Adding comment: postId={}, username={}, parentCommentId={}, content={}",
                postId, username, request.getParentCommentId(), request.getContent());

        User user = userRepository.findByName(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        Comment parent = null;

        if (request.getParentCommentId() != null) {
            parent = commentRepository.findById(request.getParentCommentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent comment not found"));
        }

        Comment comment = commentMapper.toEntity(request, user, post, parent);

        Comment savedComment = commentRepository.save(comment);

        log.info("Comment saved successfully: commentId={}", savedComment.getId());

        return commentMapper.toDto(savedComment);
    }
    
    @Override
    public List<CommentResponseDto> getCommentsByPostId(Long postId) {
    	 log.info("Fetching comments for postId={}", postId);

    	List<Comment> comments	=commentRepository.findByPostPostId(postId);
		return comments.stream().map(c->commentMapper.toDto(c)).collect(Collectors.toList());
    }

    @Override
    public void deleteComment(Long commentId,String name) {
    	log.info("Deleting comment: commentId={}", commentId);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        
        if(!comment.getUser().getName().equals(name)){
            throw new RuntimeException("You are not allowed to delete this comment");
        }
        commentRepository.delete(comment); 
        log.info("Comment deleted successfully: commentId={}", commentId);
    }

}
