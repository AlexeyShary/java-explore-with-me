package ru.practicum.ewm.service.comment.logic;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.service.comment.data.CommentDto;

import java.util.List;

@RestController
@RequestMapping(path = "/comments")
@RequiredArgsConstructor
public class CommentControllerPublic {
    private final CommentService commentService;

    @GetMapping("/{eventId}")
    public List<CommentDto> getAllByEventId(@PathVariable long eventId) {
        return commentService.getAllByEventId(eventId);
    }
}
