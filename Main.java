import java.io.IOException;
import java.io.UncheckedIOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import risu.kaeruproblem.data.Output;
import risu.kaeruproblem.data.Result;
import risu.kaeruproblem.data.Settings;
import risu.kaeruproblem.Experiment;
import risu.kaeruproblem.Formula;
import risu.util.ProcessingTime;

class Main {
  public static void main(String[] args) {
    var processingTime = new ProcessingTime();

    var settings = new Settings();
    try {
      settings = new Gson().fromJson(Files.readString(Path.of("Settings.json")), Settings.class);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
    settings.checkFormat();

    var output = new Output(settings);

    for (int a = settings.getAMin(); a <= settings.getAMax(); a++) {
      for (int n = settings.getNMin(); n <= settings.getNMax(); n++) {
        BigDecimal experiment = Experiment.expt(a, n, settings.getTimes());
        BigDecimal formula = Formula.calc(a, n);
        output.addResult(new Result(a, n, experiment, formula));
      }
    }

    try {
      Files.writeString(Path.of("results", output.getCreatedTime() + ".json"),
          new GsonBuilder().setPrettyPrinting().create().toJson(output));
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
    processingTime.printTime();
  }
}
