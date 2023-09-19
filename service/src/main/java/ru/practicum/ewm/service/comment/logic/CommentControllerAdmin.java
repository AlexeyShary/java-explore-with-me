package ru.practicum.ewm.service.comment.logic;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.service.comment.data.CommentDto;
import ru.practicum.ewm.service.comment.data.CommentUpdateRequest;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin/comments")
@RequiredArgsConstructor
public class CommentControllerAdmin {
    private final CommentService commentService;

    @PatchMapping("/{commentId}")
    public CommentDto patch(@PathVariable long commentId,
                            @Valid @RequestBody CommentUpdateRequest commentUpdateRequest) {
        return commentService.patchByAdmin(commentId, commentUpdateRequest);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long commentId) {
        commentService.deleteByAdmin(commentId);
    }
}
