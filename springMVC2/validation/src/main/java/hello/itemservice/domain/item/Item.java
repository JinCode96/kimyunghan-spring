package hello.itemservice.domain.item;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.ScriptAssert;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
//@ScriptAssert(lang="javascript", script = "_this.price * _this.quantity >= 10000", message = "총 합이 10000원이 넘어야합니다") -> 권장하지 않음. 기능이 너무 약함. 자바코드를 활용하는 것이 더 낫다.
public class Item {

    /**
     * @NotBlank : 빈값 + 공백만 있는 경우를 허용하지 않는다
     * @NotNull : null 값을 허용하지 않는다
     * @Range : 범위 안의 값이어야한다.
     * @Max : 최대를 넘지 않아야한다.
     */

    private Long id;

//    @NotBlank
    private String itemName;

//    @NotNull
//    @Range(min=1000, max=1000000)
    private Integer price;

//    @NotNull
//    @Max(9999)
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
