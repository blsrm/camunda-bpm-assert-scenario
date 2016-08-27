package org.camunda.bpm.scenario.impl;

import org.camunda.bpm.engine.impl.util.ClockUtil;
import org.camunda.bpm.engine.runtime.Job;

import java.util.Date;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ExecutableTimerJob extends ExecutableJob {

  public ExecutableTimerJob(ProcessRunnerImpl runner, Job job) {
    super(runner, job);
  }

  @Override
  public Date isExecutableAt() {
    return runtimeDelegate.getDuedate();
  }

  @Override
  public void execute() {
    // TODO (Camunda Bug?)
    ClockUtil.setCurrentTime(new Date(isExecutableAt().getTime() + 1));
    leave();
  }

}
