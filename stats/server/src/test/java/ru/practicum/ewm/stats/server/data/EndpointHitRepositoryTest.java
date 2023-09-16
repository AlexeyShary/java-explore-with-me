package ru.practicum.ewm.stats.server.data;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "classpath:test-application.properties")
class EndpointHitRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private EndpointHitRepository endpointHitRepository;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void createTestData() {
        createEndpointHit("App1", "/uri1", "1.1.1.1", LocalDateTime.now());
        createEndpointHit("App1", "/uri1", "1.1.1.1", LocalDateTime.now());
        createEndpointHit("App1", "/uri1", "1.1.1.2", LocalDateTime.now());
        createEndpointHit("App1", "/uri1", "1.1.1.2", LocalDateTime.now());
        createEndpointHit("App1", "/uri1", "1.1.1.3", LocalDateTime.now());
        createEndpointHit("App1", "/uri1", "1.1.1.3", LocalDateTime.now());
        createEndpointHit("App1", "/uri2", "1.1.1.3", LocalDateTime.now());
        createEndpointHit("App1", "/uri2", "1.1.1.3", LocalDateTime.now());
        createEndpointHit("App1", "/uri1", "1.1.1.3", LocalDateTime.now().minusMonths(1));
        createEndpointHit("App1", "/uri1", "1.1.1.3", LocalDateTime.now().minusMonths(1));
        createEndpointHit("App1", "/uri2", "1.1.1.3", LocalDateTime.now().minusMonths(1));
        createEndpointHit("App1", "/uri2", "1.1.1.3", LocalDateTime.now().minusMonths(1));

        assertThat(endpointHitRepository.findAll()).size().isEqualTo(12);
    }

    @Test
    @Order(2)
    public void findUniqueStats_emptyUri_allTime() {
        LocalDateTime start = LocalDateTime.now().minusYears(1);
        LocalDateTime end = LocalDateTime.now().plusYears(1);
        List<String> uris = null;

        List<ViewStatsProjection> viewStatsProjections = endpointHitRepository.findUniqueStats(start, end, uris);

        assertThat(viewStatsProjections).size().isEqualTo(2);

        assertThat(viewStatsProjections.get(0).getHits()).isEqualTo(3);
        assertThat(viewStatsProjections.get(1).getHits()).isEqualTo(1);
    }

    @Test
    @Order(3)
    public void findUniqueStats_withMultipleUri_allTime() {
        LocalDateTime start = LocalDateTime.now().minusYears(1);
        LocalDateTime end = LocalDateTime.now().plusYears(1);
        List<String> uris = Arrays.asList("/uri1", "/uri2", "/uri3");

        List<ViewStatsProjection> viewStatsProjections = endpointHitRepository.findUniqueStats(start, end, uris);

        assertThat(viewStatsProjections).size().isEqualTo(2);

        assertThat(viewStatsProjections.get(0).getHits()).isEqualTo(3);
        assertThat(viewStatsProjections.get(1).getHits()).isEqualTo(1);
    }

    @Test
    @Order(4)
    public void findUniqueStats_withSingleUri_allTime() {
        LocalDateTime start = LocalDateTime.now().minusYears(1);
        LocalDateTime end = LocalDateTime.now().plusYears(1);
        List<String> uris = Arrays.asList("/uri1");

        List<ViewStatsProjection> viewStatsProjections = endpointHitRepository.findUniqueStats(start, end, uris);

        assertThat(viewStatsProjections).size().isEqualTo(1);

        assertThat(viewStatsProjections.get(0).getHits()).isEqualTo(3);
    }

    @Test
    @Order(5)
    public void findUniqueStats_emptyUri_ignoreTooOld() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now().plusYears(1);
        List<String> uris = null;

        List<ViewStatsProjection> viewStatsProjections = endpointHitRepository.findUniqueStats(start, end, uris);

        assertThat(viewStatsProjections).size().isEqualTo(2);

        assertThat(viewStatsProjections.get(0).getHits()).isEqualTo(3);
        assertThat(viewStatsProjections.get(1).getHits()).isEqualTo(1);
    }

    @Test
    @Order(6)
    public void findNotUniqueStats_emptyUri_allTime() {
        LocalDateTime start = LocalDateTime.now().minusYears(1);
        LocalDateTime end = LocalDateTime.now().plusYears(1);
        List<String> uris = null;

        List<ViewStatsProjection> viewStatsProjections = endpointHitRepository.findNotUniqueStats(start, end, uris);

        assertThat(viewStatsProjections).size().isEqualTo(2);

        assertThat(viewStatsProjections.get(0).getHits()).isEqualTo(8);
        assertThat(viewStatsProjections.get(1).getHits()).isEqualTo(4);
    }

    @Test
    @Order(7)
    public void findNotUniqueStats_withMultipleUri_allTime() {
        LocalDateTime start = LocalDateTime.now().minusYears(1);
        LocalDateTime end = LocalDateTime.now().plusYears(1);
        List<String> uris = Arrays.asList("/uri1", "/uri2", "/uri3");

        List<ViewStatsProjection> viewStatsProjections = endpointHitRepository.findNotUniqueStats(start, end, uris);

        assertThat(viewStatsProjections).size().isEqualTo(2);

        assertThat(viewStatsProjections.get(0).getHits()).isEqualTo(8);
        assertThat(viewStatsProjections.get(1).getHits()).isEqualTo(4);
    }

    @Test
    @Order(8)
    public void findNotUniqueStats_withSingleUri_allTime() {
        LocalDateTime start = LocalDateTime.now().minusYears(1);
        LocalDateTime end = LocalDateTime.now().plusYears(1);
        List<String> uris = Arrays.asList("/uri1");

        List<ViewStatsProjection> viewStatsProjections = endpointHitRepository.findNotUniqueStats(start, end, uris);

        assertThat(viewStatsProjections).size().isEqualTo(1);

        assertThat(viewStatsProjections.get(0).getHits()).isEqualTo(8);
    }

    @Test
    @Order(9)
    public void findNotUniqueStats_emptyUri_ignoreTooOld() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now().plusYears(1);
        List<String> uris = null;

        List<ViewStatsProjection> viewStatsProjections = endpointHitRepository.findNotUniqueStats(start, end, uris);

        assertThat(viewStatsProjections).size().isEqualTo(2);

        assertThat(viewStatsProjections.get(0).getHits()).isEqualTo(6);
        assertThat(viewStatsProjections.get(1).getHits()).isEqualTo(2);
    }

    private void createEndpointHit(String app, String uri, String ip, LocalDateTime timestamp) {
        EndpointHit endpointHit = new EndpointHit();
        endpointHit.setApp(app);
        endpointHit.setUri(uri);
        endpointHit.setIp(ip);
        endpointHit.setHitTimestamp(timestamp);

        testEntityManager.persist(endpointHit);
    }
}