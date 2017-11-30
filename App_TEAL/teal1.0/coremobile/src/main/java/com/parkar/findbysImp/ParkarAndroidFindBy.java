package com.parkar.findbysImp;

import io.appium.java_client.MobileBy;

import org.openqa.selenium.By;

public class ParkarAndroidFindBy implements IParkarFindBy {
	@Override
	public By buildFindBy(ParkarFindBy findBy) {
		if (!"".equals(findBy.androidClassName()))
			return By.className(findBy.androidClassName());

		if (!"".equals(findBy.androidId()))
			return By.id(findBy.androidId());

		if (!"".equals(findBy.androidTagName()))
			return By.tagName(findBy.androidTagName());

		if (!"".equals(findBy.androidXpath()))
			return By.xpath(findBy.androidXpath());

		if (!"".equals(findBy.androidAccessibility()))
			return MobileBy.AccessibilityId(findBy.androidAccessibility());

		if (!"".equals(findBy.androidUiAutomator()))
			return MobileBy.AndroidUIAutomator(findBy.androidUiAutomator());
		// Fall through
		return null;
	}

}
