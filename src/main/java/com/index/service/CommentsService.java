package com.index.service;

import com.index.dto.AddCommentDto;
import com.index.dto.CommentDto;
import com.index.model.Comments;
import com.index.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentsService {

    CommentRepository commentRepository;

    public CommentsService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public CommentDto addComment(AddCommentDto addComment) {
        return commentRepository.save(Comments.createComment(addComment)).dto();
    }

}
