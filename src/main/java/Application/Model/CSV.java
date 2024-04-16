package Application.Model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

// CSV class to load course offering details from CSV file
public class CSV {
    public List<Offering> loadListFromCsv(Path csvPath) throws IOException {
        List<Offering> offeringList = new ArrayList<>();
        if (Files.exists(csvPath)) {
            offeringList = Files.lines(csvPath)
                    .skip(1)
                    .map(course -> {
                        String[] line = course.split("\"");
                        if (line.length == 1) {
                            String[] values = course.split(",");
                            return new Offering(Integer.parseInt(values[0]),
                                    values[1].trim(),
                                    values[2].trim(),
                                    values[3].trim(),
                                    Integer.parseInt(values[4]),
                                    Integer.parseInt(values[5]),
                                    Collections.singletonList(values[6].trim()),
                                    values[7].trim()
                            );
                        } else {
                            String[] values = line[0].split(",");
                            return new Offering(Integer.parseInt(values[0]),
                                    values[1].trim(),
                                    values[2].trim(),
                                    values[3].trim(),
                                    Integer.parseInt(values[4]),
                                    Integer.parseInt(values[5]),
                                    List.of(line[1].trim()),
                                    line[2].substring(1).trim()
                            );
                        }
                    }).collect(Collectors.toList());
        }
        return offeringList;
    }
}