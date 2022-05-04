package risu729.kaeruproblem.data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class Output {
  private static final String DATE_FORMAT = "yyyy-MM-dd-HH-mm-ss";

  private final Settings settings;
  private final String createdTime;
  private List<Result> results;

  public Output(final Settings settings) {
    this.settings = settings;
    createdTime = new SimpleDateFormat(DATE_FORMAT)
        .format(Calendar.getInstance().getTime());
    results = new LinkedList<Result>();
  }

  public void addResult(final Result result) {
    results.add(result);
  }

  public Settings getSettings() {
    return settings;
  }

  public String getCreatedTime() {
    return createdTime;
  }

  public List<Result> getResults() {
    return results;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    return (obj instanceof Output other)
        && settings.equals(other.settings)
        && createdTime.equals(other.createdTime)
        && results.equals(other.results);
  }

  @Override
  public int hashCode() {
    int hash = 1;
    hash = hash * 31 + settings.hashCode();
    hash = hash * 31 + createdTime.hashCode();
    hash = hash * 31 + results.hashCode();
    return hash;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName()
        + "[settings=" + settings.toString()
        + ", createdTime=" + createdTime
        + ", results=" + results.toString() + "]";
  }
}