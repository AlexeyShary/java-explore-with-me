package ru.practicum.ewm.stats.server.logic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import ru.practicum.ewm.stats.dto.EndpointHitDto;
import ru.practicum.ewm.stats.dto.ViewStatsDto;
import ru.practicum.ewm.stats.server.data.EndpointHit;
import ru.practicum.ewm.stats.server.data.EndpointHitRepository;
import ru.practicum.ewm.stats.server.data.ViewStatsProjection;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class StatsServiceTest {
    @InjectMocks
    private StatsService statsService;

    @Mock
    private EndpointHitRepository endpointHitRepository;

    @Test
    void getStats_unique() {
        when(endpointHitRepository.findUniqueStats(any(), any(), any())).thenReturn(getMockViewStatsProjectionsList());

        statsService.getStats(null, null, null, true);

        verify(endpointHitRepository, times(1)).findUniqueStats(any(), any(), any());
        verifyNoMoreInteractions(endpointHitRepository);
    }

    @Test
    void getStats_notUnique() {
        when(endpointHitRepository.findNotUniqueStats(any(), any(), any())).thenReturn(getMockViewStatsProjectionsList());

        statsService.getStats(null, null, null, false);

        verify(endpointHitRepository, times(1)).findNotUniqueStats(any(), any(), any());
        verifyNoMoreInteractions(endpointHitRepository);
    }

    @Test
    void getStats_MappingTest() {
        List<ViewStatsProjection> mockProjections = getMockViewStatsProjectionsList();

        when(endpointHitRepository.findNotUniqueStats(any(), any(), any())).thenReturn(mockProjections);

        List<ViewStatsDto> resultDto = statsService.getStats(null, null, null, false);

        assertNotNull(resultDto);

        assertEquals(mockProjections.size(), resultDto.size());

        for (int i = 0; i < mockProjections.size(); i++) {
            ViewStatsProjection mockProjection = mockProjections.get(i);
            ViewStatsDto result = resultDto.get(i);

            assertEquals(mockProjection.getApp(), result.getApp());
            assertEquals(mockProjection.getUri(), result.getUri());
            assertEquals(mockProjection.getHits(), result.getHits());
        }
    }

    @Test
    void createEndpointHit_Test() {
        when(endpointHitRepository.save(any(EndpointHit.class))).thenReturn(new EndpointHit());

        statsService.createEndpointHit(EndpointHitDto.builder().build());

        verify(endpointHitRepository, times(1)).save(any(EndpointHit.class));
        verifyNoMoreInteractions(endpointHitRepository);
    }

    private List<ViewStatsProjection> getMockViewStatsProjectionsList() {
        List<ViewStatsProjection> result = new ArrayList<>();

        ProjectionFactory projectionFactory = new SpelAwareProxyProjectionFactory();

        ViewStatsProjection projection1 = projectionFactory.createProjection(ViewStatsProjection.class);
        projection1.setApp("App1");
        projection1.setUri("Uri1");
        projection1.setHits(100L);
        result.add(projection1);

        ViewStatsProjection projection2 = projectionFactory.createProjection(ViewStatsProjection.class);
        projection2.setApp("App2");
        projection2.setUri("/uri2");
        projection2.setHits(200L);
        result.add(projection2);

        return result;
    }
}