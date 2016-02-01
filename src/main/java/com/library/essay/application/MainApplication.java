package com.library.essay.application;

import com.library.essay.persistence.entities.Essay;
import com.library.essay.services.EssayService;
import com.library.essay.services.EssayServiceImp;

public class MainApplication {

	public static void main(String[] args) {

		EssayService essayService = new EssayServiceImp();

		Essay essay = new Essay();
		essay.setTitle("my title");
		essay.setAuthor("my author");
		essay.setContent("my content");

		Essay savedEssay = essayService.saveOrUpdate(essay);

		savedEssay.setAuthor("Yunpeng");
		savedEssay = essayService.saveOrUpdate(savedEssay);

		savedEssay.setTitle("Human Evolution");
		savedEssay = essayService.saveOrUpdate(savedEssay);

		Long savedEssayId = savedEssay.getId();

		Essay myEssay = essayService.getEssay(savedEssayId);

		System.out.println("====================essayService.getEssay()========================");
		System.out.println(myEssay);

		essayService.delete(savedEssay);

		essayService.printRevisions(savedEssayId);

		essayService.printRevisionsAuditQuery(savedEssayId);

		//essayService.deleteAll();

	}

}
