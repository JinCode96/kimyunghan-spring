package hello.core.singleton;

public class SingletonService {

    /**
     * 싱글톤
     * static 영역에 한개만 만들어짐
     * 실행할 때 new 를 보고 자기 자신을 만들어놔서 instance 변수에 참조값을 넣어놓는다
     */
    private static final SingletonService instance = new SingletonService();

    // 생성자를 private 으로 하여 외부 호출 차단
    private SingletonService() {
    }

    // 객체를 호출하려면 getInstance()로만 꺼내야함
    public static SingletonService getInstance() {
        return instance;
    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
    
}
