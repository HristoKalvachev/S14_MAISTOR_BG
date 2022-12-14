package com.s14_maistorbg.controller;

import com.s14_maistorbg.model.dto.commentsDTOs.AddCommentDTO;
import com.s14_maistorbg.model.dto.commentsDTOs.CommentWithUsernameDTO;
import com.s14_maistorbg.model.dto.commentsDTOs.EditCommentDTO;
import com.s14_maistorbg.model.dto.commentsDTOs.ResponseCommentDTO;
import com.s14_maistorbg.model.entities.Comment;
import com.s14_maistorbg.model.exceptions.ForbiddenException;
import com.s14_maistorbg.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class CommentController extends AbstractController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/comments/{cid}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseCommentDTO addComment(@RequestBody AddCommentDTO commentDTO, HttpServletRequest request, @PathVariable int cid) {
        int id = getLoggedUserId(request);
        return commentService.addComment(commentDTO, id, cid);
    }

    @PutMapping("/comments/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public ResponseCommentDTO editComment(@RequestBody EditCommentDTO commentToEdit, HttpServletRequest request, @PathVariable int id) {
        int ownerId = getLoggedUserId(request);
        Comment comment = commentService.getCommentById(id);
        if (ownerId != comment.getCommentOwner().getId()){
            throw new ForbiddenException("You can not edit the comment, because your not owner!");
        }
        return commentService.editComment(commentToEdit, id);
    }

    @DeleteMapping("/comments/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public ResponseCommentDTO deleteComment(HttpServletRequest request, @PathVariable int id) {
        int ownerId = getLoggedUserId(request);
        Comment comment = commentService.getCommentById(id);
        if (ownerId != comment.getCommentOwner().getId()){
            throw new ForbiddenException("You can not delete the comment, because your not owner!");
        }
        return commentService.deleteComment(id);
    }

    @GetMapping("/comments/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public CommentWithUsernameDTO getCommentById(@PathVariable int id){
        return commentService.getCommentWithUsernameDTOById(id);
    }

    @GetMapping("/users/{id}/comments")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public ResponseEntity<List<CommentWithUsernameDTO>> getAllCommentByOwnerId(@PathVariable int id){
        return ResponseEntity.ok(commentService.getAllCommentByOwnerId(id));
    }


}
