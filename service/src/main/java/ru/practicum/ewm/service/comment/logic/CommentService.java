package ru.practicum.ewm.service.comment.logic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.service.comment.data.*;
import ru.practicum.ewm.service.event.data.event.Event;
import ru.practicum.ewm.service.event.data.event.EventRepository;
import ru.practicum.ewm.service.user.data.User;
import ru.practicum.ewm.service.user.data.UserRepository;
import ru.practicum.ewm.service.util.exception.ForbiddenException;
import ru.practicum.ewm.service.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Transactional(readOnly = true)
    public List<CommentDto> getAllByEventId(long eventId) {
        return commentRepository.findAllByEventId(eventId).stream()
                .map(CommentMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentDto create(long userId, long eventId, CommentNewDto dto) {
        User user = findUserById(userId);
        Event event = findEventById(eventId);

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setEvent(event);
        comment.setText(dto.getText());
        comment.setCreatedOn(LocalDateTime.now());

        comment = commentRepository.save(comment);

        return CommentMapper.INSTANCE.toDto(comment);
    }

    @Transactional
    public CommentDto patchByUser(long userId, long commentId, CommentUpdateRequest updateRequest) {
        findUserById(userId);
        Comment comment = findCommentById(commentId);

        if(!comment.getUser().getId().equals(userId)) {
            throw new ForbiddenException("User id=" + userId + " not owner of Comment id=" + commentId);
        }

        Optional.ofNullable(updateRequest.getText()).ifPresent(comment::setText);

        comment = commentRepository.save(comment);

        return CommentMapper.INSTANCE.toDto(comment);
    }

    @Transactional
    public CommentDto patchByAdmin(long commentId, CommentUpdateRequest updateRequest) {
        Comment comment = findCommentById(commentId);

        Optional.ofNullable(updateRequest.getText()).ifPresent(comment::setText);

        comment = commentRepository.save(comment);

        return CommentMapper.INSTANCE.toDto(comment);
    }

    @Transactional
    public void deleteByUser(long userId, long commentId) {
        findUserById(userId);
        Comment comment = findCommentById(commentId);

        if(!comment.getUser().getId().equals(userId)) {
            throw new ForbiddenException("User id=" + userId + " not owner of Comment id=" + commentId);
        }

        commentRepository.deleteById(commentId);
    }

    @Transactional
    public void deleteByAdmin(long commentId) {
        commentRepository.deleteById(commentId);
    }

    private Comment findCommentById(long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comment with id=" + id + " was not found"));
    }

    private User findUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id=" + id + " was not found"));
    }

    private Event findEventById(long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event with id=" + id + " was not found"));
    }
}