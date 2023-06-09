package ru.tinkoff.edu.java.scrapper.repo.jpa;

import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.dtos.Link;

@Repository
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public interface JpaLinkRepo extends JpaRepository<Link, Long> {
    List<Link> findLinksByLastScrappedBefore(OffsetDateTime lastScrapped);

    Link findByLinkId(long linkId);

    @Modifying
    void removeLinkByUrlLike(String url);

    Link findByUrlLike(String url);
}
