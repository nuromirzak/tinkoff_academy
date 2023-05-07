package ru.tinkoff.edu.java.scrapper.repo.jpa;

import jakarta.transaction.Transactional;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.dtos.Link;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public interface JpaLinkRepo extends JpaRepository<Link, Long> {
    List<Link> findLinksByChatsChatId(long chatId);

    @Modifying
    int removeAllBy();

    List<Link> findLinksByLastScrappedBefore(OffsetDateTime lastScrapped);

    Link findByLinkId(long linkId);

    @Modifying
    int removeLinkByUrlLike(String url);

    Link findByUrlLike(String url);
}
