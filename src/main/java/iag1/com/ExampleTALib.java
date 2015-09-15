package iag1.com;

import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class ExampleTALib {

    /**
     * The total number of periods to generate data for.
     */
    public static final int TOTAL_PERIODS = 100;

    /**
     * The number of periods to average together.
     */
    public static final int PERIODS_AVERAGE = 14;

    public static void main(String[] args) throws ParseException {
//        double[] closePrice = new double[TOTAL_PERIODS];
//        double[] out = new double[TOTAL_PERIODS];
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        MInteger begin = new MInteger();
        MInteger length = new MInteger();
        Item item = getBars("VSTO", "Vista ...");
        // Sort by date in descending order
        item.getBars().sort(new Comparator<Bar>() {
            public int compare(Bar o1, Bar o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
        double[] closePrice = new double[item.getBars().size()];
        double[] out = new double[item.getBars().size()];

//        for (int i = 0; i < closePrice.length; i++) {
//            closePrice[i] = (double) i;
//        }

        for (int i = 0; i < item.getBars().size(); i++) {
            closePrice[i] = item.getBars().get(i).getClose();
        }

        Core c = new Core();
        // RetCode retCode = c.sma(0, closePrice.length - 1, closePrice, PERIODS_AVERAGE, begin, length, out);
        RetCode retCode = c.rsi(0, item.getBars().size() - 1, closePrice, PERIODS_AVERAGE, begin, length, out);
        if (retCode == RetCode.Success) {
            System.out.println("Output Begin:" + begin.value);
            System.out.println("Output Begin:" + length.value);

            for (int i = begin.value; i < closePrice.length; i++) {
                StringBuilder line = new StringBuilder();
                line.append("Date ");
                line.append(fmt.format(item.getBars().get(i).getDate()));
                line.append("\tclose= ");
                line.append(String.format("%1$2.4f", closePrice[i]));
                line.append("\trsi=");
                line.append(String.format("%1$2.2f", out[i - begin.value]));
                System.out.println(line.toString());
            }

//            for (int i = begin.value; i < length.value; i++) {
//                StringBuilder line = new StringBuilder();
//                line.append("Period #");
//                line.append(i + 1);
//                line.append(" close= ");
//                line.append(closePrice[i]);
//                line.append(" rsi =");
//                line.append(out[i]);
//                System.out.println(line.toString());
//            }
        } else {
            System.out.println("Error");
        }
    }

    private static Item getBars(String pSymbol, String pSymbolName) throws ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Item item = new Item(pSymbol, pSymbolName);
        // The data
        Date testDate = fmt.parse("2015-09-09");

        item.getBars().add(new Bar(fmt.parse("2015-09-09") ,47.240002,47.34,46.02,46.18,182400L,46.18));
        item.getBars().add(new Bar(fmt.parse("2015-09-08") ,46.470001,47.23,46.07,46.939999,168800L,46.939999));
        item.getBars().add(new Bar(fmt.parse("2015-09-04") ,46.07,46.744999,45.720001,45.82,189700L,45.82));
        item.getBars().add(new Bar(fmt.parse("2015-09-03") ,47.290001,47.490002,46.59,46.73,256500L,46.73));
        item.getBars().add(new Bar(fmt.parse("2015-09-02") ,47.259998,47.584999,46.02,46.990002,308400L,46.990002));
        item.getBars().add(new Bar(fmt.parse("2015-09-01") ,45.91,46.990002,45.790001,46.52,256200L,46.52));
        item.getBars().add(new Bar(fmt.parse("2015-08-31") ,48.209999,48.599998,46.68,46.759998,246200L,46.759998));
        item.getBars().add(new Bar(fmt.parse("2015-08-28") ,46.419998,48.689999,46.419998,48.41,344000L,48.41));
        item.getBars().add(new Bar(fmt.parse("2015-08-27") ,45.93,46.82,45.849998,46.299999,233000L,46.299999));
        item.getBars().add(new Bar(fmt.parse("2015-08-26") ,43.970001,45.66,43.41,45.400002,336600L,45.400002));
        item.getBars().add(new Bar(fmt.parse("2015-08-25") ,44.650002,44.73,43.34,43.43,274900L,43.43));
        item.getBars().add(new Bar(fmt.parse("2015-08-24") ,43.470001,45.139999,41.34,43.950001,345000L,43.950001));
        item.getBars().add(new Bar(fmt.parse("2015-08-21") ,45.720001,45.959999,45.09,45.540001,332200L,45.540001));
        item.getBars().add(new Bar(fmt.parse("2015-08-20") ,47.48,47.48,45.970001,46.040001,166100L,46.040001));
        item.getBars().add(new Bar(fmt.parse("2015-08-19") ,48.099998,48.369999,47.41,47.639999,190900L,47.639999));
        item.getBars().add(new Bar(fmt.parse("2015-08-18") ,48.630001,49.27,48.139999,48.279999,221500L,48.279999));
        item.getBars().add(new Bar(fmt.parse("2015-08-17") ,48.369999,48.91,48.09,48.740002,292800L,48.740002));
        item.getBars().add(new Bar(fmt.parse("2015-08-14") ,48.240002,48.880001,48.119999,48.380001,220800L,48.380001));
        item.getBars().add(new Bar(fmt.parse("2015-08-13") ,47.279999,49.014999,47.00,47.709999,473800L,47.709999));
        item.getBars().add(new Bar(fmt.parse("2015-08-12") ,47.220001,47.580002,46.580002,47.310001,262300L,47.310001));
        item.getBars().add(new Bar(fmt.parse("2015-08-11") ,47.18,48.215,47.060001,47.130001,279400L,47.130001));
        item.getBars().add(new Bar(fmt.parse("2015-08-10") ,47.77,48.650002,47.200001,47.220001,298600L,47.220001));
        item.getBars().add(new Bar(fmt.parse("2015-08-07") ,48.189999,48.48,47.220001,47.740002,278600L,47.740002));
        item.getBars().add(new Bar(fmt.parse("2015-08-06") ,48.060001,48.98,47.93,48.349998,216400L,48.349998));
        item.getBars().add(new Bar(fmt.parse("2015-08-05") ,47.459999,48.389999,47.459999,48.169998,233000L,48.169998));
        item.getBars().add(new Bar(fmt.parse("2015-08-04") ,45.880001,47.310001,45.880001,47.290001,160000L,47.290001));
        item.getBars().add(new Bar(fmt.parse("2015-08-03") ,46.970001,47.09,45.630001,46.07,197800L,46.07));
        item.getBars().add(new Bar(fmt.parse("2015-07-31") ,46.360001,47.470001,45.414001,47.169998,262100L,47.169998));
        item.getBars().add(new Bar(fmt.parse("2015-07-30") ,46.080002,46.32,45.869999,46.16,122000L,46.16));
        item.getBars().add(new Bar(fmt.parse("2015-07-29") ,45.880001,46.610001,45.880001,46.25,258700L,46.25));
        item.getBars().add(new Bar(fmt.parse("2015-07-28") ,44.740002,46.259998,44.619999,46.049999,277700L,46.049999));
        item.getBars().add(new Bar(fmt.parse("2015-07-27") ,43.82,44.790001,43.57,44.700001,241300L,44.700001));
        item.getBars().add(new Bar(fmt.parse("2015-07-24") ,44.880001,45.009998,43.959999,44.150002,326000L,44.150002));
        item.getBars().add(new Bar(fmt.parse("2015-07-23") ,43.830002,44.18,43.830002,44.07,349000L,44.07));
        item.getBars().add(new Bar(fmt.parse("2015-07-22") ,42.25,43.830002,42.130001,43.830002,369900L,43.830002));
        item.getBars().add(new Bar(fmt.parse("2015-07-21") ,43.25,43.740002,42.849998,42.869999,118500L,42.869999));
        item.getBars().add(new Bar(fmt.parse("2015-07-20") ,42.84,43.360001,42.389999,43.049999,234200L,43.049999));
        item.getBars().add(new Bar(fmt.parse("2015-07-17") ,43.900002,43.93,42.84,42.950001,215000L,42.950001));
        item.getBars().add(new Bar(fmt.parse("2015-07-16") ,44.34,44.369999,43.93,43.970001,282500L,43.970001));
        item.getBars().add(new Bar(fmt.parse("2015-07-15") ,44.290001,44.380001,44.009998,44.099998,219400L,44.099998));
        item.getBars().add(new Bar(fmt.parse("2015-07-14") ,43.52,44.380001,43.52,44.32,493700L,44.32));
        item.getBars().add(new Bar(fmt.parse("2015-07-13") ,44.25,44.25,43.27,43.549999,265500L,43.549999));
        item.getBars().add(new Bar(fmt.parse("2015-07-10") ,44.400002,44.549999,43.835999,43.93,174900L,43.93));
        item.getBars().add(new Bar(fmt.parse("2015-07-09") ,44.77,44.77,43.880001,43.959999,288600L,43.959999));
        item.getBars().add(new Bar(fmt.parse("2015-07-08") ,44.00,44.93,43.799999,44.080002,670900L,44.080002));
        item.getBars().add(new Bar(fmt.parse("2015-07-07") ,44.990002,45.060001,43.974998,44.200001,376300L,44.200001));
        item.getBars().add(new Bar(fmt.parse("2015-07-06") ,44.34,45.095001,43.456001,44.720001,382300L,44.720001));
        item.getBars().add(new Bar(fmt.parse("2015-07-02") ,45.00,45.00,44.049999,44.50,441400L,44.50));
        item.getBars().add(new Bar(fmt.parse("2015-07-01") ,45.119999,45.50,44.650002,44.810001,400400L,44.810001));
        item.getBars().add(new Bar(fmt.parse("2015-06-30") ,45.189999,45.282001,44.689999,44.900002,326200L,44.900002));
        item.getBars().add(new Bar(fmt.parse("2015-06-29") ,45.220001,45.549999,44.93,44.98,285100L,44.98));
        item.getBars().add(new Bar(fmt.parse("2015-06-26") ,46.060001,46.330002,45.380001,45.740002,447800L,45.740002));
        item.getBars().add(new Bar(fmt.parse("2015-06-25") ,46.07,46.34,45.50,46.220001,143200L,46.220001));
        item.getBars().add(new Bar(fmt.parse("2015-06-24") ,46.389999,46.48,45.860001,46.009998,242300L,46.009998));
        item.getBars().add(new Bar(fmt.parse("2015-06-23") ,46.66,46.939999,46.290001,46.580002,210300L,46.580002));
        item.getBars().add(new Bar(fmt.parse("2015-06-22") ,46.720001,47.009998,46.209999,46.650002,331900L,46.650002));
        item.getBars().add(new Bar(fmt.parse("2015-06-19") ,47.400002,47.415001,46.68,47.07,501300L,47.07));
        item.getBars().add(new Bar(fmt.parse("2015-06-18") ,47.00,47.779999,47.00,47.419998,133300L,47.419998));
        item.getBars().add(new Bar(fmt.parse("2015-06-17") ,47.419998,47.959999,47.259998,47.389999,173000L,47.389999));
        item.getBars().add(new Bar(fmt.parse("2015-06-16") ,46.25,47.66,46.25,47.389999,174500L,47.389999));
        item.getBars().add(new Bar(fmt.parse("2015-06-15") ,45.889999,46.529999,45.75,46.25,524100L,46.25));
        item.getBars().add(new Bar(fmt.parse("2015-06-12") ,46.419998,46.990002,46.279999,46.529999,213400L,46.529999));
        item.getBars().add(new Bar(fmt.parse("2015-06-11") ,47.07,47.77,47.02,47.240002,215100L,47.240002));
        item.getBars().add(new Bar(fmt.parse("2015-06-10") ,45.18,47.360001,45.09,46.919998,277000L,46.919998));
        item.getBars().add(new Bar(fmt.parse("2015-06-09") ,44.830002,45.380001,44.73,44.919998,335000L,44.919998));
        item.getBars().add(new Bar(fmt.parse("2015-06-08") ,45.150002,45.490002,44.970001,44.990002,191600L,44.990002));
        item.getBars().add(new Bar(fmt.parse("2015-06-05") ,45.130001,45.48,44.799999,45.279999,248300L,45.279999));
        item.getBars().add(new Bar(fmt.parse("2015-06-04") ,44.810001,45.446999,44.709999,45.330002,294400L,45.330002));
        item.getBars().add(new Bar(fmt.parse("2015-06-03") ,45.029999,45.919998,44.98,45.02,170800L,45.02));
        item.getBars().add(new Bar(fmt.parse("2015-06-02") ,45.00,45.490002,44.639999,45.00,311700L,45.00));
        item.getBars().add(new Bar(fmt.parse("2015-06-01") ,46.130001,46.360001,45.509998,45.759998,235300L,45.759998));
        item.getBars().add(new Bar(fmt.parse("2015-05-29") ,45.75,46.630001,45.439999,46.09,383900L,46.09));
        item.getBars().add(new Bar(fmt.parse("2015-05-28") ,46.060001,46.439999,46.029999,46.25,236600L,46.25));
        item.getBars().add(new Bar(fmt.parse("2015-05-27") ,46.25,46.490002,45.630001,46.259998,354900L,46.259998));
        item.getBars().add(new Bar(fmt.parse("2015-05-26") ,46.41,46.799999,45.00,45.220001,369000L,45.220001));
        item.getBars().add(new Bar(fmt.parse("2015-05-22") ,45.830002,46.790001,45.830002,46.700001,324000L,46.700001));
        item.getBars().add(new Bar(fmt.parse("2015-05-21") ,44.82,46.09,44.82,45.82,414600L,45.82));
        item.getBars().add(new Bar(fmt.parse("2015-05-20") ,45.119999,45.84,45.09,45.25,344800L,45.25));
        item.getBars().add(new Bar(fmt.parse("2015-05-19") ,45.34,45.50,44.959999,45.049999,250500L,45.049999));
        item.getBars().add(new Bar(fmt.parse("2015-05-18") ,43.810001,46.07,43.549999,45.41,739600L,45.41));
        item.getBars().add(new Bar(fmt.parse("2015-05-15") ,43.68,44.09,43.509998,43.970001,225800L,43.970001));
        item.getBars().add(new Bar(fmt.parse("2015-05-14") ,42.290001,43.630001,40.529999,43.48,976600L,43.48));
        item.getBars().add(new Bar(fmt.parse("2015-05-13") ,44.43,44.43,43.580002,43.939999,392400L,43.939999));
        item.getBars().add(new Bar(fmt.parse("2015-05-12") ,44.02,44.759998,43.689999,44.240002,381300L,44.240002));
        item.getBars().add(new Bar(fmt.parse("2015-05-11") ,45.50,45.50,44.00,44.41,301700L,44.41));
        item.getBars().add(new Bar(fmt.parse("2015-05-08") ,44.93,45.490002,44.84,44.98,92600L,44.98));
        item.getBars().add(new Bar(fmt.parse("2015-05-07") ,44.16,44.77,43.705002,44.50,205500L,44.50));
        item.getBars().add(new Bar(fmt.parse("2015-05-06") ,44.41,44.650002,43.75,44.299999,200000L,44.299999));
        item.getBars().add(new Bar(fmt.parse("2015-05-05") ,44.970001,45.48,43.73,44.18,244700L,44.18));
        item.getBars().add(new Bar(fmt.parse("2015-05-04") ,43.790001,45.209999,43.75,45.16,186100L,45.16));
        item.getBars().add(new Bar(fmt.parse("2015-05-01") ,44.080002,44.73,43.540001,43.740002,257500L,43.740002));
        item.getBars().add(new Bar(fmt.parse("2015-04-30") ,43.75,44.200001,43.424999,43.759998,332600L,43.759998));
        item.getBars().add(new Bar(fmt.parse("2015-04-29") ,44.060001,44.419998,43.650002,43.939999,362300L,43.939999));
        item.getBars().add(new Bar(fmt.parse("2015-04-28") ,44.310001,45.00,44.040001,44.25,283300L,44.25));
        item.getBars().add(new Bar(fmt.parse("2015-04-27") ,44.93,45.169998,44.200001,44.48,215500L,44.48));
        item.getBars().add(new Bar(fmt.parse("2015-04-24") ,45.450001,45.549999,44.700001,44.880001,141100L,44.880001));
        item.getBars().add(new Bar(fmt.parse("2015-04-23") ,44.43,46.549999,44.43,45.490002,421200L,45.490002));
        item.getBars().add(new Bar(fmt.parse("2015-04-22") ,43.860001,44.759998,43.439999,44.650002,154800L,44.650002));
        item.getBars().add(new Bar(fmt.parse("2015-04-21") ,44.139999,44.360001,43.259998,43.880001,345000L,43.880001));
        item.getBars().add(new Bar(fmt.parse("2015-04-20") ,44.349998,44.77,43.860001,43.970001,384700L,43.970001));
        item.getBars().add(new Bar(fmt.parse("2015-04-17") ,43.68,44.169998,43.349998,43.950001,709800L,43.950001));
        item.getBars().add(new Bar(fmt.parse("2015-04-16") ,44.669998,45.00,43.849998,43.959999,563800L,43.959999));
        item.getBars().add(new Bar(fmt.parse("2015-04-15") ,45.00,45.509998,44.494999,44.799999,875600L,44.799999));
        item.getBars().add(new Bar(fmt.parse("2015-04-14") ,44.419998,44.419998,43.580002,43.880001,317100L,43.880001));
        item.getBars().add(new Bar(fmt.parse("2015-04-13") ,44.040001,44.560001,44.00,44.509998,121400L,44.509998));
        item.getBars().add(new Bar(fmt.parse("2015-04-10") ,43.82,44.84,43.82,44.169998,124500L,44.169998));
        item.getBars().add(new Bar(fmt.parse("2015-04-09") ,44.080002,44.57,43.529999,43.740002,122200L,43.740002));
        item.getBars().add(new Bar(fmt.parse("2015-04-08") ,44.18,44.50,43.939999,44.169998,133200L,44.169998));
        item.getBars().add(new Bar(fmt.parse("2015-04-07") ,43.16,43.935001,43.080002,43.93,200800L,43.93));
        item.getBars().add(new Bar(fmt.parse("2015-04-06") ,42.799999,44.349998,42.799999,43.380001,319800L,43.380001));
        item.getBars().add(new Bar(fmt.parse("2015-04-02") ,43.279999,43.880001,43.139999,43.259998,566900L,43.259998));
        item.getBars().add(new Bar(fmt.parse("2015-04-01") ,42.759998,43.57,42.380001,43.529999,250200L,43.529999));
        item.getBars().add(new Bar(fmt.parse("2015-03-31") ,41.810001,43.509998,41.598,42.82,344600L,42.82));
        item.getBars().add(new Bar(fmt.parse("2015-03-30") ,42.130001,42.771999,41.720001,41.990002,561700L,41.990002));
        item.getBars().add(new Bar(fmt.parse("2015-03-27") ,42.00,42.619999,41.759998,41.939999,200000L,41.939999));
        item.getBars().add(new Bar(fmt.parse("2015-03-26") ,40.650002,42.259998,40.50,41.93,258700L,41.93));
        item.getBars().add(new Bar(fmt.parse("2015-03-25") ,42.040001,42.566002,41.049999,41.110001,348200L,41.110001));
        item.getBars().add(new Bar(fmt.parse("2015-03-24") ,42.720001,43.310001,42.389999,42.490002,237100L,42.490002));
        item.getBars().add(new Bar(fmt.parse("2015-03-23") ,43.91,44.380001,42.330002,42.75,636800L,42.75));
        item.getBars().add(new Bar(fmt.parse("2015-03-20") ,43.290001,44.34,43.290001,43.959999,949900L,43.959999));
        item.getBars().add(new Bar(fmt.parse("2015-03-19") ,42.150002,43.389999,42.150002,43.139999,185700L,43.139999));
        item.getBars().add(new Bar(fmt.parse("2015-03-18") ,41.509998,43.00,41.349998,42.419998,243500L,42.419998));
        item.getBars().add(new Bar(fmt.parse("2015-03-17") ,42.00,42.349998,41.27,41.48,335900L,41.48));
        item.getBars().add(new Bar(fmt.parse("2015-03-16") ,39.98,42.080002,39.880001,42.00,362000L,42.00));
        item.getBars().add(new Bar(fmt.parse("2015-03-13") ,40.580002,40.849998,39.75,40.150002,514700L,40.150002));
        item.getBars().add(new Bar(fmt.parse("2015-03-12") ,39.560001,41.330002,39.509998,40.419998,485800L,40.419998));
        item.getBars().add(new Bar(fmt.parse("2015-03-11") ,39.200001,41.00,39.200001,40.48,1026400L,40.48));
        item.getBars().add(new Bar(fmt.parse("2015-03-10") ,39.029999,39.529999,38.509998,39.349998,876700L,39.349998));
        item.getBars().add(new Bar(fmt.parse("2015-03-09") ,41.00,41.00,39.431999,39.560001,961900L,39.560001));
        item.getBars().add(new Bar(fmt.parse("2015-03-06") ,42.50,42.59,39.330002,41.009998,1247900L,41.009998));
        item.getBars().add(new Bar(fmt.parse("2015-03-05") ,43.18,43.490002,42.509998,42.59,624300L,42.59));
        item.getBars().add(new Bar(fmt.parse("2015-03-04") ,43.75,43.900002,42.919998,43.48,297800L,43.48));
        item.getBars().add(new Bar(fmt.parse("2015-03-03") ,42.900002,43.627998,42.799999,43.00,429600L,43.00));
        item.getBars().add(new Bar(fmt.parse("2015-03-02") ,43.66,44.439999,42.59,42.93,763100L,42.93));
        item.getBars().add(new Bar(fmt.parse("2015-02-27") ,44.110001,44.490002,43.139999,43.66,686400L,43.66));
        item.getBars().add(new Bar(fmt.parse("2015-02-26") ,44.799999,45.00,44.060001,44.450001,476700L,44.450001));
        item.getBars().add(new Bar(fmt.parse("2015-02-25") ,44.75,45.00,44.268002,44.509998,682300L,44.509998));
        item.getBars().add(new Bar(fmt.parse("2015-02-24") ,44.509998,45.240002,43.59,44.98,415000L,44.98));
        item.getBars().add(new Bar(fmt.parse("2015-02-23") ,45.049999,45.639999,44.490002,44.82,332800L,44.82));
        item.getBars().add(new Bar(fmt.parse("2015-02-20") ,43.75,45.720001,43.439999,45.639999,291500L,45.639999));
        item.getBars().add(new Bar(fmt.parse("2015-02-19") ,44.50,45.154999,43.799999,43.880001,651500L,43.880001));
        item.getBars().add(new Bar(fmt.parse("2015-02-18") ,43.419998,46.560001,43.066002,45.00,1283900L,45.00));
        item.getBars().add(new Bar(fmt.parse("2015-02-17") ,40.150002,43.34,39.599998,43.259998,779300L,43.259998));
        item.getBars().add(new Bar(fmt.parse("2015-02-13") ,43.279999,44.439999,39.919998,41.02,1837000L,41.02));
        item.getBars().add(new Bar(fmt.parse("2015-02-12") ,42.50,43.369999,42.049999,43.00,1753000L,43.00));
        item.getBars().add(new Bar(fmt.parse("2015-02-11") ,39.75,42.50,39.50,41.439999,2108100L,41.439999));
        item.getBars().add(new Bar(fmt.parse("2015-02-10") ,37.02,41.060001,37.00,39.299999,2201200L,39.299999));
        item.getBars().add(new Bar(fmt.parse("2015-02-06") ,38.00,38.549999,37.720001,37.720001,917800L,37.720001));

        return item;
    }
}
