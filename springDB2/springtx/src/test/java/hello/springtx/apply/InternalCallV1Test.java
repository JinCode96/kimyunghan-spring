package hello.springtx.apply;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@SpringBootTest
public class InternalCallV1Test {

    @Autowired
    CallService callService; // 프록시 주입

    @Test
    void printProxy() {
        log.info("callService class = {}", callService.getClass());
    }

    @Test
    void internalCall() {
        callService.internal();
    }

    @Test
    void externalCall() {
        callService.external(); // 프록시를 호출하지 않고, 실제 클래스를 호출.
    }

    @TestConfiguration
    static class InternalCallV1TestConfig{

        @Bean
        CallService callService() {
            return new CallService();
        }
    }

    @Slf4j
    static class CallService{

        public void external() {
            log.info("call external");
            printTxInfo();
            this.internal(); // 내부에서 트랜잭션인 메서드를 호출. (this 가 생략되어있음) (나 자신의 인스턴스 주소)
        }

        @Transactional
        public void internal() {
            log.info("call internal");
            printTxInfo();
        }

        private void printTxInfo() {
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("tx active={}", txActive);
        }

    }


}
