package org.camunda.bpm.specs;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.runtime.ProcessInstance;

import java.util.Map;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ScenarioRunner {

  private Scenario scenario;
  private ProcessEngine processEngine;

  public ScenarioRunner(Scenario scenario) {
    this.scenario = scenario;
    Map<String, ProcessEngine> processEngines = ProcessEngines.getProcessEngines();
    if (processEngines.size() == 1) {
      processEngine = processEngines.values().iterator().next();
    }
    String message = processEngines.size() == 0 ? "No ProcessEngine found to be " +
        "registered with " + ProcessEngines.class.getSimpleName() + "!"
        : String.format(processEngines.size() + " ProcessEngines initialized. " +
        "Explicitely initialise engine by calling " + ScenarioRunner.class.getSimpleName() +
        "(scenario, engine)");
    throw new IllegalStateException(message);
  }

  public ScenarioRunner(Scenario scenario, ProcessEngine processEngine) {
    this.scenario = scenario;
    this.processEngine = processEngine;
  }

  public ScenarioRunner fromStart() {
    return this;
  }

  public ScenarioRunner fromBefore(String activityId, String... activityIds) {
    return this;
  }

  public ScenarioRunner fromAfter(String activityId, String... activityIds) {
    return this;
  }

  public ScenarioRunner from(StartAction action) {
    return this;
  }

  public ScenarioRunner toBefore(String activityId, String... activityIds) {
    return this;
  }

  public ScenarioRunner toAfter(String activityId, String... activityIds) {
    return this;
  }

  public ScenarioRunner toEnd() {
    return this;
  }

  public ProcessInstance run() { return null; }

}
