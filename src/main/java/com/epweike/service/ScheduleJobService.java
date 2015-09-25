package com.epweike.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.epweike.model.ScheduleJob;

/**
 * 
 * @Description: 计划任务管理
 * @author wuxp
 * 
 */
@Service
public class ScheduleJobService extends BaseService<ScheduleJob> {
	public final Logger log = Logger.getLogger(this.getClass());

}
