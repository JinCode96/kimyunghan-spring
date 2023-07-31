package hello.itemservice.repository.v2;

import hello.itemservice.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 간단한 쿼리 처리
 * 자동으로 스프링 빈에 등록됨
 */
public interface ItemRepositoryV2 extends JpaRepository<Item, Long> {

}
