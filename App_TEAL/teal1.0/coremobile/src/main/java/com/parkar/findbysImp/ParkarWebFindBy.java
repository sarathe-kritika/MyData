package com.parkar.findbysImp;

import org.openqa.selenium.By;

public class ParkarWebFindBy implements IParkarFindBy {

	@Override
	public By buildFindBy(ParkarFindBy findBy) {
		if (!"".equals(findBy.webClassName()))
			return By.className(findBy.webClassName());

		if (!"".equals(findBy.webCss()))
			return By.cssSelector(findBy.webCss());

		if (!"".equals(findBy.webId()))
			return By.id(findBy.webId());

		if (!"".equals(findBy.webLinkText()))
			return By.linkText(findBy.webLinkText());

		if (!"".equals(findBy.webName()))
			return By.name(findBy.webName());

		if (!"".equals(findBy.webPartialLinkText()))
			return By.partialLinkText(findBy.webPartialLinkText());

		if (!"".equals(findBy.webTagName()))
			return By.tagName(findBy.webTagName());

		if (!"".equals(findBy.webXpath()))
			return By.xpath(findBy.webXpath());
		// Fall through
		return null;
	}

}
