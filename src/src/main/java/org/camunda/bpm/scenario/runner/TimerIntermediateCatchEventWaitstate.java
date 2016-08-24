package org.camunda.bpm.scenario.runner;


import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.scenario.Scenario;
import org.camunda.bpm.scenario.action.ScenarioAction;
import org.camunda.bpm.scenario.delegate.JobDelegate;

import java.util.Date;
import java.util.Map;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class TimerIntermediateCatchEventWaitstate extends JobDelegate {

  public TimerIntermediateCatchEventWaitstate(ProcessRunnerImpl runner, HistoricActivityInstance instance, String duration) {
    super(runner, instance, duration);
    if (duration != null) {
      throw new IllegalStateException("Found a duration '" + duration + "' set. " +
          "Explicit durations are not supported for '" + getClass().getSimpleName()
          + "'. Its duration always depends on the timer defined in the BPMN process.");
    }
  }

  @Override
  protected void execute() {
    ScenarioAction action = action(runner.scenario);
    if (action != null)
      action.execute(this);
    Job job = getManagementService().createJobQuery().timers().jobId(getId()).singleResult();
    if (job != null)
      getManagementService().executeJob(job.getId());
    runner.setExecutedHistoricActivityIds();
  }

  @Override
  protected Job getRuntimeDelegate() {
    return getManagementService().createJobQuery().timers().executionId(getExecutionId()).singleResult();
  }

  @Override
  protected ScenarioAction action(Scenario.Process scenario) {
    return scenario.atTimerIntermediateCatchEvent(getActivityId());
  }

  protected void leave() {
    getManagementService().executeJob(getRuntimeDelegate().getId());
  }

  protected void leave(Map<String, Object> variables) {
    getRuntimeService().setVariables(getProcessInstance().getId(), variables);
    leave();
  }

  @Override
  protected Date getEndTime() {
    return getDuedate();
  }

}
