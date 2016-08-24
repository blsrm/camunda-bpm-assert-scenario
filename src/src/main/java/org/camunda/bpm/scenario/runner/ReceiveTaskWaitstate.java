package org.camunda.bpm.scenario.runner;


import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.scenario.Scenario;
import org.camunda.bpm.scenario.action.ScenarioAction;

import java.util.Map;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ReceiveTaskWaitstate extends MessageIntermediateCatchEventWaitstate {

  public ReceiveTaskWaitstate(ProcessRunnerImpl runner, HistoricActivityInstance instance, String duration) {
    super(runner, instance, duration);
  }

  @Override
  protected ScenarioAction action(Scenario.Process scenario) {
    return scenario.atReceiveTask(getActivityId());
  }

  @Override
  public void receiveMessage() {
    super.receiveMessage();
  }

  @Override
  public void receiveMessage(Map<String, Object> variables) {
    super.receiveMessage(variables);
  }

}
