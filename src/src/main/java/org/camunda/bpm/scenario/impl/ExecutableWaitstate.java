package org.camunda.bpm.scenario.impl;

import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.impl.calendar.DurationHelper;
import org.camunda.bpm.engine.impl.util.ClockUtil;
import org.camunda.bpm.scenario.Scenario;
import org.camunda.bpm.scenario.action.DeferredAction;
import org.camunda.bpm.scenario.action.ScenarioAction;
import org.camunda.bpm.scenario.delegate.ProcessInstanceDelegate;
import org.camunda.bpm.scenario.impl.delegate.ProcessInstanceDelegateImpl;

import java.util.Date;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public abstract class ExecutableWaitstate<I> extends AbstractExecutable<I> {

  protected HistoricActivityInstance historicDelegate;

  protected ExecutableWaitstate(ProcessRunnerImpl runner, HistoricActivityInstance instance) {
    super(runner);
    this.historicDelegate = instance;
    this.delegate = getDelegate();
  }

  public ProcessInstanceDelegate getProcessInstance() {
    return ProcessInstanceDelegateImpl.newInstance(this, runner.processInstance);
  };

  @Override
  public String getExecutionId() {
    return historicDelegate.getExecutionId();
  }

  public String getActivityId() {
    return historicDelegate.getActivityId();
  }

  public void execute() {
    ScenarioAction action = action(runner.scenario);
    if (action == null)
      throw new AssertionError("Process Instance {"
          + getProcessInstance().getProcessDefinitionId() + ", "
          + getProcessInstance().getProcessInstanceId() + "} "
          + "waits at an unexpected " + getClass().getSimpleName().substring(0, getClass().getSimpleName().length() - 9)
          + " '" + historicDelegate.getActivityId() +"'.");
    ClockUtil.setCurrentTime(isExecutableAt());
    try {
      action.execute(this);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    runner.setExecuted(historicDelegate.getId());
  }

  protected abstract ScenarioAction action(Scenario.Process scenario);

  public Date isExecutableAt() {
    return historicDelegate.getStartTime();
  }

  public void defer(String period, DeferredAction action) {
    Executable.Deferred.newInstance(runner, historicDelegate, period, action);
  }

}
