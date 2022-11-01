package com.s14_maistorbg.service;

import com.s14_maistorbg.model.dto.commentsDTOs.AddCommentDTO;
import com.s14_maistorbg.model.dto.commentsDTOs.CommentWithUsernameDTO;
import com.s14_maistorbg.model.dto.commentsDTOs.EditCommentDTO;
import com.s14_maistorbg.model.dto.commentsDTOs.ResponseCommentDTO;
import com.s14_maistorbg.model.entities.Comment;
import com.s14_maistorbg.model.entities.Craftsman;
import com.s14_maistorbg.model.entities.User;
import com.s14_maistorbg.model.exceptions.BadRequestException;
import com.s14_maistorbg.model.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService extends AbstractService{

    public ResponseCommentDTO addComment(AddCommentDTO commentDTO, int userId, int craftsmanToRate){
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        User user = getUserById(userId);
        Craftsman craftsman = getCraftsmanById(craftsmanToRate);
        validateCommentText(commentDTO.getComment());
        Comment comment = modelMapper.map(commentDTO, Comment.class);
        comment.setCommentOwner(user);
        comment.setCraftsman(craftsman);
        comment.setCommentAt(LocalDateTime.now());
        if (commentDTO.getParentCommentId() != null){
            comment.setParentComment(commentRepository.findById(commentDTO.getParentCommentId().get())
                    .orElseThrow(()-> new BadRequestException("Invalid parent comment!")));
        }
        commentRepository.save(comment);
        ResponseCommentDTO responseCommentDTO = modelMapper.map(comment, ResponseCommentDTO.class);
        return responseCommentDTO;
    }

    public ResponseCommentDTO editComment(EditCommentDTO editCommentDTO, int commentId) {
        Comment comment = getCommentById(commentId);
        validateCommentText(editCommentDTO.getComment());
        comment.setComment(editCommentDTO.getComment());
        commentRepository.save(comment);
        return modelMapper.map(comment, ResponseCommentDTO.class);
    }

    public ResponseCommentDTO deleteComment(int commentId) {
        Comment comment = getCommentById(commentId);
        commentRepository.delete(comment);
        System.out.println("The comment is successful deleted.");
        return modelMapper.map(comment, ResponseCommentDTO.class);
    }

    public Comment getCommentById(int id){
        return commentRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Comment not found!"));
    }

    public CommentWithUsernameDTO getCommentWithUsernameDTOById(int id){
        Comment comment = getCommentById(id);
        return modelMapper.map(comment, CommentWithUsernameDTO.class);
    }

    public List<CommentWithUsernameDTO> getAllCommentByOwnerId(int ownerId){
        User user = getUserById(ownerId);
        List<Comment> comments = commentRepository.findAllByCommentOwner(user);
        for (Comment c : comments){
            if (c.getCommentOwner().getId() != user.getId()){
                throw new BadRequestException("You are not owner of the comments!");
            }
        }
        List<CommentWithUsernameDTO> commentsWithUsername = new ArrayList<>();
        for (int i = 0; i < comments.size(); i++) {
            CommentWithUsernameDTO comment = modelMapper.map(comments.get(i), CommentWithUsernameDTO.class);
            comment.setOwnerUsername(user.getUsername());
            comment.setCommentToCraftsmanId(comments.get(i).getCraftsman().getUserId());
            commentsWithUsername.add(comment);
        }
        return commentsWithUsername;
    }

    private void validateCommentText(String comment){
        int minCommentLength = 10;
        if (comment == null ||
            comment.isBlank() ||
            comment.isEmpty()){
            throw new BadRequestException("There is no text in the comment body!");
        }
        if (comment.length() < minCommentLength){
            throw new BadRequestException("Write a more describing comment!");
        }
    }
}
