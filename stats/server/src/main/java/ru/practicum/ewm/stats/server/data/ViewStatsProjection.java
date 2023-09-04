package ru.practicum.ewm.stats.server.data;

public interface ViewStatsProjection {
    String getApp();

    String getUri();

    Long getHits();
}
