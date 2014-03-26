package com.itu.checkin.client;

import org.springframework.context.support.GenericXmlApplicationContext;

import com.itu.checkin.model.entity.user.type.UserCorporate;
import com.itu.checkin.service.serviceinterface.UserCorporateService;

public class TestMain {

	public static void main(String[] args) {
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath:/META-INF/spring/app-context.xml");
		ctx.refresh();

		UserCorporateService eventTypeService = ctx.getBean(
				"userCorporateService", UserCorporateService.class);
		UserCorporate userIndividual = eventTypeService.findById(1);
		userIndividual.getUserBase().setSurname("Kızıltepe");
		eventTypeService.save(userIndividual);

	}
	//
	// private static void writeAll(List<? extends Object> objectList) {
	// for (Object object : objectList) {
	// System.out.println(object);
	// }
	// }
	//
	// private static void writeOne(Object oneObject) {
	// System.out.println(oneObject);
	//
	// }
}
