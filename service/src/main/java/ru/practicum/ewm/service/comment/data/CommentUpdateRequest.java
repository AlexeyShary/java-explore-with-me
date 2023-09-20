package ru.practicum.ewm.service.comment.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpdateRequest {
    @NotBlank
    @Size(max = 5000)
    private String text;
}
