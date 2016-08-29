package org.camunda.bpm.scenario.action;


/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public interface ScenarioAction<D> {

  void execute(final D runtimeDelegate) throws Exception;

}
