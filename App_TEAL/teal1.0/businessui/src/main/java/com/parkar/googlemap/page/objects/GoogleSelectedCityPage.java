package com.parkar.googlemap.page.objects;

import org.openqa.selenium.WebDriver;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import com.parkar.basepageobject.BaseUIPage;
import com.parkar.element.interfaces.IBaseButton;
import com.parkar.element.interfaces.IBaseElement;
import com.parkar.element.interfaces.IBaseLabel;
import com.parkar.element.interfaces.IBaseLink;
import com.parkar.enums.ParkarLocateVia;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.exception.ParkarCoreUIException;
import com.parkar.findbysImp.ParkarFindBy;

public class GoogleSelectedCityPage extends BaseUIPage {
	
	@ParkarFindBy(xpath = "//button[@class='widget-pane-section-header-directions noprint']", via = ParkarLocateVia.ByElementClickable)
	public IBaseButton directionBtn;
	
	@ParkarFindBy(xpath = "//div[@id='sb_ifc51']/input", via = ParkarLocateVia.ByElementClickable)
	public IBaseElement directionSearchBox;
	
	@ParkarFindBy(xpath = "//div[@id='directions-searchbox-0']/button[@class='searchbox-searchbutton']", via = ParkarLocateVia.ByElementClickable)
	public IBaseElement directionSearchBtn;
	
	@ParkarFindBy(xpath = "//div[@class='travel-mode'][2]", via = ParkarLocateVia.ByElementClickable)
	public IBaseLink busRouteLnk;
	
	@ParkarFindBy(xpath = "//div[@class='travel-mode'][3]", via = ParkarLocateVia.ByElementClickable)
	public IBaseLink walkRouteLnk;
	
	@ParkarFindBy(xpath = "//div[@class='travel-mode'][1]", via = ParkarLocateVia.ByElementClickable)
	public IBaseLink carRouteLnk;
	
	@ParkarFindBy(xpath = "//h1[contains(text(),'Paterson')]", via = ParkarLocateVia.ByElementVisible)
	public IBaseLabel patersonTxt;
	
	@ParkarFindBy(xpath = "//h1[contains(text(),'Liverpool')]", via = ParkarLocateVia.ByElementVisible)
	public IBaseLabel liverPoolTxt;
	
	
	 String liverPoolName = "resources\\images\\LiverPoolName.png";
	 String williamPatersonMap="resources\\images\\WilliamPatersonMap.png";
	 	 

	public GoogleSelectedCityPage(WebDriver driver)
			throws ParkarCoreCommonException {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public void hoverLocation() throws Exception{
		Pattern williamPatersonMapLoc = new Pattern(williamPatersonMap);
		Screen src = new Screen();
		src.setAutoWaitTimeout(30);
		src.find(williamPatersonMapLoc).highlight(3).hover();
		src.setAutoWaitTimeout(30);
		Thread.sleep(3000);
		}
	
	
	public void direction(String location) throws ParkarCoreUIException, InterruptedException{
		directionBtn.click();
		directionSearchBox.setText(location);
		directionSearchBtn.click();
	}
	
	public void selectCarRoute() throws InterruptedException, ParkarCoreUIException{
		carRouteLnk.click();
		Thread.sleep(5000);
	}
	
	public void selectBusRoute() throws InterruptedException, ParkarCoreUIException{
		busRouteLnk.click();
		Thread.sleep(5000);
	}
	
	public void selectWalkRoute() throws InterruptedException, ParkarCoreUIException{
		walkRouteLnk.click();
		Thread.sleep(5000);
	}
	
	
	
	
	
	

}
