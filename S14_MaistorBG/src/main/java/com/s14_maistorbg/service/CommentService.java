package com.s14_maistorbg.service;

import com.s14_maistorbg.model.dto.commentsDTOs.AddCommentDTO;
import com.s14_maistorbg.model.dto.commentsDTOs.ResponseCommentDTO;
import com.s14_maistorbg.model.entities.Comment;
import com.s14_maistorbg.model.entities.User;
import com.s14_maistorbg.model.exceptions.BadRequestException;
import com.s14_maistorbg.model.exceptions.NotFoundException;

import java.time.LocalDate;

public class CommentService extends AbstractService{

    public ResponseCommentDTO addComment(AddCommentDTO commentDTO, int userId, int craftsmanId){
        User user = userRepository.findById(userId).orElseThrow(()-> new NotFoundException("User not found!"));
        if (commentDTO.getComment() == null || commentDTO.getComment().isBlank() || commentDTO.getComment().isEmpty()){
            throw new BadRequestException("There is no text in the comment body!");
        }
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        if (commentDTO.getComment().length() < 10){
            throw new BadRequestException("Write a more describing comment!");
        }
        Comment comment = new Comment();
        comment.setComment(commentDTO.getComment());
        comment.setCommentAt(LocalDate.now());
        comment.setCommentOwner(user);
        comment.setCraftsman(craftsManRepository.findById(craftsmanId));


    }
}
