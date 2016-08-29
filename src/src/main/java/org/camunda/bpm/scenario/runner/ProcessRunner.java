package org.camunda.bpm.scenario.runner;

import org.camunda.bpm.engine.ProcessEngine;

import java.util.Map;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public interface ProcessRunner {

  interface ExecutableRunner {

    ExecutableRunner engine(ProcessEngine processEngine);

    ScenarioRun execute();

    interface StartingByKey extends ExecutableRunner {

      StartingByKey fromBefore(String activityId);

      StartingByKey fromAfter(String activityId);

      ScenarioRun execute();

    }

    interface StartingByStarter extends ExecutableRunner {

      ScenarioRun execute();

    }

  }

  interface ToBeStartedBy {

    ExecutableRunner.StartingByKey startByKey(String processDefinitionKey);

    ExecutableRunner.StartingByKey startByKey(String processDefinitionKey, Map<String, Object> variables);

    ExecutableRunner.StartingByStarter startBy(ProcessStarter starter);

  }

}
