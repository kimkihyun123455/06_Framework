package edu.kh.project.calendar.model.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CalendarServiceImpl implements CalendarService{


}
