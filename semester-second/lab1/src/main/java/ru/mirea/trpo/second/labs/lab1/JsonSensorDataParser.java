package ru.mirea.trpo.second.labs.lab1;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class JsonSensorDataParser {

    private static final String NEW_LINE = "\n";
    private static final String ONE_INDENT = "    ";
    private static final String TWO_INDENT = "        ";

    private static final String TIME_HEADER = "time";
    private static final String QC_REGEX = "[a-z_]+_qc";
    private static final int DEFAULT_TIME_INDEX = 3;

    private int timeIndex = DEFAULT_TIME_INDEX;
    private List<String> params;
    private File file;

    public JsonSensorDataParser(File file, List<String> params) {
        this.file = file;
        this.params = params;
    }

    public JsonSensorDataParser(File file) {
        this.file = file;
        this.params = null;
    }

    public String parse() {
        JSONParser parser = new JSONParser();
        JSONObject object;
        try {
            object = (JSONObject) parser.parse(new FileReader(file));
        } catch(IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }
        JSONObject table = (JSONObject) object.get("table");
        JSONArray columns = (JSONArray) table.get("columnNames");
        JSONArray rows = (JSONArray) table.get("rows");
        List<Param> params = resolveParams(columns);
        parseSensors(params, rows);
        return format(params);
    }

    private String format(List<Param> params) {
        StringBuilder sb = new StringBuilder();
        Iterator<Param> iter = params.iterator();
        sb.append("{").append(NEW_LINE);
        while (iter.hasNext()) {
            Param param = iter.next();
            sb.append(ONE_INDENT).append("\"").append(param.name).append("\": {").append(NEW_LINE);
            sb.append(TWO_INDENT).append("\"start_date\": \"").append(param.calculator.start.toLocalDate()).append("\",").append(NEW_LINE);
            sb.append(TWO_INDENT).append("\"end_date\": \"").append(param.calculator.end.toLocalDate()).append("\",").append(NEW_LINE);
            sb.append(TWO_INDENT).append("\"num_records\": ").append(param.calculator.records).append(",").append("\n");
            sb.append(TWO_INDENT).append("\"min_").append(param.name).append("\": ").append(param.calculator.min).append(",").append(NEW_LINE);
            sb.append(TWO_INDENT).append("\"min_time\": ").append(param.calculator.minTime).append(",").append(NEW_LINE);
            sb.append(TWO_INDENT).append("\"max_").append(param.name).append("\": ").append(param.calculator.max).append(",").append(NEW_LINE);
            sb.append(TWO_INDENT).append("\"max_time\": ").append(param.calculator.maxTime).append(",").append(NEW_LINE);
            sb.append(TWO_INDENT).append("\"avg_").append(param.name).append("\": ").append(param.calculator.avg).append(NEW_LINE);
            if (iter.hasNext())
                sb.append(ONE_INDENT).append("},").append(NEW_LINE);
            else
                sb.append(ONE_INDENT).append("}").append(NEW_LINE);
        }
        sb.append("}");
        return sb.toString();
    }

    private void parseSensors(List<Param> params, JSONArray rows) {
        for (Object objRow : rows) {
            JSONArray row = (JSONArray) objRow;
            parseRow(row, params);
        }
    }

    private void parseRow(JSONArray row, List<Param> params) {
        String stringTime = (String) row.get(timeIndex);
        LocalDateTime time = LocalDateTime.parse(stringTime, DateTimeFormatter.ISO_DATE_TIME);
        for (Param param : params) {
            int index = param.index;
            if (((Long) row.get(index + 1)) != 0) {
                param.addTimeOnly(time);
            }
            else {
                Double floatValue = (Double) row.get(index);
                param.addValue(floatValue.floatValue(), time);
            }
        }
    }

    private List<Param> resolveParams(JSONArray columns) {
        if (this.params == null) return getAllParams(columns);
        else return getFilteredParams(columns);
    }

    private List<Param> getFilteredParams(JSONArray columns) {
        ListIterator iter = columns.listIterator();
        List<Param> params = new ArrayList<>();
        while (iter.hasNext()) {
            String currentName = (String) iter.next();
            if (currentName.matches(QC_REGEX) && iter.hasPrevious()) {
                iter.previous();
                int index = iter.previousIndex();
                String paramName = (String) iter.previous();
                if (this.params.contains(paramName))
                    params.add(new Param(paramName, index));
                iter.next();
                iter.next();
            }
            else if (currentName.equals(TIME_HEADER)) {
                timeIndex = iter.previousIndex();
            }

        }
        return params;
    }

    private List<Param> getAllParams(JSONArray columns) {
        ListIterator iter = columns.listIterator();
        List<Param> params = new ArrayList<>();
        while (iter.hasNext()) {
            String currentName = (String) iter.next();
            if (currentName.matches(QC_REGEX) && iter.hasPrevious()) {
                iter.previous();
                int index = iter.previousIndex();
                String paramName = (String) iter.previous();
                params.add(new Param(paramName, index));
                iter.next();
                iter.next();
            }
            else if (currentName.equals(TIME_HEADER)) {
                timeIndex = iter.previousIndex();
            }
        }
        return params;
    }

    private class Param {

        private ValueCalculator calculator = new ValueCalculator();

        private String name;
        private int index;

        Param(String name, int index) {
            this.name = name;
            this.index = index;
        }

        @Override
        public String toString() {
            return "Param{" +
                    "name='" + name + '\'' +
                    ", index=" + index +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Param param = (Param) o;
            return index == param.index && (name != null ? name.equals(param.name) : param.name == null);
        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + index;
            return result;
        }

        void addValue(Float value, LocalDateTime time) {
            calculator.addValue(value, time);
        }

        void addTimeOnly(LocalDateTime time) {
            calculator.addTime(time);
        }

    }

    private class ValueCalculator {

        private Float min;
        private Float max;
        private Float avg = 0.0f;
        private LocalDateTime minTime;
        private LocalDateTime maxTime;
        private LocalDateTime start;
        private LocalDateTime end;
        private int recordsWithValue = 0;
        private int records = 0;

        void addValue(Float newValue, LocalDateTime time) {
            if (max == null || newValue.compareTo(max) > 0) {
                max = newValue;
                maxTime = time;
            }
            if (min == null || newValue.compareTo(min) < 0) {
                min = newValue;
                minTime = time;
            }
            Float oldSum = avg * recordsWithValue;
            recordsWithValue++;
            avg = (oldSum + newValue) / recordsWithValue;
            addTime(time);
        }

        void addTime(LocalDateTime time) {
            if (start == null || start.isAfter(time)) {start = time;}
            if (end == null || end.isBefore(time)) {end = time;}
            records++;
        }

    }

}
