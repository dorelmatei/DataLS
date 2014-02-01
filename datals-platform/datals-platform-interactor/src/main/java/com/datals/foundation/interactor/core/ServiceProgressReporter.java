package com.datals.foundation.interactor.core;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datals.foundation.interactor.api.ProgressReporter;
import com.datals.foundation.interactor.api.TaskContext;
import com.datals.foundation.interactor.api.TaskResult;
import com.datals.foundation.interactor.api.TaskState;
import com.datals.foundation.interactor.api.TaskStatus;
import com.google.inject.Inject;
import com.intersections.ibis.runtime.assertion.ContractAssert;

/**
 * Reports service progress activity from start to end of service operation.
 * It is mandatory that the report to be called from the same thread that started the service execution.
 * 
 * @author <a href="mailto:dorel.matei@threepillarglobal.com">Dorel Matei</a>
 */
public class ServiceProgressReporter implements ProgressReporter {

	private static final Logger log = LoggerFactory.getLogger(ServiceProgressReporter.class);
	public static final int PERCENT_WORK_UNITS = 100;

	@Inject
	private TaskContextStore taskContextStore;
	
	@Inject
	private ServiceInteractor serviceInteractor;

	public void reportBegin(int workUnits) {
		log.debug("Report begin : " + workUnits);
		ContractAssert.preCondition(workUnits > 0, "workUnits is 0 or negative");
		TaskContext ctx = serviceInteractor.getCurrentTaskContext();
		TaskStatus status = ctx.getTaskStatus();
		synchronized (status) {
			if (!status.isTerminal()) {
				status.setTaskState(TaskState.RUNNING);
				status.setWorkUnits(workUnits);
				status.setWorkDone(0);
				status.setUpdateDate(new Date());
				taskContextStore.enqueueTaskContext(ctx);
				log.debug("Report begin taskContext enqueued : " + ctx);
			}
		}

	}

	public void reportWork(int workUnits, int workDone) {
		log.debug("Report work : " + workUnits + ":" + workDone);
		ContractAssert.preCondition(workUnits >= workDone, "workUnits smaller than workDone");
		TaskContext ctx = serviceInteractor.getCurrentTaskContext();
		TaskStatus status = ctx.getTaskStatus();
		synchronized (status) {
			if (!status.isTerminal()) {
				status.setTaskState(TaskState.RUNNING);
				status.setWorkUnits(workUnits);
				status.setWorkDone(workDone);
				status.setUpdateDate(new Date());
				taskContextStore.enqueueTaskContext(ctx);
				log.debug("Report work taskContext enqueued : " + ctx);
			}
		}

	}

	public void reportComplete(int workUnits, int workDone, String resultLocation) {
		log.debug("Report complete : " + workUnits + ":" + workDone + ":" + resultLocation);
		ContractAssert.preCondition(workUnits >= workDone, "workUnits smaller than workDone");
		ContractAssert.preCondition(resultLocation != null, "result is null");
		TaskContext ctx = serviceInteractor.getCurrentTaskContext();
		TaskStatus status = ctx.getTaskStatus();
		synchronized (status) {
			if (!status.isTerminal()) {
				status.setTaskState(TaskState.COMPLETE);
				status.setWorkUnits(workUnits);
				status.setWorkDone(workDone);
				status.setUpdateDate(new Date());
				TaskResult taskResult = ctx.getTaskResult();
				taskResult.setResultLocation(resultLocation);
				taskContextStore.enqueueTaskContext(ctx);
				log.debug("Report complete taskContext enqueued : " + ctx);
			}
		}
	}
	
	public void reportCancel(int workUnits, int workDone) {
		log.debug("Report cancel : " + workUnits + ":" + workDone);
		ContractAssert.preCondition(workUnits >= workDone, "workUnits smaller than workDone");
		TaskContext ctx = serviceInteractor.getCurrentTaskContext();
		TaskStatus status = ctx.getTaskStatus();
		synchronized (status) {
			if (!status.isTerminal()) {
				status.setTaskState(TaskState.CANCELED);
				status.setUpdateDate(new Date());
				status.setWorkUnits(workUnits);
				status.setWorkDone(workDone);
				taskContextStore.enqueueTaskContext(ctx);
				log.debug("Report cancel taskContext enqueued : " + ctx);
			}
		}
	}

	public void reportError(int workUnits, int workDone, String errorMessage) {
		log.debug("Report error : " + workUnits + ":" + workDone + ":" + errorMessage);
		ContractAssert.preCondition(workUnits >= workDone, "workUnits smaller than workDone");
		ContractAssert.preCondition(errorMessage != null, "errorMessage is null");
		TaskContext ctx = serviceInteractor.getCurrentTaskContext();
		TaskStatus status = ctx.getTaskStatus();
		synchronized (status) {
			if (!status.isTerminal()) {
				status.setTaskState(TaskState.ERROR);
				ctx.getTaskResult().setError(errorMessage);
				status.setWorkUnits(workUnits);
				status.setWorkDone(workDone);
				status.setUpdateDate(new Date());
				taskContextStore.enqueueTaskContext(ctx);
				log.debug("Report error taskContext enqueued : " + ctx);
			}
		}
	}

}
