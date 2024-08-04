package xpa.shadow.qtracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xpa.shadow.qtracker.data.JKAServer;

@Repository
public interface JKAServerRepository extends JpaRepository<JKAServer, String> {
}
