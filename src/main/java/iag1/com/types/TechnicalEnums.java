package iag1.com.types;

/**
 * Created by dqromney on 9/26/15.
 */
public enum TechnicalEnums {
    RSI_PERIOD_AVERAGE_DEFAULT(14)
    ,SMA_PERIOD_AVERAGE_DEFAULT(14)
    ;

    private Integer value;

    TechnicalEnums(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
