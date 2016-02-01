package com.library.essay.services;

import java.util.List;

import com.library.essay.persistence.entities.Essay;

public interface EssayService {

	Essay getEssay(long id);

	List<Essay> getEssays();

	Essay saveOrUpdate(Essay essay);

	void delete(Essay essay);

	void deleteAll();

	void printRevisions(Long essayId);

	void printRevisionsAuditQuery(Long essayId);
}
