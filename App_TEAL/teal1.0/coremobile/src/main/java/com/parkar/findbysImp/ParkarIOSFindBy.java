package com.parkar.findbysImp;

import io.appium.java_client.MobileBy;

import org.openqa.selenium.By;

public class ParkarIOSFindBy implements IParkarFindBy {

	@Override
	public By buildFindBy(ParkarFindBy findBy) {
		if (!"".equals(findBy.iosClassName()))
			return By.className(findBy.iosClassName());

		if (!"".equals(findBy.iosId()))
			return By.id(findBy.iosId());

		if (!"".equals(findBy.iosTagName()))
			return By.tagName(findBy.iosTagName());

		if (!"".equals(findBy.iosXpath()))
			return By.xpath(findBy.iosXpath());

		if (!"".equals(findBy.iosAccessibility()))
			return MobileBy.AccessibilityId(findBy.iosAccessibility());

		if (!"".equals(findBy.iosUiAutomator()))
			return MobileBy.AndroidUIAutomator(findBy.iosUiAutomator());
		// Fall through
		return null;

	}

}
