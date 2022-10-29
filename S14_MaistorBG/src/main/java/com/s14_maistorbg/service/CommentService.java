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
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService extends AbstractService{


    public ResponseCommentDTO addComment(AddCommentDTO commentDTO, int userId){
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        User user = getUserById(userId);
        Craftsman craftsman = craftsManRepository.findById(commentDTO.getCraftsmanId())
                .orElseThrow(()-> new NotFoundException("User not found!"));
        validateCommentText(commentDTO.getComment());
        Comment comment = modelMapper.map(commentDTO, Comment.class);
        comment.setCommentOwner(user);
        comment.setCraftsman(craftsman);
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
        //Todo check comment owner field
        List<Comment> comments = commentRepository.findAllByCommentOwner(user);
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
        if (comment == null ||
            comment.isBlank() ||
            comment.isEmpty()){
            throw new BadRequestException("There is no text in the comment body!");
        }
        if (comment.length() < 10){
            throw new BadRequestException("Write a more describing comment!");
        }
    }
}
