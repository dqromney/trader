package iag1.com.analytics;

import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;
import iag1.com.model.Bar;
import iag1.com.types.Direction;
import iag1.com.types.TechnicalEnums;

import java.util.List;

/**
 * TechnicalEnums class.
 *
 * Created by dqromney on 9/26/15.
 */
public class Technical {

    /**
     * Add RSI indicator to bar list.
     *
     * A Momentum Oscillator, measuring the velocity and magnitude of directional price movements.
     *
     * @param pBarList a list of populated {@link Bar} objects sorted by date in ascending order
     * @param pPeriodAverage the ar
     * @return the updated list of {@link Bar} objects
     */
    public static List<Bar> rsi(List<Bar> pBarList, Integer pPeriodAverage) {
        MInteger begin = new MInteger();
        MInteger length = new MInteger();
        double[] closePrice = new double[pBarList.size()];
        double[] out = new double[pBarList.size()];

        // Populate the closing price array
        for (int i = 0; i < pBarList.size(); i++) {
            closePrice[i] = pBarList.get(i).getClose();
        }

        // Set default to 14 day average
        if(pPeriodAverage == null) {
            pPeriodAverage = TechnicalEnums.RSI_PERIOD_AVERAGE_DEFAULT.getValue();
        }

        Core core = new Core();
        RetCode retCode = core.rsi(0, pBarList.size() - 1, closePrice, pPeriodAverage, begin, length, out);
        if (retCode == RetCode.Success) {
            // Apply technical to bar list
            for (int i = begin.value; i < closePrice.length; i++) {
                pBarList.get(i).setRsi(out[i - begin.value]);
            }
        } else {
            System.out.println(String.format("Error: %1$s", RetCode.AllocErr.name()));
        }

        return pBarList;
    }

    // Simple Moving Average
    public static List<Bar> sma(List<Bar> pBarList, Integer pPeriodAverage) {
        MInteger begin = new MInteger();
        MInteger length = new MInteger();
        double[] closePrice = new double[pBarList.size()];
        double[] out = new double[pBarList.size()];

        // Populate the closing price array
        for (int i = 0; i < pBarList.size(); i++) {
            closePrice[i] = pBarList.get(i).getClose();
        }

        // Set default to 14 day average
        if(pPeriodAverage == null) {
            pPeriodAverage = TechnicalEnums.RSI_PERIOD_AVERAGE_DEFAULT.getValue();
        }

        Core core = new Core();
        RetCode retCode = core.sma(0, pBarList.size() - 1, closePrice, pPeriodAverage, begin, length, out);
        if (retCode == RetCode.Success) {
            // Apply technical to bar list
            for (int i = begin.value; i < closePrice.length; i++) {
                pBarList.get(i).setSma(out[i - begin.value]);
            }
        } else {
            System.out.println(String.format("Error: %1$s", RetCode.AllocErr.name()));
        }

        return pBarList;
    }

    // Apply cycle changes to bar list, price change, volume change, etc
    public static List<Bar> applyCycleChanges(List<Bar> pBarList) {
        Bar prevBar = null;
        for(Bar bar: pBarList) {
            bar.setCloseChange(0.0);
            if(prevBar != null) {
                bar.setCloseChange(bar.getClose() - prevBar.getClose());
                bar.setClosePercentChange(bar.getCloseChange() / prevBar.getClose() * 100);
                Double rsiDiff = bar.getRsi() - prevBar.getRsi();
                Double smaDiff = bar.getSma() - prevBar.getSma();
                Long volumeDiff = bar.getVolume() - prevBar.getVolume();
                bar.setRsiDirection(rsiDiff == 0.0 ? Direction.EVEN : rsiDiff > 0.0 ? Direction.UP : Direction.DOWN);
                bar.setSmaDirection(smaDiff == 0.0 ? Direction.EVEN : smaDiff > 0.0 ? Direction.UP : Direction.DOWN);
                bar.setVolumeChange(volumeDiff);
                bar.setVolumeChangeDirection(volumeDiff == 0L ? Direction.EVEN : volumeDiff > 0L ? Direction.UP : Direction.DOWN);
            }
            prevBar = bar;
            System.out.println(bar.toString());
        }
        return pBarList;
    }
}
