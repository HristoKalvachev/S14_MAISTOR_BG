package com.s14_maistorbg.service;

import com.s14_maistorbg.model.dto.commentsDTOs.AddCommentDTO;
import com.s14_maistorbg.model.dto.commentsDTOs.EditCommentDTO;
import com.s14_maistorbg.model.dto.commentsDTOs.ResponseCommentDTO;
import com.s14_maistorbg.model.entities.Comment;
import com.s14_maistorbg.model.entities.Craftsman;
import com.s14_maistorbg.model.entities.User;
import com.s14_maistorbg.model.exceptions.BadRequestException;
import com.s14_maistorbg.model.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
@Service
public class CommentService extends AbstractService{


    public ResponseCommentDTO addComment(AddCommentDTO commentDTO, int userId){
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        User user = userRepository.findById(userId).orElseThrow(()-> new NotFoundException("User not found!"));
        Craftsman craftsman = craftsManRepository.findById(commentDTO.getCraftsmanId()).orElseThrow(()-> new NotFoundException("User not found!"));
        if (commentDTO.getComment() == null ||
            commentDTO.getComment().isBlank() ||
            commentDTO.getComment().isEmpty()){
            throw new BadRequestException("There is no text in the comment body!");
        }

        if (commentDTO.getComment().length() < 10){
            throw new BadRequestException("Write a more describing comment!");
        }
        Comment comment = new Comment();
        comment.setComment(commentDTO.getComment());
        comment.setCommentAt(LocalDate.now());
        comment.setCommentOwner(user);
        comment.setCraftsman(craftsman);
        if (commentDTO.getParentCommentId() != null){
            comment.setParentComment(commentRepository.findById(commentDTO.getParentCommentId().get()).
            orElseThrow(()-> new BadRequestException("Invalid parent comment!")));
        }
        commentRepository.save(comment);
        ResponseCommentDTO responseCommentDTO = modelMapper.map(comment, ResponseCommentDTO.class);
        return responseCommentDTO;
    }

    public ResponseCommentDTO editComment(EditCommentDTO editCommentDTO, int id, int commentId) {
        Comment comment = getCommentById(commentId);
        if (editCommentDTO.getComment() == null ||
                editCommentDTO.getComment().isBlank() ||
                editCommentDTO.getComment().isEmpty()){
            throw new BadRequestException("There is no text in the comment body!");
        }
        comment.setComment(editCommentDTO.getComment());
        commentRepository.save(comment);
        return modelMapper.map(comment, ResponseCommentDTO.class);
    }

    public Comment getCommentById(int id){
        return commentRepository.findById(id).orElseThrow(()-> new NotFoundException("Comment not found!"));
    }

}
