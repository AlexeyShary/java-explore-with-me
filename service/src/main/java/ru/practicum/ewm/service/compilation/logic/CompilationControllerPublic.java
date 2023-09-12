package ru.practicum.ewm.service.compilation.logic;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.service.compilation.data.CompilationDto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(path = "/compilations")
@RequiredArgsConstructor
public class CompilationControllerPublic {
    private final CompilationService compilationService;

    @GetMapping
    public List<CompilationDto> getAll(@RequestParam(required = false) Boolean pinned,
                                       @Valid @RequestParam(defaultValue = "0") @Min(0) int from,
                                       @Valid @RequestParam(defaultValue = "10") @Min(1) int size) {
        return compilationService.getAll(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto getById(@PathVariable long compId) {
        return compilationService.getById(compId);
    }
}
