package m3.uf5.ticketing;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import javafx.util.StringConverter;

public class Formatters {
    public static final int CC_NUM_SIZE = 16;
    public static final int EXT_NUM_SIZE = 4;

    public static StringConverter<Integer> getExtensioFormatter() {
	StringConverter<Integer> formatter = new StringConverter<>() {
	    @Override
	    public Integer fromString(String string) {
		string = string.replace(" ", "");
		NumberFormat nf = NumberFormat.getIntegerInstance(new Locale("CA", "ES"));
		int value;
		try {
		    value = nf.parse(string).intValue();
		} catch (ParseException e) {
		    // e.printStackTrace();
		    value = 0;
		}
		return value;
	    }

	    @Override
	    public String toString(Integer object) {
		if (object == null) return "N/F";

		return StringUtils.leftPad(String.valueOf(object), EXT_NUM_SIZE, "0").replaceFirst("(\\d{2})(\\d+)",
			"$1 $2");
	    }
	};
	return formatter;
    }

    public static StringConverter<Long> getCreditCardFormatter() {
	StringConverter<Long> formatter = new StringConverter<>() {
	    @Override
	    public Long fromString(String string) {
		string = string.replace(" ", "");
		NumberFormat nf = NumberFormat.getIntegerInstance(new Locale("CA", "ES"));
		Long value;
		try {
		    value = nf.parse(string).longValue();
		} catch (ParseException e) {
		    // e.printStackTrace();
		    value = 0L;
		}
		return value;
	    }

	    @Override
	    public String toString(Long object) {
		if (object == null) return "N/F";

		return StringUtils.leftPad(String.valueOf(object), CC_NUM_SIZE, "0")
			.replaceFirst("(\\d{4})(\\d{4})(\\d{4})(\\d+)", "$1 $2 $3 $4");
	    }
	};
	return formatter;
    }

    public static StringConverter<LocalDate> getCreditCardDateFormatter(String pattern) {
	DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern(pattern);

	StringConverter<LocalDate> formatter = new StringConverter<>() {
	    @Override
	    public LocalDate fromString(String string) {
		string = string.replace(" ", "");

		return LocalDateTime.parse(string, dtFormatter).toLocalDate();
	    }

	    @Override
	    public String toString(LocalDate object) {
		if (object == null) return "N/F";

		return object.format(dtFormatter);
	    }
	};
	return formatter;
    }

    public static StringConverter<Integer> getIntegerFormatter() {
	NumberFormat nf = NumberFormat.getIntegerInstance(new Locale("CA", "ES"));

	StringConverter<Integer> formatter = new StringConverter<>() {
	    @Override
	    public Integer fromString(String string) {
		Integer value;
		try {
		    value = nf.parse(string).intValue();
		} catch (ParseException e) {
		    // e.printStackTrace();
		    value = 0;
		}
		return value;
	    }

	    @Override
	    public String toString(Integer object) {
		return (object == null) ? nf.format(0) : nf.format(object);
	    }
	};
	return formatter;
    }

    public static StringConverter<Double> getDecimalFormatter() {
	DecimalFormat df = new DecimalFormat("#0.0");

	StringConverter<Double> formatter = new StringConverter<>() {
	    @Override
	    public Double fromString(String string) {
		Double value;
		try {
		    // value = nf.parse(string).doubleValue();
		    value = df.parse(string).doubleValue();
		} catch (ParseException e) {
		    // e.printStackTrace();
		    value = 0.0;
		}
		return value;
	    }

	    @Override
	    public String toString(Double object) {
		return (object == null) ? df.format(0.0) : df.format(object);
	    }
	};
	return formatter;
    }

    public static StringConverter<Double> getModedaFormatter() {
	StringConverter<Double> formatter = new StringConverter<>() {
	    @Override
	    public Double fromString(String string) {
		NumberFormat nf = NumberFormat.getInstance(new Locale("es", "CA"));

		Double value;
		try {
		    value = nf.parse(string).doubleValue();
		} catch (ParseException e) {
		    value = 0.0;
		}
		return value;
	    }

	    @Override
	    public String toString(Double object) {
		DecimalFormat df = new DecimalFormat("###,##0.00€");

		return (object == null) ? df.format(0.0) : df.format(object);
	    }
	};
	return formatter;
    }
}
