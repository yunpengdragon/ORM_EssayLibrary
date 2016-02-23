package com.library.essay.application;

import java.util.List;

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
		Essay myEssay = essayService.getEssay(savedEssay.getId());

		System.out.println("====================essayService.getEssay()========================");
		System.out.println(myEssay);

		for (int i = 0; i < 5; i++) {
			Essay iEssay = new Essay();
			iEssay.setTitle("test title " + i);
			iEssay.setAuthor("test author " + i);
			iEssay.setContent("test content " + i);

			essayService.saveOrUpdate(iEssay);
		}

		List<Essay> essayList = essayService.getEssays();
		System.out.println("====================essayService.getEssays()========================");
		for (Essay e : essayList) {
			System.out.println(e);
		}
		
		List<Essay> essayList2 = essayService.getEssaysCriteriaQuery();
		System.out.println("====================essayService.getEssaysCriteriaQuery()========================");
		for (Essay e : essayList2) {
			System.out.println(e);
		}

		essayService.delete(myEssay);
		essayList = essayService.getEssays();
		System.out.println("===============After essayService.delete(), essayService.getEssays()===================");
		for (Essay e : essayList) {
			System.out.println(e);
		}

		essayService.deleteAll();
		essayList = essayService.getEssays();
		System.out.println("=================After essayService.deleteAll(), essayService.getEssays()===============");
		for (Essay e : essayList) {
			System.out.println(e);
		}

	}

}
