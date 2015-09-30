package iag1.com.utils;

import java.util.Date;

/**
 * Watchlist class.
 *
 * Created by dqromney on 9/23/15.
 */
public class WatchList {
    private final String symbol;
    private final String name;
    private final String exchange;
    // Optional
    private Date ewrIssueDate;
    private Double ewrRiskLevel;
    private Double ewrProfitPotential;
    private Double ewrPayoffPeriod;

    private WatchList(WatchListBuilder pBuilder) {
        this.symbol = pBuilder.symbol;
        this.name = pBuilder.name;
        this.exchange = pBuilder.exchange;
        this.ewrIssueDate = pBuilder.ewrIssueDate;
        this.ewrRiskLevel = pBuilder.ewrRiskLevel;
        this.ewrProfitPotential = pBuilder.ewrProfitPotential;
        this.ewrPayoffPeriod = pBuilder.ewrPayoffPeriod;
    }
    public WatchList(String symbol, String name, String exchange) {
        this.symbol = symbol;
        this.name = name;
        this.exchange = exchange;
    }

    public String getSymbol() {
        return symbol;
    }
    public String getName() {
        return name;
    }
    public String getExchange() {
        return exchange;
    }
    public Date getEwrIssueDate() {
        return ewrIssueDate;
    }
    public Double getEwrRiskLevel() {
        return ewrRiskLevel;
    }
    public Double getEwrProfitPotential() {
        return ewrProfitPotential;
    }
    public Double getEwrPayoffPeriod() {
        return ewrPayoffPeriod;
    }

    /**
     * The builder pattern builder.
     */
    public static class WatchListBuilder {
        private final String symbol;
        private final String name;
        private final String exchange;
        private Date ewrIssueDate;
        private Double ewrRiskLevel;
        private Double ewrProfitPotential;
        private Double ewrPayoffPeriod;

        public WatchListBuilder(String symbol, String name, String exchange) {
            this.symbol = symbol;
            this.name = name;
            this.exchange = exchange;
        }

        public WatchListBuilder ewrIssueDate(Date pIssueDate) {
            this.ewrIssueDate = pIssueDate;
            return this;
        }
        public WatchListBuilder ewrRiskLevel(Double pEwrRiskLevel) {
            this.ewrRiskLevel = pEwrRiskLevel;
            return this;
        }
        public WatchListBuilder ewrProfitPotential(Double pEwrProfitPotential) {
            this.ewrProfitPotential = pEwrProfitPotential;
            return this;
        }
        public WatchListBuilder ewrPayoffPeriod(Double pEwrPayoffPeriod) {
            this.ewrPayoffPeriod = pEwrPayoffPeriod;
            return this;
        }
        public WatchList build() {
            return new WatchList(this);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WatchList{");
        sb.append("symbol='").append(symbol).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", exchange='").append(exchange).append('\'');
        sb.append(", ewrIssueDate=").append(ewrIssueDate);
        sb.append(", ewrRiskLevel=").append(ewrRiskLevel);
        sb.append(", ewrProfitPotential=").append(ewrProfitPotential);
        sb.append(", ewrPayoffPeriod=").append(ewrPayoffPeriod);
        sb.append('}');
        return sb.toString();
    }
}
