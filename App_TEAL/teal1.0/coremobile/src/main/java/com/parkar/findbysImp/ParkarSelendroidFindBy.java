package com.parkar.findbysImp;

import org.openqa.selenium.By;

public class ParkarSelendroidFindBy implements IParkarFindBy {

	@Override
	public By buildFindBy(ParkarFindBy findBy) {
		if (!"".equals(findBy.selendroidClassName()))
			return By.className(findBy.selendroidClassName());

		if (!"".equals(findBy.selendroidId()))
			return By.id(findBy.selendroidId());

		if (!"".equals(findBy.selendroidLinkText()))
			return By.linkText(findBy.selendroidLinkText());

		if (!"".equals(findBy.selendroidName()))
			return By.name(findBy.selendroidName());

		if (!"".equals(findBy.selendroidPartialLinkText()))
			return By.partialLinkText(findBy.selendroidPartialLinkText());

		if (!"".equals(findBy.selendroidTagName()))
			return By.tagName(findBy.selendroidTagName());

		if (!"".equals(findBy.selendroidXpath()))
			return By.xpath(findBy.selendroidXpath());
		// Fall through
		return null;

	}

}
