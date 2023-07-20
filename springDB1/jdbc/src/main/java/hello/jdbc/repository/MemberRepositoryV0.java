package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.NoSuchElementException;

/**
 * JDBC - DriverManager 사용
 */
@Slf4j
@Repository
public class MemberRepositoryV0 {

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

    public Member findById(String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";

        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
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
            close(con, psmt, rs);
        }
    }

    public void update(String memberId, int money) throws SQLException {
        String sql = "update member set money = ? where member_Id = ?";
        Connection con = null;
        PreparedStatement psmt = null;

        try {
            con = getConnection(); // 연결
            psmt = con.prepareStatement(sql); // 쿼리 전송
            psmt.setInt(1, money); // 파라미터 바인딩
            psmt.setString(2, memberId);
            int resultSize = psmt.executeUpdate();// 쿼리 실행. 영향을 받은 row 수 만큼 반환
            log.info("resultSize={}", resultSize);
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(con, psmt, null); // 리소스 정리
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
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }
    }

    private static Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }
}
