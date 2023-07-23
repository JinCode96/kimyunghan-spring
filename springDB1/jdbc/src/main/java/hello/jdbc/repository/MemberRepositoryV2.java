package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;

/**
 * JDBC - Connection Param
 */
@Slf4j
public class MemberRepositoryV2 {

    private final DataSource dataSource;

    public MemberRepositoryV2(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Member save(Member member) throws SQLException {

        String sql = "insert into member(member_id, money) values (?, ?)";

        // finally 에서 호출해야하기 때문에 밖에서 선언
        Connection con = null;
        PreparedStatement psmt = null; // sql 인잭션 공격을 막기위해 PreparedStatement 의 바인딩 방식을 사용하자.

        try {
            con = getConnection(); // 연결
            psmt = con.prepareStatement(sql); // 쿼리 전송

            psmt.setString(1, member.getMemberId()); // 파라미터 바인딩
            psmt.setInt(2, member.getMoney());
            
            psmt.executeUpdate(); // 쿼리 실행. 영향을 받은 row 수 만큼 반환
            return member;
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(con, psmt, null); // 리소스 정리
        }
    }

    public Member findById(Connection con, String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";

        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            psmt = con.prepareStatement(sql);
            psmt.setString(1, memberId);

            rs = psmt.executeQuery();

            if (rs.next()) {
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            } else {
                throw new NoSuchElementException("member not found memberId=" + memberId);
            }
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            // Connection 은 여기서 닫지 않는다
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(psmt);
        }
    }

    public void update(Connection con, String memberId, int money) throws SQLException {
        String sql = "update member set money = ? where member_Id = ?";
        PreparedStatement psmt = null;

        try {
            psmt = con.prepareStatement(sql); // 쿼리 전송
            psmt.setInt(1, money); // 파라미터 바인딩
            psmt.setString(2, memberId);
            int resultSize = psmt.executeUpdate();// 쿼리 실행. 영향을 받은 row 수 만큼 반환
            log.info("resultSize={}", resultSize);
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            // Connection 은 여기서 닫지 않는다
            JdbcUtils.closeStatement(psmt);
        }
    }

    public void delete(String memberId) throws SQLException {
        String sql = "delete from member where member_id=?";
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(con, pstmt, null);
        }
    }


    // 사용한 자원 닫아주기 (닫지 않으면 리소스 누수로 인해 커넥션 부족 장애가 생길 수도 있다.)
    private void close(Connection con, Statement stmt, ResultSet rs) {

        // JdbcUtils 사용
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        JdbcUtils.closeConnection(con);

    }

    // 커낵션 획득
    private Connection getConnection() throws SQLException {
        Connection con = dataSource.getConnection();
        log.info("get Connection={}, get Class={}", con, con.getClass());
        return con;
    }

}
